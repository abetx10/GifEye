package com.example.gifeye.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.gifeye.databinding.FragmentGifFullScreenBinding
import com.example.gifeye.data.model.GifData
import com.example.gifeye.presentation.adapter.GifPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifFullScreenFragment : Fragment() {

    private var _binding: FragmentGifFullScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var gifPagerAdapter: GifPagerAdapter
    private var initialPosition: Int = 0
    private var gifList: List<GifData> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGifFullScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialPosition = arguments?.getInt("position") ?: 0
        gifList = arguments?.getParcelableArrayList("gifList") ?: emptyList()

        gifPagerAdapter = GifPagerAdapter()
        binding.viewPager.adapter = gifPagerAdapter

        gifPagerAdapter.submitList(gifList)
        binding.viewPager.setCurrentItem(initialPosition, false)

        binding.dotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == gifList.size - 1) {
                    binding.dotsIndicator.setViewPager2(binding.viewPager)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}