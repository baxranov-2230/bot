package uz.pdp;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import uz.pdp.model.DicResult;
import uz.pdp.util.YandexUtil;

import java.util.ArrayList;

public class Main extends TelegramLongPollingBot {
    int step = 0;
    String lang = "";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
//        Main myBot=new Main();
        try {
            botsApi.registerBot(new Main());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

    public String getBotToken() {
        return "1095299717:AAF1mKUs2nEy3oh2E-GMvgfakA_xjuAzZcY";
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        String send = message.getText();
        SendMessage sendMessage = new SendMessage();
        if (text.equals("/start")) {
            step = 0;
        } else if (text.equals("RU-EN") || text.equals("EN-RU") || text.equals("RU-FR") || text.equals("FR-RU")) {
            lang = text.toLowerCase();
            step = 2;
        } else if (text.equals("Change Language")) {
            step = 1;
        }


        switch (step) {
            case 0:
                send = "Welcome! ";
                step = 1;
            case 1:
                //  send += "Select language";

//                step=2;
                break;
            case 2:
                if (lang.startsWith("ru")) {
                    send = "Отправьте ваше слово";
                    step = 3;
                } else if (lang.startsWith("en")) {
                    send = "Send your word";
                    step = 3;
                } else if (lang.startsWith("fr")) {
                    send = "Envoyez votre mot";
                    step = 3;
                } else {
                    send = "Language not selected. Please choose language";
                    step = 1;
                }
                break;
            case 3:
                text = message.getText();
                DicResult dicResult = YandexUtil.lookup(lang, text);

                    send = dicResult
                            .getDef()
                            .get(0)
                            .getTr()
                            .get(0)
                            .getText();
                    break;


        }

            sendMessage.setText(send);
            sendMessage.setChatId(message.getChatId());
            setKeyboard(sendMessage);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }






    }

    private void setKeyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);
        ArrayList<KeyboardRow> rows = new ArrayList<KeyboardRow>();
        KeyboardRow row1, row2;
        KeyboardButton button1, button2, button3, button4;
        switch (step) {
            case 0:
                break;
            case 1:
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("EN-RU");
                button2 = new KeyboardButton("RU-EN");
                row1.add(button1);
                row1.add(button2);

                row2 = new KeyboardRow();
                button3 = new KeyboardButton("FR-RU");
                button4 = new KeyboardButton("RU-FR");
                row2.add(button3);
                row2.add(button4);

                rows.add(row1);
                rows.add(row2);
                keyboardMarkup.setKeyboard(rows);
                break;
            case 2:

            case 3:
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("Change Language");
                row1.add(button1);
                rows.add(row1);
                keyboardMarkup.setKeyboard(rows);
                break;
        }
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public String getBotUsername() {
        return "@ahror7238_bot";
    }
}
