-- MySQL dump 10.13  Distrib 5.7.16, for Win64 (x86_64)
--
-- Host: localhost    Database: bolsaempleo
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `competencias`
--

DROP TABLE IF EXISTS `competencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `competencias_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competencias`
--

LOCK TABLES `competencias` WRITE;
/*!40000 ALTER TABLE `competencias` DISABLE KEYS */;
INSERT INTO `competencias` VALUES (1,'ofimática ','manejo de ofimática ',3),(3,'hjhjkvg','fjjuh',3),(7,'competencias','fffzbnzkdkkzkdkdkzkdkndnxnxnxnnxxnxbxbxnnxnxnx\n\n',3),(9,'Programación ','Java\nAndroid\nPhp\nMysql ',7),(10,'Paqueteria office','Word\nExcel\nPowerPoint\nPublisher\nWordPad',7),(11,'Mantenimiento  ','Equipo de cómputo ',7);
/*!40000 ALTER TABLE `competencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cursos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `horas` int(11) NOT NULL,
  `fecha_ini` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `cursos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (2,'empleo 2','jskssksks',567,'2010-08-30','2012-01-15',3),(3,'hzjzjnz','bzjzjz',60,'2010-08-30','2012-08-30',6),(4,'Electrónica Digital ','Uso de Protoboard\n',380,'2014-02-08','2014-03-02',7),(5,'Mantenimiento de equipos de cómputo ','Arquitectura de la computadora ',200,'2015-08-03','2015-10-26',7);
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleos`
--

