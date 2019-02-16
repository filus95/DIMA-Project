CREATE DATABASE  IF NOT EXISTS `library_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `library_1`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library_1
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
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `post_date` datetime NOT NULL,
  `content` mediumtext NOT NULL,
  `image_link` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Opening time changed','2018-11-30 14:35:00','We have changed the opening time. Take a look for them on the library page in the EasyLib app!','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ-PiVSbeLQvh576nohHbz4WnkVaVVqnqdexG9rK0UYqBvA-hC.mipiacitu.it'),(2,'New opening!','2017-06-26 10:00:00','The library is opening a new physical shop in Bergamo. Come to visit us, we have prepared for you some drinks and free food and applied a 10% of discount on all your buying only for today!','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ-PiVSbeLQvh576nohHbz4WnkVaVVqnqdexG9rK0UYqBvA-hC'),(3,'New books available!','2019-02-11 13:00:00','There are many new books that you can discover in our library! Come to visit us or consult our catalogue in the app.','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ-PiVSbeLQvh576nohHbz4WnkVaVVqnqdexG9rK0UYqBvA-hC'),(6,'New coffee distributor','2019-01-12 17:03:13','The library has a new technological coffee distributor. For the first week the prices will be halved! Try it and give us a feedback. Your contribution is important for us. ','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ-PiVSbeLQvh576nohHbz4WnkVaVVqnqdexG9rK0UYqBvA-hC');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-16 15:46:09
