package com.telebotZodiac.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.telebotZodiac.bot.service.ShelterService;
import com.telebotZodiac.bot.shelter.ShelterGoroskop;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Service

public class TelegramBotUpdatesListener implements UpdatesListener {
    //    делаем статики кнопок о классике
    private static final String GOROSKOP_CLASSIC_BUTTON = "Классический гороскоп ♈♉♊♋♌♍♎";
    private static final String CALLBACK_SHOW_INFO_CLASSIC = "SHOW_INFO_CLASSIC";
    private static final String CALLBACK_SHOW_DESCRIPTION_CLASSIC = "SHOW_INSTRUCTION_CLASSIC";


//    делаем кнопки о китайском

    private static final String GOROSKOP_CHINA_BUTTON = "Китайский гороскоп 🐀🐂🐅🐇🐉🐍🐎";
    private static final String CALLBACK_SHOW_INFO_CHINA = "SHOW_INFO_CHINA";
    private static final String CALLBACK_SHOW_DESCRIPTION_CHINA = "SHOW_INSTRUCTION_CHINA";


    private static final Pattern PATTERN = Pattern.compile("(^[+|8][0-9\\s]+)\\s(\\w*@.+\\D$)");
    // делаем логи по листенеру
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    //       @Autowired
    private TelegramBot telegramBot;

    private final ShelterService shelterService;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, ShelterService shelterService) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
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
            } else if (update.callbackQuery() != null) {
                processCallbackQuery(update);

//так мы получаем водолея при введении 01.02 - те рабочий код
            } else if (update.message() != null && "01.02".equals(update.message().text())) {
                String messageText = update.message().text();
                String name = update.message().chat().firstName();

                if (update.hasMessage() && update.getMessage().hasText()) {
                    String messageText = update.getMessage().getText();
                    long chatId = update.getMessage().getChatId();

                    if (messageText.matches("[0-3]?[0-9].[0-1]?[0-9]")) {
                        String[] dateParts = messageText.split("\\.");
                        int day = Integer.parseInt(dateParts[0]);
                        int month = Integer.parseInt(dateParts[1]);

                        String zodiacSign = getZodiacSign(day, month);

                        sendMessage(chatId, "Ваш знак зодиака: " + zodiacSign);
                    } else {
                        sendMessage(chatId, "Пожалуйста, введите день и месяц в формате 'дд.мм'");
                    }
                }

                String msg = "Привет, " + name;
                long chatId = update.message().chat().id();

                SendMessage sendMessage = new SendMessage(chatId, msg);
                telegramBot.execute(sendMessage);

//                if (update.hasMessage() && update.getMessage().hasText()) {
//                    String messageText = update.getMessage().getText();
//                    long chatId = update.getMessage().getChatId();

                    if (messageText.matches("[0-3]?[0-9].[0-1]?[0-9]")) {
                        String[] dateParts = messageText.split("\\.");
                        int day = Integer.parseInt(dateParts[0]);
                        int month = Integer.parseInt(dateParts[1]);
//
                        String zodiacSign = getZodiacSign(day, month);
//
                      SendMessage sendMessage2 = new SendMessage(chatId, "Ваш знак зодиака: " + zodiacSign);
                        telegramBot.execute(sendMessage2);
                    } else {
                        SendMessage sendMessage3 = new SendMessage(chatId, "Пожалуйста, введите день и месяц в формате 'дд.мм'");
                        telegramBot.execute(sendMessage3);
                    }


            }
