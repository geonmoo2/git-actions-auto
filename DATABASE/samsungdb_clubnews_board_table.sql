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
-- Table structure for table `clubnews_board_table`
--

DROP TABLE IF EXISTS `clubnews_board_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clubnews_board_table` (
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clubnews_board_table`
--

LOCK TABLES `clubnews_board_table` WRITE;
/*!40000 ALTER TABLE `clubnews_board_table` DISABLE KEYS */;
INSERT INTO `clubnews_board_table` VALUES (1,'2024-07-02 10:45:06.807609',NULL,'삼성 라이온즈는 2일부터 대구 삼성 라이온즈 파크에서 열리는 KIA 타이거즈와의 홈 3연전 동안“달빛시리즈”이벤트를 진행한다.\r\n\r\n\r\n\r\n양 팀 선수단은 해당시리즈 기간 동안 올드 유니폼을 입고 경기를 치른다.\r\n달빛시리즈를 맞아 특별한 시구 행사를 마련했다. 시리즈 첫날인 2일에는 KIA 타이거즈 캐릭터 호걸이 시구와 대구시 캐릭터 도달쑤가 시타를 맡는다. 3일에는 광주 대구 고속도로 개통 40주년을 기념하여 한국도로공사 대표이사와 캐릭터가 시구와 시타를 진행할 예정이다. 마지막 날인 4일에는 구단에 사연을 접수해 선정된 양 팀 팬이 시구와 시타의 기회를 갖는다.\r\n달빛시리즈 기간 동안 합동 전광판을 운영하고, 양 팀 개시 영상 및 선수 등장 영상이 송출된다. 이 밖에 4일(목) 경기 전에는 삼성 이성규 선수와 KIA 최형우 선수의 팬 사인회가 열린다.\r\n이벤트에 대한 자세한 내용은 구단 SNS를 통해서 확인할 수 있다.\r\n\r\n',0,'1234','[보도자료] 삼성, 2~4일 KIA전 달빛시리즈 운영','user2',NULL,NULL),(2,'2024-07-02 10:45:41.700896',NULL,'삼성 라이온즈 타자 김영웅이 올곧은병원에서 시상하는 5월 월간 MVP로 선정됐다.\r\n\r\n\r\n\r\n김영웅은 5월 한 달간 24경기에 출전해 타율 0.278(90타수 25안타 6홈런) 16타점 14득점을 기록했다.\r\n\r\n\r\n\r\n특히, 이 기간 동안 6개의 홈런을 치며 데뷔 첫 두 자릿수 홈런을 달성하고, 월간 홈런 공동 2위에 올랐다.\r\n\r\n\r\n\r\nMVP에 선정된 김영웅은 “MVP를 받았으면 좋겠다 생각했는데 생각보다 빨리 받은 것 같아 기분 좋다. 상을 받은 만큼 더 열심히 해야 할 것 같다”고 말했다.\r\n\r\n\r\n\r\n이날 시상은 올곧은병원 임경환 원장이 진행했으며, 김영웅에게는 소정의 상품권이 주어졌다.',0,'1234','김영웅, 올곧은병원 5월 월간 MVP 선정','user2',NULL,NULL),(3,'2024-07-02 10:46:05.324113',NULL,'삼성 라이온즈는 28일(화) 내야수 오재일을 내주고 KT로부터 내야수 박병호를 데려오는 트레이드를 단행했다.\r\n\r\n\r\n새롭게 라이온즈의 유니폼을 입게 된 박병호는 2005년 1차 지명을 받아 LG에 입단했다. 프로 통산 1614경기에 출전하며 통산 타율0.276, 383홈런, 1151타점, OPS 0.923을 기록했다. 팀에 필요한 오른손 장타자로서 팀타선의 좌우 밸런스를 공고하게 함은 물론 월등한 홈런 생산성이라는 장점을 펜스 거리가 짧은 라이온즈 파크에서 극대화시킬 것으로 기대된다.',0,'1234','삼성 오재일 - KT 박병호 1대1 트레이드','user2',NULL,NULL),(4,'2024-07-02 10:46:22.519477',NULL,'삼성 라이온즈 투수 원태인이 올곧은병원에서 시상하는 4월 월간 MVP로 선정됐다.\r\n원태인은 4월 한 달간 5경기에 등판해 29⅓이닝을 던지며 4승 1패 평균자책점 2.15을 기록했다.\r\nMVP에 선정된 원태인은 “4월 모습을 이번 달에도 이어 나갔어야 했는데 그러지 못했다. 부진한 모습 보여 드려 아쉽다. 앞으로 더 잘할 수 있도록 노력하겠다”고 말했다.\r\n이날 시상은 올곧은병원 우동화 병원장이 진행했으며 원태인에게는 소정의 상품권이 주어졌다.\r\n\r\n',0,'1234','원태인, 올곧은병원 4월 월간 MVP 선정','user2',NULL,NULL),(5,'2024-07-02 10:46:35.390800',NULL,'삼성 라이온즈가 18일(토) 한화 이글스와의 대구 홈경기에서 ‘농심 배홍동데이’ 행사를 진행한다. \r\n\r\n\r\n\r\n먼저 대구 삼성 라이온즈파크(이하 라팍)광장에서 포토존 이벤트를 준비했다. 배홍동 포토존에서 인증 사진을 찍고 자신의 SNS에 업로드하면 선착순 1000명에게 배홍동 비빔면과 쫄쫄면이 들어있는 간식세트를 제공한다.\r\n\r\n\r\n\r\n경기 중에는 ‘배홍동 큰사발’ 출시기념 ‘큰사이즈발 이벤트’를 진행한다. 이날 경기장을 찾아주신 팬 중 발 사이즈가 큰 관객 6명(남자 3명, 여자 3명)에게 삼성 라이온즈와 농심에서 준비한 선물을 증정할 계획이다. 이 밖에 배홍동 댄스 챌린지, 현장 SNS 추첨 등 다양한 이벤트가 진행될 예정이다.\r\n\r\n\r\n\r\n당일 시구·시타는 농심이 진행하는 ‘세상을 울리는 안심 캠페인’의 일환으로 대구 수성소방서 소속 임기환 소방장과 배대표(배홍동 캐릭터)가 각각 맡는다. 임기환 소방장은 2021년 수성동 주택 화재에서 일가족 4명을 구조하는 등 생명 구조에 헌신한 공로로 제29회 KBS 119상에서 대상의 영예를 차지했다.',0,'1234','삼성, ‘농심 배홍동데이’ 진행','user2',NULL,NULL),(6,'2024-07-02 10:46:51.289882',NULL,'삼성 라이온즈가 3월 30일(토)부터 2024시즌 팬북 판매를 시작한다. 올해 팬북은 전년도 종이 팬북과는 달리 특별히 선수들의 사진이 포토카드로 구성되어 있는 ‘포토카드북’(이하 팬북) 형태로 제작되었다.\r\n\r\n\r\n2024 오키나와 스프링캠프 사진으로 구성된 팬북은 1000개 한정 선착순 판매되며, 해당 사진들은 오직 이번 팬북을 통해서만 만나볼 수 있다.\r\n\r\n\r\n가장 큰 특징은 권당 랜덤으로 1장 제공되는 오승환, 구자욱, 원태인 등 선수 10인의 스페셜 포토카드에 해당 선수의 친필 사인이 새겨져 있다는 점이다.\r\n\r\n\r\n또한 삼성 라이온즈 선수 20인의 포토카드와 함께 구단 유니폼, 마스코트 등이 그려진 스티커가 포함되어 있다. 여분으로 제공되는 속지에는 추가로 구입한 포토카드를 모을 수도 있다. 팬 개인의 취향에 따라 선수들의 포토카드를 자유롭게 수집하고 꾸밀 수 있는 포토카드 앨범인 셈이다.\r\n\r\n\r\n2024시즌 팬북은 대구삼성라이온즈파크 내 팀스토어와 팀스토어 온라인몰(samsunglionsmall.com)을 통해 오는 30일(토)부터 구입할 수 있으며, 선착순 한정 판매되는 팬북의 판매가는 3만5000원이다.',0,'1234',' 삼성 라이온즈, 2024시즌 팬 포토카드북 발행','user2',NULL,NULL),(7,'2024-07-02 10:47:04.780394',NULL,'삼성 라이온즈가 29일(금) 코레일 대구본부와 업무 협약을 체결했다.\r\n\r\n\r\n\r\n이번 협약은 KTX를 이용하는 대구 경북, 수도권 팬들의 경기 관람이 활성화됨에 따라 장거리 응원에 나서는 라이온즈팬들의 이동 편의를 위해 이루어졌다. 주요 협력 사업으로 동대구역을 방문하는 팬들에게 특별한 추억을 선물하고, KTX 이용 시 다양한 혜택을 제공한다.\r\n\r\n\r\n\r\n삼성 라이온즈는 동대구역을 찾는 고객들을 위해 특별 포토존을 설치했다. 실제 선수들이 사용하는 라커룸과 유니폼이 전시하여 선수들과 한 공간에 있다는 경험을 제공한다.\r\n\r\n\r\n\r\n코레일 대구본부는 삼성 라이온즈를 응원하는 팬들을 위해 다양한 혜택을 제공한다. 다른 지역에서 방문하는 라이온즈 팬들은 5~40% 할인된 KTX 이용료와 삼성 라이온즈 한정판 굿즈를 포함한 특별 상품을 구매할 수 있다. 이 상품은 레츠코레일 홈페이지와 코레일톡을 통해 구매할 수 있다.\r\n\r\n\r\n\r\n삼성 라이온즈와 코레일 대구본부는 팬들에게 더 나은 서비스를 제공 할 수 있도록 지속적으로 협력해 나갈 것이다.\r\n\r\n',0,'1234','삼성, 코레일 대구본부와 업무협약 체결','user2',NULL,NULL);
/*!40000 ALTER TABLE `clubnews_board_table` ENABLE KEYS */;
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
