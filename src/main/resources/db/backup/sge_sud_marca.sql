-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: sge_sud
-- ------------------------------------------------------
-- Server version	5.7.37-log

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
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marca` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `ativo` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` VALUES (1,'VANISH',1),(2,'ABSOLUT',1),(3,'POLIAKOV',1),(4,'LABEL 5',1),(5,'BALLANTINES',1),(6,'JAGERMEISTER',1),(7,'LAGARTO',1),(8,'MIMOSIN',1),(9,'LENOR',1),(10,'MICOLOR',1),(11,'SANYTOL',1),(12,'ARIEL',1),(13,'MARLBORO',1),(14,'PHILIP MORRIS',1),(15,'CONTINENTE',1),(16,'AUSTIN',1),(17,'ELIXYR',1),(18,'SKIP',1),(19,'FAIRY',1),(20,'CODINA',1),(21,'AMBER LEAF',1),(22,'AMOOS',1),(23,'CHESTERFIELD',1),(24,'INTERVAL',1),(25,'L&M',1),(26,'CAMEL',1),(27,'BENSON & HEDGES',1),(28,'WINSTON',1),(29,'REDSTON',1),(30,'BOOMERANG',1),(31,'NEWS',1),(32,'GULIWER',1),(33,'MON TERROIR',1),(34,'RENOVA',1),(35,'FINISH',1),(36,'FULMAR',1),(37,'REPO',1),(38,'DOVE',1),(39,'FA',1),(40,'FLEUR DU PAYS',1),(41,'COCA-COLA',1),(42,'LA SALUD',1),(43,'SUPER CROIX',1),(44,'X-TRA',1),(45,'LECHAT',1),(46,'AXE',1),(48,'NOBAL',1),(49,'MICADERM',1),(50,'COTTAGE',1),(51,'SANEX',1),(52,'BIOTAN',1),(53,'ORO',1),(54,'OLYMPIA',1),(55,'AT HOME',1),(56,'QUERAY',1),(57,'HARIBO',1),(58,'PROF',1),(59,'SUPER BOCK',1),(60,'PROVOST',1),(61,'NAT.  HONEY',1),(62,'TUROLIVA',1),(63,'ABRIL',1),(64,'HEINEKEN',1),(65,'BREF',1),(66,'DESPERADOS',1),(67,'SAN MIGUEL',1),(68,'RICARD',1),(69,'51',1),(70,'PRADO',1),(71,'TESTE TRIGGER',1);
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_insere_marca
	AFTER INSERT
	ON marca
	FOR EACH ROW
BEGIN
 INSERT INTO sge_makro.marca
( codigo, 
nome, 
ativo)
VALUES (
NEW.codigo,
NEW.nome,
NEW.ativo);

END */;;
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
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_update_marca
	AFTER UPDATE
	ON marca
	FOR EACH ROW
BEGIN
 UPDATE sge_makro.marca
SET nome = NEW.nome,
	ativo = NEW.ativo
WHERE 
codigo = OLD.codigo;


END */;;
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
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_delete_marca
	BEFORE DELETE
	ON marca
	FOR EACH ROW
BEGIN
 DELETE FROM sge_makro.marca

WHERE  codigo = OLD.codigo;


END */;;
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

-- Dump completed on 2022-03-29 23:26:34