//            else {
//                failedMessage(update.message().chat().id());
//            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    public void startMessage(Update update) {
        String name = update.message().chat().firstName();
        String msg = "Привет, " + name + "! Добро пожаловать в бот-гороскоп." +
                " Нами предоставлены гороскопы разных направлений: Классический и Китайский" +
                " Вы можете выбрать информацию о гороскопе,  " +
                "а так же по дате рождения вы можете узнать чуть больше о себе.";

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
                new InlineKeyboardButton(GOROSKOP_CLASSIC_BUTTON).callbackData(GOROSKOP_CLASSIC_BUTTON)};
        InlineKeyboardButton[] buttonsRow2 = {
                new InlineKeyboardButton(GOROSKOP_CHINA_BUTTON).callbackData(GOROSKOP_CHINA_BUTTON)};
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttonsRow, buttonsRow2);
        SendMessage sendMessage = new SendMessage(id, msg);
        sendMessage.replyMarkup(inlineKeyboard);
        telegramBot.execute(sendMessage);
    }


    private void processCallbackQuery(Update update) {
        Long chatId = update.callbackQuery().message().chat().id();
//
        if (GOROSKOP_CLASSIC_BUTTON.equalsIgnoreCase(update.callbackQuery().data())) {
            createButtonClassicZodiacShelter(chatId);
        } else if (GOROSKOP_CHINA_BUTTON.equalsIgnoreCase(update.callbackQuery().data())) {
            createButtonChinaZodiacShelter(chatId);
        } else if (CALLBACK_SHOW_DESCRIPTION_CLASSIC.equalsIgnoreCase(update.callbackQuery().data())) {
            sendShelterDescription(chatId, ShelterGoroskop.CLASSIC);
        } else if (CALLBACK_SHOW_DESCRIPTION_CHINA.equalsIgnoreCase(update.callbackQuery().data())) {
            sendShelterDescription(chatId, ShelterGoroskop.CHINA);
        } else if (CALLBACK_SHOW_INFO_CLASSIC.equalsIgnoreCase(update.callbackQuery().data())) {
            sendShelterInstruction(chatId, ShelterGoroskop.CLASSIC);
        } else if (CALLBACK_SHOW_INFO_CHINA.equalsIgnoreCase(update.callbackQuery().data())) {
            sendShelterInstruction(chatId, ShelterGoroskop.CHINA);
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
        String msg = "Выберите действие:";
        InlineKeyboardButton[] buttonsRowForDataUser = {
                new InlineKeyboardButton("Вводим свои данные - по дате ⌨ ").callbackData(dataUser)};
        InlineKeyboardButton[] buttonsRowForDataGoroscop = {
                new InlineKeyboardButton(" \uD83D\uDEC8 Информация о гороскопе \uD83E\uDDD0").callbackData(dataGoroscop)};

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttonsRowForDataUser, buttonsRowForDataGoroscop);
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(inlineKeyboard);
        telegramBot.execute(sendMessage);

    }

    private void createButtonClassicZodiacShelter(Long chatId) {
        createButtonInfoMenu(chatId, CALLBACK_SHOW_INFO_CLASSIC, CALLBACK_SHOW_DESCRIPTION_CLASSIC);
    }

    private void createButtonChinaZodiacShelter(Long chatId) {
        createButtonInfoMenu(chatId, CALLBACK_SHOW_INFO_CHINA, CALLBACK_SHOW_DESCRIPTION_CHINA);
//    }
    }

    private void sendShelterDescription(Long chatId, ShelterGoroskop type) {
        SendMessage sendMessage = new SendMessage(chatId, shelterService.getDescription(type));
        telegramBot.execute(sendMessage);
    }

    private void sendShelterInstruction(Long chatId, ShelterGoroskop type) {
        SendMessage sendMessage = new SendMessage(chatId, shelterService.getInstruction(type));
        telegramBot.execute(sendMessage);
    }

    private void failedMessage(Long chatId) {
        String msg = "Извините, я не понимаю что делать";
        SendMessage sendMessage = new SendMessage(chatId, msg);
        telegramBot.execute(sendMessage);
    }

    private boolean checkMessagePattern(String text) {
        Matcher matcher = PATTERN.matcher(text);
        return matcher.matches();
    }



    private String getZodiacSign(int day, int month) {
        // Реализуйте логику определения знака зодиака здесь

        // Пример реализации:
        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
            return "Овен";
        } else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
            return "Телец";
        } else if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) {
            return "Близнецы";
        } else if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) {
            return "Рак";
        } else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
            return "Лев";
        } else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
            return "Дева";
        } else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
            return "Весы";
        } else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
            return "Скорпион";
        } else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
            return "Стрелец";
        } else if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
            return "Козерог";
        } else if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
            return "Водолей";
        } else {
            return "Рыбы";
        }
//
    }
}
