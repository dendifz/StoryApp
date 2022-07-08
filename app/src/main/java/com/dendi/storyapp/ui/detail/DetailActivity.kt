package com.dendi.storyapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dendi.storyapp.data.model.Story
import com.dendi.storyapp.databinding.ActivityDetailBinding
import com.dendi.storyapp.utils.ConstVal.DETAIL_ITEM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityDetailBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Story>(DETAIL_ITEM) as Story
        binding.apply {
            Glide.with(ivStoryDetail)
                .load(data.photoUrl)
                .into(ivStoryDetail)
            textStorydescriptionDetail.text = data.description
            textDateDetail.text = data.createdAt
        }
    }
}