package com.shi.simbo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shi.simbo.task.LoadItemTask;
import com.shi.simbo.task.ThreadPools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mediaRadioGroup;
    private RadioGroup yearRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaRadioGroup = findViewById(R.id.media_radio_group);
        mediaRadioGroup.setOnCheckedChangeListener(getOnCheckedChangeListener());

        yearRadioGroup = findViewById(R.id.year_radio_group);
        yearRadioGroup.setOnCheckedChangeListener(getOnCheckedChangeListener());
    }

    private RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (radioGroup, i) -> {
            RadioButton radioButton = radioGroup.findViewById(i);
            radioButton.setBackgroundResource(R.drawable.radiobutton_background);
            changeGrid();
        };
    }

    private void changeGrid() {
        mediaRadioGroup = findViewById(R.id.media_radio_group);
        int mediaButtonId = mediaRadioGroup.getCheckedRadioButtonId();

        yearRadioGroup = findViewById(R.id.year_radio_group);
        int yearButtonId = yearRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(yearButtonId);

        String resource = String.format("%s/yeah/%s.html"
                ,getResources().getString(R.string.app_source_host)
                ,radioButton.getText());


        ThreadPools.threadPoolExecutor.submit(new LoadItemTask(resource));






    }


}