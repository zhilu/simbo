package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shi.simbo.entity.GridItem;
import com.shi.simbo.task.loader.GridItemLoader;
import com.shi.simbo.task.loader.SeriesGridItemLoader;
import com.shi.simbo.task.loader.MovieGridItemLoader;
import com.shi.simbo.task.loader.LoaderConfig;
import com.shi.simbo.task.ThreadPools;
import com.shi.simbo.view.GridViewItemAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mediaRadioGroup;
    private RadioGroup yearRadioGroup;
    private GridView gridView;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gridView.setAdapter(new GridViewItemAdapter(MainActivity.this,msg));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridViewId);
        gridView.setOnItemSelectedListener(onItemSelectedListener);
        gridView.setOnItemClickListener(onItemClickListener);

        mediaRadioGroup = findViewById(R.id.media_radio_group);
        mediaRadioGroup.setOnCheckedChangeListener(getOnCheckedChangeListener());

        yearRadioGroup = findViewById(R.id.year_radio_group);
        yearRadioGroup.setOnCheckedChangeListener(getOnCheckedChangeListener());

        RadioButton yearButton = findViewById(R.id.year_2021);
        yearButton.performClick();


    }




    private RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (radioGroup, i) -> {
            RadioButton radioButton = radioGroup.findViewById(i);
            radioButton.setBackgroundResource(R.drawable.radiobutton_background);
            changeGrid();
        };
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this,
                    "点击位置:"+position,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this,
                    "点击位置2:"+position,
                    Toast.LENGTH_SHORT).show();
            GridItem item = (GridItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra("source", item.getSource());
            startActivity(intent);
        }
    };

    private void changeGrid() {
        mediaRadioGroup = findViewById(R.id.media_radio_group);
        int mediaButtonId = mediaRadioGroup.getCheckedRadioButtonId();

        boolean isMovie =mediaButtonId == R.id.type_movie;


        yearRadioGroup = findViewById(R.id.year_radio_group);
        int yearButtonId = yearRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(yearButtonId);

        String host = getResources().getString(R.string.app_source_host);

        CharSequence year = radioButton.getText();
        String resource = isMovie ?
                String.format("%s/movie/", host)
                : String.format("%s/yeah/%s.html",host, radioButton.getText());

        LoaderConfig config = new LoaderConfig();
        if (isMovie) {
            config.setHost(host);
            config.setSource(String.format("%s/movie/", host));
            config.addParam("year", String.valueOf(year));
        } else {
            config.setSource(String.format("%s/yeah/%s.html", host, radioButton.getText()));
        }

        GridItemLoader gridItemloader = isMovie ?
                new MovieGridItemLoader(config)
                : new SeriesGridItemLoader(config);


        ThreadPools.executor.execute(()->{
            List<GridItem> items = gridItemloader.loadItems();
            Message message = Message.obtain();
            message.obj = items;
            mHandler.sendMessage(message);
        });







    }


}