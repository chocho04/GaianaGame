package com.gaiana.game

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.gaiana.game.databinding.ActivityDieBinding
import com.gaiana.game.model.Gameplay
import com.gaiana.game.model.Media

class DieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vvBackground.isVisible = false
        binding.vvBackground.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.video))
        binding.vvBackground.start()
        binding.vvBackground.setOnPreparedListener { it.isLooping = true }

        binding.btnA0.isVisible = false

            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnA0.isVisible = true
                binding.vvBackground.isVisible = true
                binding.ivAmiSega.isVisible = false
                binding.tvDescription.text = "За съжаление това беше твоят край ..."
                binding.tvDeathCount.text = "Вече умря " + Gameplay.counter.toString() + "00 пъти. Пълен срам майна!"
            }, 10000)

        binding.btnA0.setOnClickListener {
            Gameplay.counter ++
            Intent(this, ArbatActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    override fun onBackPressed() {

    }
    override fun onPause() {
        super.onPause()
        Media.mp?.pause()
        binding.vvBackground.pause()
    }
    override fun onResume() {
        super.onResume()
        Media.mp?.start()
        binding.vvBackground.start()
    }
}