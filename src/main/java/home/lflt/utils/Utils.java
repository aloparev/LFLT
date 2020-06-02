package home.lflt.utils;

import com.google.gson.Gson;
import home.lflt.model.Lot;
import home.lflt.model.fmpQuote;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class Utils {
    public static boolean checkPortfolio(LocalDateTime checkMe, int epochDays) {
        log.info("checkMe=" + checkMe + ", epochs=" + epochDays);

        LocalDateTime now = LocalDateTime.now();
        boolean ans = false;
//        log.info("public static boolean checkPortfolio(LocalDate checkMe, int epochDays)");
//        log.info("epochs=" + epochDays);
//        log.info("checkMe.plusMonths(1).getMonthValue()=" + checkMe.plusMonths(1).getMonthValue());

        if(checkMe == null)
            ans = true;
        else if(epochDays == 28 && now.compareTo(checkMe.plusMonths(1)) >= 0) {
//            log.info("if(epochDays == 28 && now.compareTo(checkMe.plusMonths(1)) >= 0)");
            ans = true;
        } else if(now.compareTo(checkMe.plusDays(epochDays)) >= 0) {
            ans = true;
//            log.info("now.compareTo(checkMe.plusDays(epochDays)) >= 0");
        }

        log.info("checkPortfolio return=" + ans);
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
        String link = baseUrl.concat(symbol).concat(System.getenv("FMG_API"));
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

    private static String readUrl(String urlString) throws Exception {
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
