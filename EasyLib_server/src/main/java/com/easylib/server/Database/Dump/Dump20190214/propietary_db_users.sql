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
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `hashed_pd` blob,
  `salt` blob,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `messaging_token` varchar(255) NOT NULL DEFAULT '0',
  `google_id_token` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `Users_email_uindex` (`email`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Kosta','leo@io.cit',_binary '˜π\ƒ:â∏ù›©3(P£âÜ\Ã8≠q]\“\ÎÀ°ÜiE\„I',_binary 'QQd\–\÷QıGÉæ]\Ï†zG',NULL,NULL,'',NULL),(3,'Filus','raff@io.cit',_binary '˜taØÃªèVX\ﬁwX∆ÖöVK¡.Y\Ë∑4°âÜ\\°',_binary ';aÇ∫\Ì®\ÔZIß•?P\ \∆˛',NULL,NULL,'cjdfadshfdka√≤jggajn√≤fd',NULL),(5,'bau','lio@io.cit',_binary '\Ì†D@CªÔº∞G\‰ æ∑T(\"o\√\Ôd\«~,ÜÉ',_binary 'út]]∫(\r]##ÜÑ∂¯',NULL,NULL,'',NULL),(7,'billa','cicciolina69@io.cit',_binary '\‘LOö≥¸Ò\Àm\Ï\Ó&,9\Â\'@\Âí≈úﬁÅõù',_binary '´&©\È¡y¡êw\"ÛèÛ©#',NULL,NULL,'',NULL),(8,'Vince','fumoerba@io.cit',_binary 'º∂{˙uÚ†¿\Ì\Ô©∫πGç ïjGlË∑∞ΩR',_binary 'PΩí#ÿπ\Ì_¢í˚v≤',NULL,NULL,'',NULL),(10,'Fede','bettix4@io.cit',_binary '\nÔä†\À\0®0\√\ÕZ\∆\„Ö*∫`∏\”(Qß≠.ÇBü9óë',_binary '9\"3\È\«DØg\Ÿ!\Ô\Î',NULL,NULL,'',NULL),(11,'banabnanan','mailainocmcao',_binary 'W\„CFN§©\…¿û+\∆	˙c\ÿ\ÎkÆ\◊\ÕLdN{\‘\‚\Ó¡[',_binary '≥XBù¨\…y3}$\ÀmmK',NULL,NULL,'',NULL),(12,'','',_binary '¯\Ã@I£¡\’Q†IA÷¶¨«Å\«y\ﬂo\⁄\–:Ñ\‡^\≈˜\Îp',_binary 'nVØ[ÅtIÄø3QP*`\Œ',NULL,NULL,'',NULL),(15,'ciao','era ',_binary '\Á\√\À\‘◊àÙavîb6v*\rc\'\0≠ªÆ,¢\\ê\œpÄ¿',_binary '%zå˘v[F\ÏK\È\"Eü\r',NULL,NULL,'f9CVC7HcXec:APA91bHYCAjY4KEU-bZeQ5QFuCMiHjXYwU7ZPcK93a1EOVXtx55xhhKvDkI4djr2sHy_uJWqitBMEgXBIpJTjq5p8cRlLjRp-LpYmBZYpFiD1GfESRo_aMyobnS4E8OvP-GBwEyHUYeV',NULL),(17,'kkss','cc',_binary 'Æè4oV<\Ìô\–.˚ÛáÇ\‚á≈µº\œe•V-h\ﬁ\Õ',_binary 'é\ﬁ/ \È\¬Lï\ﬁPs\”;',NULL,NULL,'',NULL),(19,'p','sdsa',_binary 'Jí\Ó}@<Iˆ=\≈ö\Â0≠≤6*5óV∫ß.80˙ŒΩ',_binary '\Ã`¸Ω.“ú•L]j\r',NULL,NULL,'',NULL),(21,'peo','lsaddsda',_binary 'Ú`π.X5=*àláO\Õ3óK\nÿä©\ÌXCÚ¸¢ç\Ô\Î®',_binary 'k…é\‡\Âˇô∞uã:q≠ÿ∂˝',NULL,NULL,'',NULL),(23,'bongo','raff@libero.it',_binary 'í\n!cJÚy>É3t\€¢n∂Ü<ë)ó≥äÃøzù2S@',_binary '5ñ\Íz\'\‹ˆ?MM\“]M\œ',NULL,NULL,'',NULL),(24,'sjns','xnsj',_binary 'æ™\‚$¯+\À\Z\0aW=V[ø\€täô:ñ\ R˚≥\„gG\Ê\Ê',_binary 'an˙W\÷q$õ˜≥˜\ÀD¥',NULL,NULL,'',NULL),(25,'iandolfi','fake@it',_binary 'J¶7\ÎNóæv\⁄–íÉ8\„PxÀïd-@¶\".',_binary 'íJ\€¸õÉ∞U}ê¥¢A\Œ',NULL,NULL,'f9CVC7HcXec:APA91bHYCAjY4KEU-bZeQ5QFuCMiHjXYwU7ZPcK93a1EOVXtx55xhhKvDkI4djr2sHy_uJWqitBMEgXBIpJTjq5p8cRlLjRp-LpYmBZYpFiD1GfESRo_aMyobnS4E8OvP-GBwEyHUYeV',NULL),(26,NULL,'test1',_binary 'ÿÄé\Óss}ˇ6j6% ìH7â≠\Ê\ÂQ\÷jU¶Ö)\ﬂ',_binary '\Êùl|g∑c˛,\Ê\'l…Å','test1','test1','0',NULL),(27,NULL,'hdhdhd',_binary '\ÍÅ\œ<\«yD/\⁄jrW\…\‰!\≈\'N\ÕZ≠\‰^\Î',_binary 'µO\—E\„ÑÚY\—Mc\0','dhhdbd','dhbdbd','0',NULL),(28,NULL,'fjfj',_binary 'ÚyÇóTQıi°û4¢¢4\–G\ÈÃ∏t\Í\ÿuÛ',_binary '´i5Åö µ˛\n£ªø]˛','djdjf','hrhdh','0',NULL),(29,NULL,'fjcj',_binary '©%\‘k\'˙µ|≤â÷º‹ëù©øßßF1\ÎP¢êi 6}L',_binary 'Ω&y&eπ\’e⁄ΩñWòt','cjfj','yduf','0',NULL),(31,NULL,'aaaa',_binary '[B@5059bb2',_binary '[B@5b0db8f4','raff','bong','e4GnMXG5F4s:APA91bE9jZ8XQ_X0DkRCRgUChGRwig4y0PH3GhxfMd019IzsN0CVPe9KXvk-9FOXvxn11jel2W6opGOhuoZR0CDhpdl4XQOKZYEk6TZC7SWbw8_E8OtGgPleSEJxA-G3WtyEsagKQg5-',NULL),(32,NULL,'c',_binary 'R∞QT§ZSÑßr\“\»_\≈\Î©ÿáS\\˛µæ:çÙ\Ì.ın',_binary '|\0A5˜C˛X&\Î}¨\n','c','c','0',NULL),(33,NULL,'figa',_binary 'oéVS\ÈÑ\”\÷!3\nÊáÄ§Zû\Z)∑Z~\«˜˝¶zSqêC',_binary 'ﬁå.\Îá\ng.Iı\'ê ','cazzo','cazzo','0',NULL),(34,NULL,'b',_binary 'íùjR©,\"<BóΩ.a√¢dªêbbìc–Çk\ÓÒ',_binary 'ÆoªJùÚ9h\ƒ˚k\Èwh','b','b','0',NULL),(47,NULL,'info.easylib2018@gmail.com',NULL,NULL,'Raffaele','Bongo','e4GnMXG5F4s:APA91bE9jZ8XQ_X0DkRCRgUChGRwig4y0PH3GhxfMd019IzsN0CVPe9KXvk-9FOXvxn11jel2W6opGOhuoZR0CDhpdl4XQOKZYEk6TZC7SWbw8_E8OtGgPleSEJxA-G3WtyEsagKQg5-','eyJhbGciOiJSUzI1NiIsImtpZCI6IjdjMzA5ZTNhMWMxOTk5Y2IwNDA0YWI3MTI1ZWU0MGI3Y2RiY2FmN2QiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1NzcyMDkyNzIwMzgtdWhjdWE4czJ0cTV1OWYzZjhvYXZoZGtodXU2MnZwODguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1NzcyMDkyNzIwMzgtOXBqMzBka2RkYjA4dWV1bThsb3Y4YWV1bzZzZ3A1dGQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDk3NzgxMjYxNjE5MDU2ODUzMzIiLCJlbWFpbCI6ImluZm8uZWFzeWxpYjIwMThAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJSYWZmYWVsZSBCb25nbyIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLTgzYjRmX3pHRkx3L0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FDZXZvUVAzX1FOSVVMM3hKb1J3WmJvUkhPSDJEbndNUWcvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IlJhZmZhZWxlIiwiZmFtaWx5X25hbWUiOiJCb25nbyIsImxvY2FsZSI6Iml0IiwiaWF0IjoxNTQ5Nzg4Mzc4LCJleHAiOjE1NDk3OTE5Nzh9.0lj7KNTh9nmknl-RR9t9c6Lv8mxVbDcEdWdhj29XHk2XUc5Yig-ryoncc4y8dTk-gLkF7A6qCr2G9XB9Msw-cG7pNODY4ou8aohiCfAwD3l0v2G4hxSAnYJ2tH14BQKDPyKo2VWjjDH4kVbMUNNoBhYg37AbgsHFStf3egXZ_7gkYlaV2xcknLPG8Y4VaUIuVWoDOKbOqCze0Z_EEcAZEHh2BtvDGbWamAPT9Q6syYl-MKIc2zGAIYM6R2VTo-0uVhhnbv0N4HkBRzuQCLHpQHz-VIH2_B03-3SxwNLWkrKw4YML8ILnPycHp9ZGn4WQQAGn0Hy4Q0PnZwAJSzNVJA'),(50,NULL,'email',_binary '\Ï3ç\È\„Zpp\‡0a-,6ì¿≥/	\Ë\€rC1veA',_binary 'hÒ|˚ıˇõÒß•ÙcÖ\Ô+','pooo','pii','0',NULL),(52,NULL,'poi',_binary 'ˆ\Œ\‚\n§ør´2-=§)!	¢è)è™ç8âI	≥˜yOﬁ£',_binary 'à\Õx–¥†\Âß˜\“7.\’\€','abah','bbaba','0',NULL);
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

-- Dump completed on 2019-02-14 11:14:10
