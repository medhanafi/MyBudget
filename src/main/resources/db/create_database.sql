--------------------------------------------
-- Reset database and user
--------------------------------------------

DROP DATABASE IF EXISTS budget_dev_db;

DROP OWNED BY budget_dev_db CASCADE;
DROP USER IF EXISTS budget_dev_user;

--------------------------------------------
-- Create user
--------------------------------------------
CREATE USER budget_dev_user WITH PASSWORD 'budget_dev_pwd';

--------------------------------------------
-- Create database database_name
--------------------------------------------

CREATE DATABASE budget_dev_db
  WITH OWNER = budget_dev_user
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;


-- improve selects on partitionned tables
ALTER DATABASE budget_dev_db SET constraint_exclusion=on;
