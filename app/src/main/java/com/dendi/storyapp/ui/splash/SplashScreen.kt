package com.dendi.storyapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dendi.storyapp.databinding.SplashScreenBinding
import com.dendi.storyapp.ui.StoryActivity
import com.dendi.storyapp.utils.ConstVal.time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var spBinding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(spBinding.root)
        val intentToHome = Intent(this@SplashScreen, StoryActivity::class.java)
        val handler = Handler(mainLooper)

        lifecycleScope.launch(Dispatchers.Default) {
            handler.postDelayed({
                startActivity(intentToHome)
                finish()
            }, time.toLong())
        }
    }

}