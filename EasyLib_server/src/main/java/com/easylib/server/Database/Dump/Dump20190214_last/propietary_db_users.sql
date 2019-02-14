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
INSERT INTO `users` VALUES (2,'Kosta','leo@io.cit',_binary '��\�:���ݩ3(P���\�8�q]\�\�ˡ�iE\�I',_binary 'QQd\�\�Q�G��]\�zG',NULL,NULL,'',NULL),(3,'Filus','raff@io.cit',_binary '�ta�̻�VX\�wXƅ�VK�.Y\�4���\\�',_binary ';a��\�\�ZI��?P\�\��',NULL,NULL,'cjdfadshfdkaòjggajnòfd',NULL),(5,'bau','lio@io.cit',_binary '\��D@C�ＰG\� ��T(\"o\�\�d\�~,��',_binary '�t]]�(\r]##����',NULL,NULL,'',NULL),(7,'billa','cicciolina69@io.cit',_binary '\�LO����\�m\�\�&,9\�\'@\�Ŝށ��',_binary '�&�\��y��w\"��#',NULL,NULL,'',NULL),(8,'Vince','fumoerba@io.cit',_binary '��{�u��\�\���G� �jGl跰�R',_binary 'P��#ع\�_���v�',NULL,NULL,'',NULL),(10,'Fede','bettix4@io.cit',_binary '\n�\�\0�0\�\�Z\�\�*�`�\�(Q��.�B�9��',_binary '9\"3\�\�D�g\�!\�\�',NULL,NULL,'',NULL),(11,'banabnanan','mailainocmcao',_binary 'W\�CFN��\���+\�	�c\�\�k�\�\�LdN{\�\�\��[',_binary '�XB��\�y3}$\�mmK',NULL,NULL,'',NULL),(12,'','',_binary '�\�@I��\�Q�IA֦�ǁ\�y\�o\�\�:�\�^\��\�p',_binary 'nV�[�tI��3QP*`\�',NULL,NULL,'',NULL),(15,'ciao','era ',_binary '\�\�\�\�׈�av�b6v*\rc\'\0���,�\\�\�p��',_binary '%z��v[F\�K\�\"E�\r',NULL,NULL,'f9CVC7HcXec:APA91bHYCAjY4KEU-bZeQ5QFuCMiHjXYwU7ZPcK93a1EOVXtx55xhhKvDkI4djr2sHy_uJWqitBMEgXBIpJTjq5p8cRlLjRp-LpYmBZYpFiD1GfESRo_aMyobnS4E8OvP-GBwEyHUYeV',NULL),(17,'kkss','cc',_binary '��4oV<\�\�.��\�ŵ�\�e�V-h\�\�',_binary '�\�/ \�\�L�\�Ps\�;',NULL,NULL,'',NULL),(19,'p','sdsa',_binary 'J�\�}@<�I�=\��\�0��6*5�V��.80�ν',_binary '\�`��.Ҝ�L]j\r',NULL,NULL,'',NULL),(21,'peo','lsaddsda',_binary '�`�.X5=*�l�O\�3�K\n؊�\�XC����\�\�',_binary 'kɎ\�\����u�:q�ض�',NULL,NULL,'',NULL),(23,'bongo','raff@libero.it',_binary '�\n!cJ�y>�3t\��n��<�)���̿z�2S@',_binary '5�\�z\'\��?MM\�]M\�',NULL,NULL,'',NULL),(24,'sjns','xnsj',_binary '��\�$�+\�\Z\0aW=V[�\�t��:�\�R��\�gG\�\�',_binary 'an�W\�q$����\�D�',NULL,NULL,'',NULL),(25,'iandolfi','fake@it',_binary 'J�7\�N��v\�В�8\�Px˕d-@�\".',_binary '�J\�����U}���A\�',NULL,NULL,'f9CVC7HcXec:APA91bHYCAjY4KEU-bZeQ5QFuCMiHjXYwU7ZPcK93a1EOVXtx55xhhKvDkI4djr2sHy_uJWqitBMEgXBIpJTjq5p8cRlLjRp-LpYmBZYpFiD1GfESRo_aMyobnS4E8OvP-GBwEyHUYeV',NULL),(26,NULL,'test1',_binary '؀�\�ss}�6j6% �H7��\�\�Q\�jU��)\�',_binary '\�l|g�c�,\�\'lɁ','test1','test1','0',NULL),(27,NULL,'hdhdhd',_binary '\�\�<�\�yD/\�jrW\�\�!\�\'N\�Z�\�^\�',_binary '�O\�E\���Y\�Mc\0','dhhdbd','dhbdbd','0',NULL),(28,NULL,'fjfj',_binary '�y��TQ�i��4��4\�G\�̸t\�\�u�',_binary '�i5��ʵ�\n���]�','djdjf','hrhdh','0',NULL),(29,NULL,'fjcj',_binary '�%\�k\'��|��ּܑ�����F1\�P��i 6}L',_binary '�&y&e�\�eڽ�W�t','cjfj','yduf','0',NULL),(31,NULL,'aaaa',_binary '[B@5059bb2',_binary '[B@5b0db8f4','raff','bong','e4GnMXG5F4s:APA91bE9jZ8XQ_X0DkRCRgUChGRwig4y0PH3GhxfMd019IzsN0CVPe9KXvk-9FOXvxn11jel2W6opGOhuoZR0CDhpdl4XQOKZYEk6TZC7SWbw8_E8OtGgPleSEJxA-G3WtyEsagKQg5-',NULL),(32,NULL,'c',_binary 'R�QT�ZS��r\�\�_\�\�؇S\\���:��\�.�n',_binary '|\0A5�C�X&\�}�\n','c','c','0',NULL),(33,NULL,'figa',_binary 'o�VS\�\�\�!3\n懀�Z�\Z)�Z~\����zSq�C',_binary 'ތ.\�\ng.I�\'� ','cazzo','cazzo','0',NULL),(34,NULL,'b',_binary '��jR�,\"<B��.aâd��bb�cЂk\��',_binary '�o�J��9h\��k\�wh','b','b','0',NULL),(47,NULL,'info.easylib2018@gmail.com',NULL,NULL,'Raffaele','Bongo','e4GnMXG5F4s:APA91bE9jZ8XQ_X0DkRCRgUChGRwig4y0PH3GhxfMd019IzsN0CVPe9KXvk-9FOXvxn11jel2W6opGOhuoZR0CDhpdl4XQOKZYEk6TZC7SWbw8_E8OtGgPleSEJxA-G3WtyEsagKQg5-','eyJhbGciOiJSUzI1NiIsImtpZCI6IjdjMzA5ZTNhMWMxOTk5Y2IwNDA0YWI3MTI1ZWU0MGI3Y2RiY2FmN2QiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1NzcyMDkyNzIwMzgtdWhjdWE4czJ0cTV1OWYzZjhvYXZoZGtodXU2MnZwODguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1NzcyMDkyNzIwMzgtOXBqMzBka2RkYjA4dWV1bThsb3Y4YWV1bzZzZ3A1dGQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDk3NzgxMjYxNjE5MDU2ODUzMzIiLCJlbWFpbCI6ImluZm8uZWFzeWxpYjIwMThAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJSYWZmYWVsZSBCb25nbyIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLTgzYjRmX3pHRkx3L0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FDZXZvUVAzX1FOSVVMM3hKb1J3WmJvUkhPSDJEbndNUWcvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IlJhZmZhZWxlIiwiZmFtaWx5X25hbWUiOiJCb25nbyIsImxvY2FsZSI6Iml0IiwiaWF0IjoxNTQ5Nzg4Mzc4LCJleHAiOjE1NDk3OTE5Nzh9.0lj7KNTh9nmknl-RR9t9c6Lv8mxVbDcEdWdhj29XHk2XUc5Yig-ryoncc4y8dTk-gLkF7A6qCr2G9XB9Msw-cG7pNODY4ou8aohiCfAwD3l0v2G4hxSAnYJ2tH14BQKDPyKo2VWjjDH4kVbMUNNoBhYg37AbgsHFStf3egXZ_7gkYlaV2xcknLPG8Y4VaUIuVWoDOKbOqCze0Z_EEcAZEHh2BtvDGbWamAPT9Q6syYl-MKIc2zGAIYM6R2VTo-0uVhhnbv0N4HkBRzuQCLHpQHz-VIH2_B03-3SxwNLWkrKw4YML8ILnPycHp9ZGn4WQQAGn0Hy4Q0PnZwAJSzNVJA'),(50,NULL,'email',_binary '\�3�\�\�Zpp\��0a-,6���/	\�\�rC1veA',_binary 'h�|�������c�\�+','pooo','pii','0',NULL),(52,NULL,'poi',_binary '�\�\�\n��r�2-=�)!	��)���8�I	��yOޣ',_binary '�\�xд�\��\�7.\�\�','abah','bbaba','0',NULL);
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

-- Dump completed on 2019-02-14 11:33:14
