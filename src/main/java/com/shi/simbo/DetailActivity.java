package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.shi.simbo.entity.Episode;
import com.shi.simbo.entity.SeriesDetail;
import com.shi.simbo.entity.SeriesItem;
import com.shi.simbo.task.LoadSeriesTask;
import com.shi.simbo.task.ParseUrlTask;
import com.shi.simbo.task.ThreadPools;
import com.shi.simbo.view.EpisodeAdapter;


public class DetailActivity extends AppCompatActivity {

    private WebView webview;
    private TextView textViewTitle;
    private TextView textViewDesc;
    private TextView textViewActor;
    private TextView textViewRelease;
    private GridView gridView;

    String url = "http://2hanju.com/m3u8.php?vid=QQLf2HRCnAKF329twhLk[c]h0RNWTHQZo8C9idcUmXau8m99Znwx7IWU0BKaOhZryBYagBwnqCIo22E0sGQQfWiXwBUQoY[a]bWD6FY&mi=1";


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SeriesDetail detail = (SeriesDetail) msg.obj;
            webview.loadUrl(getResources().getString(R.string.app_source_host)+detail.getUrl());
            textViewTitle.setText(detail.getTitle());
            textViewDesc.setText(detail.getDescription());
            textViewActor.setText(detail.getActors());
            textViewRelease.setText(detail.getRelease());
            gridView.setAdapter(new EpisodeAdapter(DetailActivity.this,msg));

        }
    };

    Handler episodeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String detail = (String) msg.obj;
            webview.loadUrl(getResources().getString(R.string.app_source_host)+detail);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webview = findViewById(R.id.detailVideoViewId);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        textViewTitle = findViewById(R.id.detailTextViewId);
        textViewDesc = findViewById(R.id.tv_description_Id);
        textViewActor = findViewById(R.id.tv_actors_Id);
        textViewRelease = findViewById(R.id.tv_release_Id);

        gridView =findViewById(R.id.gv_episode_id);

        gridView.setOnItemClickListener(onItemClickListener);

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        String host = getResources().getString(R.string.app_source_host);
        String url = host + source;


        ThreadPools.executor.submit(()->{
            LoadSeriesTask task = new LoadSeriesTask(url);
            SeriesDetail detail = task.loadSeries();
            Episode episode = detail.getEpisodes().get(detail.getCurrent());
            String episodeUrl = new ParseUrlTask(host + episode.getUrl()).parse();
            detail.setUrl(episodeUrl);
            Message message = Message.obtain();
            message.obj = detail;
            mHandler.sendMessage(message);
        });

    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Episode episode = (Episode) parent.getItemAtPosition(position);
            ThreadPools.executor.submit(()->{
                String url = new ParseUrlTask(getResources().getString(R.string.app_source_host) + episode.getUrl()).parse();
                Message message = Message.obtain();
                message.obj = url;
                episodeHandler.sendMessage(message);
            });
        }
    };
}