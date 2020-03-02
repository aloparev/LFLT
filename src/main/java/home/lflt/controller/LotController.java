package home.lflt.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import home.lflt.model.Lot;
import home.lflt.model.Stock;
import home.lflt.model.fmpQuote;
import home.lflt.repo.LotRepo;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/lot")
public class LotController {
    private LotRepo lotRepo;
    private StockRepo stockRepo;

    public LotController(LotRepo lotRepo, StockRepo stockRepo){
        this.lotRepo = lotRepo;
        this.stockRepo = stockRepo;
    }

    @GetMapping
    public String test(Model model) {
        Stock stock = stockRepo.getBySymbol("GOOG");
        log.info("get stock: " + stock);

        fmpQuote quote = getQuote(stock.getSymbol());
        log.info("get quote: " + quote.toString());

        int units = 10;
        Lot lot = new Lot(stock.getSymbol(), units, quote.getPrice(), quote.getDateFromTstamp());
        log.info("lot created: " + lot.toString());

        lotRepo.save(lot);
        return "stock";
    }


    private fmpQuote getQuote(String symbol) {
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

    private String readUrl(String urlString) throws Exception {
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
