package com.main.apiendpoint_task.repo;

import com.main.apiendpoint_task.entity.FeePrice;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeeRepo {

    private final JdbcClient jdbcClient;

    public FeeRepo(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<FeePrice> getAllFeePrices(){
        return jdbcClient.sql("select * from fee_approval").query(
//                (rs,rowNumber)->{
//
//                        FeePrice feePrice = new FeePrice();
//                        feePrice.setId(rs.getInt("id"));
//                        feePrice.setFeeLabel(rs.getString("fee_label"));
//                        feePrice.setSubmittedBy(rs.getString("submitted_by"));
//                        feePrice.setChangeSummary(rs.getString("change_summary"));
//                        feePrice.setSubmittedOn(rs.getDate("submitted_on"));
//                        feePrice.setApproverAssigned(rs.getString("approver_assigned"));
//                        feePrice.setStatus(rs.getString("status"));
//                        return feePrice;
//                }
                FeePrice.class
                ).list();
    }
}
