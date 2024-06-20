package com.example.demo.repository;

import com.example.demo.entity.VehicleInfo;
import com.example.demo.entity.BatteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VehicleInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public VehicleInfo findByCarId(Integer carId) {
        String sql = "SELECT car_id, vid, battery_type, total_mileage, battery_health_state FROM vehicle_info WHERE car_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{carId}, new VehicleInfoRowMapper());
    }

    private static class VehicleInfoRowMapper implements RowMapper<VehicleInfo> {
        @Override
        public VehicleInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleInfo vehicleInfo = new VehicleInfo();
            vehicleInfo.setCarId(rs.getInt("car_id"));
            vehicleInfo.setVid(rs.getString("vid"));
            vehicleInfo.setBatteryType(BatteryType.valueOf(rs.getString("battery_type")));
            vehicleInfo.setTotalMileage(rs.getInt("total_mileage"));
            vehicleInfo.setBatteryHealthState(rs.getInt("battery_health_state"));
            return vehicleInfo;
        }
    }
}
