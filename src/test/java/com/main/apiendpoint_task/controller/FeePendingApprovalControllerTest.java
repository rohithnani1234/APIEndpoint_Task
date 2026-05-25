package com.main.apiendpoint_task.controller;

import com.main.apiendpoint_task.response.ApiResponse;
import com.main.apiendpoint_task.service.FeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeePendingApprovalController.class)
class FeePendingApprovalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeService feeService;

    @Test
    void testGetPrices_success() throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus("success");
        apiResponse.setMessage("All fee approval details fetched successfully");

        when(feeService.getAllPrices()).thenReturn(apiResponse);

        mockMvc.perform(get("/fees/approvals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("All fee approval details fetched successfully"));

        verify(feeService,times(1)).getAllPrices();

    }
}