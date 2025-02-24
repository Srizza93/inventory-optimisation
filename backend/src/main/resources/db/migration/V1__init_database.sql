CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS inventory_parameters (
    id VARCHAR(255) PRIMARY KEY DEFAULT gen_random_uuid(),
    delivery_delay INT NOT NULL,
    package_format INT NOT NULL,
    working_days_consumption INT NOT NULL,
    weekend_consumption INT NOT NULL,
    purchase_day VARCHAR(255) NOT NULL
);

INSERT INTO inventory_parameters
    (id, delivery_delay, package_format, working_days_consumption, weekend_consumption, purchase_day)
    VALUES (gen_random_uuid(), 2, 2, 3, 4, 'SUNDAY');
