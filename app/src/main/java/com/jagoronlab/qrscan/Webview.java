package com.jagoronlab.qrscan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class Webview extends AppCompatActivity {


    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        Uri uri = getIntent().getData();
        if (uri != null){
            String path = uri.toString();
            // Toast.makeText(Webview.this,"Path ="+path,Toast.LENGTH_LONG).show();
            webView = findViewById(R.id.webview);
            webView.loadUrl(path);

        }

        String url = getIntent().getStringExtra("keyurl");

        webView = findViewById(R.id.webview);


        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.loadUrl(url);

    }
}
