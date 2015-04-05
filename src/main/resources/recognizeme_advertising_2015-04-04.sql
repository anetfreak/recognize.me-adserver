# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.23)
# Database: recognizeme_advertising
# Generation Time: 2015-04-05 04:15:42 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table ad_brand
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ad_brand`;

CREATE TABLE `ad_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `website` varchar(255) NOT NULL DEFAULT '',
  `domain` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `brandImage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table ad_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ad_category`;

CREATE TABLE `ad_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table ad_content_type
# ------------------------------------------------------------

CREATE TABLE `ad_content_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_type_name` varchar(45) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table advertisement
# ------------------------------------------------------------

CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `ad_brand_id` int(11) NOT NULL,
  `region` varchar(45) NOT NULL,
  `language` varchar(45) NOT NULL DEFAULT 'EN-US',
  `ad_content` longblob NOT NULL,
  `ad_content_type` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `expiry_date` datetime NOT NULL,
  `ad_category_id` int(11) NOT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ad_category_id_idx` (`ad_category_id`),
  KEY `fk_ad_brand_id_idx` (`ad_brand_id`),
  CONSTRAINT `fk_ad_brand_id` FOREIGN KEY (`ad_brand_id`) REFERENCES `ad_brand` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ad_category_id` FOREIGN KEY (`ad_category_id`) REFERENCES `ad_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
