create database db_tpdlc2017;

use db_tpdlc2017;

CREATE TABLE `palabras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valor` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `documentos` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `hash` binary(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
);
