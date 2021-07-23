package io.wotkins.utils;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class PropertyController {

    private Properties properties;

    private PropertyController(){
        this.properties = new Properties();
        loadProperties();
    }

    public Properties getProperties(){
        return properties;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    private void loadProperties(){
        try {
            InputStreamReader fr = new InputStreamReader(new FileInputStream("src/main/resources/project.properties"),
                    StandardCharsets.UTF_8);
            properties.load(fr);
            fr.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
