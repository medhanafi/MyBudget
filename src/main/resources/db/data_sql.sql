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





-- DROP SEQUENCE public.seq_category;

CREATE SEQUENCE public.seq_category
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;

-- DROP SEQUENCE public.seq_item;

CREATE SEQUENCE public.seq_item
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

-- DROP SEQUENCE public.seq_item_shopping_list;

CREATE SEQUENCE public.seq_item_shopping_list
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

-- DROP SEQUENCE public.seq_shopping_list;

CREATE SEQUENCE public.seq_shopping_list
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

-- DROP SEQUENCE public.seq_sub_category;

CREATE SEQUENCE public.seq_sub_category
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

	
	
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(1, 'Revenus', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(3, 'Foyer', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(4, 'Vie quotidienne', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(5, 'Enfants', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(6, 'Transport	', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(7, 'Santé', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(8, 'Assurance', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(9, 'Enseignement', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(10, 'Charité', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(11, 'Epargne', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(12, 'Loisirs', 'active', 0);
INSERT INTO category (id, category_label, category_state, category_total_cost) VALUES(13, 'Vacances', 'active', 0);

-- Calculate of subCatotalCost from all items

drop function sub_category_update cascade;
DROP TRIGGER IF EXISTS sub_category_update ON item;

CREATE FUNCTION sub_category_update() RETURNS trigger AS $sub_category_update$
    BEGIN
      		update sub_category set sub_category_total_cost=(
select case when sum(expected_amount) is null then 0 else sum(expected_amount) end 
from item where item_labelle!='Salaire open' and item_labelle!='Virement' and sub_category=sub_category.id);

return new;
    END;
$sub_category_update$ LANGUAGE plpgsql;



CREATE TRIGGER sub_category_update BEFORE INSERT OR update or delete ON item
    EXECUTE PROCEDURE sub_category_update();
   


    
    -- Calculate of CatotalCost from all subcat

drop function category_update cascade;
DROP TRIGGER IF EXISTS category_update ON sub_category;

CREATE FUNCTION category_update() RETURNS trigger AS $category_update$
    BEGIN
      		update category set category_total_cost=(
select case when sum(sub_category_total_cost) is null then 0 else sum(sub_category_total_cost) end 
from sub_category where sub_category_label!='Salaires et pourboires' and category=category.id);

return new;
    END;
$category_update$ LANGUAGE plpgsql;



CREATE TRIGGER category_update BEFORE INSERT OR update or delete ON sub_category
    EXECUTE PROCEDURE category_update();
   

