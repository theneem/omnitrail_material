-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 27, 2020 at 09:18 PM
-- Server version: 10.2.32-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scienceclub_relisant`
--

-- --------------------------------------------------------

--
-- Table structure for table `Book`
--

CREATE TABLE `Book` (
  `BookID` int(11) NOT NULL,
  `ReligionID` int(11) NOT NULL,
  `BookName` varchar(1000) NOT NULL,
  `BookDesc` varchar(8000) NOT NULL,
  `Author` varchar(1000) NOT NULL,
  `BookImg` varchar(8000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Book`
--

INSERT INTO `Book` (`BookID`, `ReligionID`, `BookName`, `BookDesc`, `Author`, `BookImg`) VALUES
(3, 1, 'Paryusana Kalpasutra ', 'Facsimile reprint of the illustrated Prakrit manuscript pt No. 761 of 1899-1915 at the BORI, Edited text and English translation by Dr Shreenand Bapat Introduction by Prof Dhavalikar and a brief outline of the pajjosavanakappa by Dr Poddar', ' M K Dhavalikar and Shreenand L Bapat', 'https://images-na.ssl-images-amazon.com/images/I/91ryixvbEuL.jpg'),
(4, 1, 'Mahavir Vani', 'Osho pravachan on the mahavir', 'Osho', 'https://m.media-amazon.com/images/I/417HuY5y++L.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `City`
--

CREATE TABLE `City` (
  `CityID` int(11) NOT NULL,
  `country_code` varchar(2) NOT NULL,
  `state_code` varchar(32) NOT NULL,
  `city_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `City`
--

INSERT INTO `City` (`CityID`, `country_code`, `state_code`, `city_name`) VALUES
(1, 'IN', 'GJ', 'Gandhinagar'),
(2, 'IN', 'GJ', 'Ahmedabad'),
(3, 'IN', 'GJ', 'Bhavnagar'),
(4, 'IN', 'GJ', 'Palitana'),
(5, 'IN', 'GJ', 'Junagadh'),
(6, 'IN', 'GJ', 'Taranga'),
(7, 'IN', 'GJ', 'Shankeshwar'),
(8, 'IN', 'GJ', 'Dholera'),
(9, 'IN', 'GJ', 'Bhuj'),
(10, 'IN', 'GJ', 'Junagadh'),
(11, 'IN', 'GJ', 'Gadhada'),
(12, 'IN', 'GJ', 'Vadtal'),
(13, 'IN', 'GJ', 'Somnath'),
(14, 'IN', 'GJ', 'Modhera');

-- --------------------------------------------------------

--
-- Table structure for table `Country`
--

CREATE TABLE `Country` (
  `country_code` varchar(2) NOT NULL,
  `country_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Country`
--

INSERT INTO `Country` (`country_code`, `country_name`) VALUES
('CA', 'Canada'),
('IN', 'India'),
('GB', 'United Kingdom'),
('US', 'United States');

-- --------------------------------------------------------

--
-- Table structure for table `Events`
--

CREATE TABLE `Events` (
  `EventID` int(11) NOT NULL,
  `EventName` varchar(2000) NOT NULL,
  `EventDetails` varchar(8000) NOT NULL,
  `EventType` varchar(10) NOT NULL,
  `EventParentID` int(11) NOT NULL,
  `EentStartDate` datetime NOT NULL,
  `EventEndDate` datetime NOT NULL,
  `EventImg` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Events`
--

INSERT INTO `Events` (`EventID`, `EventName`, `EventDetails`, `EventType`, `EventParentID`, `EentStartDate`, `EventEndDate`, `EventImg`) VALUES
(1, 'Annual Flag hosting', 'Dadasaheb annual falg ceremoney event descriptino ', 'temple', 1, '2020-05-12 15:24:49', '2020-05-19 15:24:57', 'http://scienceclub.in/relisantapi/img/temple/dadasaheb.png'),
(2, 'Mahavirswami Janma kalyanak', 'Dadasaheb annual falg ceremoney event descriptino ', 'temple', 1, '2020-06-10 15:24:49', '2020-05-19 15:24:57', 'http://scienceclub.in/relisantapi/img/temple/dadasaheb.png'),
(3, 'Mahavirswami Janma kalyanak - 2', 'Dadasaheb annual falg ceremoney event descriptino ', 'temple', 1, '2020-06-10 15:24:49', '2020-05-19 15:24:57', 'http://scienceclub.in/relisantapi/img/temple/dadasaheb.png'),
(4, 'Mahavirswami Janma kalyanak - 3', 'Dadasaheb annual falg ceremoney event descriptino ', 'temple', 1, '2020-06-10 15:24:49', '2020-05-19 15:24:57', 'http://scienceclub.in/relisantapi/img/temple/dadasaheb.png');

-- --------------------------------------------------------

--
-- Table structure for table `Feature`
--

CREATE TABLE `Feature` (
  `FeatureID` int(11) NOT NULL,
  `FeatureName` varchar(2000) NOT NULL,
  `FeatureDetails` varchar(8000) NOT NULL,
  `FeatureType` varchar(10) NOT NULL,
  `FeatureParentID` int(11) NOT NULL,
  `FeatureImg` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Feature`
--

INSERT INTO `Feature` (`FeatureID`, `FeatureName`, `FeatureDetails`, `FeatureType`, `FeatureParentID`, `FeatureImg`) VALUES
(1, 'Bhojanshala', 'Food facility available at this temple. ', 'temple', 1, 'ttp://scienceclub.in/relisantapi/img/temple/dadasaheb.png'),
(2, 'Dharmashala', 'Boarding - Living accommodation available to stay at this temple', 'temple', 1, 'ttp://scienceclub.in/relisantapi/img/temple/dadasaheb.png'),
(3, 'Dharmashala - 2', 'Boarding - Living accommodation available to stay at this temple', 'temple', 1, 'ttp://scienceclub.in/relisantapi/img/temple/dadasaheb.png'),
(4, 'Dharmashala - 3', 'Boarding - Living accommodation available to stay at this temple', 'temple', 1, 'ttp://scienceclub.in/relisantapi/img/temple/dadasaheb.png');

-- --------------------------------------------------------

--
-- Table structure for table `God`
--

CREATE TABLE `God` (
  `GodID` int(11) NOT NULL,
  `ReligionID` int(11) NOT NULL,
  `GodName` varchar(8000) NOT NULL,
  `ParentGoodID` int(11) DEFAULT NULL,
  `GodImg` varchar(8000) NOT NULL,
  `GodDesc` varchar(8000) NOT NULL,
  `GodStory` varchar(8000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `God`
--

INSERT INTO `God` (`GodID`, `ReligionID`, `GodName`, `ParentGoodID`, `GodImg`, `GodDesc`, `GodStory`) VALUES
(1, 1, 'Rushabh Dev', NULL, 'rushabhdev.png', 'First god of a=Jainism also known as Aadinath. ', 'This is the short autobiography and identity of the god Rushabh Dev.'),
(2, 1, 'Mahavir Swami', NULL, 'mahavir.png', 'This is the last god of jainisam.', 'This is short autobigraphy and idenity of the god Mahavir swamit'),
(3, 1, 'Ajitanatha', NULL, '', '', ''),
(4, 1, 'Sambhavanatha', NULL, '', '', ''),
(5, 1, 'Abhinandan Swami', NULL, '', '', ''),
(6, 1, 'Sumatinatha', NULL, '', '', ''),
(7, 1, 'Padma prabhu', NULL, '', '', ''),
(8, 1, 'Suparshva natha', NULL, '', '', ''),
(9, 1, 'Chandra prabhu', NULL, '', '', ''),
(10, 1, 'Suvidhi Nath', NULL, '', '', ''),
(11, 1, 'Shitala natha', NULL, '', '', ''),
(12, 1, 'Shreyansa natha', NULL, '', '', ''),
(13, 1, 'Vasupujya Swami', NULL, '', '', ''),
(14, 2, 'Swami Narayan Bhagwan', NULL, '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `Photo`
--

CREATE TABLE `Photo` (
  `PhotoID` bigint(20) NOT NULL,
  `PhotoDesc` varchar(1000) NOT NULL,
  `SourceType` varchar(100) NOT NULL DEFAULT 'URL' COMMENT 'URL, PATH,BLOB',
  `Path` varchar(5000) NOT NULL,
  `Photo` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Photo`
--

INSERT INTO `Photo` (`PhotoID`, `PhotoDesc`, `SourceType`, `Path`, `Photo`) VALUES
(1, 'Samyagduma', 'URL', 'https://i.ibb.co/LvMQ2Y3/Samyagdruma.jpg', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Rank`
--

CREATE TABLE `Rank` (
  `RankID` bigint(20) NOT NULL,
  `ParentRankID` bigint(20) DEFAULT NULL,
  `ReligionID` bigint(20) NOT NULL,
  `RankName` varchar(1000) NOT NULL,
  `RankDesc` varchar(5000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Rank`
--

INSERT INTO `Rank` (`RankID`, `ParentRankID`, `ReligionID`, `RankName`, `RankDesc`) VALUES
(1, NULL, 1, 'Gachhhadhipati', 'gacchadhipati of each samuday');

-- --------------------------------------------------------

--
-- Table structure for table `Religion`
--

CREATE TABLE `Religion` (
  `ReligionID` bigint(20) NOT NULL,
  `ParentReligionID` bigint(20) DEFAULT NULL,
  `ReligionName` varchar(8000) NOT NULL,
  `ReligionDesc` varchar(5000) NOT NULL,
  `headerimg` text NOT NULL,
  `primarybook` varchar(8000) NOT NULL,
  `noofgod` int(11) NOT NULL,
  `nooftemple` int(11) NOT NULL,
  `noofsaint` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Religion`
--

INSERT INTO `Religion` (`ReligionID`, `ParentReligionID`, `ReligionName`, `ReligionDesc`, `headerimg`, `primarybook`, `noofgod`, `nooftemple`, `noofsaint`) VALUES
(1, NULL, 'Jain', 'Jain Description', 'http://scienceclub.in/relisantapi/img/jainism.png', 'Kalpasutra', 13, 7, 4),
(2, NULL, 'Swaminarayan', 'Swaminarayan Religion', 'http://scienceclub.in/relisantapi/img/swaminarayanism.png', 'Sikshapatri', 1, 8, 2),
(3, NULL, 'Hinduism', 'Hindu Description', 'http://scienceclub.in/relisantapi/img/hinduism.png', 'Geeta', 0, 2, 2),
(4, NULL, 'Buddhism', 'Buddhism Description', 'http://scienceclub.in/relisantapi/img/buddhisam.png', '', 0, 2, 2),
(5, 1, 'Svetambar - Deravasi', 'Jain Sthanakvasi', 'http://scienceclub.in/relisantapi/img/jainism1.png', '', 0, 0, 0),
(14, 1, 'Svetambar - Sthanakvasi', 'Svetambar - Sthanakvasi', '', '', 0, 0, 0),
(15, 1, 'Digambar', 'Digambar', '', '', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Saint`
--

CREATE TABLE `Saint` (
  `SaintID` int(11) NOT NULL,
  `ReligionID` int(11) NOT NULL,
  `SaintName` varchar(8000) NOT NULL,
  `ParentSaintID` int(11) NOT NULL,
  `SectID` int(11) DEFAULT NULL,
  `Samudai` varchar(1000) NOT NULL,
  `SaintDesc` varchar(8000) NOT NULL,
  `SaintStory` varchar(8000) NOT NULL,
  `BirthDate` date DEFAULT NULL,
  `SaintDate` date DEFAULT NULL,
  `DeathDate` date DEFAULT NULL,
  `SaintIMG` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Saint`
--

INSERT INTO `Saint` (`SaintID`, `ReligionID`, `SaintName`, `ParentSaintID`, `SectID`, `Samudai`, `SaintDesc`, `SaintStory`, `BirthDate`, `SaintDate`, `DeathDate`, `SaintIMG`) VALUES
(1, 1, 'Samyagdruma Shreeji Maharaj saheb', 2, 5, 'Saagar Samudai', 'Samyagdruma maharaj was born in Vartej  a small village near Bhavnagar, She has dedicated her life for the rural school kids education as a teacher. Following mahavir swami foot step he waited till the Mothers wish with society and waited till 52 years to start journey with saint hood. ', '', '0000-00-00', NULL, NULL, 'http://scienceclub.in/relisantapi/img/saint/smitdruma.jpg'),
(2, 1, 'Subhodaya Shreeji Maharaj saheb', 0, 14, 'Saagar Samudai', 'Samyagdruma maharaj was born in Vartej  a small village near Bhavnagar, She has dedicated her life for the rural school kids education as a teacher. Following mahavir swami foot step he waited till the Mothers wish with society and waited till 52 years to start journey with saint hood. ', '', '1966-10-05', NULL, '2020-08-01', 'http://scienceclub.in/relisantapi/img/Jain/saint/Subhodaya.jpg'),
(3, 2, 'Pramukh Swami Maharaj', 0, NULL, 'BAPS', 'Pramukh Swami Maharaj  was the guru and Pramukh, or president, of the BAPS Swaminarayan Sanstha, an international Hindu socio-spiritual organization. BAPS regards him as the fifth spiritual successor of Swaminarayan.', 'Pramukh swami As president of BAPS, he had overseen the growth of BAPS from an organization centered in Gujarat, India, to one spread around the world, maintaining many Hindu mandirs and centers outside of India. He built more than 1,100 Hindu temples, including the Swaminarayan Akshardham temples in New Delhi and Gandhinagar, Gujarat.[2] He had also spearheaded the efforts of BAPS Charities, which is the charitable service organization affiliated with BAPS. He was succeeded as the guru and President of the BAPS Swaminarayan Sanstha by Mahant Swami Maharaj.', '1921-12-07', NULL, '2016-08-13', 'http://scienceclub.in/relisantapi/img/swaminarayan/saint/Pramukhswami.jpg'),
(4, 2, 'Mahant Swami Maharaj', 3, NULL, 'BAPS', 'Mahant Swami Maharaj  is the present guru and president of the BAPS Swaminarayan Sanstha, a Hindu denomination within the Swaminarayan Sampradaya.[4][5][6]:157 BAPS regards him as the sixth spiritual successor of Swaminarayan', 'Mahant Swami Maharaj received initiation as a Hindu swami from Yogiji Maharaj in 1961.[10][11] Mahant Swami Maharaj was revealed by Pramukh Swami Maharaj as his future spiritual and administrative successor in 2012,[12] roles he commenced upon Pramukh Swami Maharaj\'s passing in August 2016', '1933-09-13', NULL, NULL, 'http://scienceclub.in/relisantapi/img/swaminarayan/saint/Mahant_Swami_Maharaj.jpg'),
(5, 1, 'Acharya Vijay Ratnasundarsuri', 0, 14, 'Sagar Samudai', 'Ratnasundarsuri (born 5 January 1948) is an Indian Jain monk, activist and Gujarati language writer. He is well known for his lectures on spirituality and social issues', 'Ratnasundarsuri was born at Depla village near Palitana (now in Gujarat), India to Dalichand and Champaben. His birth name was Rajni. He was initiated in asceticism in 1967 under Bhuvanbhanusuri. He was conferred the title of Acharya in 1996. He spent four years in Delhi starting 2006. In 2011, he started a petition to ban meat export from India.[2] In July 2013, he filed a petition to the Rajya Sabha to ban sex education and online pornography.[3]', '1948-01-05', '1967-01-01', NULL, 'http://scienceclub.in/relisantapi/img/Jain/saint/Acharya_Ratnasundarsuri_Maharaj.jpg'),
(6, 1, 'Muni Shri Pramansagar Maharaj', 0, 15, '', 'Digambar monk who took initiative in Dharmbachao Andolan to oppose the High Court\'s decision to ban Sallekhana. He is a disciple of Acharya Vidyasagar. He called for a massive gathering of 1 crore community members to celebrate the Supreme Court\'s order staying the ban on Sallekhana by chanting 1 crore hymns.', 'Pramansagar was born on 27 June 1967 in Hazaribagh, Jharkhand as Naveen Kumar Jain.[1] His parents Surendra Kumar Jain and Sohni Devi Jain live near Bengali Durga Mandap.[2] He got Vairagya on 4 March 1984 and was initiated as a Digambara monk by Acharya Vidyasagar on 31 March 1988 in Sonagiri.[3] ', '1967-06-27', NULL, NULL, 'http://scienceclub.in/relisantapi/img/Jain/saint/Muni_Shri_Pramansagar_Ji.jpg'),
(7, 3, 'Jaggi Vasudev', 0, NULL, 'ISHA Foundation', 'Jaggi Vasudev[2] (born 3 September 1957), generally referred to as Sadhguru,[2][a] is an Indian yogi[6] and author.[7][8]\r\n\r\nIn 1992, Vasudev established Isha Foundation, which has been involved in various activities in the field of spirituality, education, and environment. ', 'Born in Mysore, Karnataka, India, in a Telugu speaking family,[9] Jaggi Vasudev was the youngest of four children – two boys and two girls. His mother was a housewife and his father an ophthalmologist with Indian Railways.[10] Due to the nature of his father\'s job, the family moved frequently.[11]:39\r\n\r\nAfter his schooling at Demonstration School, Mysore and Mahajana Pre-University College, he graduated from the University of Mysore with a bachelor\'s degree in English in 1973.[12] Vasudev refused to pursue a post-graduate course, despite parental insistence and instead took to business.[13]', '1957-09-03', NULL, NULL, 'http://scienceclub.in/relisantapi/img/hindu/saint/Sadhguru.jpg'),
(8, 3, 'Morari Bapu', 0, NULL, '', 'Morari Bapu (born Moraridas Prabhudas Hariyani) is Hindu spiritual leader and preacher from Gujarat state of India who is popularly known for his discourses on Ramcharitmanas across various cities in India and abroad. He is also known for philanthropy and social reforms through his discourses.', 'Morari Bapu was born on 25 September 1946 (Shivaratri according to Hindu calendar) in Talgajarda village near Mahuva, Gujarat, to Prabhudas Bapu Hariyani and Savitri Ben Hariyani, in a family of six brothers and two sisters. His family followed Nimbarka Sampradaya, a Hindu Vaishnava tradition. He considers his grandfather Tribhovandas Hariyani as his guru, spiritual teacher, and learnt Ramcharitmanas from him at a place now known as Chitrakutdhaam. He memorised the chaupais (couplets) while travelling from Talgajarda to primary and secondary schools in Mahuva.', '1946-09-25', NULL, NULL, 'http://scienceclub.in/relisantapi/img/hindu/saint/morai%20bapu.jpg'),
(9, 4, 'Dalai Lama', 0, NULL, '', 'Dalai Lama Standard Tibetan:  is a title given by the Tibetan people for the foremost spiritual leader of the Gelug or \"Yellow Hat\" school of Tibetan Buddhism, the newest of the classical schools of Tibetan Buddhism. The 14th and current Dalai Lama is Tenzin Gyatso, who lives as a refugee in India.', 'he Dalai Lama is also considered to be the successor in a line of tulkus who are believed to be incarnations of Avalokite?vara, a Bodhisattva of Compassion. The name is a combination of the Mongolic word dalai meaning \"ocean\" or \"big\" (coming from Mongolian title Dalaiyin qan or Dalaiin khan, translated as Gyatso in Tibetan) and the Tibetan word  meaning \"master, guru\".', NULL, NULL, NULL, 'http://scienceclub.in/relisantapi/img/buddha/saint/Dalailama.jpg'),
(10, 4, 'Matthieu Ricard', 0, NULL, '', 'Matthieu Ricard (Nepali:  born 15 February 1946) is a French writer, photographer, translator and Buddhist monk who resides at Shechen Tennyi Dargyeling Monastery in Nepal.', 'Matthieu Ricard grew up among the personalities and ideas of French intellectual circles. He received a PhD degree in molecular genetics from the Pasteur Institute in 1972. He then decided to forsake his scientific career and instead practice Tibetan Buddhism, living mainly in the Himalayas.\r\n\r\nRicard is a board member of the Mind and Life Institute. He received the French National Order of Merit for his humanitarian work in the East with Karuna-Shechen, the non-profit organization he co-founded in 2000 with Rabjam Rinpoche. Since 1989, he has acted as the French interpreter for the 14th Dalai Lama.', '1946-02-15', NULL, NULL, 'http://scienceclub.in/relisantapi/img/buddha/saint/Matthieu_Ricard.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `State`
--

CREATE TABLE `State` (
  `country_code` varchar(2) NOT NULL,
  `state_code` varchar(32) NOT NULL,
  `state_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `State`
--

INSERT INTO `State` (`country_code`, `state_code`, `state_name`) VALUES
('IN', 'GJ', 'GUJARAT'),
('IN', 'KL', 'KERALA'),
('IN', 'MH', 'MAHARASHTRA'),
('IN', 'RJ', 'RAJASTHAN');

-- --------------------------------------------------------

--
-- Table structure for table `Temple`
--

CREATE TABLE `Temple` (
  `TempleID` int(11) NOT NULL,
  `ReligionID` int(11) NOT NULL,
  `TempleName` varchar(8000) NOT NULL,
  `TempleStory` varchar(8000) NOT NULL,
  `TempleIMG` varchar(8000) NOT NULL,
  `address` varchar(2000) NOT NULL,
  `CityID` int(100) NOT NULL,
  `country_code` char(2) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `lang` decimal(11,8) NOT NULL,
  `lat` decimal(11,8) NOT NULL,
  `wiki_link` varchar(1000) NOT NULL,
  `PrimaryDeity` varchar(2000) NOT NULL,
  `GoverningBody` varchar(2000) NOT NULL,
  `ContactPerson` varchar(2000) NOT NULL,
  `ContactNumber` varchar(2000) NOT NULL,
  `Creator` varchar(2000) NOT NULL,
  `CompletionPerios` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Temple`
--

INSERT INTO `Temple` (`TempleID`, `ReligionID`, `TempleName`, `TempleStory`, `TempleIMG`, `address`, `CityID`, `country_code`, `zip`, `lang`, `lat`, `wiki_link`, `PrimaryDeity`, `GoverningBody`, `ContactPerson`, `ContactNumber`, `Creator`, `CompletionPerios`) VALUES
(1, 1, 'DadaSaheb Bhavnagar', 'Dada saheb jain temple mahavir swami', 'http://scienceclub.in/relisantapi/img/temple/dadasaheb.png', 'panvadi, nr civil hospital', 3, 'IN', '364001', 21.76963380, 72.14166090, 'https://en.wikipedia.org/wiki/Palitana_temples', 'Rushabh Dev Bhagavan', 'Aanandji Kalyanji', 'Mr gautam Shah', '+91 7657587645', 'Vastupal - Tejpal', '65 AD'),
(2, 1, 'Mota Derasar Bhavnagar', 'Mota Deraaser temple parshwanath ', 'http://scienceclub.in/relisantapi/img/temple/motuderasar.jpg', '', 3, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(3, 2, 'Swaminarayan Akshardham (New Delhi)', 'Swaminarayan Akshardham (New Delhi) is a Hindu temple, and a spiritual-cultural campus in New Delhi, India.[1][2] Also referred to as Akshardham Temple or Delhi Akshardham, the complex displays millennia of traditional Hindu and Indian culture, spirituality, and architecture. Inspired by Yogiji Maharaj and created by Pramukh Swami Maharaj, it was constructed by BAPS.', 'http://scienceclub.in/relisantapi/img/swaminarayan/temple/Akshardham_delhi.jpg', '', 1, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(4, 1, 'Shri Shatrunjaya Tirtha, Palitana', 'The Palitana temples of Jainism are located on Shatrunjaya hill by the city of Palitana in Bhavnagar district, Gujarat, India.', 'http://scienceclub.in/relisantapi/img/Jain/temple/Shetrunjay_Palitana_Jain_Temples.jpg', '', 4, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(5, 1, 'Hutheesing Jain Temple', 'Hutheesing Temple  is the best known Jain temple in Ahmedabad in Gujarat, India. It was constructed in 1848', 'http://scienceclub.in/relisantapi/img/Jain/temple/Hutheesinh_Temple.jpg', 'At Bardolpura, Madhupura', 2, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(6, 1, 'Girnar, Jain temples ', 'he group temples of Jainism are situated on the Mount Girnar situated near Junagadh in Junagadh district, Gujarat, India. There temples are sacred to the Digambara and the Svetambara branches of Jainism.', 'http://scienceclub.in/relisantapi/img/Jain/temple/Jain_temples_on_Girnar.jpg', '', 5, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(9, 1, 'Taranga Jain Tirtha', 'Taranga is a Jain pilgrimage center near Kheralu in Mehsana district, Gujarat, India, with two compounds of Jain temples that are important examples of the M?ru-Gurjara style of architecture.', 'http://scienceclub.in/relisantapi/img/Jain/temple/Taranga_Temple.jpg', '', 6, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(10, 1, 'Shankeshwar Jain Temple', 'The Shankheshwar Jain Temple is located in the center of Shankheshwar town of Patan district, Gujarat, India. The temple is dedicated to Parshwanath and is an important place of pilgrimage for the followers of Jainism', 'http://scienceclub.in/relisantapi/img/Jain/temple/Sankeshwarji.jpg', '', 7, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(11, 2, 'Swaminarayan Akshardham (Gandhinagar)', 'Swaminarayan Akshardham in Gandhinagar, Gujarat is a large Hindu temple complex inspired by Yogiji Maharaj (1892-1971) the fourth spiritual successor of Swaminarayan, and created by Pramukh Swami Maharaj (1921-2016), the fifth spiritual successor of Swaminarayan according to the BAPS denomination of Swaminarayan Hinduism. Located in the capital of Gujarat, the complex was built over 13 years and is a tribute to Swaminarayan and his life and teachings.[1] At the center of the 23-acre complex is the Akshardham mandir, which is built from 6,000 metric tons of pink sandstone from Rajasthan.[2] The complex\'s name refers to the divine abode of Swaminarayan in the BAPS philosophy; followers of Swaminarayan believe that the jiva or soul goes to Akshardham after attaining moksha, or liberation. BAPS followers worship Swaminarayan as God almighty.', 'http://scienceclub.in/relisantapi/img/swaminarayan/temple/Akshardham_Gandhinagar.jpg', '', 1, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(12, 2, 'Shri Swaminarayan Mandir, Ahmedabad', 'Shri Swaminarayan Mandir is the first temple Swaminarayan constructed. It was built in Ahmedabad in 1822, and presents images of Nara Narayana, who occupies the principal seat of the temple, and forms of Arjuna and Krishna at the central altar. The left altar has murtis of Radha Krishna. The land for construction of the temple was donated by the East India Company government of the day.', 'http://scienceclub.in/relisantapi/img/swaminarayan/temple/Shree_Swaminarayan_Sampraday,_Ahmedabad.jpg', '', 2, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(13, 2, 'Shri Swaminarayan Mandir, Vadtal', 'The temple in Vadtal, also known as Vadtal Swaminarayan, is in the shape of a lotus, with nine domes in the inner temple. The land for this shrine was donated by Joban Pagi, a dacoit who was later converted into a devotee by Swaminarayan.', 'http://scienceclub.in/relisantapi/img/swaminarayan/temple/Vadtaltemple.jpg', '', 12, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(14, 3, 'Somnath temple', 'The Somnath temple located in Prabhas Patan near Junagadh in Saurashtra on the western coast of Gujarat, India is believed to be the first among the twelve jyotirlinga shrines of Shiva. It is an important pilgrimage and tourist spot of Gujarat.', 'http://scienceclub.in/relisantapi/img/hindu/temple/Somnath-temple.jpg', '', 13, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(15, 3, 'Sun Temple, Modhera', 'The Sun Temple is a Hindu temple dedicated to the solar deity Surya located at Modhera village of Mehsana district, Gujarat, India. It is situated on the bank of the river Pushpavati.', 'http://scienceclub.in/relisantapi/img/hindu/temple/Sun_Temple,_Modhera.jpg', '', 14, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(16, 4, 'Mahabodhi Temple', 'The Mahabodhi Temple (literally: \"Great Awakening Temple\") or the Mahabodhi Mahavihar, a UNESCO World Heritage Site, is an ancient, but much rebuilt and restored, Buddhist temple in Bodh Gaya, marking the location where the Buddha is said to have attained enlightenment.[1] Bodh Gaya (in Gaya district) is about 96 km (60 mi) from Patna, Bihar state, India.', 'http://scienceclub.in/relisantapi/img/buddha/temple/Mahabodhitemple.jpg', '', 1, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(17, 4, 'Borobudur', 'Borobudur, or Barabudur (Indonesian: Candi Borobudur, Javanese: ????????????, romanized: Candhi Barabudhur) is a 9th-century Mahayana Buddhist temple in Magelang Regency, not far from the town of Muntilan, in Central Java, Indonesia. It is the world\'s largest Buddhist temple.', 'http://scienceclub.in/relisantapi/img/buddha/temple/Mahabodhitemple.jpg', '', 1, 'IN', '', 0.00000000, 0.00000000, '', '', '', '', '', '', ''),
(20, 2, 'Swaminarayan Mandir, Gadhada', 'Shri Swaminarayan Mandir, Gadhada  also known as shri Gopinathji Dev mandir is a Hindu temple in Gadhada. This temple was built by Swaminarayan himself.', 'http://scienceclub.in/relisantapi/img/Jain/temple/jetalpur.jpg', '', 11, '', '', 0.00000000, 0.00000000, 'https://en.wikipedia.org/wiki/Swaminarayan_Mandir,_Gadhada', '', '', '', '', '', ''),
(21, 2, 'Shri Swaminarayan Mandir, Junagadh', 'Shri Swaminarayan Mandir, Junagadh  is a Hindu temple in Junagadh, Gujarat, India. This temple was ordered to be built by Lord Swaminarayan himself, the founder of the Swaminarayan Sampraday.', 'http://scienceclub.in/relisantapi/img/Jain/temple/Chhatri_of_Lord_Swaminarayan\'s_Charanavind.jpg', '', 10, '', '', 0.00000000, 0.00000000, 'https://en.wikipedia.org/wiki/Shri_Swaminarayan_Mandir,_Junagadh', '', '', '', '', '', ''),
(22, 2, 'Swaminarayan Mandir, Bhuj', 'Shri Swaminarayan Mandir, Bhuj  is a Hindu temple in Bhuj. This temple (mandir) that was constructed by Swaminarayan, founder of the Swaminarayan Sampraday.', 'http://scienceclub.in/relisantapi/img/Jain/temple/Old_Bhuj_Temple.jpg', '', 9, '', '', 0.00000000, 0.00000000, 'https://en.wikipedia.org/wiki/Old_Bhuj_Temple.jpg', '', '', '', '', '', ''),
(23, 2, 'Shri Swaminarayan Mandir, Dholera', 'Shri Swaminarayan Mandir, Dholera is a Hindu temple in Dholera, Gujarat, India, and is one of six Shri Swaminarayan Temples built by Swaminarayan.', 'https://en.wikipedia.org/wiki/Dholera_Swaminarayan_temple.jpg', '', 8, '', '', 0.00000000, 0.00000000, 'https://en.wikipedia.org/wiki/Shri_Swaminarayan_Mandir,_Dholera', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `wikilinks`
--

CREATE TABLE `wikilinks` (
  `wikilinkid` int(11) NOT NULL,
  `type` varchar(100) NOT NULL,
  `ReligionID` int(11) NOT NULL,
  `wikilink` varchar(500) NOT NULL,
  `processed` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wikilinks`
--

INSERT INTO `wikilinks` (`wikilinkid`, `type`, `ReligionID`, `wikilink`, `processed`) VALUES
(1, 'temple', 1, 'https://en.wikipedia.org/wiki/Undavalli_Caves', 0),
(2, 'temple', 1, 'https://en.wikipedia.org/wiki/Ramateertham#Bodhikonda', 0),
(4, 'temple', 1, 'https://en.wikipedia.org/wiki/Siddalakona', 0),
(5, 'temple', 1, 'https://en.wikipedia.org/wiki/Gummileru#Shree_Shankheshwar_Parshwanath_Jain_temple', 0),
(6, 'temple', 1, 'https://en.wikipedia.org/wiki/Mangalagiri#Tourism', 0),
(7, 'temple', 1, 'https://en.wikipedia.org/wiki/Jainism_in_Assam', 0),
(8, 'temple', 1, 'https://en.wikipedia.org/wiki/Sri_Surya_Pahar', 0),
(9, 'temple', 1, 'https://en.wikipedia.org/wiki/Son_Bhandar_Caves', 0),
(10, 'temple', 1, 'https://en.wikipedia.org/wiki/Rajgir', 0),
(11, 'temple', 1, 'https://en.wikipedia.org/wiki/Jal_Mandir', 0),
(12, 'temple', 1, 'https://en.wikipedia.org/wiki/Champapuri', 0),
(13, 'temple', 1, 'https://en.wikipedia.org/wiki/Arang_Jain_temples', 0),
(14, 'temple', 1, 'https://en.wikipedia.org/wiki/Sri_Digambar_Jain_Lal_Mandir', 0),
(15, 'temple', 1, 'https://en.wikipedia.org/wiki/Naya_Mandir', 0),
(16, 'temple', 1, 'https://en.wikipedia.org/wiki/Ahinsa_Sthal', 0),
(17, 'temple', 1, 'https://en.wikipedia.org/wiki/Shri_Atma_Vallabh_Jain_Smarak', 0),
(18, 'temple', 1, 'https://en.wikipedia.org/wiki/D%C4%81d%C4%81bad%C4%AB#Mehrauli', 0),
(19, 'temple', 1, 'https://en.wikipedia.org/wiki/Bava_Pyara_Caves', 0),
(20, 'temple', 1, 'https://en.wikipedia.org/wiki/Dhank_Caves', 0),
(21, 'temple', 1, 'https://en.wikipedia.org/wiki/Talaja_Caves', 0),
(22, 'temple', 1, 'https://en.wikipedia.org/wiki/Palitana_temples', 0),
(23, 'temple', 1, 'https://en.wikipedia.org/wiki/Hutheesing_Jain_Temple', 0),
(24, 'temple', 1, 'https://en.wikipedia.org/wiki/Girnar_Jain_temples', 0),
(25, 'temple', 1, 'https://en.wikipedia.org/wiki/Bhadreshwar_Jain_Temple', 0),
(26, 'temple', 1, 'https://en.wikipedia.org/wiki/Taranga_Jain_temple', 0),
(27, 'temple', 1, 'https://en.wikipedia.org/wiki/Shankheshwar_Jain_Temple', 0),
(28, 'temple', 1, 'https://en.wikipedia.org/wiki/Vataman', 0),
(29, 'temple', 1, 'https://en.wikipedia.org/wiki/Mahudi_Jain_Temple', 0),
(30, 'temple', 1, 'https://en.wikipedia.org/wiki/Songadh_(Saurashtra)', 0),
(31, 'temple', 1, 'https://en.wikipedia.org/wiki/Shantinath_Jain_temple,_Kothara', 0),
(32, 'temple', 1, 'https://en.wikipedia.org/wiki/Adalaj', 0),
(33, 'temple', 1, 'https://en.wikipedia.org/wiki/Pavagadh_Hill#Jain_Temple', 0),
(34, 'temple', 1, 'https://en.wikipedia.org/wiki/Naliya#Naliya_Jain_Derasar', 0),
(35, 'temple', 1, 'https://en.wikipedia.org/wiki/Kumbhariya,_Banaskantha_district#Kumbhariyaji_Jain_temple', 0),
(36, 'temple', 1, 'https://en.wikipedia.org/wiki/Mehsana#Simandhar_Swami_Jain_Derasar', 0),
(37, 'temple', 1, 'https://en.wikipedia.org/wiki/Palanpur#Temples', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Book`
--
ALTER TABLE `Book`
  ADD PRIMARY KEY (`BookID`);

--
-- Indexes for table `City`
--
ALTER TABLE `City`
  ADD PRIMARY KEY (`CityID`);

--
-- Indexes for table `Country`
--
ALTER TABLE `Country`
  ADD PRIMARY KEY (`country_code`),
  ADD KEY `country_name` (`country_name`);

--
-- Indexes for table `Events`
--
ALTER TABLE `Events`
  ADD PRIMARY KEY (`EventID`);

--
-- Indexes for table `Feature`
--
ALTER TABLE `Feature`
  ADD PRIMARY KEY (`FeatureID`);

--
-- Indexes for table `God`
--
ALTER TABLE `God`
  ADD PRIMARY KEY (`GodID`);

--
-- Indexes for table `Photo`
--
ALTER TABLE `Photo`
  ADD PRIMARY KEY (`PhotoID`);

--
-- Indexes for table `Rank`
--
ALTER TABLE `Rank`
  ADD PRIMARY KEY (`RankID`),
  ADD KEY `FK_rank_ParentRankID` (`ParentRankID`),
  ADD KEY `FK_Rank_ReligionID` (`ReligionID`);

--
-- Indexes for table `Religion`
--
ALTER TABLE `Religion`
  ADD PRIMARY KEY (`ReligionID`),
  ADD KEY `FK_Religion_ParentReligion` (`ParentReligionID`);

--
-- Indexes for table `Saint`
--
ALTER TABLE `Saint`
  ADD PRIMARY KEY (`SaintID`);

--
-- Indexes for table `State`
--
ALTER TABLE `State`
  ADD PRIMARY KEY (`country_code`,`state_code`),
  ADD KEY `state_name` (`state_name`);

--
-- Indexes for table `Temple`
--
ALTER TABLE `Temple`
  ADD PRIMARY KEY (`TempleID`),
  ADD KEY `Fk_Temple_City` (`CityID`);

--
-- Indexes for table `wikilinks`
--
ALTER TABLE `wikilinks`
  ADD PRIMARY KEY (`wikilinkid`),
  ADD UNIQUE KEY `uniqueu_linkname` (`wikilink`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Book`
--
ALTER TABLE `Book`
  MODIFY `BookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `City`
--
ALTER TABLE `City`
  MODIFY `CityID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Events`
--
ALTER TABLE `Events`
  MODIFY `EventID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `Feature`
--
ALTER TABLE `Feature`
  MODIFY `FeatureID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `God`
--
ALTER TABLE `God`
  MODIFY `GodID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Photo`
--
ALTER TABLE `Photo`
  MODIFY `PhotoID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Rank`
--
ALTER TABLE `Rank`
  MODIFY `RankID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Religion`
--
ALTER TABLE `Religion`
  MODIFY `ReligionID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `Saint`
--
ALTER TABLE `Saint`
  MODIFY `SaintID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Temple`
--
ALTER TABLE `Temple`
  MODIFY `TempleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `wikilinks`
--
ALTER TABLE `wikilinks`
  MODIFY `wikilinkid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Rank`
--
ALTER TABLE `Rank`
  ADD CONSTRAINT `FK_Rank_ReligionID` FOREIGN KEY (`ReligionID`) REFERENCES `Religion` (`ReligionID`),
  ADD CONSTRAINT `FK_rank_ParentRankID` FOREIGN KEY (`ParentRankID`) REFERENCES `Rank` (`RankID`);

--
-- Constraints for table `Religion`
--
ALTER TABLE `Religion`
  ADD CONSTRAINT `FK_Religion_ParentReligion` FOREIGN KEY (`ParentReligionID`) REFERENCES `Religion` (`ReligionID`);

--
-- Constraints for table `Temple`
--
ALTER TABLE `Temple`
  ADD CONSTRAINT `Fk_Temple_City` FOREIGN KEY (`CityID`) REFERENCES `City` (`CityID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
