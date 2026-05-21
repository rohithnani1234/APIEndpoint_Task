package com.main.apiendpoint_task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.apiendpoint_task.entity.FeePrice;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private String status;
    private String message;
    private Data data;

    @lombok.Data
    public static class Data{
        @JsonProperty("fee_approval_details")
        private List<FeePrice> feeApprovalDetails;
    }
}
