package com.shi.simbo.task.loader;

import com.shi.simbo.entity.GridItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MovieGridItemLoader implements GridItemLoader {

    private String host;
    private String resource;
    private String year="2021";


    public MovieGridItemLoader(LoaderConfig context) {
        if(Objects.nonNull(context)){
            this.host = context.getHost();
            this.resource = context.getSource();
            this.year = context.getParam("year");
        }
    }


    @Override
    public List<GridItem> loadItems() {
        try{
            Document document = Jsoup.connect(resource).get();

            Elements elements = document.getElementsByClass("contain_con");


            List<GridItem> items = new LinkedList<>();
            for (Element element : elements) {
                String title = element.getElementsByClass("box_tt")
                        .get(0)
                        .getElementsByTag("h2")
                        .get(0)
                        .text();

                if(null!= title && title.startsWith(year)){
                    Elements movieElements = element.getElementsByClass("list")
                            .get(0).getElementsByTag("a");
                    System.out.println(movieElements);
                    for (Element movieElement : movieElements) {
                        String source = movieElement.attr("href");
                        String imgSrc = getImage(source);
                        String movieName = movieElement.attr("title");

                        GridItem gridItem = new GridItem();
                        gridItem.setMovie(true);
                        gridItem.setSource(source);
                        gridItem.setImgSrc(imgSrc);
                        gridItem.setTitle(movieName);
                        gridItem.setCurrent("1");
                        items.add(gridItem);
                    }
                    break;
                }
            }
            System.out.println(items);
            return items;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getImage(String source) {
        try{
            String url = host + source;
            Document document = Jsoup.connect(url).get();

            Elements elements = document.getElementsByClass("jianjie_left")
                    .get(0).getElementsByTag("img");

            return elements.get(0).attr("src");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
