package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shi.simbo.entity.Episode;
import com.shi.simbo.entity.SeriesDetail;
import com.shi.simbo.task.LoadSeriesTask;
import com.shi.simbo.task.ParseUrlTask;
import com.shi.simbo.task.ThreadPools;
import com.shi.simbo.view.EpisodeAdapter;


public class DetailActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private ProgressBar progressBar;
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
        frameLayout =findViewById(R.id.full_frame);
        progressBar = findViewById(R.id.process_bar);


        webview = findViewById(R.id.detailVideoViewId);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.setWebViewClient(new WebViewClient());

        textViewTitle = findViewById(R.id.detailTextViewId);
        textViewDesc = findViewById(R.id.tv_description_Id);
        textViewActor = findViewById(R.id.tv_actors_Id);
        textViewRelease = findViewById(R.id.tv_release_Id);

        gridView =findViewById(R.id.gv_episode_id);

        gridView.setOnItemClickListener(onItemClickListener);

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        boolean type = intent.getBooleanExtra("type",false);
        String host = getResources().getString(R.string.app_source_host);
        String url = host + source;


        ThreadPools.executor.submit(()->{
            LoadSeriesTask task = new LoadSeriesTask(url);
            task.setMovie(type);
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



    class MyWebChromeClient extends WebChromeClient {
        private View myView = null;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);//加载完网页进度条消失
            } else {
                progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar.setProgress(newProgress);//设置进度值
            }
        }

        // 全屏
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);

            webview.setVisibility(View.INVISIBLE);
            frameLayout.addView(view);
            frameLayout.setVisibility(View.VISIBLE);
            myView = view;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
            setFullScreen();
        }

        // 退出全屏
        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (myView != null) {
                frameLayout.removeAllViews();
                frameLayout.setVisibility(View.GONE);

                webview.setVisibility(View.VISIBLE);
                myView = null;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                quitFullScreen();
            }
        }


    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        final WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setAttributes(attrs);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


}