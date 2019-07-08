--
-- PostgreSQL database dump
--
--   pg_dump -h10.0.101.244 -Upostgres -p5432   --no-privileges   --no-owner  --file=security_demo.sql --dbname=security_demo
-- Dumped from database version 10.6
-- Dumped by pg_dump version 10.9 (Ubuntu 10.9-1.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.permission (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    gmt_create timestamp(0) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    gmt_modified timestamp(0) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- Name: permission_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.permission_id_seq OWNED BY public.permission.id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    gmt_create timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    gmt_modified timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: role_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.role_permission (
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL
);


--
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."user" (
    id bigint NOT NULL,
    username character varying NOT NULL,
    name character varying,
    email character varying,
    mobile character varying,
    gmt_create timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    gmt_modified timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_password_reset_date timestamp with time zone NOT NULL,
    enabled smallint DEFAULT 0 NOT NULL,
    password character varying(255) NOT NULL
);


--
-- Name: COLUMN "user".enabled; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public."user".enabled IS '是否启用，默认0';


--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- Name: user_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- Name: permission id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permission ALTER COLUMN id SET DEFAULT nextval('public.permission_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.permission (id, name, description, gmt_create, gmt_modified) FROM stdin;
1	can_list_user	可以查看所有用户	2019-05-29 18:03:57+08	2019-05-29 18:03:57+08
2	can_test	可以访问测试	2019-05-29 18:04:13+08	2019-05-29 18:04:13+08
3	can_manage_user	可以管理用户	2019-05-29 20:45:35+08	2019-05-29 20:45:35+08
4	can_manage_menu	可以管理菜单	2019-05-29 20:45:42+08	2019-05-29 20:45:42+08
5	can_manage_resource	可以管理资源	2019-05-29 20:47:34+08	2019-05-29 20:47:34+08
6	admin	admin	2019-06-12 17:03:01+08	2019-06-12 17:03:01+08
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.role (id, name, description, gmt_create, gmt_modified) FROM stdin;
1	Admin	管理员	2019-05-29 18:00:40.606921+08	2019-05-29 18:00:40.606921+08
2	Tester	测试员	2019-05-29 18:00:53.142858+08	2019-05-29 18:00:53.142858+08
\.


--
-- Data for Name: role_permission; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.role_permission (role_id, permission_id) FROM stdin;
1	1
1	2
1	3
1	4
2	2
1	6
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."user" (id, username, name, email, mobile, gmt_create, gmt_modified, last_password_reset_date, enabled, password) FROM stdin;
2	ryan	Ryan Miao	ryan@demo.com	123	2019-05-29 17:51:44.997224+08	2019-05-29 17:51:44.997224+08	2019-05-29 17:50:31+08	0	$2a$10$UlHogW/QSdARkLXiCBNECOKSGOqkbI764KHV8QmHkuDkr4kYKAQEy
5	admin	Admin	admin@demo.com	789	2019-05-29 17:52:38.299833+08	2019-05-29 17:52:38.299833+08	2019-05-29 17:51:20+08	0	$2a$10$.qWTS46mRGbKiCJviVIBnuiIbOG/YUB863GwRdx/zwESZGFg474nK
4	test	Test	test@demo.com	456	2019-05-29 17:52:08.980826+08	2019-05-29 17:52:08.980826+08	2019-05-29 17:50:53+08	0	$2a$10$Dq8oj3i/KtXt2fJirWNr/OaRVfoY.oOkOUMSnQWL2Dnj.GLkujui6
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.user_role (user_id, role_id) FROM stdin;
5	1
4	2
\.


--
-- Name: permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.permission_id_seq', 5, true);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.role_id_seq', 2, true);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.user_id_seq', 5, true);


--
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: permission uniq_permission_name; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT uniq_permission_name UNIQUE (name);


--
-- Name: role uniq_role_name; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT uniq_role_name UNIQUE (name);


--
-- Name: CONSTRAINT uniq_role_name ON role; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON CONSTRAINT uniq_role_name ON public.role IS '角色名称唯一';


--
-- Name: user_role uniq_user_role; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT uniq_user_role UNIQUE (user_id, role_id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: uniq_role_permission; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX uniq_role_permission ON public.role_permission USING btree (role_id, permission_id);


--
-- Name: uniq_username; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX uniq_username ON public."user" USING btree (username);


--
-- Name: INDEX uniq_username; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON INDEX public.uniq_username IS '用户名唯一';


--
-- PostgreSQL database dump complete
--

