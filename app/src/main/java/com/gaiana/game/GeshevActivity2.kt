package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityGeshev2Binding
import com.gaiana.game.model.Media

class GeshevActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityGeshev2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeshev2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.geshev2)
        Media.mp?.start()

        binding.btnCloser.setOnClickListener {
            Media.mp = MediaPlayer.create(this, R.raw.geshevend)
            Media.mp?.start()

            val intent = Intent(this, DieActivity::class.java)
            startActivity(intent)
        }
        binding.btnNazad.setOnClickListener {
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