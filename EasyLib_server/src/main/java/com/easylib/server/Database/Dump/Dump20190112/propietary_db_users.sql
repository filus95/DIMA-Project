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
INSERT INTO `users` VALUES (1,'Filus','raff@io.cit',_binary '˜taØÃªèVX\ﬁwX∆ÖöVK¡.Y\Ë∑4°âÜ\\°',_binary ';aÇ∫\Ì®\ÔZIß•?P\ \∆˛',NULL,NULL),(2,'Kosta','leo@io.cit',_binary '˜π\ƒ:â∏ù›©3(P£âÜ\Ã8≠q]\“\ÎÀ°ÜiE\„I',_binary 'QQd\–\÷QıGÉæ]\Ï†zG',NULL,NULL),(5,'bau','lio@io.cit',_binary '\Ì†D@CªÔº∞G\‰ æ∑T(\"o\√\Ôd\«~,ÜÉ',_binary 'út]]∫(\r]##ÜÑ∂¯',NULL,NULL),(7,'billa','cicciolina69@io.cit',_binary '\‘LOö≥¸Ò\Àm\Ï\Ó&,9\Â\'@\Âí≈úﬁÅõù',_binary '´&©\È¡y¡êw\"ÛèÛ©#',NULL,NULL),(8,'Vince','fumoerba@io.cit',_binary 'º∂{˙uÚ†¿\Ì\Ô©∫πGç ïjGlË∑∞ΩR',_binary 'PΩí#ÿπ\Ì_¢í˚v≤',NULL,NULL),(10,'Fede','bettix4@io.cit',_binary '\nÔä†\À\0®0\√\ÕZ\∆\„Ö*∫`∏\”(Qß≠.ÇBü9óë',_binary '9\"3\È\«DØg\Ÿ!\Ô\Î',NULL,NULL),(11,'banabnanan','mailainocmcao',_binary 'W\„CFN§©\…¿û+\∆	˙c\ÿ\ÎkÆ\◊\ÕLdN{\‘\‚\Ó¡[',_binary '≥XBù¨\…y3}$\ÀmmK',NULL,NULL),(12,'','',_binary '¯\Ã@I£¡\’Q†IA÷¶¨«Å\«y\ﬂo\⁄\–:Ñ\‡^\≈˜\Îp',_binary 'nVØ[ÅtIÄø3QP*`\Œ',NULL,NULL),(15,'ciao','era ',_binary '\Á\√\À\‘◊àÙavîb6v*\rc\'\0≠ªÆ,¢\\ê\œpÄ¿',_binary '%zå˘v[F\ÏK\È\"Eü\r',NULL,NULL),(17,'kkss','cc',_binary 'Æè4oV<\Ìô\–.˚ÛáÇ\‚á≈µº\œe•V-h\ﬁ\Õ',_binary 'é\ﬁ/ \È\¬Lï\ﬁPs\”;',NULL,NULL),(19,'p','sdsa',_binary 'Jí\Ó}@<Iˆ=\≈ö\Â0≠≤6*5óV∫ß.80˙ŒΩ',_binary '\Ã`¸Ω.“ú•L]j\r',NULL,NULL),(21,'peo','lsaddsda',_binary 'Ú`π.X5=*àláO\Õ3óK\nÿä©\ÌXCÚ¸¢ç\Ô\Î®',_binary 'k…é\‡\Âˇô∞uã:q≠ÿ∂˝',NULL,NULL);
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
