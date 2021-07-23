package io.wotkins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import io.wotkins.enums.MessageType;
import io.wotkins.enums.NewsType;
import io.wotkins.utils.PropertyController;

import java.util.Properties;


@Component
public class MessageHandler {
    private NewsMaker newsMaker;
    private final Properties properties;

    @Autowired
    private MessageHandler(PropertyController properties, NewsMaker newsMaker){
        this.properties = properties.getProperties();
        this.newsMaker = newsMaker;
    }

    public Object handleMessgae(Update update){
        MessageType messageType = getMessageType(update);
        switch (messageType){
            case TEXT: return handleText(update);
            case COMMAND: return handleCommand(update);
            case CALLBACK: return handleCallback(update);
            default: return null;
        }
    }

    private EditMessageText handleCallback(Update update) {
        return null;
    }

    private Object handleCommand(Update update) {
        String command = update.getMessage().getText().substring(1);

        if (command.contains("weather")){
            return getWeather(command,update);
        } else if (command.equals("help")){
            return AnswerMessage.sendSimple(properties.getProperty("helpMessage"),
                    update.getMessage());
        } else {
            return AnswerMessage.sendSimple(properties.getProperty("nonCommandMessage")
                    + command,update.getMessage());
        }

    }

    private SendMessage getWeather(String command, Update update){

        Integer countOfDays;
        try {
            countOfDays = Integer.parseInt(command.split(" ")[1]);
        } catch (IndexOutOfBoundsException ignored){
            countOfDays = 7;
        } catch (NumberFormatException e){
            return AnswerMessage.sendSimple(properties.getProperty("weatherErrorMessage")
                    ,update.getMessage());
        }
        return AnswerMessage.sendList(newsMaker.getNews(countOfDays, NewsType.WEATHER),
                update.getMessage());
    }

    private SendMessage handleText(Update update){
        return AnswerMessage.sendSimple("hey", update.getMessage());
    }

    private MessageType getMessageType(Update update){
        if (update.hasCallbackQuery()){
            return MessageType.CALLBACK;
        } else if(update.getMessage().getText().startsWith("/")){
            return MessageType.COMMAND;
        } else {
            return MessageType.TEXT;
        }
    }

}
