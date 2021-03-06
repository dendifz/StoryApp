package com.dendi.storyapp.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dendi.storyapp.R
import com.dendi.storyapp.data.source.local.paging.LoadingStateAdapter
import com.dendi.storyapp.databinding.ActivityHomeBinding
import com.dendi.storyapp.ui.StoryActivity
import com.dendi.storyapp.ui.addstory.AddStoryFragment
import com.dendi.storyapp.ui.maps.MapsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val homeVM: HomeVM by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.frameContainer.visibility = View.GONE


        setMenu()
        playPropertyAnimation()

        binding.btnAddStory.setOnClickListener {
            val mFragmentManager = supportFragmentManager
            val mHomeFragment = AddStoryFragment()
            val fragment = mFragmentManager.findFragmentByTag(AddStoryFragment::class.java.simpleName)

            if (fragment !is AddStoryFragment) {

                binding.apply {
                    btnAddStory.visibility = View.GONE
                    rvStory1.visibility = View.GONE
                    frameContainer.visibility = View.VISIBLE
                    showLoading()
                }

                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, mHomeFragment, AddStoryFragment::class.java.simpleName)
                    .commit()
            }
        }


        binding.rvStory1.layoutManager = LinearLayoutManager(this)

        val adapter = HomeStoryAdapter()
        binding.rvStory1.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        homeVM.tokenUser().observe(this) {
            if (it.isNotEmpty()) {
                homeVM.pagingStory(it).observe(this){ data ->
                    adapter.submitData(lifecycle, data)
                }
            } else startActivity(Intent(this, StoryActivity::class.java))
        }
    }

    private fun setMenu(){
        if (binding.toolbar.menu != null){
            binding.toolbar.apply {
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.log_out -> {
                            homeVM.removeSession()
                            true
                        }
                        R.id.map -> {
                            val intent = Intent(applicationContext, MapsActivity::class.java)
                            startActivity(intent)
                            true
                        }
                        else -> true
                    }
                }
            }
        }
    }

    private fun playPropertyAnimation() {

        val btnAddStoryAnim = ObjectAnimator.ofFloat(binding.btnAddStory, View.ALPHA, 1f).setDuration(1000)
        val rvStoryAnim = ObjectAnimator.ofFloat(binding.rvStory1, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(btnAddStoryAnim, rvStoryAnim)
            start()
        }
    }

    fun showLoading() {
        binding.apply {
            frameContainer.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
            pbGeneral.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        binding.apply {
            frameContainer.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
            pbGeneral.visibility = View.GONE
        }
    }
}