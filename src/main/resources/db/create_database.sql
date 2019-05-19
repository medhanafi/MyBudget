--------------------------------------------
-- Reset database and user
--------------------------------------------

DROP DATABASE IF EXISTS my_budget_db;

DROP OWNED BY my_budget_db CASCADE;
DROP USER IF EXISTS my_budget_db;

--------------------------------------------
-- Create user
--------------------------------------------
CREATE USER my_budget_user WITH PASSWORD 'my_budget_pwd';

--------------------------------------------
-- Create database database_name
--------------------------------------------

CREATE DATABASE my_budget_db
  WITH OWNER = my_budget_user
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;


-- improve selects on partitionned tables
ALTER DATABASE my_budget_db SET constraint_exclusion=on;
