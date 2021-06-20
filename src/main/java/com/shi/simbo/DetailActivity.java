package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.shi.simbo.entity.Episode;
import com.shi.simbo.entity.SeriesDetail;
import com.shi.simbo.task.LoadSeriesTask;
import com.shi.simbo.task.ParseUrlTask;



public class DetailActivity extends AppCompatActivity {

    private WebView webview;
    private TextView textView;

    String url = "http://2hanju.com/m3u8.php?vid=QQLf2HRCnAKF329twhLk[c]h0RNWTHQZo8C9idcUmXau8m99Znwx7IWU0BKaOhZryBYagBwnqCIo22E0sGQQfWiXwBUQoY[a]bWD6FY&mi=1";


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SeriesDetail detail = (SeriesDetail) msg.obj;
            webview.loadUrl(getResources().getString(R.string.app_source_host)+detail.getUrl());
            textView.setText(detail.getTitle());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webview = findViewById(R.id.detailVideoViewId);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        textView = findViewById(R.id.detailTextViewId);
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        String host = getResources().getString(R.string.app_source_host);
        String url = host + source;


        new Thread() {
            @Override
            public void run() {
                super.run();
                LoadSeriesTask task = new LoadSeriesTask(url);
                SeriesDetail detail = task.loadSeries();
                Episode episode = detail.getEpisodes().get(detail.getCurrent());
                String url = new ParseUrlTask(host + episode.getUrl()).parse();
                detail.setUrl(url);
                Message message = Message.obtain();
                message.obj = detail;
                mHandler.sendMessage(message);
            }
        }.start();

    }
}