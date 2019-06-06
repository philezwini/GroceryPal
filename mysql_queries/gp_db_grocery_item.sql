-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: gp_db
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `grocery_item`
--

DROP TABLE IF EXISTS `grocery_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grocery_item` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` varchar(140) NOT NULL,
  `User_Id` int(11) NOT NULL,
  `isActive` int(11) NOT NULL DEFAULT '1',
  `Status` varchar(8) NOT NULL DEFAULT 'unknown',
  `Store_URL` varchar(128) NOT NULL DEFAULT 'unknown',
  PRIMARY KEY (`Id`),
  KEY `User_Id` (`User_Id`),
  CONSTRAINT `grocery_item_ibfk_1` FOREIGN KEY (`User_Id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grocery_item`
--

LOCK TABLES `grocery_item` WRITE;
/*!40000 ALTER TABLE `grocery_item` DISABLE KEYS */;
INSERT INTO `grocery_item` VALUES (16,'Corn','Umbila boy!',6,0,'current','Unknown'),(17,'sssss','Magical Stuff',6,0,'current','Unknown'),(18,'Milk','Parmalat Full Cream',6,1,'current','Unknown'),(19,'Corn Flakes','Cereal. Kellogs. 1kg.',6,1,'current','Unknown'),(20,'Oats','Jungle Oats. 750g',6,1,'current','Unknown'),(21,'Bread','Albany. Brown.',6,1,'current','Unknown'),(22,'Eggs','Rainbow. 30. Large.',6,0,'current','Unknown'),(23,'Eggs','Raingbow. Extra Large.',6,1,'current','Unknown'),(24,'Cheese','Parmalat. Sliced.',6,1,'current','Unknown'),(25,'Polony','Rainbow. Chicken. 500g.',6,1,'current','Unknown'),(26,'Spread','Stork. 1kg.',6,1,'current','Unknown'),(27,'Peanut butter','Black Cat. 250g',6,1,'current','Unknown'),(28,'Juice','Clover. Krush. 2l.',6,1,'current','Unknown'),(29,'Chicken','Rainbow. Mixed Portions. 2kg.',6,1,'current','Unknown'),(30,'Wors','Boerewors.',6,1,'current','Unknown'),(31,'Mince','Lean. Beef.',6,1,'current','Unknown'),(32,'Soup','Knorr. Minestrone.',6,1,'current','Unknown'),(33,'Soup','Knorr. Beef & Vegetable.',6,1,'current','Unknown'),(34,'Soup','Knorr. Brown Onion. ',6,1,'current','Unknown'),(35,'Onion','Large',6,1,'current','Unknown'),(36,'Tomato','Medium. 1kg.',6,1,'current','Unknown'),(37,'Green Pepper','Large. 1kg.',6,1,'current','Unknown'),(38,'Carrots','Large. 1kg.',6,1,'current','Unknown'),(39,'Toothpaste','Colgate. Active Salt. 75ml.',6,1,'current','Unknown'),(40,'Dishwashing Liquid','Sunlight. 750ml.',6,1,'current','Unknown'),(41,'Spice','Robertsons. Barbecue. 100g.',6,1,'current','Unknown'),(42,'Robertsons','Chicken. 100g.',6,1,'current','Unknown'),(43,'Robertsons','Black Pepper. 100g.',6,1,'current','Unknown'),(44,'Robertsons','White Pepper. 100g.',6,1,'current','Unknown'),(45,'Bar Soap','Dettol. Active. 175g.',6,1,'current','Unknown'),(46,'Bar Soap','Dettol. Original. 175mg.',6,1,'current','Unknown'),(47,'Washing Powder','Sunlight. 2kg.',6,1,'current','Unknown'),(48,'Deodorant','Axe. 150ml.',6,1,'current','Unknown'),(49,'Roll-On','Shield. Men.',6,1,'current','Unknown'),(50,'Cooking Oil','Canola. 750ml',6,1,'current','Unknown');
/*!40000 ALTER TABLE `grocery_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-16 12:11:24
