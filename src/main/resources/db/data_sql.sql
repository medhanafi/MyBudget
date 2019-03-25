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


--TABLES
CREATE TABLE category (
	id int8 NOT NULL,
	category_label varchar(64) NOT NULL,
	category_state varchar(25) NULL,
	category_total_cost numeric(10,2) NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE sub_category (
	id int8 NOT NULL,
	sub_category_label varchar(64) NOT NULL,
	sub_category_state varchar(25) NULL,
	sub_category_total_cost numeric(10,2) NOT NULL,
	category int8 NULL,
	CONSTRAINT sub_category_pkey PRIMARY KEY (id),
	CONSTRAINT fk_category_ FOREIGN KEY (category) REFERENCES category(id)
);

CREATE TABLE item (
	id int8 NOT NULL,
	date_item date NOT NULL,
	expected_amount numeric(8,2) NOT NULL,
	expected_quantity int4 NOT NULL,
	item_labelle varchar(128) NOT NULL,
	sub_category int8 NULL,
	item_status varchar(25) NULL,
	CONSTRAINT item_pkey PRIMARY KEY (id),
	CONSTRAINT fk_sub_category_ FOREIGN KEY (sub_category) REFERENCES sub_category(id)
);

CREATE TABLE item_shopping_list (
	id int8 NOT NULL,
	actual_amount numeric(10,2) NULL,
	actual_quantity int4 NULL,
	purchased_date date NULL,
	item int8 NOT NULL,
	shopping_list int8 NOT NULL,
	CONSTRAINT item_shopping_list_pkey PRIMARY KEY (id),
	CONSTRAINT fk_item_ FOREIGN KEY (item) REFERENCES item(id),
	CONSTRAINT fk_shooping_list_ FOREIGN KEY (shopping_list) REFERENCES shopping_list(id)
);


CREATE TABLE shopping_list (
	id int8 NOT NULL,
	allocated_amount numeric(10,2) NOT NULL,
	date_created date NOT NULL,
	shopping_list_name varchar(130) NULL,
	CONSTRAINT shopping_list_pkey PRIMARY KEY (id)
);



-- DROP SEQUENCE seq_category;

CREATE SEQUENCE seq_category
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;

-- DROP SEQUENCE seq_item;

CREATE SEQUENCE seq_item
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

-- DROP SEQUENCE seq_item_shopping_list;

CREATE SEQUENCE seq_item_shopping_list
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

-- DROP SEQUENCE seq_shopping_list;

CREATE SEQUENCE seq_shopping_list
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;

-- DROP SEQUENCE seq_sub_category;

CREATE SEQUENCE seq_sub_category
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807;


-- Calculate of subCatotalCost from all items

-- drop function sub_category_update cascade;
-- DROP TRIGGER IF EXISTS sub_category_update ON item_shopping_list;

-- CREATE FUNCTION sub_category_update() RETURNS trigger AS $sub_category_update$
--     BEGIN
-- 		update sub_category set sub_category_total_cost=(
-- with item_tab as (
-- 	select id, item_labelle, 
-- 	case when (select  sum(actual_amount*actual_quantity)  from item_shopping_list where item=i.id) is null then 0 
-- 	else (select  sum(actual_amount*actual_quantity)  from item_shopping_list where item=i.id) end as actual , sub_category  from item i
-- 	where item_labelle!='salaire')
-- select case when sum(actual) is null then 0 else sum(actual) end
-- from item_tab it where it.sub_category=sub_category.id);

-- return new;
--     END;
-- $sub_category_update$ LANGUAGE plpgsql;


-- CREATE TRIGGER sub_category_update BEFORE INSERT OR update or delete ON item_shopping_list
--     EXECUTE PROCEDURE sub_category_update();
   
--    

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
   