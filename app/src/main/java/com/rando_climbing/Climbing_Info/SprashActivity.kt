package com.rando_climbing.Climbing_Info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.google.firebase.analytics.FirebaseAnalytics

class SprashActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val handler = Handler()
    private val runnable = Runnable {

        // NewsActivityに遷移
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)

        // NewsActicityから戻るボタンでSprashActivity遷移しないように
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprash)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        handler.postDelayed(runnable, 1200)
    }

    override fun onStop() {
        super.onStop()

        // スプラッシュ画面中にアプリを落とした時にはrunnableが呼ばれないようにする
        handler.removeCallbacks(runnable)
    }
}
