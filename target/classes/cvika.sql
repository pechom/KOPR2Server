CREATE DATABASE IF NOT EXISTS `cvika`;
USE `cvika`;

DROP TABLE IF EXISTS `predmet`;
CREATE TABLE `predmet` (
  `puuid` char(36) NOT NULL,
  `nazov` char(36) NOT NULL,
  UNIQUE KEY `puuid` (`puuid`),
  UNIQUE KEY `nazov` (`nazov`),
  PRIMARY KEY (`puuid`, `nazov`)
);

DROP TABLE IF EXISTS `ucastnik`;
CREATE TABLE `ucastnik` (
  `uuuid` varchar(36) NOT NULL,
  `meno` varchar(20) NOT NULL,
  `priezvisko` varchar(30) NOT NULL,
  PRIMARY KEY (`uuuid`),
  UNIQUE KEY `uuid` (`uuuid`)
);

DROP TABLE IF EXISTS `prezencka`;
CREATE TABLE `prezencka` (
  `auuid` varchar(36) NOT NULL,
  `cas` datetime NOT NULL,
  `predmet_uuid` varchar(36) NOT NULL,
  PRIMARY KEY (`auuid`,`predmet_uuid`),
  UNIQUE KEY `auuid` (`auuid`),
  KEY `fk_prezencka_predmet1_idx` (`predmet_uuid`),
  CONSTRAINT `fk_prezencka_predmet1` FOREIGN KEY (`predmet_uuid`) REFERENCES `predmet` (`puuid`) ON DELETE CASCADE ON UPDATE CASCADE
); 

DROP TABLE IF EXISTS `ucastnici_prezencky`;
CREATE TABLE `ucastnici_prezencky` (
  `prezencka_uuid` varchar(36) NOT NULL,
  `ucastnik_uuid` varchar(36) NOT NULL,
  PRIMARY KEY (`prezencka_uuid`,`ucastnik_uuid`),
  KEY `fk_prezencka_has_ucastnik_ucastnik1_idx` (`ucastnik_uuid`),
  KEY `fk_prezencka_has_ucastnik_prezencka1_idx` (`prezencka_uuid`),
  CONSTRAINT `fk_prezencka_has_ucastnik_prezencka1` FOREIGN KEY (`prezencka_uuid`) REFERENCES `prezencka` (`auuid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prezencka_has_ucastnik_ucastnik1` FOREIGN KEY (`ucastnik_uuid`) REFERENCES `ucastnik` (`uuuid`) ON DELETE CASCADE ON UPDATE CASCADE
);