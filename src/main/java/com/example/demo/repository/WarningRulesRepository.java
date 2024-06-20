package com.example.demo.repository;

import com.example.demo.entity.BatteryType;
import com.example.demo.entity.WarningRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WarningRulesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<WarningRules> findByRuleId(Integer ruleId) {
        String sql = "SELECT id, rule_id, name, battery_type, rule_description FROM warning_rules WHERE rule_id = ?";
        return jdbcTemplate.query(sql, new Object[]{ruleId}, new WarningRulesRowMapper());
    }

    public List<WarningRules> findByBatteryType(BatteryType batteryType) {
        String sql = "SELECT id, rule_id, name, battery_type, rule_description FROM warning_rules WHERE battery_type = ?";
        return jdbcTemplate.query(sql, new Object[]{batteryType.name()}, new WarningRulesRowMapper());
    }

    private static class WarningRulesRowMapper implements RowMapper<WarningRules> {
        @Override
        public WarningRules mapRow(ResultSet rs, int rowNum) throws SQLException {
            WarningRules warningRules = new WarningRules();
            warningRules.setId(rs.getInt("id"));
            warningRules.setRuleId(rs.getInt("rule_id"));
            warningRules.setName(rs.getString("name"));
            warningRules.setBatteryType(BatteryType.valueOf(rs.getString("battery_type")));
            warningRules.setRuleDescription(rs.getString("rule_description"));
            return warningRules;
        }
    }
}
