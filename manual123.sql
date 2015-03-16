-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.15 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-03-16 17:41:54
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for manual
CREATE DATABASE IF NOT EXISTS `manual` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `manual`;


-- Dumping structure for table manual.attachment
CREATE TABLE IF NOT EXISTS `attachment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  `path` varchar(50) DEFAULT NULL,
  `upload_date` datetime NOT NULL,
  `user_comment` varchar(100) DEFAULT NULL,
  `contact_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `attachment_contact_fk_idx` (`contact_id`),
  CONSTRAINT `attachment_contact_fk` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- Dumping data for table manual.attachment: ~7 rows (approximately)
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` (`id`, `title`, `path`, `upload_date`, `user_comment`, `contact_id`) VALUES
	(1, 'Фото', '1.jpg', '2014-11-09 22:40:45', 'обновление', 1),
	(3, 'Текст', '3.doc', '2015-02-28 11:05:12', 'положения', 2),
	(7, 'Второе', '7.jpg', '2015-03-09 22:45:58', NULL, 1),
	(8, 'Стандарт', '8.docx', '2015-03-10 03:48:13', '    ', 5);
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;


-- Dumping structure for table manual.contact
CREATE TABLE IF NOT EXISTS `contact` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `midle_name` varchar(20) DEFAULT NULL,
  `birth_date` date NOT NULL,
  `sex_id` varchar(1) NOT NULL,
  `marital_status_id` int(2) unsigned NOT NULL,
  `citizenship` varchar(20) DEFAULT NULL,
  `website` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  `company` varchar(20) DEFAULT NULL,
  `country` varchar(20) NOT NULL,
  `town` varchar(20) NOT NULL,
  `street` varchar(50) DEFAULT NULL,
  `house` int(3) unsigned DEFAULT NULL,
  `flat` int(3) unsigned DEFAULT NULL,
  `index_value` int(6) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `contact_sex_fk_idx` (`sex_id`),
  KEY `contact_marital_status_fk_idx` (`marital_status_id`),
  CONSTRAINT `contact_marital_status_fk` FOREIGN KEY (`marital_status_id`) REFERENCES `marital_status` (`id`) ON UPDATE NO ACTION,
  CONSTRAINT `contact_sex_fk` FOREIGN KEY (`sex_id`) REFERENCES `sex` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table manual.contact: ~7 rows (approximately)
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` (`id`, `first_name`, `last_name`, `midle_name`, `birth_date`, `sex_id`, `marital_status_id`, `citizenship`, `website`, `email`, `image`, `company`, `country`, `town`, `street`, `house`, `flat`, `index_value`) VALUES
	(1, 'Юлия', 'Борисевская', 'Валентиновна', '1994-03-16', 'ж', 4, 'BLR', NULL, 'borisevska.yu@mail.ru', NULL, NULL, 'Беларусь', 'Минск', 'Коласа', NULL, NULL, NULL),
	(2, 'Светлана', 'Каулькина', 'Олеговна', '1964-02-16', 'ж', 2, 'Беларусь', 'www.kaulkina.by', 'kaulkina.s@mail.ru', NULL, 'Торгмаш', 'Беларусь', 'Гродно', 'Лиможа', 50, 262, 155555),
	(3, 'Анастасия', 'Хорошко', 'Валерьевна', '1994-02-09', 'ж', 4, 'Беларусь', NULL, NULL, NULL, 'БГУИР', 'Беларусь', 'Минск', 'Беды', 4, 510, 114558),
	(4, 'Игорь', 'Васильев', 'Аркадьевич', '1958-01-01', 'м', 1, 'Беларусь', 'www.igorv.by', 'vasiliev@gmail.com', NULL, 'Атлант', 'Беларусь', 'Брест', 'Победы', 20, 12, 158999),
	(5, 'Борис', 'Новиков', 'Анатольевич', '1980-04-17', 'м', 3, NULL, NULL, 'novikov@mail.ru', NULL, NULL, 'Беларусь', 'Могилев', NULL, NULL, NULL, NULL),
	(6, 'Вячеслав', 'Романенко', 'Юрьевич', '1985-11-25', 'м', 1, NULL, NULL, NULL, NULL, NULL, 'Беларусь', 'Молодечно', 'Октябрьская', 25, NULL, NULL),
	(7, 'Ангелина', 'Шведова', 'Васильевна', '1973-12-05', 'ж', 2, NULL, NULL, NULL, NULL, NULL, 'Россия', 'Москва', NULL, NULL, NULL, NULL),
	(8, 'Валерий', 'Качалов', 'Георгиевич', '1959-03-16', 'м', 1, 'Беларусь', NULL, NULL, NULL, NULL, 'Беларусь', 'Витебск', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;


-- Dumping structure for table manual.marital_status
CREATE TABLE IF NOT EXISTS `marital_status` (
  `id` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table manual.marital_status: ~4 rows (approximately)
/*!40000 ALTER TABLE `marital_status` DISABLE KEYS */;
INSERT INTO `marital_status` (`id`, `title`) VALUES
	(1, 'женат'),
	(2, 'замужем'),
	(3, 'не женат'),
	(4, 'не замужем');
/*!40000 ALTER TABLE `marital_status` ENABLE KEYS */;


-- Dumping structure for table manual.phone
CREATE TABLE IF NOT EXISTS `phone` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_code` int(3) unsigned NOT NULL,
  `operator_code` int(3) unsigned NOT NULL,
  `basic_number` int(7) unsigned NOT NULL,
  `phone_type_id` int(1) unsigned NOT NULL,
  `user_comment` varchar(100) DEFAULT NULL,
  `contact_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `phone_contact_fk_idx` (`contact_id`),
  KEY `phone_phone_type_fk_idx` (`phone_type_id`),
  CONSTRAINT `phone_contact_fk` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `phone_phone_type_fk` FOREIGN KEY (`phone_type_id`) REFERENCES `phone_type` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table manual.phone: ~6 rows (approximately)
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
INSERT INTO `phone` (`id`, `country_code`, `operator_code`, `basic_number`, `phone_type_id`, `user_comment`, `contact_id`) VALUES
	(1, 375, 29, 2286668, 1, NULL, 1),
	(2, 375, 29, 4586989, 1, 'Velcom', 2),
	(3, 315, 152, 766552, 2, 'личный', 1),
	(6, 375, 29, 7899988, 1, NULL, 5),
	(7, 375, 44, 1266654, 1, 'new', 5),
	(8, 375, 17, 7150202, 2, 'Минск', 5),
	(9, 375, 29, 8899965, 1, 'МТС', 8);
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;


-- Dumping structure for table manual.phone_type
CREATE TABLE IF NOT EXISTS `phone_type` (
  `id` int(1) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table manual.phone_type: ~2 rows (approximately)
/*!40000 ALTER TABLE `phone_type` DISABLE KEYS */;
INSERT INTO `phone_type` (`id`, `title`) VALUES
	(1, 'мобильный'),
	(2, 'домашний');
/*!40000 ALTER TABLE `phone_type` ENABLE KEYS */;


-- Dumping structure for table manual.sex
CREATE TABLE IF NOT EXISTS `sex` (
  `id` varchar(1) NOT NULL,
  `title` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table manual.sex: ~2 rows (approximately)
/*!40000 ALTER TABLE `sex` DISABLE KEYS */;
INSERT INTO `sex` (`id`, `title`) VALUES
	('ж', 'женский'),
	('м', 'мужской');
/*!40000 ALTER TABLE `sex` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
