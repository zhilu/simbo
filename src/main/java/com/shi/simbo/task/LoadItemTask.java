package com.shi.simbo.task;


import com.shi.simbo.entity.GridItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class LoadItemTask {

    private String resource;

    public LoadItemTask(String resource) {
        this.resource = resource;
    }


    public List<GridItem> loadItems() {
        try{
            Document document = Jsoup.connect(resource).get();

            Elements elements = document.getElementsByClass("dsjlist")
                    .select("li");


            List<GridItem> items = new LinkedList<>();
            for (Element element : elements) {
                String source = element.getElementsByTag("a").first().attr("href");
                String imgSrc = element.getElementsByTag("img").first().attr("src");
                String title = element.getElementsByTag("img").first().attr("title");
                String current = element.getElementsByTag("span").last().text();

                GridItem gridItem = new GridItem();
                gridItem.setMovie(false);
                gridItem.setSource(source);
                gridItem.setImgSrc(imgSrc);
                gridItem.setTitle(title);
                gridItem.setCurrent(current);
                items.add(gridItem);
            }
            return items;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
