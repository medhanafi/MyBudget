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


INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(14, 'Loyer', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(15, 'Electricité/gaz', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(16, 'Eau', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(17, 'Téléphone/Abonnement', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(18, 'Internet/Câble/TV', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(19, 'Mobilier/électroménager', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(20, 'Produits pour la maison', 'active', 0, 3);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(21, 'Traites du véhicule', 'active', 0, 6);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(22, 'Carburant', 'active', 0, 6);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(23, 'Bus/taxi/train', 'active', 0, 6);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(24, 'Réparations', 'active', 0, 6);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(25, 'Immatriculation', 'active', 0, 6);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(26, 'Scolarité', 'active', 0, 9);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(27, 'Livres', 'active', 0, 9);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(28, 'Sortie scolaire', 'active', 0, 9);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(29, 'Courses', 'active', 0, 4);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(30, 'Fournitures personnelles', 'active', 0, 4);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(31, 'Vêtements', 'active', 0, 4);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(32, 'Services de nettoyage', 'active', 0, 4);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(33, 'Restaurants', 'active', 0, 4);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(34, 'Docteur/dentiste', 'active', 0, 7);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(35, 'Médicaments', 'active', 0, 7);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(36, 'Club de sport', 'active', 0, 7);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(37, 'Urgence', 'active', 0, 7);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(38, 'Cadeaux', 'active', 0, 10);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(39, 'Dons', 'active', 0, 10);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(40, 'Zakat', 'active', 0, 10);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(41, 'autre', 'active', 0, 10);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(42, 'Vidéos/dvd', 'active', 0, 12);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(43, 'Musique', 'active', 0, 12);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(44, 'Jeux', 'active', 0, 12);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(45, 'Voyage', 'active', 0, 13);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(46, 'Hébergement', 'active', 0, 13);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(47, 'Nourriture', 'active', 0, 13);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(48, 'Soins', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(49, 'Vêtements', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(50, 'Droits de scolarité', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(51, 'Cantine scolaire', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(52, 'Fournitures scolaires', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(53, 'Babysitting', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(54, 'Jouets', 'active', 0, 5);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(55, 'Auto', 'active', 0, 8);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(56, 'Foyer/loyer', 'active', 0, 8);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(57, 'Fonds d''urgence', 'active', 0, 11);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(58, 'Virements sur livret', 'active', 0, 11);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(59, 'Investissements', 'active', 0, 11);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(60, 'faculté', 'active', 0, 11);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(61, 'Salaires et pourboires', 'active', 0, 1);
INSERT INTO sub_category (id, sub_category_label, sub_category_state, sub_category_total_cost, category) VALUES(62, 'Virements sur livret', 'active', 0, 1);


