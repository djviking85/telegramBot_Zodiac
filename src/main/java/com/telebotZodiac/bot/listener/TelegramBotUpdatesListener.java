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
import org.springframework.stereotype.Service;

import java.util.List;
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
            }
            else if (update.callbackQuery() != null) {
                processCallbackQuery(update);
            }
            else if (update.message() != null && update.message().text().equals(update.message().text())) {
                String messageText2 = update.message().text();
                String name = update.message().chat().firstName();

                String msg = "Намасте, " + name;
                long chatId = update.message().chat().id();

                SendMessage sendMessage = new SendMessage(chatId, msg);
                telegramBot.execute(sendMessage);

                if (messageText2.matches("\\d{2}:\\d{2}:\\d{4}")) {
                    // Парсим день, месяц и год из текста на китайца
                    String[] dateChinaParts = messageText2.split(":");

                    int day = Integer.parseInt(dateChinaParts[0]);
                    int month = Integer.parseInt(dateChinaParts[1]);
                    int year = Integer.parseInt(dateChinaParts[2]);

                    String horoscopeChina = shelterService.calculateChineseHoroscope(day, month, year);
                    SendMessage sendMessageChinaCorrect = new SendMessage(chatId, horoscopeChina);
                    telegramBot.execute(sendMessageChinaCorrect);

                } else if (update.message() != null && update.message().text().equals(update.message().text())) {
                    //тут включается классика проверка по дате
                    String messageText = update.message().text();
                    long chatId2 = update.message().chat().id();
                    if (messageText.matches("[0-3]?[0-9].[0-1]?[0-9]")) {
                        String[] dateParts = messageText.split("\\.");
                        int day = Integer.parseInt(dateParts[0]);
                        int month = Integer.parseInt(dateParts[1]);
                        String zodiacSign = shelterService.getZodiacSign(day, month);
                        SendMessage sendMessage2 = new SendMessage(chatId2, "Ваш знак зодиака: " + zodiacSign);
                        telegramBot.execute(sendMessage2);
                    } else {
                        SendMessage sendMessage4 = new SendMessage(chatId, "Пожалуйста, введите день и месяц в формате 'дд.мм' (Пример 19.11) - для европейского календаря, а вот для китайского надо ввести формат дд:мм:гггг , пример 19:11:1985");
                        telegramBot.execute(sendMessage4);
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void startMessage(Update update) {
        String name = update.message().chat().firstName();
        String msg = "Привет, " + name + "! Добро пожаловать в бот-гороскоп." +
                " Нами предоставлены гороскопы разных направлений: Классический и Китайский" +
                " Вы можете выбрать информацию о гороскопе,  " +
                "а так же по дате рождения вы можете узнать чуть больше о себе.";

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
        }
    }

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




}
