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
-- Table structure for table `faq_board_table`
--

DROP TABLE IF EXISTS `faq_board_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq_board_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `board_contents` varchar(1000) DEFAULT NULL,
  `board_hits` int DEFAULT NULL,
  `board_pass` varchar(255) DEFAULT NULL,
  `board_title` varchar(255) DEFAULT NULL,
  `board_writer` varchar(20) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq_board_table`
--

LOCK TABLES `faq_board_table` WRITE;
/*!40000 ALTER TABLE `faq_board_table` DISABLE KEYS */;
INSERT INTO `faq_board_table` VALUES (1,'2024-07-02 10:49:30.671953',NULL,'경기장 내 안전과 쾌적한 관람을 위하여 주류 및 캔, 병과 1L 초과 PET음료의 반입이 제한됩니다.\r\n정해진 크기의 가방 및 쇼핑백류 각 1개 허용, 그 이외 가방, 아이스박스, 상자, 가스버너 등은 반입이 제한됩니다.\r\n가방 45Cm X 45Cm X 20Cm , 쇼핑백류 30Cm X 50Cm X 12Cm\r\n주류는 5도 이하의 저도 주류로 제한됩니다.\r\n이 외에 타인의 관람 방해 및 쾌적한 구장환경 관리에 저해가 되는 물품과 음식은 반입이 제한될 수 있습니다.\r\n자세한 사항은 KBO홈페이지의 B SAFE 캠페인을 참고해 주세요.',0,'1234','야구장 내 물품 반입 규정이 어떻게 되나요?','user2',NULL,NULL),(2,'2024-07-02 10:49:44.987093','2024-07-02 11:15:58.891035','KBO 홈페이지, KBO 공식 어플에서 확인할 수 있습니다.',0,'','라인업, 일정 등 경기정보를 알고싶어요','user2',NULL,NULL),(3,'2024-07-02 10:50:04.820660',NULL,'월화수목금 경기 시작 1시간 30분 전, 토일 경기 시작 2시간 전 입니다.',0,'1234','경기장 입장은 몇시부터 가능하나요?','user2',NULL,NULL),(4,'2024-07-02 10:50:12.572044',NULL,'총 3개입니다. 중앙매표소 부근의 Gate3 (3루 출입구), Gate 1 (1루 출입구), Gate 2(외야 출입구)\r\nGate 2(외야 출입구)는 당일 입장객 수에 따라 유동적으로 운영합니다.',0,'1234','입장 가능 게이트는 어디 어디가 있나요?','user2',NULL,NULL);
/*!40000 ALTER TABLE `faq_board_table` ENABLE KEYS */;
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
