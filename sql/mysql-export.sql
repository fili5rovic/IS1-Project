-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: is1_projekat
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `audio_kategorija`
--

DROP TABLE IF EXISTS `audio_kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio_kategorija` (
  `id_audio` int NOT NULL,
  `id_kategorija` int NOT NULL,
  PRIMARY KEY (`id_audio`,`id_kategorija`),
  KEY `kategorija_idx` (`id_kategorija`),
  CONSTRAINT `audio` FOREIGN KEY (`id_audio`) REFERENCES `audio_snimak` (`ida`) ON DELETE CASCADE,
  CONSTRAINT `kategorija` FOREIGN KEY (`id_kategorija`) REFERENCES `kategorija` (`idk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audio_kategorija`
--

LOCK TABLES `audio_kategorija` WRITE;
/*!40000 ALTER TABLE `audio_kategorija` DISABLE KEYS */;
INSERT INTO `audio_kategorija` VALUES (1,1),(2,1),(2,2),(2,3),(3,3);
/*!40000 ALTER TABLE `audio_kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audio_snimak`
--

DROP TABLE IF EXISTS `audio_snimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio_snimak` (
  `ida` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `trajanje` int NOT NULL,
  `vlasnik_idk` int NOT NULL,
  `datum` datetime NOT NULL,
  PRIMARY KEY (`ida`),
  KEY `id_vlasnik_idx` (`vlasnik_idk`),
  CONSTRAINT `id_vlasnik` FOREIGN KEY (`vlasnik_idk`) REFERENCES `korisnik` (`idk`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audio_snimak`
--

LOCK TABLES `audio_snimak` WRITE;
/*!40000 ALTER TABLE `audio_snimak` DISABLE KEYS */;
INSERT INTO `audio_snimak` VALUES (1,'glasovna1',39,1,'2024-12-12 00:00:00'),(2,'Ekonomija drustva',632,3,'2022-06-22 00:00:00'),(3,'ETF',209,2,'2025-02-10 00:00:00');
/*!40000 ALTER TABLE `audio_snimak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idk` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idk`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (1,'Glasovna poruka'),(2,'Podcast'),(3,'Dokumentarac'),(4,'Pesma');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idk` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `godiste` date NOT NULL,
  `pol` enum('musko','zensko','nebinarno','drugo') NOT NULL,
  `id_mesto` int NOT NULL,
  PRIMARY KEY (`idk`),
  KEY `korisnik_mesto_idx` (`id_mesto`),
  CONSTRAINT `korisnik_mesto` FOREIGN KEY (`id_mesto`) REFERENCES `mesto` (`idm`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Filip','filip@gmail.com','2003-09-03','musko',1),(2,'Jovan','jovan@gmail.com','2002-07-15','musko',1),(3,'Lazar','lazar@gmail','2003-07-31','musko',1),(4,'Milica','milica@gmail.com','1999-11-29','zensko',2),(5,'Vanja','vanja@gmail.com','2005-06-16','zensko',3),(17,'test','test@gmail.com','2000-09-03','musko',1);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `idm` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idm`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Beograd'),(2,'Novi Sad'),(3,'Nis');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `id_korisnik` int NOT NULL,
  `id_audio` int NOT NULL,
  `ocena` int NOT NULL,
  `datum` datetime NOT NULL,
  PRIMARY KEY (`id_korisnik`,`id_audio`),
  KEY `ocena_audio_idx` (`id_audio`),
  CONSTRAINT `ocena_audio` FOREIGN KEY (`id_audio`) REFERENCES `audio_snimak` (`ida`) ON DELETE CASCADE,
  CONSTRAINT `ocena_korisnik` FOREIGN KEY (`id_korisnik`) REFERENCES `korisnik` (`idk`) ON DELETE CASCADE,
  CONSTRAINT `ocena_chk_1` CHECK ((`ocena` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (1,1,5,'2024-01-03 13:30:08'),(2,2,3,'2024-01-02 09:23:47'),(3,3,2,'2023-07-07 11:01:59');
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `omiljeni`
--

DROP TABLE IF EXISTS `omiljeni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `omiljeni` (
  `id_korisnika` int NOT NULL,
  `id_audio` int NOT NULL,
  PRIMARY KEY (`id_korisnika`,`id_audio`),
  KEY `omiljeni_audio_idx` (`id_audio`),
  CONSTRAINT `omiljeni_audio` FOREIGN KEY (`id_audio`) REFERENCES `audio_snimak` (`ida`) ON DELETE CASCADE,
  CONSTRAINT `omiljeni_korisnik` FOREIGN KEY (`id_korisnika`) REFERENCES `korisnik` (`idk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `omiljeni`
--

LOCK TABLES `omiljeni` WRITE;
/*!40000 ALTER TABLE `omiljeni` DISABLE KEYS */;
INSERT INTO `omiljeni` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `omiljeni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `idp` int NOT NULL AUTO_INCREMENT,
  `cena` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idp`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,1000.00),(2,1500.00),(3,2000.00),(5,15.00);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `idp` int NOT NULL AUTO_INCREMENT,
  `datum_od` datetime NOT NULL,
  `idk` int NOT NULL,
  `cena` decimal(10,2) NOT NULL,
  `id_paket` int NOT NULL,
  PRIMARY KEY (`idp`),
  UNIQUE KEY `idk_UNIQUE` (`idk`),
  KEY `pretplata_paket_idx` (`id_paket`),
  CONSTRAINT `idk` FOREIGN KEY (`idk`) REFERENCES `korisnik` (`idk`) ON DELETE CASCADE,
  CONSTRAINT `pretplata_paket` FOREIGN KEY (`id_paket`) REFERENCES `paket` (`idp`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,'2024-07-15 00:00:00',1,1000.00,1),(2,'2024-06-06 00:00:00',2,1500.00,2),(3,'2024-07-16 00:00:00',3,2000.00,3),(4,'2024-08-07 00:00:00',4,2000.00,3),(15,'2024-07-15 00:00:00',5,1000.00,1);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slusanje`
--

DROP TABLE IF EXISTS `slusanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slusanje` (
  `id_korisnik` int NOT NULL,
  `id_audio` int NOT NULL,
  `datum_slusanja_od` datetime NOT NULL,
  `sekunda_od` int NOT NULL,
  `sekunda_odslusano` int NOT NULL,
  PRIMARY KEY (`id_korisnik`,`id_audio`),
  KEY `slusanje_audio_idx` (`id_audio`),
  CONSTRAINT `slusanje_audio` FOREIGN KEY (`id_audio`) REFERENCES `audio_snimak` (`ida`),
  CONSTRAINT `slusanje_korisnik` FOREIGN KEY (`id_korisnik`) REFERENCES `korisnik` (`idk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slusanje`
--

LOCK TABLES `slusanje` WRITE;
/*!40000 ALTER TABLE `slusanje` DISABLE KEYS */;
INSERT INTO `slusanje` VALUES (1,1,'2025-01-03 00:00:00',0,30),(2,2,'2025-01-04 00:00:00',0,3);
/*!40000 ALTER TABLE `slusanje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-10 14:09:56
