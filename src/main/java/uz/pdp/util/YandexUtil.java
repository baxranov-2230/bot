package uz.pdp.util;

import com.google.gson.Gson;
import uz.pdp.model.DicResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class YandexUtil {
    public static DicResult lookup(String lang, String text) {
        try {
            String key = "dict.1.1.20200225T153149Z.020e76a505f3c144.7d86a37cc0c9335f5d8c1ade335d24b4dcf921fc";

            Gson gson=new Gson();
            URL url = new URL("https://dictionary.yandex.net/api/v1/dicservice.json/lookup" +
                    "?key=" + key + "&lang=" + lang + "&text=" + text);
            URLConnection connection = url.openConnection();

            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            DicResult result=gson.fromJson(reader, DicResult.class);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
