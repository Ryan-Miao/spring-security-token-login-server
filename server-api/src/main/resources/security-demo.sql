/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.101.244
 Source Server Type    : PostgreSQL
 Source Server Version : 100006
 Source Host           : 10.0.101.244:5432
 Source Catalog        : security_demo
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100006
 File Encoding         : 65001

 Date: 12/06/2019 20:56:30
*/


-- ----------------------------
-- Sequence structure for permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."permission_id_seq";
CREATE SEQUENCE "public"."permission_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."role_id_seq";
CREATE SEQUENCE "public"."role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_id_seq";
CREATE SEQUENCE "public"."user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."permission";
CREATE TABLE "public"."permission" (
  "id" int8 NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "gmt_create" timestamptz(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO "public"."permission" VALUES (1, 'can_list_user', '可以查看所有用户', '2019-05-29 18:03:57+08', '2019-05-29 18:03:57+08');
INSERT INTO "public"."permission" VALUES (2, 'can_test', '可以访问测试', '2019-05-29 18:04:13+08', '2019-05-29 18:04:13+08');
INSERT INTO "public"."permission" VALUES (3, 'can_manage_user', '可以管理用户', '2019-05-29 20:45:35+08', '2019-05-29 20:45:35+08');
INSERT INTO "public"."permission" VALUES (4, 'can_manage_menu', '可以管理菜单', '2019-05-29 20:45:42+08', '2019-05-29 20:45:42+08');
INSERT INTO "public"."permission" VALUES (5, 'can_manage_resource', '可以管理资源', '2019-05-29 20:47:34+08', '2019-05-29 20:47:34+08');
INSERT INTO "public"."permission" VALUES (6, 'admin', 'admin', '2019-06-12 17:03:01+08', '2019-06-12 17:03:01+08');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS "public"."role";
CREATE TABLE "public"."role" (
  "id" int8 NOT NULL DEFAULT nextval('role_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO "public"."role" VALUES (1, 'Admin', '管理员', '2019-05-29 18:00:40.606921+08', '2019-05-29 18:00:40.606921+08');
INSERT INTO "public"."role" VALUES (2, 'Tester', '测试员', '2019-05-29 18:00:53.142858+08', '2019-05-29 18:00:53.142858+08');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."role_permission";
CREATE TABLE "public"."role_permission" (
  "role_id" int8 NOT NULL,
  "permission_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO "public"."role_permission" VALUES (1, 1);
INSERT INTO "public"."role_permission" VALUES (1, 2);
INSERT INTO "public"."role_permission" VALUES (1, 3);
INSERT INTO "public"."role_permission" VALUES (1, 4);
INSERT INTO "public"."role_permission" VALUES (2, 2);
INSERT INTO "public"."role_permission" VALUES (1, 6);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "id" int8 NOT NULL DEFAULT nextval('user_id_seq'::regclass),
  "username" varchar COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar COLLATE "pg_catalog"."default",
  "email" varchar COLLATE "pg_catalog"."default",
  "mobile" varchar COLLATE "pg_catalog"."default",
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "last_password_reset_date" timestamptz(6) NOT NULL,
  "enabled" int2 NOT NULL DEFAULT 0,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."user"."enabled" IS '是否启用，默认0';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (2, 'ryan', 'Ryan Miao', 'ryan@demo.com', '123', '2019-05-29 17:51:44.997224+08', '2019-05-29 17:51:44.997224+08', '2019-05-29 17:50:31+08', 0, '$2a$10$CXiN/3Qy.UqJlX.uBlNf4Oxf375P41ztN4nDuMaSmrKNi2Z7CUP5m');
INSERT INTO "public"."user" VALUES (5, 'admin', 'Admin', 'admin@demo.com', '789', '2019-05-29 17:52:38.299833+08', '2019-05-29 17:52:38.299833+08', '2019-05-29 17:51:20+08', 0, '$2a$10$WGp1834JChrqL7MdhzE9BOLa89EbiqgGDnRZh/t1alaOZ9iXyudpG');
INSERT INTO "public"."user" VALUES (4, 'test', 'Test', 'test@demo.com', '456', '2019-05-29 17:52:08.980826+08', '2019-05-29 17:52:08.980826+08', '2019-05-29 17:50:53+08', 0, '$2a$10$hf3EP6BtwgkrEg4topzv2ul65i1vNuMLin6K3QgEwHv/vsAenBgf2');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_role";
CREATE TABLE "public"."user_role" (
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO "public"."user_role" VALUES (5, 1);
INSERT INTO "public"."user_role" VALUES (4, 2);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."permission_id_seq"
OWNED BY "public"."permission"."id";
SELECT setval('"public"."permission_id_seq"', 6, true);
ALTER SEQUENCE "public"."role_id_seq"
OWNED BY "public"."role"."id";
SELECT setval('"public"."role_id_seq"', 3, true);
ALTER SEQUENCE "public"."user_id_seq"
OWNED BY "public"."user"."id";
SELECT setval('"public"."user_id_seq"', 6, true);

-- ----------------------------
-- Uniques structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD CONSTRAINT "uniq_permission_name" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD CONSTRAINT "permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD CONSTRAINT "uniq_role_name" UNIQUE ("name");
COMMENT ON CONSTRAINT "uniq_role_name" ON "public"."role" IS '角色名称唯一';

-- ----------------------------
-- Primary Key structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD CONSTRAINT "role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table role_permission
-- ----------------------------
CREATE UNIQUE INDEX "uniq_role_permission" ON "public"."role_permission" USING btree (
  "role_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "permission_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table user
-- ----------------------------
CREATE UNIQUE INDEX "uniq_username" ON "public"."user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."uniq_username" IS '用户名唯一';

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table user_role
-- ----------------------------
ALTER TABLE "public"."user_role" ADD CONSTRAINT "uniq_user_role" UNIQUE ("user_id", "role_id");
