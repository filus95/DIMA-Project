CREATE DATABASE  IF NOT EXISTS `propietary_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `propietary_db`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: propietary_db
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `libraries`
--

DROP TABLE IF EXISTS `libraries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `libraries` (
  `id_lib` int(11) NOT NULL AUTO_INCREMENT,
  `lib_name` varchar(255) NOT NULL,
  `schema_name` varchar(255) NOT NULL,
  `image_link` varchar(255) NOT NULL,
  `telephone_number` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `description` mediumtext,
  PRIMARY KEY (`id_lib`),
  UNIQUE KEY `libraries_schema_name_uindex` (`schema_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libraries`
--

LOCK TABLES `libraries` WRITE;
/*!40000 ALTER TABLE `libraries` DISABLE KEYS */;
INSERT INTO `libraries` VALUES (1,'Biblioteca Campus Leonardo','library_1','https://www.trumba.com/i/DgD2Go5hDnjQbhfM4iT5WsEM.jpg','021994492','Via Ampere 2 - 20131 (MI)','bca@poli.it','La Biblioteca Campus Leonardo di Milano, appartenente al Politecnico di Milano, è una delle maggiori biblioteche scientifiche italiane.[1] Si è formata nel 2017 dall\'unione della Biblioteca Centrale di Ingegneria e Biblioteca Centrale di Architettura entrambe presenti nella sede di Città Studi del Politecnico di Milano.\r\n\r\nORARI\r\n\r\nLUN 08:00 - 20:00\r\nMAR 08:00 - 20:00\r\nMER 08:00 - 20:00\r\nGIO 08:00 - 20:00\r\nVEN 08:00 - 20:00\r\nSAB 08:00 - 20:00\r\nDOM chiuso'),(2,'Biblioteca Valvassori Peroni','library_2','https://findingtimetowrite.files.wordpress.com/2018/09/unilibbristolwills-memorial-library.jpg','021994492','Via Valvassori Peroni 51 - 20135 (MI)','lambrate@poli.it','È la più giovane delle biblioteche rionali ed è anche la più grande con i suoi 2.500 mq distribuiti su tre livelli. Con oltre 200 posti di lettura si trova a due passi dalla stazione di Lambrate e dagli atenei del Polo scientifico e tecnologico di Città Studi. \r\nPer raggiungerla: bus 54, 93 / tram 19, 33 / M2 (Lambrate), FS Lambrate.\r\n\r\nORARI\r\n\r\nLUN 08:00 - 20:00\r\nMAR 08:00 - 20:00\r\nMER 08:00 - 20:00\r\nGIO 08:00 - 20:00\r\nVEN 08:00 - 20:00\r\nSAB 08:00 - 20:00\r\nDOM chiuso');
/*!40000 ALTER TABLE `libraries` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-15 17:49:09
