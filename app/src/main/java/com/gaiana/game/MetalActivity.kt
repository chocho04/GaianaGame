package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityMetalBinding
import com.gaiana.game.model.Media

class MetalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMetalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMetalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.direktormetal)
        Media.mp?.start()

        binding.btnDishane1.setOnClickListener {
            Media.mp = MediaPlayer.create(this, R.raw.direktorend)
            Media.mp?.start()
            val intent = Intent(this, DieActivity::class.java)
            startActivity(intent)
        }

        binding.btnZarqzvam.setOnClickListener {
            val intent = Intent(this, GeshevActivity1::class.java)
            startActivity(intent)
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