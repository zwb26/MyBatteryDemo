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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WarningService {

    @Autowired
    private VehicleInfoRepository vehicleInfoRepository;

    @Autowired
    private WarningRulesRepository warningRulesRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<WarningResponse> processWarnings(List<WarningRequest> requests) {
        List<WarningResponse> responses = new ArrayList<>();

        for (WarningRequest request : requests) {
            if (request.getCarId() == null) {
                throw new IllegalArgumentException("CarId must not be null");
            }

            VehicleInfo vehicle = vehicleInfoRepository.findByCarId(request.getCarId());
            if (vehicle == null) {
                throw new RuntimeException("Vehicle not found for CarId: " + request.getCarId());
            }

            List<WarningRules> rules;

            if (request.getWarnId() != null) {
                rules = getRulesFromCache(request.getWarnId());
            } else {
                rules = warningRulesRepository.findByBatteryType(vehicle.getBatteryType());
            }

            Map<String, Double> signalMap = parseSignal(request.getSignal());

            for (WarningRules rule : rules) {
                WarningResponse response = new WarningResponse();
                response.setCarId(request.getCarId());
                response.setBatteryType(getBatteryTypeName(vehicle.getBatteryType()));
                response.setWarnName(rule.getName());
                response.setWarnLevel(calculateWarningLevel(rule.getRuleDescription(), signalMap));
                responses.add(response);
            }
        }
        return responses;
    }

    List<WarningRules> getRulesFromCache(Integer ruleId) {
        String cacheKey = "warn_rules_" + ruleId;
        List<WarningRules> rules = (List<WarningRules>) redisTemplate.opsForValue().get(cacheKey);
        if (rules == null) {
            rules = warningRulesRepository.findByRuleId(ruleId);
            redisTemplate.opsForValue().set(cacheKey, rules);
        }
        return rules;
    }

    Map<String, Double> parseSignal(String signal) {
        try {
            JsonNode jsonNode = objectMapper.readTree(signal);
            return objectMapper.convertValue(jsonNode, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid signal format", e);
        }
    }

    int calculateWarningLevel(String ruleDescription, Map<String, Double> signalMap) {
        try {
            JsonNode ruleJson = objectMapper.readTree(ruleDescription);
            if (ruleJson == null || !ruleJson.has("rules")) {
                throw new RuntimeException("Invalid rule description format");
            }
            JsonNode rules = ruleJson.get("rules");

            for (JsonNode rule : rules) {
                double threshold = rule.get("threshold").asDouble();
                int level = rule.get("level").asInt();

                if (signalMap.containsKey("Mx") && signalMap.containsKey("Mi")) {
                    double difference = signalMap.get("Mx") - signalMap.get("Mi");
                    if (difference >= threshold) {
                        return level;
                    }
                } else if (signalMap.containsKey("Ix") && signalMap.containsKey("Ii")) {
                    double difference = signalMap.get("Ix") - signalMap.get("Ii");
                    if (difference >= threshold) {
                        return level;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse rule description", e);
        }
        return 5; // Default level when no rule matches
    }

    private String getBatteryTypeName(BatteryType batteryType) {
        switch (batteryType) {
            case TERNARY:
                return "三元电池";
            case LFP:
                return "铁锂电池";
            default:
                return "未知电池类型";
        }
    }
}
