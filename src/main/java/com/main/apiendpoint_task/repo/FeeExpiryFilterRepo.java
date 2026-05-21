package com.main.apiendpoint_task.repo;

import com.main.apiendpoint_task.entity.FeeExpiryFilter;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeeExpiryFilterRepo {

    private final JdbcClient jdbcClient;

    public FeeExpiryFilterRepo(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<FeeExpiryFilter> getDashboardData(String search){
        String sql= """
                
                
                     SELECT
                            id,
                            client_name AS clientName,
                            fee_label AS feeLabel,
                            fee_qualifier AS feeQualifier,
                            expiry_date AS expiryDate,
                            status,
                            ae_name AS aeName,
                
                            CASE
                                WHEN expiry_date <= CURRENT_DATE + INTERVAL '1 month' THEN '1-month'
                                WHEN expiry_date <= CURRENT_DATE + INTERVAL '3 months' THEN '3-month'
                                WHEN expiry_date <= CURRENT_DATE + INTERVAL '6 months' THEN '6-month'
                            END AS window,
                
                            CASE
                                WHEN expiry_date <= CURRENT_DATE + INTERVAL '1 month' THEN 'ONE_MONTH'
                                WHEN expiry_date <= CURRENT_DATE + INTERVAL '3 months' THEN 'THREE_MONTH'
                                WHEN expiry_date <= CURRENT_DATE + INTERVAL '6 months' THEN 'SIX_MONTH'
                            END AS bucket
                
                        FROM fee
                        WHERE expiry_date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '6 months'
                
                """;
        if(search!=null && !search.isEmpty() ){
            sql+="AND (LOWER(client_name) LIKE :search OR LOWER(fee_label) LIKE :search )";
        }

        sql+="ORDER BY expiry_date";

        var query=jdbcClient.sql(sql);
        if(search!=null && !search.isBlank() ){
            query=query.param("search","%"+search.toLowerCase()+"%");
        }
        return query.query(FeeExpiryFilter.class).list();
    }
}
