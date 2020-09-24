package home.lflt.utils;

import com.google.gson.Gson;
import home.lflt.model.MarketsInsiderHead;
import home.lflt.model.fmpQuote;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

import static home.lflt.utils.Constants.*;

@Slf4j
public class Utils {
    public static boolean checkPortfolio(LocalDateTime then, char updateFreq, int delay) {
        log.info("then = " + then + ", updateFreq=" + updateFreq + ", epochs=" + delay);
        LocalDateTime now = LocalDateTime.now();
//        boolean update = true;

        if(then == null)
            return true;
        else
            switch (updateFreq) {
                case 'd':
                    return Duration.between(then, now).toMinutes() > ( DAY_IN_MINUTES * delay - DAY_OFFSET_IN_MINUTES );
                case 'w':
                    return Duration.between(then, now).toHours() > ( WEEK_IN_HOURS * delay - WEEK_OFFSET_IN_HOURS );
                case 'M':
                    return Duration.between(then, now).toHours() > ( MONTH_IN_HOURS * delay - MONTH_OFFSET_IN_HOURS );
                default:
                    log.info("updateFreq doesn't match any switch option");
                    return false;
            }
    }

    public static void fmpHistoricalPrice(String symbol, String from, String to) {
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

    public static fmpQuote fmpGetQuote(String symbol) {
        String baseUrl = "https://financialmodelingprep.com/api/v3/quote/";
        String link = baseUrl.concat(symbol).concat(System.getenv("FMP_API"));
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

    public static MarketsInsiderHead miGetQuote(String symbol) {
        String miBaseUrlStart = "https://markets.businessinsider.com/stocks/";
        String miBaseUrlEnd = "-stock";
        String link = miBaseUrlStart.concat(symbol).concat(miBaseUrlEnd);
        double price = 0;
        double change = -111;
        double changeAbs = -1;

        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            log.info(symbol + " not found\n" + e.getMessage());
        }

        try {
            String priceStr = doc.select("span.price-section__current-value").text();
            String changeStr = doc.select("span.price-section__relative-value").text();
//            System.out.println(priceStr + "; " + changeStr);
//            String changeStr = doc.select("#pushBorder div.aktien-big-font.text-nowrap span").last().text();
            NumberFormat nf = NumberFormat.getInstance(Locale.US); // Looks like a US format
            price = nf.parse(priceStr).doubleValue();

            if(changeStr.charAt(1) == '+') {
                change = nf.parse(changeStr.substring(1, changeStr.length()-2)).doubleValue();
            }
            else {
                change = nf.parse(changeStr.substring(0, changeStr.length()-2)).doubleValue();
            }

//            log.info("changeAbs " + doc.select("#pushBorder div.aktien-big-font.text-nowrap span.push-data.colorRed.aktien-big-font.text-nowrap.big-font-small.colorBlack").text());
//            log.info("scraped title: " + doc.title());
//            log.info("price " + price);
//            log.info("change " + change);
//            log.info("changeAbs " + changeAbs);
        } catch (Exception ne) {
            log.info("couldn't scrape values from page");
        }

        if(change == -111) {
            price = 0;
            change = 0;
            changeAbs = 0;
        } else
            changeAbs = price * change / 100;

        return MarketsInsiderHead.builder()
                .symbol(symbol)
                .price(price)
                .changeAbs(changeAbs)
                .change(change)
                .build();
    }

    public static BasicTextEncryptor encryptor() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        System.out.println("System.getenv(\"salt\")=" + System.getenv("salt"));
        encryptor.setPassword(System.getenv("salt"));
        return encryptor;
    }
}
