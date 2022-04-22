package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.gaiana.game.databinding.ActivityToiletVodaBinding
import com.gaiana.game.model.Media

class ToiletVodaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToiletVodaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToiletVodaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.gaiana810)
        Media.mp?.start()

        binding.btnFantaEnd.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.buttonanta)
            Media.mp?.start()
            binding.btnFantaEnd.isClickable = false
            binding.btnNeFanta.isVisible = false

            Handler(Looper.getMainLooper()).postDelayed({
                Intent(this, ArbatActivity::class.java).also {
                startActivity(it)
            }}, 5000)


        }

        binding.btnNeFanta.setOnClickListener {
            Intent(this, GaianaActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    override fun onBackPressed() {
    }
    override fun onPause() {
        Media.mp?.pause()
        super.onPause()
    }
    override fun onResume() {
        Media.mp?.start()
        super.onResume()
    }
}