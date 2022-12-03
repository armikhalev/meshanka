--
-- PostgreSQL database cluster dump
--

-- Started on 2022-09-08 12:07:51 EDT

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--


-- For binary upgrade, must preserve pg_authid.oid
SELECT pg_catalog.binary_upgrade_set_next_pg_authid_oid('10'::pg_catalog.oid);

ALTER ROLE "megatron" WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;






-- Completed on 2022-09-08 12:07:51 EDT

--
-- PostgreSQL database cluster dump complete
--

