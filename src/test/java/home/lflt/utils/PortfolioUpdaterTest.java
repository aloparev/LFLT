package home.lflt.utils;

import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PortfolioUpdaterTest {

    @Test
    void balancing() {
        Portfolio pp = new Portfolio("dummy P", "RANDOM", 0, 1000, 'd', 1);
//        pp.setName("dummy pp");
//        pp.setInfo("to test balance field");
//        pp.setType("RANDOM");
//        pp.setBalance(0);
//        pp.setFunds(1000);
//        pp.setEpochs(1);
        log.info("pp created=" + pp);

        Lot newLot = new Lot("foo", "s1", 4, 210);
        newLot.setPortfolio(pp);
        pp.setBalance(pp.getBalance() + pp.getFunds() - newLot.getIpt());
        pp.getLots().add(newLot);
        assertEquals(160, pp.getBalance());

        Lot anotherLot = new Lot("boo", "s2", 4, 240);
        newLot.setPortfolio(pp);
//        log.info("IPT2=" + String.valueOf(anotherLot.getIpt()));
//        log.info("balance2=" + String.valueOf(pp.getBalance()));
//        log.info("funds2=" + String.valueOf(pp.getFunds()));
        pp.setBalance(pp.getBalance() + pp.getFunds() - anotherLot.getIpt());
        pp.getLots().add(anotherLot);
        // 1160 - 960
        assertEquals(200, pp.getBalance());

        for(Lot ll : pp.getLots())
            pp.setBalance(pp.getBalance() + ll.getIpt());

//        pp.setBalance(pp.getBalance() + newLot.getIpt() + anotherLot.getIpt());
        assertEquals(2000, pp.getBalance());
//        log.info("pp end=" + pp);
    }

    @Test
    void addLot() {
//        Lot alreadyExists = lotRepo.getByPortfolioIdAndSymbol(pp.getId(), newLot.getSymbol());
//        if(alreadyExists == null) {
//            pp.getLots().add(newLot);
//            log.info("alreadyExists == null");
//        } else {
//            alreadyExists.setUnits(alreadyExists.getUnits() + newLot.getUnits());
//            alreadyExists.setIpt(alreadyExists.getIpt() + newLot.getIpt());
//            alreadyExists.setIp(alreadyExists.getIpt() / alreadyExists.getUnits());
//            log.info("alreadyExists exists");
//        }
//        pp.setUstamp(LocalDateTime.now());

//        pp.getLots().add(new BuyingAlgorithm(stockRepo, pp.getFunds()).buyStockRandomly());
//        log.info("pp updated=" + pp.toString());

    }
}