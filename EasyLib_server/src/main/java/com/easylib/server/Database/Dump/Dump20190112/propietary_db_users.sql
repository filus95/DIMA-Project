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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `hashed_pd` blob NOT NULL,
  `salt` blob NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `Users_email_uindex` (`email`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Filus','raff@io.cit',_binary '�ta�̻�VX\�wXƅ�VK�.Y\�4���\\�',_binary ';a��\�\�ZI��?P\�\��',NULL,NULL),(2,'Kosta','leo@io.cit',_binary '��\�:���ݩ3(P���\�8�q]\�\�ˡ�iE\�I',_binary 'QQd\�\�Q�G��]\�zG',NULL,NULL),(5,'bau','lio@io.cit',_binary '\��D@C�ＰG\� ��T(\"o\�\�d\�~,��',_binary '�t]]�(\r]##����',NULL,NULL),(7,'billa','cicciolina69@io.cit',_binary '\�LO����\�m\�\�&,9\�\'@\�Ŝށ��',_binary '�&�\��y��w\"��#',NULL,NULL),(8,'Vince','fumoerba@io.cit',_binary '��{�u��\�\���G� �jGl跰�R',_binary 'P��#ع\�_���v�',NULL,NULL),(10,'Fede','bettix4@io.cit',_binary '\n�\�\0�0\�\�Z\�\�*�`�\�(Q��.�B�9��',_binary '9\"3\�\�D�g\�!\�\�',NULL,NULL),(11,'banabnanan','mailainocmcao',_binary 'W\�CFN��\���+\�	�c\�\�k�\�\�LdN{\�\�\��[',_binary '�XB��\�y3}$\�mmK',NULL,NULL),(12,'','',_binary '�\�@I��\�Q�IA֦�ǁ\�y\�o\�\�:�\�^\��\�p',_binary 'nV�[�tI��3QP*`\�',NULL,NULL),(15,'ciao','era ',_binary '\�\�\�\�׈�av�b6v*\rc\'\0���,�\\�\�p��',_binary '%z��v[F\�K\�\"E�\r',NULL,NULL),(17,'kkss','cc',_binary '��4oV<\�\�.��\�ŵ�\�e�V-h\�\�',_binary '�\�/ \�\�L�\�Ps\�;',NULL,NULL),(19,'p','sdsa',_binary 'J�\�}@<�I�=\��\�0��6*5�V��.80�ν',_binary '\�`��.Ҝ�L]j\r',NULL,NULL),(21,'peo','lsaddsda',_binary '�`�.X5=*�l�O\�3�K\n؊�\�XC����\�\�',_binary 'kɎ\�\����u�:q�ض�',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-12 22:11:05
