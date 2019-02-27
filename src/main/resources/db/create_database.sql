-- Run as admin

--------------------------------------------
-- Reset database_name
--------------------------------------------

DROP DATABASE IF EXISTS my_budget;

DROP TABLESPACE IF EXISTS tbs_my_budget;
--DROP TABLESPACE IF EXISTS tbs_my_budget_data;
--DROP TABLESPACE IF EXISTS tbs_my_budget_index;


--------------------------------------------
-- Create user database_user
--------------------------------------------
DROP OWNED BY my_budget CASCADE;
DROP USER IF EXISTS my_budget;
CREATE USER my_budget WITH PASSWORD 'my_budget';

--------------------------------------------
-- Create database database_name
--------------------------------------------

CREATE DATABASE my_budget
  WITH OWNER = my_budget
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'French_France.1252'
       LC_CTYPE = 'French_France.1252'
       CONNECTION LIMIT = -1;

-- create tablespaces
CREATE TABLESPACE tbs_my_budget LOCATION 'C:\my_budget_data';

-- add rights
GRANT ALL ON TABLESPACE tbs_my_budget TO my_budget;
--GRANT ALL ON TABLESPACE tbs_my_budget_data TO my_budget;
--GRANT ALL ON TABLESPACE tbs_my_budget_index TO my_budget;

-- improve selects on partitionned tables
ALTER DATABASE my_budget SET constraint_exclusion=on;
