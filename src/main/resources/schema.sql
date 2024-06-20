USE demo_v5;

DROP TABLE IF EXISTS vehicle_info;
CREATE TABLE vehicle_info (
                              car_id INT AUTO_INCREMENT PRIMARY KEY,
                              vid VARCHAR(16) NOT NULL,
                              battery_type ENUM('TERNARY', 'LFP') NOT NULL,
                              total_mileage INT NOT NULL,
                              battery_health_state INT NOT NULL
);

INSERT INTO vehicle_info (vid, battery_type, total_mileage, battery_health_state) VALUES
                                                                                      ('1', 'TERNARY', 100, 100),
                                                                                      ('2', 'LFP', 600, 95),
                                                                                      ('3', 'TERNARY', 300, 98);

DROP TABLE IF EXISTS warning_rules;
CREATE TABLE warning_rules (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               rule_id INT NOT NULL,
                               name VARCHAR(255) NOT NULL,
                               battery_type ENUM('TERNARY', 'LFP') NOT NULL,
                               rule_description TEXT NOT NULL
);

INSERT INTO warning_rules (rule_id, name, battery_type, rule_description) VALUES
                                                                              (1, '电压差报警', 'TERNARY', '{"rules": [{"threshold": 5, "level": 0}, {"threshold": 3, "level": 1}, {"threshold": 1, "level": 2}, {"threshold": 0.6, "level": 3}, {"threshold": 0.2, "level": 4}, {"threshold": 0, "level": 5}]}'),
                                                                              (2, '电压差报警', 'LFP', '{"rules": [{"threshold": 2, "level": 0}, {"threshold": 1, "level": 1}, {"threshold": 0.7, "level": 2}, {"threshold": 0.4, "level": 3}, {"threshold": 0.2, "level": 4}, {"threshold": 0, "level": 5}]}'),
                                                                              (3, '电流差报警', 'TERNARY', '{"rules": [{"threshold": 3, "level": 0}, {"threshold": 1, "level": 1}, {"threshold": 0.2, "level": 2}, {"threshold": 0, "level": 5}]}'),
                                                                              (4, '电流差报警', 'LFP', '{"rules": [{"threshold": 1, "level": 0}, {"threshold": 0.5, "level": 1}, {"threshold": 0.2, "level": 2}, {"threshold": 0, "level": 5}]}');
