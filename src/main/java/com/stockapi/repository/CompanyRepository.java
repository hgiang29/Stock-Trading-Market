package com.stockapi.repository;

import com.stockapi.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    @Query(nativeQuery = true,
            value = "select * from company" +
                    " where company.id =" +
                    " (select company_id from stock" +
                    " where symbol = :stockSymbol)")
    public Company findCompanyByStock(@Param("stockSymbol") String symbol);

}
