package com.main.apiendpoint_task.service;

import com.main.apiendpoint_task.entity.FeePrice;
import com.main.apiendpoint_task.repo.FeeRepo;
import com.main.apiendpoint_task.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeeServiceTest {
    @Mock
    private FeeRepo feeRepo;

    @InjectMocks
    private FeeService feeService;

    private List<FeePrice> getMockData(){
        FeePrice feePrice = new FeePrice(
                1, "Fee A", "Rohith",
                "Changed price",
                new Date(),
                "Manager1",
                "PENDING"
        );
        FeePrice feePrice2 = new FeePrice(
                2, "Fee B", "Kiran",
                "Updated discount",
                new Date(),
                "Manager2",
                "APPROVED"
        );
        return Arrays.asList(feePrice,feePrice2);
    }

    @Test
    void testGetAllPrices_Success(){
        List<FeePrice> mockList=getMockData();

        when(feeRepo.getAllFeePrices()).thenReturn(mockList);
        ApiResponse response=feeService.getAllPrices();

        assertNotNull(response);
        assertEquals("success",response.getStatus());
        assertEquals("All fee approval details fetched successfully",response.getMessage());

        assertNotNull(response.getData());
        assertNotNull(response.getData().getFeeApprovalDetails());
        assertEquals(2,response.getData().getFeeApprovalDetails().size());

        verify(feeRepo,times(1)).getAllFeePrices();
    }
}