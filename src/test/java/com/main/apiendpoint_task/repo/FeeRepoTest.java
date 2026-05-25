package com.main.apiendpoint_task.repo;

import com.main.apiendpoint_task.entity.FeePrice;
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
class FeeRepoTest {
    @Mock
    private JdbcClient jdbcClient;
    @Mock
    private JdbcClient.StatementSpec statementSpec;
    @Mock
    private JdbcClient.MappedQuerySpec<FeePrice> mappedQuerySpec;
    @InjectMocks
    private FeeRepo feeRepo;

    @Test
    void testGetAllPrices(){
        List<FeePrice> mockList=new ArrayList<>();

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.query(FeePrice.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(mockList);

        List<FeePrice> list = feeRepo.getAllFeePrices();
        assertNotNull(list);
        verify(jdbcClient).sql(anyString());
    }
}