package com.example.gifeye.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gifeye.R
import com.example.gifeye.databinding.FragmentGifListBinding
import com.example.gifeye.presentation.adapter.GifAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GifListFragment : Fragment() {

    private var _binding: FragmentGifListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GifListViewModel by viewModels()
    private lateinit var gifAdapter: GifAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGifListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gifAdapter = GifAdapter(
            emptyList(),
            onDeleteClick = { id -> viewModel.deleteGif(id) },
            onItemClick = { position -> openGifFullScreen(position) }
        )
        binding.recyclerViewGifList.adapter = gifAdapter

        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        binding.recyclerViewGifList.apply {
            this.layoutManager = layoutManager
            adapter = gifAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        Log.d("GifListFragment", "End of list reached, loading next page...")
                        viewModel.fetchNextPage()
                    }
                }
            })
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.gifs.collectLatest { gifs ->
                Log.d("GifListFragment", "New GIFs received, size: ${gifs.size}")
                gifAdapter.updateGifs(gifs)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                Log.d("GifListFragment", "Loading state changed: $isLoading")
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                Log.d(
                    "GifListFragment",
                    "Progress bar visibility set to ${binding.progressBar.visibility == View.VISIBLE}"
                )
            }
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString()
            if (query.isNotBlank()) {
                Log.d("GifListFragment", "New search initiated with query: $query")
                viewModel.searchGifs(query)
            } else {
                Toast.makeText(context, "Please enter a search query.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGifFullScreen(position: Int) {
        binding.progressBar.visibility = View.GONE
        Log.d("GifListFragment", "Progress bar hidden before opening full screen")

        val fragment = GifFullScreenFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("gifList", ArrayList(viewModel.gifs.value))
                putInt("position", position)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}