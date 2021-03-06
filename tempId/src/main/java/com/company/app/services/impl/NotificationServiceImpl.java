package com.company.app.services.impl;

import com.company.app.mainLogic.TelegramBotService;
import com.company.app.services.api.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationServiceImpl implements NotificationService {

    //todo stateless
    private static final boolean logEnabled = true;
    private List<String> log;
    @Autowired
    private TelegramBotService bot;

    public NotificationServiceImpl() {
        this.log = new ArrayList<>();
    }

    public void eventNotification(Object message) {
        bot.getChats().keySet().stream()
                .map(chatId -> {
                    SendMessage answer = new SendMessage();
                    answer.setText(message.toString());
                    answer.setChatId(chatId.toString());
                    return answer;
                })
                .forEach(bot::sendAnswer);
    }

    public void logNotification(Object message) {
        if (logEnabled) {
            log.add(message.toString());
        }
    }

    public void showLog() {
        for (String s : log) {
            System.out.println(s);
        }
    }

    public void errorNotification(Exception e) {
        e.printStackTrace();
    }
}
