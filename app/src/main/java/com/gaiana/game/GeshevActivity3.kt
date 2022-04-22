package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityGeshev3Binding
import com.gaiana.game.model.Media

class GeshevActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityGeshev3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeshev3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.geshev3)
        Media.mp?.start()

        binding.btn31.setOnClickListener {
            val intent = Intent(this, FinalActivity::class.java)
            startActivity(intent)
        }
        binding.btnToIstorichka.setOnClickListener {
            val intent = Intent(this, IstorichkaActivity::class.java)
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