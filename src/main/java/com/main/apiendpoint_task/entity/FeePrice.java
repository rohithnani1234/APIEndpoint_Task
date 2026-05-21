package com.main.apiendpoint_task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FeePrice {
    private int id;
    private String feeLabel;
    private String submittedBy;
    private String changeSummary;
    private Date submittedOn;
    private String approverAssigned;
    private String status;
}
