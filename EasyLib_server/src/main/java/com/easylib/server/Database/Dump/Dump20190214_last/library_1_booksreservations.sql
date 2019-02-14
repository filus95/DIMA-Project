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
-- Table structure for table `booksreservations`
--

DROP TABLE IF EXISTS `booksreservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `booksreservations` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_identifier` varchar(255) NOT NULL,
  `book_title` varchar(255) NOT NULL,
  `reservation_date` datetime NOT NULL,
  `starting_reservation_date` datetime DEFAULT NULL,
  `ending_reservation_date` datetime DEFAULT NULL,
  `quantity` int(11) NOT NULL DEFAULT '1',
  `taken` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`reservation_id`),
  UNIQUE KEY `table_name_reservation_id_uindex` (`reservation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booksreservations`
--

LOCK TABLES `booksreservations` WRITE;
/*!40000 ALTER TABLE `booksreservations` DISABLE KEYS */;
INSERT INTO `booksreservations` VALUES (5,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(6,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(7,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(8,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(9,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(10,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(11,14,'1909430188','Andrea Pirlo: I Think Therefore I Play','1901-02-01 00:00:00','2018-12-03 00:00:00','2018-12-30 00:00:00',0,0),(25,101,'8852068317','Il codice Del Piero','2019-01-21 00:45:37',NULL,NULL,1,0),(44,8,'8852016953','Il simbolo perduto','2019-02-04 19:14:27',NULL,NULL,1,0),(45,0,'0743277716','ciao','2019-02-05 22:47:41',NULL,NULL,1,0),(50,31,'8898001339','Maradona','2019-02-09 12:54:22',NULL,NULL,0,0),(51,31,'9788832970395','Maradona è amico mio','2019-02-09 12:59:48',NULL,NULL,0,0),(55,1,'0385542690','origin','2019-02-14 10:07:39',NULL,NULL,1,0);
/*!40000 ALTER TABLE `booksreservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_on_reservation` AFTER INSERT ON `booksreservations` FOR EACH ROW BEGIN
     declare q int;

     select library_1.books.quantity into q from library_1.books
                where library_1.books.identifier = NEW.book_identifier;
     
     update library_1.books set quantity = quantity - NEW.quantity
     where library_1.books.identifier = NEW.book_identifier;
     
     if (q = 0) then
        update library_1.books set books.reserved = true
        where library_1.books.identifier = NEW.book_identifier;
    end if; 
  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `waiting_list_flowing` AFTER DELETE ON `booksreservations` FOR EACH ROW begin
    
    declare cond integer;
    declare id_user integer;
    declare v_reservation_date DATETIME;
    declare start_res_date DATE;
    declare end_res_date DATE;
    declare quantity integer;

    select waiting_position into cond from library_1.waitinglist where waitinglist.book_identifier =
                                                          OLD.book_identifier and waiting_position = 1;
    
    if cond > 0 then 
      
      select user_id into id_user from waitinglist where waitinglist.book_identifier =
                                                           OLD.book_identifier and waiting_position = 1;

      select waitinglist.reservation_date into v_reservation_date from waitinglist where waitinglist.book_identifier =
                                                                    OLD.book_identifier and waiting_position = 1;

      select waitinglist.quantity into quantity from library_1.waitinglist where library_1.waitinglist.book_identifier =
                                                            OLD.book_identifier and waiting_position = 1;

      delete from library_1.waitinglist where library_1.waitinglist.book_identifier = old.book_identifier and
                                library_1.waitinglist.waiting_position = 1;

      update library_1.waitinglist set library_1.waitinglist.waiting_position = library_1.waitinglist.waiting_position - 1
        where library_1.waitinglist.book_identifier = OLD.book_identifier;

    end if;
  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_count_on_delivering` AFTER DELETE ON `booksreservations` FOR EACH ROW begin

    declare x boolean;

    select library_1.books.reserved into x from library_1.books
    where library_1.books.identifier = OLD.book_identifier;

      update library_1.books set library_1.books.quantity = library_1.books.quantity + OLD.quantity
      where library_1.books.identifier = OLD.book_identifier;

  if ( x = false ) then
    update library_1.books set books.reserved = false
      where library_1.books.identifier = OLD.book_identifier;
    end if;

  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-14 11:33:17
