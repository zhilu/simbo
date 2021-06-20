package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private WebView webview;
    private MediaPlayer mediaPlayer;

    String url = "http://2hanju.com/m3u8.php?vid=QQLf2HRCnAKF329twhLk[c]h0RNWTHQZo8C9idcUmXau8m99Znwx7IWU0BKaOhZryBYagBwnqCIo22E0sGQQfWiXwBUQoY[a]bWD6FY&mi=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webview = findViewById(R.id.detailVideoViewId);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);



    }
}