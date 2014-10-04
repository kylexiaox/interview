/*
 Navicat Premium Data Transfer

 Source Server         : Postgresql
 Source Server Type    : PostgreSQL
 Source Server Version : 90305
 Source Host           : localhost
 Source Database       : Interview
 Source Schema         : Interview

 Target Server Type    : PostgreSQL
 Target Server Version : 90305
 File Encoding         : utf-8

 Date: 10/04/2014 00:16:05 AM
*/

-- ----------------------------
--  Table structure for MESSAGE
-- ----------------------------
DROP TABLE IF EXISTS MESSAGE;
CREATE TABLE MESSAGE (
	MESSAGE_ID serial,
	REPLY_MESSAGE_ID int4 NOT NULL,
	USER_ID int4 NOT NULL,
	USER_NICK_NAME varchar NOT NULL,
	MESSAGE_CONTENT varchar(100) NOT NULL,
	TIME_STAMP timestamp(6) NOT NULL
)
WITH (OIDS=FALSE);
ALTER TABLE MESSAGE OWNER TO postgres;

COMMENT ON COLUMN MESSAGE.USER_NICK_NAME IS 'duplicate record';

-- ----------------------------
--  Primary key structure for table MESSAGE
-- ----------------------------
ALTER TABLE MESSAGE ADD PRIMARY KEY (MESSAGE_ID) NOT DEFERRABLE INITIALLY IMMEDIATE;

