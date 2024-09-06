package ru.luc1o.tgbot.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.luc1o.tgbot.infrastructure.configuration.properties.BotPropertiesConfiguration;

import java.io.Serializable;

@Slf4j
@Component
public class MyBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final BotPropertiesConfiguration.BotProperties botProperties;

    private final TelegramClient telegramClient;
    private final BeanNameUrlHandlerMapping beanNameHandlerMapping;

    public MyBot(BotPropertiesConfiguration.BotProperties botProperties, BeanNameUrlHandlerMapping beanNameHandlerMapping) {
        this.botProperties = botProperties;
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.beanNameHandlerMapping = beanNameHandlerMapping;
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }


    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                log.info("text: {}", message.getText());
            }
        }
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method var1) {
        try {
            return telegramClient.execute(var1);
        } catch (TelegramApiException e) {
            log.error("execute error", e);
            throw new RuntimeException(e);
        }
    }
}
