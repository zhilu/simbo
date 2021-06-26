package com.shi.simbo.task.loader;


import com.shi.simbo.entity.GridItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SeriesGridItemLoader implements GridItemLoader {

    private String resource;

    public SeriesGridItemLoader(LoaderConfig context) {
        if(Objects.nonNull(context)){
            this.resource =context.getSource();
        }
    }


    @Override
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
