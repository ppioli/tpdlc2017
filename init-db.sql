create database db_tpdlc2017;

use db_tpdlc2017;

CREATE TABLE `palabras` (
  `id` binary(20) NOT NULL,
  `val` varchar(40) NOT NULL,
  `maxCount` int(5) NOT NULL,
  `docCount` int(5) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
);

CREATE TABLE `documentos` (
  `id` binary(20),
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `palabrasxdocumentos` (
	`idPalabra` binary(20) NOT NULL,
	`idDocumento` binary(20) NOT NULL,
	`frecuencia` int(4) NOT NULL,
	PRIMARY KEY (`idPalabra`, `idDocumento`),
	CONSTRAINT `FK_idPalabra` FOREIGN KEY (`idPalabra`) REFERENCES `palabras` (`id`),
	CONSTRAINT `FK_idDocumento` FOREIGN KEY (`idDocumento`) REFERENCES `documentos` (`id`)

);
