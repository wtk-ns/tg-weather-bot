package io.wotkins.logic;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;


public class AnswerMessage extends SendMessage {

    private AnswerMessage(){}

    public static SendMessage sendSimple(String msgText, Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(msgText);
        sendMessage.enableHtml(true);

        return sendMessage;
    }

    public static SendMessage sendList(List<String> list, Message message){

        SendMessage sm = new SendMessage();
        StringBuilder stringBuilder = new StringBuilder();
        for(String text : list){
            stringBuilder.append(text);
            stringBuilder.append("\n\n");
        }
        sm.setText(stringBuilder.toString());
        sm.setChatId(message.getChatId().toString());
        sm.enableHtml(true);

        return sm;

    }

    public static EditMessageText editSimple(String newText, Message message){
        EditMessageText em = new EditMessageText();
        em.setText(newText);
        em.setChatId(message.getChatId().toString());
        em.setMessageId(message.getMessageId() + 1);
        em.enableHtml(true);
        return em;
    }

}
