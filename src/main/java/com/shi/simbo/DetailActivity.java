package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class DetailActivity extends AppCompatActivity {

    private WebView webview;

    String url = "http://2hanju.com/m3u8.php?vid=QQLf2HRCnAKF329twhLk[c]h0RNWTHQZo8C9idcUmXau8m99Znwx7IWU0BKaOhZryBYagBwnqCIo22E0sGQQfWiXwBUQoY[a]bWD6FY&mi=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webview = findViewById(R.id.detailVideoViewId);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        System.out.println(getResources().getString(R.string.app_source_host)+source);



    }
}