/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Steven
 * Created: Jul 11, 2016
 */
DROP DATABASE IF EXISTS egauge;
CREATE DATABASE egauge;
USE egauge;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS data;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS device;
DROP TABLE IF EXISTS userRoles;



CREATE TABLE device ( 
    deviceId VARCHAR(64) PRIMARY KEY,
    deviceName VARCHAR(60)
);

CREATE TABLE data (
    dataId VARCHAR(64) PRIMARY KEY,
    deviceId VARCHAR(64),
    timeRecorded datetime,
    power decimal(30,15),
    instPower decimal(7,3),
    FOREIGN KEY (deviceId)
        REFERENCES device(deviceId)
);


CREATE TABLE userRoles (
    roleId INT PRIMARY KEY AUTO_INCREMENT,
    roleName ENUM('defaultuser', 'admin') 
);

CREATE TABLE users (
    userId VARCHAR(64) PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    firstName VARCHAR(64) NOT NULL,
    lastName VARCHAR(64) NOT NULL,
    preferredCampus ENUM('Kingston', 'Brockville', 'Cornwall'),
    password BLOB NOT NULL,
    passwordSalt BLOB NOT NULL,
    
    timeEnteredQueue DATETIME NULL,
    availaleStartTime DATETIME NULL,
    availableEndTime DATETIME NULL,
    isActive boolean DEFAULT FALSE,
    extendIimeTries INT DEFAULT 0,

    roleId INT,
    deviceId VARCHAR(64) NULL,
    

    FOREIGN KEY (roleId)
        REFERENCES userRoles(roleId),
    FOREIGN KEY (deviceId) 
        REFERENCES device (deviceId)
);

SET FOREIGN_KEY_CHECKS=1;

INSERT INTO userRoles VALUES (null, 'admin'),
                             (null, 'defaultuser');

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES ('1a42990f-8f22-4212-9c71-f86ae89d5af1','Cornwall_Power'),('7e4d9817-9141-4b3d-8fa4-c1b5b590a050','Kingston_Wand1_Power'),('822073b6-2e40-45a2-bbb9-551768eb86e6','Brockville_Power'),('ed28534b-9fee-423d-bac5-57670e71f6c2','Kingston_TotalPower'),('fc60a7bc-4cff-4ea3-816e-57e86a6ee43c','Kingston_Wand2_Power');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;
