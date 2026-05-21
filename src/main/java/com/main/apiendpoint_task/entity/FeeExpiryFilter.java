package com.main.apiendpoint_task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeExpiryFilter {

    private String clientName;
    private String feeLabel;
    private String feeQualifier;
    private LocalDate expiryDate;
    private String window;
    public String bucket;
    private String status;
    private String aeName;

}
