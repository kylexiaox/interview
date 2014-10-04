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

 Date: 10/04/2014 00:24:29 AM
*/

-- ----------------------------
--  Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
	USER_ID serial,
	NICK_NAME varchar(50),
	TOKEN varchar(20) ,
	USER_TYPE varchar(20) 
)
WITH (OIDS=FALSE);
ALTER TABLE USERS OWNER TO postgres;

COMMENT ON COLUMN USERS.USER_ID IS 'UserId';
COMMENT ON COLUMN USERS.NICK_NAME IS 'NICK_NAME';
COMMENT ON COLUMN USERS.TOKEN IS 'DYNAMIC TOKEN';

-- ----------------------------
--  Primary key structure for table USER
-- ----------------------------
ALTER TABLE USERS ADD PRIMARY KEY (USER_ID) NOT DEFERRABLE INITIALLY IMMEDIATE;

