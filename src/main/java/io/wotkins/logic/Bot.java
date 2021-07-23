package io.wotkins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final Map<String, String> envMap = System.getenv();
    private final MessageHandler handler;

    @Autowired
    public Bot(MessageHandler handler){
        this.handler = handler;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Object responce = handler.handleMessgae(update);

        if(responce instanceof SendMessage){
            sendMessage((SendMessage) responce);
        } else {
            editMessage((EditMessageText) responce);
        }
    }

    public void editMessage(EditMessageText message){
        try {
            execute(message);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return envMap.get("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return envMap.get("BOT_TOKEN");
    }


}
