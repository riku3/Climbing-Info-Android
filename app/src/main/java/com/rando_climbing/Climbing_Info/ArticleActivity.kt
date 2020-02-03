package com.rando_climbing.Climbing_Info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class ArticleActivity : AppCompatActivity() {

    private lateinit var mAdView: AdView
    private var articleWebview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        articleWebview = findViewById<WebView>(R.id.article_detail_webview)
        articleWebview!!.getSettings().setJavaScriptEnabled(true)
        articleWebview!!.setWebViewClient(WebViewClient())
        articleWebview!!.loadUrl(intent.getStringExtra("link"))
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                //TODO:Web遷移しているときは前のWeb画面に戻る処理を追加する
                finish()
            }
            R.id.shareButton -> {
                shareButtonClick()
            }
        }
        return true
    }

    private fun shareButtonClick() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, articleWebview!!.url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
