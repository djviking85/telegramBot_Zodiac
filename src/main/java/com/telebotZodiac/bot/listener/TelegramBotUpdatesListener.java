package com.telebotZodiac.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.telebotZodiac.bot.model.NotificationTask;
import com.telebotZodiac.bot.repository.NotificationTaskRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class TelegramBotUpdatesListener implements UpdatesListener {
    // делаем логи по листенеру
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

//    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
//        this.telegramBot = telegramBot;
//        this.notificationTaskRepository = notificationTaskRepository;
//    }

    //    @Autowired
    private TelegramBot telegramBot;

    private final NotificationTaskRepository notificationTaskRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

//    создание старта, и прикрепление паттерна, паттенр проверяется на наличие ошибки

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String msg = update.message().text();
            if (msg.equals("/start")) {
                SendMessage message = new SendMessage(
                        update.message().chat().id(),
                        String.format("Здравствуйте %s, вы зарегистрировались в моей сети.  Введите задачу в формате dd.MM.yyyy HH:mm Задача (пример: 12.02.2023 10:45 Сходить в магазин.)", update.message().chat().firstName())
                );
                logger.info("Start button has been activated ^)");
                telegramBot.execute(message);
            }

            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W\\w+]+)");
            Matcher matcher = pattern.matcher(msg);
            if (matcher.matches()) {
                String dateTimeStr = msg.substring(0, 16);
                String text = msg.substring(17);
                System.out.println(dateTimeStr);
                LocalDateTime dateTime;
                try {
                    dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    System.out.println(text);
                    NotificationTask notificationTask = new NotificationTask(update.message().chat().id(), dateTime, text);
                    notificationTaskRepository.save(notificationTask);
                    logger.info("Напоминание {} успешно сохранено", notificationTask);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
