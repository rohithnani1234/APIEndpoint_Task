package com.main.apiendpoint_task.service;

import com.main.apiendpoint_task.entity.FeeExpiryFilter;
import com.main.apiendpoint_task.repo.FeeExpiryFilterRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeeExpiryFilterServiceTest {

    @Mock
    private FeeExpiryFilterRepo feeExpiryFilterRepo;

    @InjectMocks
    private FeeExpiryFilterService feeExpiryFilterService;

//    private FeeExpiryFilter feeExpiryFilter;

    private List<FeeExpiryFilter> getMockData() {
        FeeExpiryFilter f1=new FeeExpiryFilter(
                "ClientA","Fee1", "Q1",LocalDate.now().plusDays(10),"1-month","ONE_MONTH",
                "ACTIVE","John"
        );


        FeeExpiryFilter f2 = new FeeExpiryFilter(
                "ClientB", "Fee2", "Q2",
                LocalDate.now().plusDays(40),
                "3-month", "THREE_MONTH",
                "ACTIVE", "Mike"
        );

        FeeExpiryFilter f3 = new FeeExpiryFilter(
                "ClientC", "Fee3", "Q3",
                LocalDate.now().plusDays(100),
                "6-month", "SIX_MONTH",
                "ACTIVE", "David"
        );

        return Arrays.asList(f1, f2, f3);
    }

    @Test
    void testGetDashboard_basic(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());

        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"expriyDate","asc",0,5,null);
        assertNotNull(result);
        assertTrue(result.containsKey("summary"));
        assertTrue(result.containsKey("data"));

        List<?> data=(List<?>) result.get("data");
        assertEquals(3,data.size());

        verify(feeExpiryFilterRepo,times(1)).getDashboardData(null);
    }

    @Test
    void testSorting_clientName(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());

        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"clientName","asc",0,5,null);
        List<FeeExpiryFilter> list =  (List<FeeExpiryFilter>) result.get("data");
        assertEquals("ClientA",list.get(0).getClientName());
    }

    @Test
    void testSorting_descending(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());
        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"clientName","desc",0,5,null);
        List<FeeExpiryFilter> list =  (List<FeeExpiryFilter>) result.get("data");
        assertEquals("ClientC",list.get(0).getClientName());
    }

    @Test
    void testBucketFilter(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());
        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"expriyDate","asc",0,5,"ONE_MONTH");
        List<FeeExpiryFilter> list =  (List<FeeExpiryFilter>) result.get("data");
        assertEquals(1,list.size());
        assertEquals("ONE_MONTH",list.get(0).getBucket());
    }

    @Test
    void testPagination(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());;
        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"expriyDate","asc",0,2,null);
        List<?> list=(List<?>) result.get("data");
        assertEquals(2,list.size());
    }

    @Test
    void testEmptyData(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());

        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"expriyDate","asc",0,5,null);
        assertEquals(3,result.get("totalElements"));
    }

    @Test
    void testSummary(){
        when(feeExpiryFilterRepo.getDashboardData(null)).thenReturn(getMockData());
        Map<String,Object> result=feeExpiryFilterService.getDashboard(null,"expriyDate","asc",0,5,null);
        Map<String,Integer> summary=(Map<String, Integer>) result.get("summary");
        assertEquals(1,summary.get("oneMonth"));
        assertEquals(1,summary.get("threeMonths"));
        assertEquals(1,summary.get("sixMonths"));
    }
}