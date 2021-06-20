package com.shi.simbo.task;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ParseUrlTask {
    private String resource;

    public ParseUrlTask(String resource) {
        this.resource = resource;
    }


    public String parse() {
        try{
            Document document = Jsoup.connect(resource).get();

            Elements elements = document.getElementsByTag("iframe");

            for (Element element : elements) {
                String url = element.attr("src");
                return url;
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
