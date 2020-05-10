begin transaction;
-- category

CREATE SEQUENCE seq_category START 71;
CREATE TABLE category (
	id int8 NOT NULL,
	category_label varchar(64) NOT NULL,
	category_state varchar(25) NULL,
	category_total_cost numeric(10,2) NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id)
);

-- sub_category
CREATE SEQUENCE seq_sub_category START 93;
CREATE TABLE sub_category (
	id int8 NOT NULL,
	sub_category_label varchar(64) NOT NULL,
	sub_category_state varchar(25) NULL,
	sub_category_total_cost numeric(10,2) NOT NULL,
	category int8 NULL,
	CONSTRAINT sub_category_pkey PRIMARY KEY (id),
	CONSTRAINT fk_category_ FOREIGN KEY (category) REFERENCES category(id)
);


-- family

CREATE SEQUENCE seq_family START 86;
CREATE TABLE family (
	id int8 NOT NULL,
	code varchar(10) NULL,
	pwd varchar(30) NULL,
	CONSTRAINT family_pkey PRIMARY KEY (id)
);

-- item
CREATE SEQUENCE seq_item START 650;
CREATE TABLE item (
	id int8 NOT NULL,
	date_item date NOT NULL,
	expected_amount numeric(8,2) NOT NULL,
	expected_quantity int4 NOT NULL,
	item_labelle varchar(128) NOT NULL,
	sub_category int8 NULL,
	item_status varchar(25) NULL,
	family int8 NULL,
	CONSTRAINT item_pkey PRIMARY KEY (id),
	CONSTRAINT fk_sub_category_ FOREIGN KEY (sub_category) REFERENCES sub_category(id),
	CONSTRAINT fk_family_ FOREIGN KEY (family) REFERENCES family(id)
);


-- shopping_list
CREATE SEQUENCE seq_shopping_list START 34;
CREATE TABLE shopping_list (
	id int8 NOT NULL,
	allocated_amount numeric(10,2) NOT NULL,
	date_created date NOT NULL,
	shopping_list_name varchar(130) NULL,
	family int8 NULL,
	CONSTRAINT shopping_list_pkey PRIMARY KEY (id),	
	CONSTRAINT fk_family_ FOREIGN KEY (family) REFERENCES family(id)
);

-- item_shopping_list
CREATE SEQUENCE seq_item_shopping_list START 120;
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

commit;
