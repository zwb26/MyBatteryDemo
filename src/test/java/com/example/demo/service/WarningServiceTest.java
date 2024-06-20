package com.example.demo.service;

import com.example.demo.entity.BatteryType;
import com.example.demo.entity.VehicleInfo;
import com.example.demo.entity.WarningRules;
import com.example.demo.repository.VehicleInfoRepository;
import com.example.demo.repository.WarningRulesRepository;
import com.example.demo.vo.WarningRequest;
import com.example.demo.vo.WarningResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WarningServiceTest {

    @Mock
    private VehicleInfoRepository vehicleInfoRepository;

    @Mock
    private WarningRulesRepository warningRulesRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WarningService warningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessWarnings_ValidInput() throws Exception {
        WarningRequest request = new WarningRequest();
        request.setCarId(1);
        request.setWarnId(1);
        request.setSignal("{\"Mx\":12.0,\"Mi\":0.6}");

        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setCarId(1);
        vehicleInfo.setBatteryType(BatteryType.TERNARY);

        WarningRules rule = new WarningRules();
        rule.setBatteryType(BatteryType.TERNARY);
        rule.setRuleDescription("{\"rules\":[{\"threshold\":10.0, \"level\":2}]}");
        rule.setName("High voltage difference");

        when(vehicleInfoRepository.findByCarId(1)).thenReturn(vehicleInfo);
        when(warningRulesRepository.findByRuleId(1)).thenReturn(Collections.singletonList(rule));
        when(redisTemplate.opsForValue()).thenReturn(mock(ValueOperations.class));
        when(objectMapper.readTree(anyString())).thenAnswer(invocation -> {
            String json = invocation.getArgument(0, String.class);
            return new ObjectMapper().readTree(json);
        });
        when(objectMapper.convertValue(any(), eq(Map.class))).thenAnswer(invocation -> {
            JsonNode jsonNode = invocation.getArgument(0, JsonNode.class);
            return new ObjectMapper().convertValue(jsonNode, Map.class);
        });

        List<WarningResponse> responses = warningService.processWarnings(Collections.singletonList(request));

        assertNotNull(responses);
        assertEquals(1, responses.size());
        WarningResponse response = responses.get(0);
        assertEquals(1, response.getCarId());
        assertEquals("三元电池", response.getBatteryType());
        assertEquals("High voltage difference", response.getWarnName());
        assertEquals(2, response.getWarnLevel());
    }

    // Add more tests for edge cases and error handling
}
