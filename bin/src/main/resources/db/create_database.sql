--------------------------------------------
-- Reset database and user
--------------------------------------------

DROP DATABASE IF EXISTS my_budget_db;

DROP USER IF EXISTS my_budget_user;

--------------------------------------------
-- Create user
--------------------------------------------
CREATE USER my_budget_user WITH PASSWORD 'my_budget_pwd';

--------------------------------------------
-- Create database database_name
--------------------------------------------

CREATE DATABASE my_budget_db
    WITH 
    OWNER = my_budget_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE postgres
    IS 'My vudget database for dev environnement';
-- improve selects on partitionned tables
ALTER DATABASE my_budget_db SET constraint_exclusion=on;
