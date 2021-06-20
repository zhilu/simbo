package com.shi.simbo.task;


import com.shi.simbo.entity.SeriesItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class LoadItemTask implements Runnable  {

    private String resource;

    public LoadItemTask(String resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        try{
            Document document = Jsoup.connect(resource).get();
            System.out.println(document.body());

            Elements elements = document.getElementsByClass("dsjlist")
                    .select("li");

            System.out.println("---------------------------------");
            System.out.println(elements.html());
            List<SeriesItem> items = new LinkedList<>();
            for (Element element : elements) {
                String source = element.getElementsByTag("a").first().attr("href");
                String imgSrc = element.getElementsByTag("img").first().attr("src");
                String title = element.getElementsByTag("img").first().attr("title");
                String current = element.getElementsByTag("span").last().text();

                SeriesItem seriesItem = new SeriesItem();
                seriesItem.setSource(source);
                seriesItem.setImgSrc(imgSrc);
                seriesItem.setTitle(title);
                seriesItem.setCurrent(current);
                items.add(seriesItem);
                System.out.println(seriesItem.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
