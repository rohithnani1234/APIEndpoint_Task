package com.main.apiendpoint_task.controller;

import com.main.apiendpoint_task.entity.FeePrice;
import com.main.apiendpoint_task.response.ApiResponse;
import com.main.apiendpoint_task.service.FeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fees")
public class FeePendingApprovalController {

    private final FeeService feeService;

    public FeePendingApprovalController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping("/approvals")
    public ApiResponse getPrices(){
        return feeService.getAllPrices();
    }
}
