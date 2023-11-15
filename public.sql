/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 150004 (150004)
 Source Host           : localhost:5432
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150004 (150004)
 File Encoding         : 65001

 Date: 10/11/2023 13:00:29
*/


-- ----------------------------
-- Table structure for tg_demo
-- ----------------------------
DROP TABLE IF EXISTS "public"."tg_demo";
CREATE TABLE "public"."tg_demo" (
  "id" int8 NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "deleted" bool NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "json_profile" json,
  "sex" int2
)
;
ALTER TABLE "public"."tg_demo" OWNER TO "postgres";
COMMENT ON COLUMN "public"."tg_demo"."id" IS 'ID';
COMMENT ON COLUMN "public"."tg_demo"."name" IS '名称';
COMMENT ON COLUMN "public"."tg_demo"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tg_demo"."deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."tg_demo"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."tg_demo" IS '自动构建基准字段';

-- ----------------------------
-- Records of tg_demo
-- ----------------------------
BEGIN;
INSERT INTO "public"."tg_demo" ("id", "name", "create_time", "deleted", "update_time", "json_profile", "sex") VALUES (1, 'lihexu-pc', '2023-09-08 10:44:50', 'f', '2023-09-08 10:44:55', '[{"aaa":123}]', 1);
COMMIT;

-- ----------------------------
-- Primary Key structure for table tg_demo
-- ----------------------------
ALTER TABLE "public"."tg_demo" ADD CONSTRAINT "demo_copy1_pkey" PRIMARY KEY ("id");
