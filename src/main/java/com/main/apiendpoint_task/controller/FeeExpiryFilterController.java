package com.main.apiendpoint_task.controller;

import com.main.apiendpoint_task.service.FeeExpiryFilterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fees")
public class FeeExpiryFilterController {

    private final FeeExpiryFilterService feeExpiryFilterService;

    public FeeExpiryFilterController(FeeExpiryFilterService feeExpiryFilterService) {
        this.feeExpiryFilterService = feeExpiryFilterService;
    }

    @GetMapping("/expiry-dashboard")
    public Map<String, Object> getDashboard(@RequestParam(required = false) String search,
                                            @RequestParam(defaultValue = "expiryDate") String sortBy,
                                            @RequestParam(defaultValue = "asc")String direction,
                                            @RequestParam(defaultValue = "0")int page,
                                            @RequestParam(defaultValue = "5")int size,
                                            @RequestParam(required = false)String bucket){
        return feeExpiryFilterService.getDashboard(search,sortBy,direction,page,size,bucket);
    }
}
