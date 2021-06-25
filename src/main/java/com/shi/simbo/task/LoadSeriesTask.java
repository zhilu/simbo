package com.shi.simbo.task;


import com.shi.simbo.entity.Episode;
import com.shi.simbo.entity.SeriesDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class LoadSeriesTask {

    private String resource;

    public LoadSeriesTask(String resource) {
        this.resource = resource;
    }


    public SeriesDetail loadSeries() {
        try{
            Document document = Jsoup.connect(resource).get();
            Elements elements = document.getElementsByClass("jianjie_right")
                    .select("p");

            SeriesDetail item = new SeriesDetail();
            if(elements.size() ==4){
                item.setTitle(elements.get(0).text());
                item.setDescription(elements.get(1).text());
                item.setRelease(elements.get(2).text());
                item.setActors(elements.get(3).text());
            }

            elements = document.getElementsByClass("list")
                    .select("a[target='_top']");
            List<Episode> episodes = new LinkedList<>();
            for (Element element : elements) {
                Episode episode = new Episode();
                episode.setUrl(element.attr("href"));
                episode.setName(element.text());
                episodes.add(episode);
            }
            item.setEpisodes(episodes);
            System.out.println(item);
            return item;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
