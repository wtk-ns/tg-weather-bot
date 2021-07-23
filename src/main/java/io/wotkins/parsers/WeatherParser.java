package io.wotkins.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherParser {

    public List<String> parseWeather(String url, Integer gap){
        List<String> weeklyWEather = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            List<Element> li = document.getElementsByTag("li");

            if (!li.isEmpty()){


                List<Element> tabs = new ArrayList<>();
                int daysCounter = 0;
                for (int i=0;i<li.size();i++){
                    Element element = li.get(i);
                    if(element.className().contains("tab-w")){
                        daysCounter++;
                        if(daysCounter>gap){
                            break;
                        }
                        tabs.add(element);
                        StringBuilder format= new StringBuilder();

                        String weatherDescription = element.getElementsByTag("span").get(0).attr("title").trim();

                        format.append(getSmile(weatherDescription) + " ");
                        format.append("<b>" + element.getElementsByClass("day-week").get(0).text() + " (");
                        format.append(element.getElementsByClass("numbers-month").get(0).text() + " " +
                                element.getElementsByClass("month").get(0).text() + "):</b>\n");

                        format.append(weatherDescription + "\n");
                        format.append(element.getElementsByClass("day-temperature").get(0).text() + ".." +
                                element.getElementsByClass("night-temperature").get(0).text());
                        weeklyWEather.add(format.toString());

                    }
                }

            } else {
                throw new Exception("Can't find li tags. List is empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        return weeklyWEather;

    }

    private String getSmile(String description){
        String temp = description.toLowerCase();
        if (temp.contains("осадки")){
            return "\u2602";
        } else if (temp.contains("ясно")){
            return "\u2600";
        } else if (temp.contains("облачно")){
            return "\u2601";
        } else if(temp.contains("снег")){
            return "\u2603";
        } else {
            return "\uD83D\uDD35";
        }

    }
}
