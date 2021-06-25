package com.shi.simbo;

import com.shi.simbo.entity.SeriesDetail;
import com.shi.simbo.task.LoadItemTask;
import com.shi.simbo.task.LoadMovieTask;
import com.shi.simbo.task.LoadSeriesTask;
import com.shi.simbo.task.ParseUrlTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testLoadItem() throws IOException, InterruptedException {



    }

    @Test
    public void testLoadSeries() throws IOException, InterruptedException {
        LoadSeriesTask task = new LoadSeriesTask("http://www.2hanju.com/hanju/2470.html");
        task.loadSeries();


    }

    @Test
    public void testLoadUrl() throws IOException, InterruptedException {
        ParseUrlTask task = new ParseUrlTask("http://www.2hanju.com/player/2470_1_1.html");
        task.parse();


    }

    @Test
    public void testMovieUrl() throws IOException, InterruptedException {
        LoadMovieTask task = new LoadMovieTask("http://2hanju.com/movie/");
        task.setHost("http://2hanju.com");
        task.loadItems();


    }
}