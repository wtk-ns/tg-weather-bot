package io.wotkins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.wotkins.enums.NewsType;
import io.wotkins.parsers.WeatherParser;
import io.wotkins.utils.PropertyController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Component
public class NewsMaker {
    Properties properties;
    private final WeatherParser weatherParser;

    @Autowired
    private NewsMaker(WeatherParser weatherParser, PropertyController propertyController){
        this.weatherParser = weatherParser;
        this.properties = propertyController.getProperties();
    }

    public List<String> getNews(Integer gap, NewsType type){
        List<String> fin = new ArrayList<>();
        switch (type){
            case WEATHER: fin = weatherParser.parseWeather(type.getUrl(), gap);

        }

        if(fin.isEmpty()){
            fin.add(properties.getProperty("emptyParser")+ "\u2639");
        }
        return fin;
    }





}
