package com.example.repocardandroid

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.repocardandroid.utils.SessionManagement
import kotlinx.android.synthetic.main.activity_webview.*


class WebViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        supportActionBar?.hide()
        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
//        webview.webViewClient = WebClient()
        val myWebView: WebView = webview
        myWebView.settings.loadsImagesAutomatically = true
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.setSupportZoom(true)
        myWebView.settings.builtInZoomControls = true
        myWebView.settings.displayZoomControls = false
        myWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        myWebView.webViewClient = WebViewClient()
        val session = SessionManagement(this)
        val url = session.checkServerAddress(session.keyServerAddress)
        println("AAAAAAA : $url")
        myWebView.loadUrl("http://$url")
    }
}