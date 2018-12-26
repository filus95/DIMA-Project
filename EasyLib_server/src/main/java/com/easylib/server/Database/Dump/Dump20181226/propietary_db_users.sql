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
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `Users_email_uindex` (`email`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Filus','raff@io.cit',_binary '˜taØÃªèVX\ﬁwX∆ÖöVK¡.Y\Ë∑4°âÜ\\°',_binary ';aÇ∫\Ì®\ÔZIß•?P\ \∆˛'),(2,'Kosta','leo@io.cit',_binary '˜π\ƒ:â∏ù›©3(P£âÜ\Ã8≠q]\“\ÎÀ°ÜiE\„I',_binary 'QQd\–\÷QıGÉæ]\Ï†zG'),(5,'bau','lio@io.cit',_binary '\Ì†D@CªÔº∞G\‰ æ∑T(\"o\√\Ôd\«~,ÜÉ',_binary 'út]]∫(\r]##ÜÑ∂¯'),(7,'billa','cicciolina69@io.cit',_binary '\‘LOö≥¸Ò\Àm\Ï\Ó&,9\Â\'@\Âí≈úﬁÅõù',_binary '´&©\È¡y¡êw\"ÛèÛ©#'),(8,'Vince','fumoerba@io.cit',_binary 'º∂{˙uÚ†¿\Ì\Ô©∫πGç ïjGlË∑∞ΩR',_binary 'PΩí#ÿπ\Ì_¢í˚v≤'),(10,'Fede','bettix4@io.cit',_binary '\nÔä†\À\0®0\√\ÕZ\∆\„Ö*∫`∏\”(Qß≠.ÇBü9óë',_binary '9\"3\È\«DØg\Ÿ!\Ô\Î'),(11,'banabnanan','mailainocmcao',_binary 'W\„CFN§©\…¿û+\∆	˙c\ÿ\ÎkÆ\◊\ÕLdN{\‘\‚\Ó¡[',_binary '≥XBù¨\…y3}$\ÀmmK');
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

-- Dump completed on 2018-12-26 13:37:42
