CREATE DATABASE  IF NOT EXISTS `snacksnap` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `snacksnap`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: snacksnap
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `defaults` bit(1) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name_receiver` varchar(255) DEFAULT NULL,
  `phone_receiver` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`),
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,_binary '\0','Tan xuan 4','Huu Duc','0123456789',1),(2,_binary '\0','Tan xuan 3','Duc','0135627384',1),(3,_binary '','Man Thien','Duc ','12345678',1);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_price` double NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,60,1),(3,0,3),(4,0,4),(5,0,5);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_details`
--

DROP TABLE IF EXISTS `cart_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pricesp` double NOT NULL,
  `so_luong` int NOT NULL,
  `cart_id` bigint DEFAULT NULL,
  `orders_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhq1m50l0ke2fkqxxd6ubo3x4b` (`cart_id`),
  KEY `FKgwy4ca8jimru9c7bv952hggcl` (`orders_id`),
  KEY `FKngo5q1x6m7sudq0m8ylo5prrh` (`product_id`),
  CONSTRAINT `FKgwy4ca8jimru9c7bv952hggcl` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKhq1m50l0ke2fkqxxd6ubo3x4b` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKngo5q1x6m7sudq0m8ylo5prrh` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_details`
--

LOCK TABLES `cart_details` WRITE;
/*!40000 ALTER TABLE `cart_details` DISABLE KEYS */;
INSERT INTO `cart_details` VALUES (1,30,1,NULL,1,6),(2,40,2,NULL,1,2),(3,70,2,NULL,2,8),(4,50,2,NULL,3,3),(5,20,1,NULL,3,2),(6,20,1,NULL,4,2),(8,50,1,NULL,4,12),(10,25,1,NULL,5,3),(12,25,1,NULL,6,3),(13,60,2,NULL,6,5),(14,20,1,NULL,7,2),(15,25,1,NULL,8,3),(16,75,3,NULL,9,3),(17,60,2,NULL,10,5),(18,30,2,NULL,11,7),(19,20,1,NULL,12,2),(21,20,1,NULL,13,2),(22,50,1,NULL,13,12),(23,25,1,NULL,14,13),(24,60,2,1,NULL,5);
/*!40000 ALTER TABLE `cart_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2o7ijfxp9nv014xfgn93go7cd` (`name`),
  UNIQUE KEY `UK_mm4cmvteo84wq24upfvucdy08` (`product_id`),
  CONSTRAINT `FKgpextbyee3uk9u6o2381m7ft1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'https://drive.google.com/uc?export=view&id=1yrVuvq3d947JeehXHs7GOv7YwxHnw3Jt',1),(2,'https://drive.google.com/uc?export=view&id=1JWPYyG0iOa8nkX4eX7mzNNDYrzUXpyGQ',2),(3,'https://drive.google.com/uc?export=view&id=1exv4NLO2jXAxNneRa-DAH-P5EqidUgZi',3),(4,'https://drive.google.com/uc?export=view&id=1AwybjtOS1_30fgWf0LPj-5GFYrtUP3vh',4),(5,'https://drive.google.com/uc?export=view&id=1KtPOTQvmE6BfZ4-4jkl6tqM3h37uxzwj',5),(6,'https://drive.google.com/uc?export=view&id=1YbwMDqo853ea0VW9WqO2L-OgUt41Rdbi',6),(7,'https://drive.google.com/uc?export=view&id=1OIpga1TuOPyNn08qe7XnI4eZvpCHIwmp',7),(8,'https://drive.google.com/uc?export=view&id=1qjIvcJO86_ZJ5giPlWslvfpPJO2AvAUh',8),(9,'https://drive.google.com/uc?export=view&id=11U0Q8RHkqpmAXZdsJp0gJi5WA549_9ep',9),(10,'https://drive.google.com/uc?export=view&id=16nI6qPm0BUt4xmxwESJe53f4z9osoxLb',10),(11,'https://drive.google.com/uc?export=view&id=1_AkUIojwPjm5Na348jSP1Qc03IOI0JZ_',11),(12,'https://drive.google.com/uc?export=view&id=19XFTMvgDx5eBQMwc-fsWC9qROe4QtFlA',12),(13,'https://drive.google.com/uc?export=view&id=12cykgnnv2TMgufYXPps7M1BiX8gic4Dl',13);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_date` date DEFAULT NULL,
  `total_price` double NOT NULL,
  `type_delivery` int NOT NULL,
  `address_id` bigint DEFAULT NULL,
  `status_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `promotion_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5464gxwc32ongdvka2rtvw96` (`address_id`),
  KEY `FKnoettwqr56yaai4i8nwxg4huo` (`status_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  KEY `FKkl19lst67x545047o4n1d0jpv` (`promotion_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKf5464gxwc32ongdvka2rtvw96` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKkl19lst67x545047o4n1d0jpv` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`),
  CONSTRAINT `FKnoettwqr56yaai4i8nwxg4huo` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2024-02-06',70,2,2,4,1,NULL),(2,'2024-04-25',95,1,3,4,1,NULL),(3,'2024-04-26',70,2,2,4,1,NULL),(4,'2024-05-01',95,1,2,3,1,NULL),(5,'2024-05-06',50,1,3,4,1,NULL),(6,'2024-05-06',110,1,3,4,1,NULL),(7,'2024-03-03',45,1,1,4,1,NULL),(8,'2024-03-04',50,1,3,4,1,NULL),(9,'2024-05-08',100,1,1,2,1,1),(10,'2024-05-08',65,1,1,2,1,1),(11,'2024-05-08',10,2,3,2,1,1),(12,'2024-05-08',45,1,3,5,1,NULL),(13,'2024-05-09',75,1,1,1,1,1),(14,'2024-05-10',30,1,3,1,1,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_favorite` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `sold` bigint NOT NULL,
  `status` int NOT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,_binary '\0','Salad',15,0,0,3),(2,_binary '','Trà sữa',20,1,1,2),(3,_binary '','Tokkboki',25,2,1,1),(4,_binary '','Bánh trứng',25,0,1,3),(5,_binary '\0','Gà xiên',30,0,1,1),(6,_binary '\0','Mì ý',30,0,1,1),(7,_binary '','Trà đào',15,0,1,2),(8,_binary '','Mì cay',35,2,1,4),(9,_binary '\0','Pepsi',35,0,1,2),(10,_binary '\0','Soup',20,0,1,4),(11,_binary '\0','Gà rán',35,0,1,3),(12,_binary '','Gà quay',50,0,1,1),(13,_binary '\0','Bánh',25,0,1,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `discount` double NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (1,20,_binary ''),(2,50,_binary ''),(3,30,_binary '\0'),(4,40,_binary '\0');
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Chờ xác nhận'),(2,'Chờ thực hiện '),(3,'Chờ vận chuyển'),(4,'Thành công'),(5,'Đã hủy');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '','mtf072111@gmail.com','https://drive.google.com/uc?export=view&id=1k-U2xgK9TAgzfamXC_WKMnFdLT8ukPNo','Nguyen Huu Duc','542245','$2a$10$AYBb39gpDBlriCV2b2dWD.RliMyfdG0cTyIN4/Tb2QabblgsbMW2a','0383367703',2),(3,_binary '','huuduc@gmail.com',NULL,'Nguyen Huu Duc','046232','$2a$10$ORWg.ceK0SnhGD2rLRDB4.HpVdX0qO1/PmRXYASM8F8Bje.6OrB4K','012345789',1),(4,_binary '','artorias000a@gmail.com',NULL,'Be Truong Duong ','436888','$2a$10$Kkv.GffStCWuZq9BnRdZWefH27MhNkuoYm46LauvsK8soZ75cszyC','0123456789',2),(5,_binary '','bw067111@gmail.com','https://drive.google.com/uc?export=view&id=1jZw66u80PvqGFu7piYRK3GZ_qkOV_fjB','dức','854226','$2a$10$8x.0A.GKZ3RzW.POfdAWW.2ipaXgcrV8.thboU56xScDerT8nIFPa','01234678',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-10 21:29:31
