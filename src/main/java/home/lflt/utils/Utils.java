package home.lflt.utils;

import com.google.gson.Gson;
import home.lflt.model.fmpQuote;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class Utils {
    public static boolean checkPortfolio(LocalDate dateToCheck, int epochDays) {
        LocalDateTime now = LocalDateTime.now();
        boolean ans = false;
        log.info("public static boolean checkPortfolio(LocalDate dateToCheck, int epochDays)");
        log.info("epochs=" + epochDays);
        log.info("dateToCheck.plusMonths(1).getMonthValue()=" + dateToCheck.plusMonths(1).getMonthValue());

//        monthly
        if(epochDays == 28 && dateToCheck.plusMonths(1).getMonthValue() <= now.getMonthValue()) {
            log.info("if(epochDays == 28 && dateToCheck.plusMonths(1).getMonthValue() <= now.getMonthValue()) {");
            ans = true;
        }

        if(dateToCheck.plusDays(epochDays).getDayOfMonth() <= now.getDayOfMonth())
            ans = true;

        log.info("ans=" + ans);
        return ans;
    }

    public static void historicalPrice(String symbol, String from, String to) {
        String baseUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/";
        String baseUrl1 = "?from=";
        String baseUrl2 = "&to=";
        String link = baseUrl.concat(symbol).concat(baseUrl1).concat(from).concat(baseUrl2).concat(to);

        try {
            log.info(readUrl(link));
        } catch (Exception ee) {
            ee.printStackTrace();
            log.info("couldnt read from url");
        }
    }

    public static fmpQuote getQuote(String symbol) {
        String baseUrl = "https://financialmodelingprep.com/api/v3/quote/";
        String link = baseUrl.concat(symbol);
        String rawJson = "";
        String cleanJson = "";
        Gson gson = new Gson();

        try {
            rawJson = readUrl(link);
//            System.out.println(rawJson);

        } catch (Exception ee) {
            ee.printStackTrace();
            log.info("couldnt read rawJson from url");
        }

//        String oo = "{\"fmpQuote\":" + rawJson + "}";
        cleanJson = rawJson.substring(1, rawJson.length() - 1);
        return gson.fromJson(cleanJson, fmpQuote.class);
    }

    public static String readUrl(String urlString) throws Exception {
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(urlString);
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder stringBuilder = new StringBuilder();
            int index;
            char[] chars = new char[1024];
            while ((index = bufferedReader.read(chars)) != -1)
                stringBuilder.append(chars, 0, index);

            return stringBuilder.toString();
        } finally {
            if (bufferedReader != null)
                bufferedReader.close();
        }
    }
}
