create database db_tpdlc2017;

use db_tpdlc2017;

CREATE TABLE `palabras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maxCount` int(11) NOT NULL,
  `val` varchar(20) NOT NULL,
  `hash` binary(20) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
);

CREATE TABLE `documentos` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `hash` binary(20) NOT NULL UNIQUE,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `palabrasxdocumentos` (
	`idPalabra` int(11) NOT NULL,
	`idDocumento` int(8) NOT NULL,
	`frecuencia` int(4) NOT NULL,
	PRIMARY KEY (`idPalabra`, `idDocumento`),
	CONSTRAINT `FK_idPalabra` FOREIGN KEY (`idPalabra`) REFERENCES `palabras` (`id`),
	CONSTRAINT `FK_idDocumento` FOREIGN KEY (`idDocumento`) REFERENCES `documentos` (`id`)

);

CREATE UNIQUE INDEX `hashPalabras`
ON `palabras` ( `hash`);

CREATE UNIQUE INDEX `hashDocumentos`
ON `documentos` ( `hash`);
