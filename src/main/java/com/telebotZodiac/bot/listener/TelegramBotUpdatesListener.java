package com.telebotZodiac.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TelegramBotUpdatesListener implements UpdatesListener {
    //    делаем статики кнопок о классике
    private static final String GOROSKOP_CLASSIC_BUTTON = "Классический гороскоп \uD83D\uDC36";
    private static final String GOROSKOP2 = "Клас гороскоп \uD83D\uDC36";
    private static final String CALLBACK_SHOW_INFO_CLASSIC = "SHOW_INFO_CLASSIC";
    private static final String CALLBACK_SHOW_INSTRUCTION_CLASSIC = "SHOW_INSTRUCTION_CLASSIC";


//    делаем кнопки о китайском

    private static final String GOROSKOP_CHINA_BUTTON = "Китайский гороскоп \uD83D\uDC37";
    private static final String GOROSKOP3 = "Китай гороскоп \uD83D\uDC36";

    // делаем логи по листенеру
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);



       @Autowired
    private TelegramBot telegramBot;


    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
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
            if (update.message() != null && "/start".equals(update.message().text())) {
                startMessage(update);
//            } else if (update.callbackQuery() != null) {
//                processCallbackQuery(update);
//            } else if (update.message().photo() != null) {
//                saveReportPhoto(update);
//            } else if (update.message() != null && "Отчет".equalsIgnoreCase(update.message().text().substring(0, 5))) {
//                saveReport(update);
//            } else if (update.message() != null && checkMessagePattern(update.message().text())) {
//                saveUser(update);
//            } else {
//                failedMessage(update.message().chat().id());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void startMessage(Update update) {
        String name = update.message().chat().firstName();
        String msg = "Привет, " + name + "! Добро пожаловать в бот-гороскоп." +
                " Нами предоставлены гороскопы разных направлений," +
                " вы по дате своего рождения можете выбрать свое направление и все дела. " +
                "Мы более подробно расскажем кто вы и что вы Выберете гороскоп";

//        updates.forEach(update -> {
//            logger.info("Processing update: {}", update);
//            String msg = update.message().text();
//            if (msg.equals("/start")) {
//                SendMessage message = new SendMessage(
//                        update.message().chat().id(),
//                        String.format("Здравствуйте %s, Добро пожаловать в бот-гороскоп.  Введите дату рождения в формате dd.MM.yyyy (пример: 12.02.2023) и вы узнаете, что ожидает данный знак зодиака.", update.message().chat().firstName())
//                );
//                logger.info("Start button has been activated ^)");
//                telegramBot.execute(message);
        long id = update.message().chat().id();
//        тут мы прикручиваем кнопки в основное меню
        InlineKeyboardButton[] buttonsRow = {
                new InlineKeyboardButton(GOROSKOP_CLASSIC_BUTTON).callbackData(GOROSKOP2)};
        InlineKeyboardButton[] buttonsRow2 = {
                new InlineKeyboardButton(GOROSKOP_CHINA_BUTTON).callbackData(GOROSKOP3)};
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttonsRow, buttonsRow2);
        SendMessage sendMessage = new SendMessage(id, msg);
        sendMessage.replyMarkup(inlineKeyboard);
        telegramBot.execute(sendMessage);
    }


    private void processCallbackQuery(Update update) {
        Long chatId = update.callbackQuery().message().chat().id();
//
        if (GOROSKOP_CLASSIC_BUTTON.equalsIgnoreCase(update.callbackQuery().data())) {
            createButtonClassicZodiac(chatId);
        } else if (GOROSKOP_CHINA_BUTTON.equalsIgnoreCase(update.callbackQuery().data())) {
            createButtonChinaZodiac(chatId);
//        } else if (CALLBACK_SHOW_INFO_DOGS.equalsIgnoreCase(update.callbackQuery().data())) {
//            sendShelterInfo(chatId, ShelterType.DOG);
//        } else if (CALLBACK_SHOW_INFO_CATS.equalsIgnoreCase(update.callbackQuery().data())) {
//            sendShelterInfo(chatId, ShelterType.CAT);
//        } else if (CALLBACK_SHOW_MENU_REPORT.equalsIgnoreCase(update.callbackQuery().data())) {
//            createButtonsReportMenu(chatId);
//        } else if (CALLBACK_CHOOSE_SEND_REPORT.equalsIgnoreCase(update.callbackQuery().data())) {
//            sendReportMessage(chatId);
//        } else if (CALLBACK_CHOOSE_FORM_REPORT.equalsIgnoreCase(update.callbackQuery().data())) {
//            sendReportForm(chatId);
        }
    }
//доработать баттон инфо
    private void createButtonInfoMenu(Long chatId, String dataUser, String dataGoroscop) {
        String msg = "выбираем действие";
        InlineKeyboardButton[] buttonsRowForDataUser = {
                new InlineKeyboardButton("ВВодим свои данные \uD83C\uDFD8 ").callbackData(dataUser)};
        InlineKeyboardButton[] buttonsRowForDataGoroscop = {
                new InlineKeyboardButton(" \uD83D\uDC15 Информация о гороскопе \uD83D\uDC08").callbackData(dataGoroscop)};

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttonsRowForDataUser, buttonsRowForDataGoroscop);
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(inlineKeyboard);
        telegramBot.execute(sendMessage);
    }

    private void createButtonClassicZodiac(Long chatId) {
        createButtonInfoMenu(chatId, CALLBACK_SHOW_INFO_CLASSIC, CALLBACK_SHOW_INSTRUCTION_CLASSIC);
    }
    private void createButtonChinaZodiac(Long chatId) {
        return;
//    }
    }
}
