package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.gaiana.game.databinding.ActivityFinalBinding
import com.gaiana.game.model.Media

class FinalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vvBackground2.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.finalactv))
        binding.vvBackground2.start()
        binding.vvBackground2.setOnPreparedListener { it.isLooping = true }

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.finalact)
        Media.mp?.start()

        binding.btnA0.isVisible = false
        Handler(Looper.getMainLooper()).postDelayed({
            binding.btnA0.isVisible = true
        }, 9000)

        binding.btnA0.setOnClickListener {
            Intent(this, ArbatActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnGaf2.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf2)
            Media.mp?.start()
            binding.tvExplainer.text = "насладете се на нашата колекция от гафове"
        }
        binding.btnGaf3.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf3)
            Media.mp?.start()
            binding.tvExplainer.text = "играта е оригинално правена през 1999 година"
        }
        binding.btnGaf5.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf5)
            Media.mp?.start()
            binding.tvExplainer.text = "оригиналната игра е създадена в рамките на няколко месеца"
        }
        binding.btnGaf6.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf6)
            Media.mp?.start()
            binding.tvExplainer.text = "основно разчитахме на мозъчните припръдвания на Цоцика"
        }
        binding.btnGaf7.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf7)
            Media.mp?.start()
            binding.tvExplainer.text = "играта е преработвана веднъж за уиндоус през 2018 година"
        }
        binding.btnGaf8.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf8)
            Media.mp?.start()
            binding.tvExplainer.text = "играта е недовършена, оригинално се очакваше да е много по дълга"
        }
        binding.btnGaf9.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf9)
            Media.mp?.start()
            binding.tvExplainer.text = "последните няклко епизода са правени месеци по късно"
        }
        binding.btnGaf10.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf10)
            Media.mp?.start()
            binding.tvExplainer.text = "оригинално е замислена да няма положителен завършек"
        }
        binding.btnGaf15.setOnClickListener {
            Media.mp?.stop()
            Media.mp?.prepare()
            Media.mp = MediaPlayer.create(this, R.raw.gaf15)
            Media.mp?.start()
            binding.tvExplainer.text = "благодарим ви че изслушахте мозъчните ни пръдни отпреди 20 години..."
        }



    }
    override fun onBackPressed() {
    }
    override fun onPause() {
        super.onPause()
        Media.mp?.pause()
        binding.vvBackground2.pause()
    }
    override fun onResume() {
        super.onResume()
        Media.mp?.start()
        binding.vvBackground2.start()
    }
}