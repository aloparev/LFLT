package home.lflt.utils;

import com.google.gson.Gson;
import home.lflt.model.Lot;
import home.lflt.model.MarketsInsiderHead;
import home.lflt.model.fmpQuote;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

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
        String link = baseUrl.concat(symbol).concat("?apikey=148413889da13eff86f99945088b5ffe");
//        String link = baseUrl.concat(symbol).concat(System.getenv("FMG_API"));
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
        double price = -1;
        double changePct = -1;
        double changeAbs = -1;

        Document doc = null;
        try {
//            doc = Jsoup.connect("https://en.wikipedia.org/").get();
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            log.info("scraped title: " + doc.title());

//            Elements newsHeadlines = doc.select("#mp-itn b a");
//            for (Element headline : newsHeadlines) {
//                log.info(headline.attr("title") + "\n\t" + headline.absUrl("href"));
//            }

            NumberFormat nf = NumberFormat.getInstance(Locale.US); // Looks like a US format
            String changePctStr = doc.select("#pushBorder div.aktien-big-font.text-nowrap span").last().text();
            price = nf.parse(doc.select("#pushBorder span.push-data.aktien-big-font.text-nowrap.no-padding-at-all").text()).doubleValue();
            changeAbs = nf.parse(doc.select("#pushBorder div.aktien-big-font.text-nowrap span").first().text()).doubleValue();
            changePct = nf.parse(changePctStr.substring(1, changePctStr.length()-2)).doubleValue();

            log.info("price " + price);
//            log.info("changeAbs " + doc.select("#pushBorder div.aktien-big-font.text-nowrap span.push-data.colorRed.aktien-big-font.text-nowrap.big-font-small.colorBlack").text());
            log.info("changeAbs " + changeAbs);
            log.info("changePct " + changePct);
        } catch (Exception ne) {
            log.info("null pointer while reading doc");
        }

        return MarketsInsiderHead.builder()
                .price(price)
                .changeAbs(changeAbs)
                .changePct(changePct)
                .build();
    }
}
