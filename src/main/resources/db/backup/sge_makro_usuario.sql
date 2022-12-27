-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: sge_makro
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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(120) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `observacao` text,
  `ativo` tinyint(1) DEFAULT '1',
  `codigo_empresa` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'AILTON LUIZ','contato@ailtonluiz.com','$2a$10$RoznDLn5srxuj8.i3FcPtOHW7e1Wwuom5UcmDzJvT3MI6kKvbpTky','(376) 610-680','',1,1),(3,'HORACIO','horacio@sgssud.com','$2a$10$tQhcmrhRx.Pl0UQND/eVTuR0HuNUYu6DWC.z9W1FFUL0QxOolxzSC','(376) 657-302','',1,1),(4,'Administrador','administrador','$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG','(376) 610-680',NULL,1,1),(5,'FRANCISCO GOMEZ','franciscogomez@sgssud.com','$2a$10$9.HAdI4mIVBsX.cOCyQZN.5STcco/NvsO0fK9xPfKaTkRB9kmQoJ.','(376) 638-550','',0,1),(6,'JUAN CASTILLO','juancastillo@sgssud.com','$2a$10$q5W1lzR5ETNm0TFkJwBUv.AFQyvlPYno63mLUNGBrTd2KpFUki7se','(376) 688-396','',1,1),(7,'WALTER','walter@sgssud.com','$2a$10$Lsa2EqjM9suN5PFendGr6.OnPvPEchlixkrUAa8/6r4CUIb5z3LrK','(376) 638-550','',1,1),(8,'TESTE','teste@teste.com','$2a$10$i/MXvKFPhgec2dhE4QaZ5.Vqt/KzeZp28Q2VHTC9238yLn7sozGGq','(123) 132-321','123',1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-29 23:26:41
