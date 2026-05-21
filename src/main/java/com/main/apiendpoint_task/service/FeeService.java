package com.main.apiendpoint_task.service;

import com.main.apiendpoint_task.entity.FeePrice;
import com.main.apiendpoint_task.repo.FeeRepo;
import com.main.apiendpoint_task.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeService {

    private final FeeRepo feeRepo;

    public  FeeService(FeeRepo feeRepo) {
        this.feeRepo = feeRepo;
    }

    public ApiResponse getAllPrices() {

        List<FeePrice> list = feeRepo.getAllFeePrices();

        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setMessage("All fee approval details fetched successfully");

        ApiResponse.Data data=new ApiResponse.Data();
        data.setFeeApprovalDetails(list);
        response.setData(data);
        return response;
    }
}