DROP TABLE IF EXISTS `empleos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `puesto` varchar(50) NOT NULL,
  `salario` float NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `vacantes` int(3) NOT NULL,
  `domicilio` varchar(150) DEFAULT NULL,
  `id_empresa` int(11) NOT NULL,
  `id_municipio` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_empresa` (`id_empresa`),
  KEY `id_municipio` (`id_municipio`),
  CONSTRAINT `empleos_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `empleos_ibfk_2` FOREIGN KEY (`id_municipio`) REFERENCES `municipios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleos`
--

LOCK TABLES `empleos` WRITE;
/*!40000 ALTER TABLE `empleos` DISABLE KEYS */;
INSERT INTO `empleos` VALUES (4,'ANALISTA',2000,'KKKSSMX XM',1,'3ra avn sur',1,1,1),(5,'rdtfrgy',45,'sdfgh',4,'dasdas',1,1,0);
/*!40000 ALTER TABLE `empleos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresas`
--

DROP TABLE IF EXISTS `empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rfc` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` bigint(15) NOT NULL,
  `correo` varchar(75) DEFAULT NULL,
  `encargado` varchar(100) NOT NULL,
  `domicilio` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `rfc` (`rfc`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresas`
--

LOCK TABLES `empresas` WRITE;
/*!40000 ALTER TABLE `empresas` DISABLE KEYS */;
INSERT INTO `empresas` VALUES (1,'EYCC98986468876','Servicios Tecnicos y de Reparacion del Sur',9621234567,'resetsur@gmail.com','Encargado A','Domicilio conocido'),(2,'qwe98345600000022111','coca cola',1962123456,'coca@gmail.com','karla mendz','asdasdasdasd'),(3,'RUE987687GSYSS07','Nombre empresa 1',9621221189,'email@ash.com','Encargado empresa ','domicilio conocido'),(4,'dfgyuhjukighf','esdfghjkjlhg',334567890,'erdtfgh@hg.com','dgfhj','dfghj'),(5,'aaksdjghakjh','mi compañia  de avión',889809283012983,'micorreo@hotmail.com','juan ramos','col centro , tuxtla gutierrez');
/*!40000 ALTER TABLE `empresas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `escolares`
--

DROP TABLE IF EXISTS `escolares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `escolares` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `escuela` varchar(150) NOT NULL,
  `no_nivel` int(2) NOT NULL,
  `documento` varchar(300) NOT NULL,
  `profesion` varchar(300) DEFAULT NULL,
  `fecha_ini` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `escolares_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escolares`
--

LOCK TABLES `escolares` WRITE;
/*!40000 ALTER TABLE `escolares` DISABLE KEYS */;
INSERT INTO `escolares` VALUES (1,'ITTAP ',3,'Certificado ','Lic en ingennieria','2012-08-30','2017-12-05',3),(2,'CBTis ',2,'Certificado ',NULL,'2010-08-01','2013-06-10',3),(5,'hsjsks',0,'certificado',NULL,'1995-08-30','1992-12-12',3),(6,'Unach',3,'Titulo',NULL,'1989-11-11','1989-12-12',8),(10,'Teodomiro Palacios ',0,'Certificado ',NULL,'1999-08-22','2005-07-14',7),(11,'Sor Juana Inés De La Cruz ',1,'Certificado ',NULL,'2005-08-18','2008-07-07',7),(12,'Preparatoria 1',2,'Certificado ',NULL,'2008-08-06','2011-06-22',7),(13,'Instituto Tecnológico de Tapachula ',3,'Título Profesional ',NULL,'2011-08-22','2015-05-25',7);
/*!40000 ALTER TABLE `escolares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lenguajes`
--

DROP TABLE IF EXISTS `lenguajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lenguajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `habla` int(3) NOT NULL,
  `lee` int(3) NOT NULL,
  `escribe` int(3) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `lenguajes_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lenguajes`
--

LOCK TABLES `lenguajes` WRITE;
/*!40000 ALTER TABLE `lenguajes` DISABLE KEYS */;
INSERT INTO `lenguajes` VALUES (1,'inglés',10,50,10,3),(2,'japones',20,0,0,3),(4,'hsjs',0,0,0,3),(5,'Inglés ',0,0,0,7);
/*!40000 ALTER TABLE `lenguajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `municipios`
--

DROP TABLE IF EXISTS `municipios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `municipios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipios`
--

LOCK TABLES `municipios` WRITE;
/*!40000 ALTER TABLE `municipios` DISABLE KEYS */;
INSERT INTO `municipios` VALUES (1,'Acacoyagua'),(2,'Acal'),(3,'Acapetahua'),(4,'Altamirano'),(5,'Amatán'),(6,'Amatenango de la Frontera'),(7,'Amatenango del Valle'),(8,'Angel Albino Corzo'),(9,'Arriaga'),(10,'Bejucal de Ocampo'),(11,'Bella Vista'),(12,'Berriozábal'),(13,'Bochil'),(14,'El Bosque'),(15,'Cacahoatán'),(16,'Catazajá'),(17,'Cintalapa'),(18,'Coapilla'),(19,'Comitán de Domínguez'),(20,'La Concordia'),(21,'Copainalá'),(22,'Chalchihuitán'),(23,'Chamula'),(24,'Chanal'),(25,'Chapultenango'),(26,'Chenalhó'),(27,'Chiapa de Corzo'),(28,'Chiapilla'),(29,'Chicoasén'),(30,'Chicomuselo'),(31,'Chilón'),(32,'Escuintla'),(33,'Francisco León'),(34,'Frontera Comalapa'),(35,'Frontera Hidalgo'),(36,'La Grandeza'),(37,'Huehuetán'),(38,'Huixtán'),(39,'Huitiupán'),(40,'Huixtla'),(41,'La Independencia'),(42,'Ixhuatán'),(43,'Ixtacomitán'),(44,'Ixtapa'),(45,'Ixtapangajoya'),(46,'Jiquipilas'),(47,'Jitotol'),(48,'Juárez'),(49,'Larráinzar'),(50,'La Libertad'),(51,'Mapastepec'),(52,'Las Margaritas'),(53,'Mazapa de Madero'),(54,'Mazatán'),(55,'Metapa'),(56,'Mitontic'),(57,'Motozintla'),(58,'Nicolás Ruíz'),(59,'Ocosingo'),(60,'Ocotepec'),(61,'Ocozocoautla de Espinosa'),(62,'Ostuacán'),(63,'Osumacinta'),(64,'Oxchuc'),(65,'Palenque'),(66,'Pantelhó'),(67,'Pantepec'),(68,'Pichucalco'),(69,'Pijijiapan'),(70,'El Porvenir'),(71,'Villa Comaltitlán'),(72,'Pueblo Nuevo Solistahuacán'),(73,'Rayón'),(74,'Reforma'),(75,'Las Rosas'),(76,'Sabanilla'),(77,'Salto de Agua'),(78,'San Cristóbal de las Casas'),(79,'San Fernando'),(80,'Siltepec'),(81,'Simojovel'),(82,'Sitalá'),(83,'Socoltenango'),(84,'Solosuchiapa'),(85,'Soyaló'),(86,'Suchiapa'),(87,'Suchiate'),(88,'Sunuapa'),(89,'Tapachula'),(90,'Tapalapa'),(91,'Tapilula'),(92,'Tecpatán'),(93,'Tenejapa'),(94,'Teopisca'),(95,'No Existe Ningun Municipio Con esta Clave'),(96,'Tila'),(97,'Tonalá'),(98,'Totolapa'),(99,'La Trinitaria'),(100,'Tumbalá'),(101,'Tuxtla Gutiérrez'),(102,'Tuxtla Chico'),(103,'Tuzantán'),(104,'Tzimol'),(105,'Unión Juárez'),(106,'Venustiano Carranza'),(107,'Villa Corzo'),(108,'Villaflores'),(109,'Yajalón'),(110,'San Lucas'),(111,'Zinacantán'),(112,'San Juan Cancuc'),(113,'Aldama'),(114,'Benemerito de las Americas'),(115,'Maravilla Tenejapa'),(116,'Marqués de Comillas'),(117,'Montecristo de Guerrero'),(118,'San Andrés Duraznal'),(119,'Santiago el Pinar'),(120,'Belisario Domínguez'),(121,'Emiliano Zapata'),(122,'El Parral'),(123,'Mezcalapa');
/*!40000 ALTER TABLE `municipios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curp` varchar(18) NOT NULL,
  `nombres` varchar(40) NOT NULL,
  `ape_pat` varchar(20) NOT NULL,
  `ape_mat` varchar(20) NOT NULL,
  `fecha_nac` date NOT NULL,
  `sexo` set('M','F') NOT NULL,
  `edo_civil` set('soltero(a)','casado(a)','divorciado(a)','viudo(a)','union libre') NOT NULL,
  `telefono` bigint(15) NOT NULL,
  `domicilio` varchar(150) DEFAULT NULL,
  `licencia` tinyint(1) NOT NULL DEFAULT '0',
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `curp` (`curp`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `personas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` VALUES (1,'EOZC950830hcssmr07','carlos ezam','escobar','zamora','1995-09-30','M','soltero(a)',9626969807,'Tapachula Chiapas',1,3),(3,'NAPY950508MVCRS04','Yusari Anahí','Navarro','Pérez','1995-08-05','F','soltero(a)',9625656231,'Tapachula',1,5),(4,'CBD950319MCSMR06','Amor','camacho','bello','1995-03-19','F','casado(a)',9622200290,'jdkdodld',1,6),(5,'123456789123456789','erik','de león','x','1983-10-25','M','soltero(a)',9611544620,'centroccc',1,8),(6,'NALM931012MCNJCS07','Mónica','Najera','López','1993-10-14','F','soltero(a)',9621408956,'12 Av. sur Prolongación Tapachula Chiapas',1,7),(7,'yyyyyyyyyyyyyyyyyy','erik','de leon','d','1983-10-25','M','soltero(a)',9611544620222,'centro',0,9);
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saves`
--

DROP TABLE IF EXISTS `saves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_empleo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_empleo` (`id_empleo`),
  CONSTRAINT `saves_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `saves_ibfk_2` FOREIGN KEY (`id_empleo`) REFERENCES `empleos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saves`
--

LOCK TABLES `saves` WRITE;
/*!40000 ALTER TABLE `saves` DISABLE KEYS */;
INSERT INTO `saves` VALUES (1,4,9);
/*!40000 ALTER TABLE `saves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajos`
--

DROP TABLE IF EXISTS `trabajos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trabajos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empresa` varchar(150) NOT NULL,
  `puesto` varchar(150) NOT NULL,
  `actividades` varchar(300) NOT NULL,
  `fecha_ini` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `trabajos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajos`
--

LOCK TABLES `trabajos` WRITE;
/*!40000 ALTER TABLE `trabajos` DISABLE KEYS */;
INSERT INTO `trabajos` VALUES (1,'ittap','tecnico','mantenimiento','2015-08-08','2016-10-01',3),(2,'Ecosur','Área de mantenimiento ','Mantenimiento a equipos de cómputo\nMantenimiento de redes ','2016-09-15','2017-03-24',7);
/*!40000 ALTER TABLE `trabajos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `authKey` varchar(50) DEFAULT NULL,
  `accessToken` varchar(255) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','admin@admin.com','$2y$13$lZiWZ2cCMEXMYLs9iL0Tlun6hSy3veOoUO6NZhH6DPjLtyj/z2VhW','PPtxjRcMiVTmJ8gQL8Qx9jYCEUTkEQ5y','',0),(3,'Carlos.ezam','carlos.ezam@Gmail.com','$2y$13$plIYpi8YqA62Ex8aM3ZEXeplOGObP.YdTDJpsVjM5OFVi2vGQ4Hd2','7_rLdDrmbWGphpQWwAgY1Ft8dRbN3MXp','T8_h2lgOC74QYtsnbRubW0jYilk8XCeu',0),(5,'Yusari','yusari@gmail.com','$2y$13$CFeIImKbfJAdI50fxZpdU.e9wNCK0JQ0Ui7FXdppFxnYaRKGrwjGO','1mUNz9PhXsKwGow3osL7WnuYa8VsBRFK','g8E0mKY77N6BCljLnHC-bEERWYZwmzDe',0),(6,'Mi.amor','darcy@gmail.com','$2y$13$RvlFGudRSi3CL.2zNoaBj.YFHDwCG1Jb0fhllEvkq63fUcTlm5jju','EJs9I3n3dlh7lymAm5VxA1gFirmRbY81','c3FS4V9sQnIuDHASxlCIJaR3nKKUK79p',0),(7,'kristel','kristel01809@gmail.com','$2y$13$iaX/hSeTJmi8/.4CXBKd6ekgdVcfH3LIbhk1/jnQDl6k94sEYrYly','gg3P1lFe4igpdm4-2SicBWwkEqia5g2N','I2f286JIdU0-BmNMhS3ZZNvJALf1WSH8',0),(8,'erik10','erik@x.com','$2y$13$/vGLT.fIZbxpRc9snhsbD.DIC3wkMACshad0DkNgBh6h2XYca0Bea','Y71ITlWkk6w0PPJ2ruCASDU1JoguIl0L','OUQdsFc3lhvd7aro1Eqn0R2LJHBGQr3y',0),(9,'erik11','erik@hotmail.com','$2y$13$aaFz95Y2.ir3KhE4fVdiLu/iOexgceu3WLMG5NrMbHUVqEjqIh9Zy','IIRtB-_991d6K-cDqhYev5B5CoZjMnvf','-zvIBSFv-4VNEWHmqjs9AsW6EgfLGkR4',0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-01 16:49:38
