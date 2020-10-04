package home.lflt.utils;

import com.google.gson.Gson;
import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
import home.lflt.model.Quote;
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
import java.util.Random;
import java.util.Set;

import static home.lflt.utils.Constants.*;

@Slf4j
public class Utils {
    public static boolean checkPortfolioUpdate(LocalDateTime then, char updateFreq, int delay) {
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

    /**
     * prepare portfolio for rendering by fetching it from the db
     * and filling transient values
     */
    public static Portfolio preparePortfolioRendering(Portfolio pf) {
        pf.setCptSum(pf.getBalance());
        pf.setChange(0);
        pf.setPlTotalSum(0);
//        log.info(">> pf after set = " + pf.toString());

        Set<Lot> lots = pf.getLots();
//        log.info(">> pf.getLots();");
        for(Lot lot : lots) {
            Quote quote = getQuoteMi(lot.getSymbol());
//            log.info("got quote for: " + quote);

            lot.setYesterdayClose(lot.getCp());
            if (quote.getPrice() != 0) {
                lot.setCp(quote.getPrice());
//                lot.setUstamp(LocalDateTime.now());
            } else {
                lot.setError(true);
            }
            lot.setCpt(lot.getUnits() * lot.getCp());
            lot.setIpt(lot.getUnits() * lot.getIp());
            lot.setChange(quote.getChange());
            lot.setPlt(lot.getCpt() - lot.getIpt());

            pf.setCptSum(pf.getCptSum() + lot.getCpt());
            pf.setChange(pf.getChange() + lot.getChange());
            pf.setPlTotalSum(pf.getPlTotalSum() + lot.getPlt());

//            if(lot.getYesterdayClose() == lot.getCp())
        }
//        log.info("pf after transient update=" + pf.toString());

        return pf;
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

    public static Quote getQuoteMi(String symbol) {
        String baseUrlPrefix = "https://markets.businessinsider.com/stocks/";
        String baseUrlSuffix = "-stock";
        String link = baseUrlPrefix.concat(symbol.toLowerCase()).concat(baseUrlSuffix);
        double price = 0;
        double change = -111;
        double changeAbs = -1;
        Document doc = null;
//        log.info("link: " + link);

        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            log.info("couldn't download: " + link);
        }
//        log.info("html=" + doc);

//        String priceSection = doc.select("div.price-section__values").text();
//        log.info("priceSection: " + priceSection);
        if(doc != null) {
            try {
                String priceStr = doc.selectFirst("span.price-section__current-value").text();
                String changeStr = doc.selectFirst("span.price-section__relative-value").text();
                //            log.info("priceStr=" + priceStr + "; changeStr=" + changeStr);
                //            String changeStr = doc.select("#pushBorder div.aktien-big-font.text-nowrap span").last().text();
                NumberFormat nf = NumberFormat.getInstance(Locale.US); // Looks like a US format
                price = nf.parse(priceStr).doubleValue();
                //            log.info("price=" + price);
                if (changeStr.charAt(0) == '+') {
                    change = nf.parse(changeStr.substring(1, changeStr.length() - 2)).doubleValue();
                    //                log.info("changeStr.substring(1, changeStr.length() - 2): " + changeStr.substring(1, changeStr.length() - 2));
                } else {
                    change = nf.parse(changeStr.substring(0, changeStr.length() - 2)).doubleValue();
                    //                log.info("changeStr.substring(0, changeStr.length() - 2): " + changeStr.substring(0, changeStr.length() - 2));
                }
                //            log.info("change=" + change);
            } catch (Exception ee) {
                log.info("couldn't parse values from html, error: " + ee.getMessage());
            }
        }

        if(change == -111) {
            price = 0;
            change = 0;
            changeAbs = 0;
        } else
            changeAbs = price * change / 100;

        return Quote.builder()
                .symbol(symbol)
                .price(price)
                .changeAbs(changeAbs)
                .change(change)
                .build();
    }

    public static BasicTextEncryptor encryptor() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
//        System.out.println("System.getenv(\"salt\")=" + System.getenv("salt"));
//        encryptor.setPassword("pop");
        encryptor.setPassword(System.getenv("salt"));
        return encryptor;
    }

    public static int getRandomIntBetween(int min, int max) {
        boolean loop = true;
        Random rand = new Random();
        int num = 0;

        while(loop) {
            num = rand.nextInt(max); // upper limit excluded
            if(min <= num && num <= max) loop = false;
        }

        return num;
    }

    public static boolean stringNotNullAndNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
