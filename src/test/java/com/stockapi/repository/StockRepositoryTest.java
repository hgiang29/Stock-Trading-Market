package com.stockapi.repository;

import com.stockapi.model.Company;
import com.stockapi.model.Stock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockRepositoryTest {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EntityManager entityManager;
    @Test
    public void createStock(){
        Stock stock = new Stock("mcsf", 50.5, 1000);
        Company company = entityManager.find(Company.class, 2);
        stock.setCompany(company);

        Stock savedStock = stockRepository.save(stock);
        System.out.println(savedStock);
    }

    @Test
    public void createCompany(){
        Company company = new Company("Microsoft", "This company produce windows");
        companyRepository.save(company);
    }

    @Test
    public void readStockInfo(){
        Company company = entityManager.find(Company.class, 3);
        Stock stock = company.getStock();

        System.out.println(stock);
    }

    @Test
    public void findStockBySymbol(){
        Stock stock = stockRepository.findBySymbol("appl");
        System.out.println(stock);
    }

    @Test
    public void getStockCompanyInfo(){
        Stock stock = stockRepository.findBySymbol("appl");
        Company company = companyRepository.findCompanyByStock(stock.getSymbol());

        System.out.println(company);
    }

    @Test
    public void searchStock() {
        String keyword = "m";
        List<Stock> stocks = stockRepository.searchStock(keyword);

        System.out.println(stocks);
    }



}
