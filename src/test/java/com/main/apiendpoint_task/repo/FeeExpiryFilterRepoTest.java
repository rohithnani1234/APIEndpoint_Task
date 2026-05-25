package com.main.apiendpoint_task.repo;

import com.main.apiendpoint_task.entity.FeeExpiryFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeeExpiryFilterRepoTest {
    @Mock
    private JdbcClient jdbcClient;
    @Mock
    private JdbcClient.StatementSpec statementSpec;
    @Mock
    private JdbcClient.MappedQuerySpec<FeeExpiryFilter> mappedQuerySpec;
    @InjectMocks
    private FeeExpiryFilterRepo feeExpiryFilterRepo;

    @Test
    void testGetDashboardData_noSearch(){
        List<FeeExpiryFilter> mockList=new ArrayList<>();
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.query(FeeExpiryFilter.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(mockList);

        List<FeeExpiryFilter> result=feeExpiryFilterRepo.getDashboardData(null);
        assertNotNull(result);
        verify(jdbcClient).sql(anyString());
    }

    @Test
    void testGetDashboardData_search(){
        List<FeeExpiryFilter> mockList=new ArrayList<>();
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(eq("search"),any())).thenReturn(statementSpec);
        when(statementSpec.query(FeeExpiryFilter.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(mockList);

        List<FeeExpiryFilter> list=feeExpiryFilterRepo.getDashboardData("client");
        assertNotNull(list);
        verify(statementSpec).param(eq("search"),any());
    }
}