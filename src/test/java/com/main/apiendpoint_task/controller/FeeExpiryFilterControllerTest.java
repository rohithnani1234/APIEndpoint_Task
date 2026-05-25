package com.main.apiendpoint_task.controller;

import com.main.apiendpoint_task.service.FeeExpiryFilterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeeExpiryFilterController.class)
class FeeExpiryFilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeExpiryFilterService feeExpiryFilterService;

    @Test
    void testGetDashboard_withAllParamas() throws Exception {
        Map<String,Object> mockResponse=new HashMap<>();
        mockResponse.put("page",0);
        mockResponse.put("size",5);

        when(feeExpiryFilterService.getDashboard("john","expiryDate","asc",0,5,"ONE_MONTH")).thenReturn(mockResponse);

        mockMvc.perform(get("/fees/expiry-dashboard")
                .param("search","john")
                .param("sortBy","expiryDate")
                .param("direction","asc")
                .param("page","0")
                .param("size","5")
                .param("bucket","ONE_MONTH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(5));
        verify(feeExpiryFilterService,times(1))
                .getDashboard("john","expiryDate","asc",0,5,"ONE_MONTH");
    }

    @Test
    void testGetDashboard_withDefaultParams() throws Exception {
          Map<String,Object> mockResponse=new HashMap<>();
          mockResponse.put("page",0);
          mockResponse.put("size",5);

          when(feeExpiryFilterService.getDashboard(null,"expiryDate","asc",0,5,null)).thenReturn(mockResponse);

          mockMvc.perform(get("/fees/expiry-dashboard"))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$.page").value(0))
                  .andExpect(jsonPath("$.size").value(5));
          verify(feeExpiryFilterService,times(1)).getDashboard(null,"expiryDate","asc",0,5,null);
    }

    @Test
    void testGetDashboard_emptyResponse() throws Exception {
        when(feeExpiryFilterService.getDashboard(null,"expiryDate","asc",0,5,null)).thenReturn(new HashMap<>());

        mockMvc.perform(get("/fees/expiry-dashboard"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

    }

    @Test
    void testGetDashboard_exception() throws Exception{
        when(feeExpiryFilterService.getDashboard(any(),any(),any(), anyInt(),anyInt(),any())).thenThrow(new RuntimeException("Error Occuered"));
//        mockMvc.perform(get("/fees/expiry-dashboard"))
//                .andExpect(status().isInternalServerError());
        assertThrows(Exception.class,()->{
            mockMvc.perform(get("/fees/expiry-dashboard"));
        });
    }
}