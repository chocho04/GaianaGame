package com.gaiana.game

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

/** Full-screen game look: hide the status and navigation bars (swipe to peek). */
fun Activity.goImmersive() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window, window.decorView).apply {
        hide(WindowInsetsCompat.Type.systemBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

/** Keep [content] clear of camera cutouts and of the bars when they slide in. */
fun padForInsets(content: View) {
    val base = content.paddingTop to content.paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(content) { v, insets ->
        val safe = insets.getInsetsIgnoringVisibility(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        v.updatePadding(top = base.first + safe.top, bottom = base.second + safe.bottom)
        insets
    }
}
