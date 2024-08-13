-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: samsungdb
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `payment_entity`
--

DROP TABLE IF EXISTS `payment_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_entity` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `payment_date_time` datetime(6) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `book_id` bigint DEFAULT NULL,
  `imp_uid` varchar(255) DEFAULT NULL,
  `cancel_amount` decimal(38,2) DEFAULT NULL,
  `merchant_uid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `FKcty5cjs5mjj3ymbgh5rgyq9e` (`book_id`),
  CONSTRAINT `FKcty5cjs5mjj3ymbgh5rgyq9e` FOREIGN KEY (`book_id`) REFERENCES `book_entity` (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_entity`
--

LOCK TABLES `payment_entity` WRITE;
/*!40000 ALTER TABLE `payment_entity` DISABLE KEYS */;
INSERT INTO `payment_entity` VALUES (1,40000.00,'2024-06-24 16:50:22.587039','bank','성공',12,NULL,NULL,NULL),(2,25000.00,'2024-06-24 17:25:22.316916','mobile','성공',13,NULL,NULL,NULL),(3,40000.00,'2024-06-24 17:44:26.160692','bank','성공',14,NULL,NULL,NULL),(11,45000.00,'2024-06-25 16:24:37.303411','mobile','성공',50,NULL,NULL,NULL),(12,45000.00,'2024-06-25 16:34:49.467046','mobile','성공',51,NULL,NULL,NULL),(13,15000.00,'2024-06-25 16:39:11.029445','mobile','성공',52,NULL,NULL,NULL),(14,20000.00,'2024-06-25 17:02:23.454370','card','성공',54,NULL,NULL,NULL),(15,1.00,'2024-06-25 17:24:21.957668','phone','paid',NULL,'imp_234832741539',0.00,'merchant_b9fa0c16-a2ed-4c4c-a809-c83c597a5573'),(16,1.00,'2024-06-26 15:48:48.750333','phone','paid',NULL,'imp_900487993183',0.00,'merchant_117d667e-82a4-4bae-8128-b3c6686ddfaf'),(17,1.00,'2024-06-27 10:27:58.827775','phone','cancelled',63,'imp_472632466211',1.00,'merchant_ed9a5c47-e21f-444c-86a7-899bcd850635'),(18,1.00,'2024-06-27 10:47:54.266697','phone','paid',64,'imp_451838454128',0.00,'merchant_f4e85a0e-7251-497e-b8bc-0d8ecdd6591c'),(19,9.00,'2024-06-27 11:06:49.716431','phone','cancelled',65,'imp_749986228207',9.00,'merchant_d468b30f-3b01-4e25-9504-a1ee9dd7c98b'),(20,2.00,'2024-06-27 11:35:37.852155','card','cancelled',66,'imp_632675426579',2.00,'merchant_8122b327-a723-4a6c-b19e-61066ef24f38'),(21,1.00,'2024-06-27 11:45:15.872254','card','cancelled',69,'imp_850277951673',1.00,'merchant_23ccaafa-02c9-42d9-beb9-059753bbd431'),(22,2.00,'2024-06-27 16:03:45.477221','phone','paid',70,'imp_514799449706',0.00,'merchant_4822ebca-8ea3-4fde-9deb-73b1d2a472cc'),(23,1.00,'2024-06-27 16:13:49.430811','phone','paid',71,'imp_156408929910',0.00,'merchant_a7dcb928-07c9-48e5-b1de-d632ef05711f'),(24,2.00,'2024-06-27 17:11:50.643644','phone','cancelled',72,'imp_168884462889',2.00,'merchant_dd846640-73ae-4a01-88b2-f358e4b5f639'),(25,2.00,'2024-06-27 17:13:57.854994','phone','cancelled',73,'imp_521017498829',2.00,'merchant_f7f97065-c6b3-4721-a8ea-8eab950c25a4'),(26,2.00,'2024-06-27 17:15:56.331637','phone','paid',74,'imp_357132132473',0.00,'merchant_0293432d-8802-40d2-8f8c-ff3eaf0ca179'),(27,2.00,'2024-06-27 17:18:06.074776','phone','paid',75,'imp_841230491792',0.00,'merchant_d7e8abbf-edab-4746-a7d8-05f07c19972b'),(28,2.00,'2024-06-27 17:22:51.064812','phone','cancelled',76,'imp_173551800130',2.00,'merchant_bc7868bb-6c70-41f5-8b3c-91564c14dd16'),(29,2.00,'2024-06-27 17:24:47.530341','card','cancelled',77,'imp_094662014037',2.00,'merchant_73b2bb17-af7d-4bf5-bb28-04c8d88735e3'),(30,2.00,'2024-06-27 17:38:05.249515','phone','paid',78,'imp_597461670635',0.00,'merchant_65fac96c-a8e1-454c-89d1-90874e4be5bc'),(31,1.00,'2024-06-27 17:39:58.517059','phone','cancelled',79,'imp_076576625010',1.00,'merchant_dfcf55f7-0c13-4d99-9044-3e2eea72accc'),(32,2.00,'2024-06-28 12:19:39.965068','phone','cancelled',80,'imp_049754021237',2.00,'merchant_7aeffec8-18bb-40ea-bdd9-0569dab949b0'),(33,1.00,'2024-06-28 12:21:35.946624','phone','paid',81,'imp_689865543543',0.00,'merchant_f51000bc-aa51-4001-a34e-9d08538ecbeb'),(34,1.00,'2024-06-28 12:22:25.778956','phone','cancelled',82,'imp_174927545854',1.00,'merchant_abe57f1e-f7b8-4089-a00d-3d349d0d9899'),(35,2.00,'2024-06-28 14:18:22.173732','phone','cancelled',83,'imp_874865697196',2.00,'merchant_8bc64947-6e4b-4454-a8f9-80dd06139d02'),(36,2.00,'2024-06-28 17:31:21.317318','phone','cancelled',84,'imp_937459227420',2.00,'merchant_c951e0ae-03b6-4a41-ab27-8c543f1a9b07'),(37,2.00,'2024-07-01 14:11:29.159351','phone','paid',87,'imp_570661397604',0.00,'merchant_2ed0f369-f48e-4886-863a-5b3ce0bdb02c'),(38,3.00,'2024-07-01 14:21:37.039405','phone','paid',88,'imp_021276922916',0.00,'merchant_49dd0758-baba-423b-a140-5004baf3cadf'),(39,4.00,'2024-07-01 14:24:32.061790','phone','paid',89,'imp_928453317848',0.00,'merchant_caccc27d-22ae-49c5-aadf-9d049fcdefbd'),(40,1.00,'2024-07-01 14:31:54.013986','phone','paid',90,'imp_075826824659',0.00,'merchant_e00e393d-eba3-49ce-bd02-73882a04a4d8'),(41,1.00,'2024-07-01 14:32:29.874508','phone','paid',91,'imp_979930430064',0.00,'merchant_3fb53b6c-e9c5-4bd8-b9ec-3b778c270122'),(42,4.00,'2024-07-01 14:34:14.438883','phone','cancelled',92,'imp_406031296956',4.00,'merchant_6db2df66-9ebb-42eb-a2aa-7ffde621c656'),(43,2.00,'2024-07-01 15:45:35.984591','phone','cancelled',93,'imp_547313345085',2.00,'merchant_d7ab5a04-cedb-4739-b63c-a6a85e095e52'),(44,1.00,'2024-07-01 16:05:12.725433','phone','cancelled',95,'imp_018484240178',1.00,'merchant_e7017e87-cdc8-4af8-bde6-336252850c16'),(45,1.00,'2024-07-01 17:18:00.129839','phone','cancelled',96,'imp_270848993886',1.00,'merchant_cca859f8-7fc1-44bf-8cf0-e3102483bbed'),(46,1.00,'2024-07-01 17:25:11.575962','phone','paid',97,'imp_683292237687',0.00,'merchant_ba8f257c-0527-4860-9b6e-aa4c420d3a7d'),(47,3.00,'2024-07-01 17:27:52.901057','phone','paid',98,'imp_092450740755',0.00,'merchant_faa06110-4c46-4aea-862e-273405ba5ec8'),(48,2.00,'2024-07-01 17:37:41.566187','phone','paid',99,'imp_574037358205',0.00,'merchant_6adea002-9ab2-4556-a3fa-69e1d4b5e68f');
/*!40000 ALTER TABLE `payment_entity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-02 14:04:42
