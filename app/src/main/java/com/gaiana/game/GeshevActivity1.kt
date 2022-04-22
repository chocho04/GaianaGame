package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityGeshev1Binding
import com.gaiana.game.model.Media

class GeshevActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityGeshev1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeshev1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.geshev1)
        Media.mp?.start()

        binding.btnDirektor.setOnClickListener {
            val intent = Intent(this, GeshevActivity2::class.java)
            startActivity(intent)
        }
        binding.btnNikoi.setOnClickListener {
            val intent = Intent(this, GeshevActivity3::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {

    }
    override fun onPause() {
        super.onPause()
        Media.mp?.pause()
    }
    override fun onResume() {
        super.onResume()
        Media.mp?.start()
    }
}