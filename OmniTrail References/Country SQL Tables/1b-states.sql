-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2019 at 04:31 AM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE `states` (
  `country_code` varchar(2) NOT NULL,
  `state_code` varchar(32) NOT NULL,
  `state_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `('IN','states`
--


-- dumping sates of india 

INSERT INTO `states` (`country_code`, `state_code`, `state_name`) VALUES
('IN','GJ', 'GUJARAT'),
('IN','MH', 'MAHARASHTRA'),
('IN','RJ', 'RAJASTHAN'),
('IN','KL', 'KERALA');

('IN','GJ',

INSERT INTO `State` (`country_code`, `state_name`, `state_code`) VALUES
('IN','ANDHRA PRADESH','AP'),
('IN','ARUNACHAL PRADESH','AR'),
('IN','ASSAM','AS'),
('IN','BIHAR','BR'),
('IN','CHHATTISGARH','CG'),
('IN','DELHI','DL'),
('IN','GOA','GA'),
('IN','HARYANA','HR'),
('IN','HIMACHAL PRADESH','HP'),
('IN','JAMMU & KASHMIR','JK'),
('IN','JHARKHAND','JS'),
('IN','KARNATAKA','KA'),
('IN','MADHYA PRADESH','MP'),
('IN','MANIPUR','MN'),
('IN','MEGHALAYA','ML'),
('IN','MIZORAM','MZ'),
('IN','NAGALAND','NL'),
('IN','ORISSA','OR'),
('IN','PUNJAB','PB'),
('IN','SIKKIM','SK'),
('IN','TAMIL NADU','TN'),
('IN','TRIPURA','TR'),
('IN','UTTAR PRADESH','UP'),
('IN','WEST BENGAL','WB')








(1, 'ANDAMAN AND NICOBAR ISLANDS'),
(2, 'ANDHRA PRADESH'),
(3, 'ARUNACHAL PRADESH'),
(4, 'ASSAM'),
(5, 'BIHAR'),
(6, 'CHATTISGARH'),
(7, 'CHANDIGARH'),
(8, 'DAMAN AND DIU'),
(9, 'DELHI'),
(10, 'DADRA AND NAGAR HAVELI'),
(11, 'GOA'),
(13, 'HIMACHAL PRADESH'),
(14, 'HARYANA'),
(15, 'JAMMU AND KASHMIR'),
(16, 'JHARKHAND'),
(18, 'KARNATAKA'),
(19, 'LAKSHADWEEP'),
(20, 'MEGHALAYA'),
(22, 'MANIPUR'),
(23, 'MADHYA PRADESH'),
(24, 'MIZORAM'),
(25, 'NAGALAND'),
(26, 'ORISSA'),
(27, 'PUNJAB'),
(28, 'PONDICHERRY'),
(30, 'SIKKIM'),
(31, 'TAMIL NADU'),
(32, 'TRIPURA'),
(33, 'UTTARAKHAND'),
(34, 'UTTAR PRADESH'),
(35, 'WEST BENGAL'),
(36, 'TELANGANA');

INSERT INTO `states` (`country_code`, `state_code`, `state_name`) VALUES
('US', 'AL', 'Alabama'),
('US', 'AK', 'Alaska'),
('US', 'AS', 'American Samoa'),
('US', 'AZ', 'Arizona'),
('US', 'AR', 'Arkansas'),
('US', 'CA', 'California'),
('US', 'CO', 'Colorado'),
('US', 'CT', 'Connecticut'),
('US', 'DE', 'Delaware'),
('US', 'FM', 'Federated States of Micronesia'),
('US', 'FL', 'Florida'),
('US', 'GA', 'Georgia'),
('US', 'GU', 'Guam'),
('US', 'HI', 'Hawaii'),
('US', 'ID', 'Idaho'),
('US', 'IL', 'Illinois'),
('US', 'IN', 'Indiana'),
('US', 'IA', 'Iowa'),
('US', 'KS', 'Kansas'),
('US', 'KY', 'Kentucky'),
('US', 'LA', 'Louisiana'),
('US', 'ME', 'Maine'),
('US', 'MH', 'Marshall Islands'),
('US', 'MD', 'Maryland'),
('US', 'MA', 'Massachusetts'),
('US', 'MI', 'Michigan'),
('US', 'MN', 'Minnesota'),
('US', 'MS', 'Mississippi'),
('US', 'MO', 'Missouri'),
('US', 'MT', 'Montana'),
('US', 'NE', 'Nebraska'),
('US', 'NV', 'Nevada'),
('US', 'NH', 'New Hampshire'),
('US', 'NJ', 'New Jersey'),
('US', 'NM', 'New Mexico'),
('US', 'NY', 'New York'),
('US', 'NC', 'North Carolina'),
('US', 'ND', 'North Dakota'),
('US', 'MP', 'Northern Mariana Islands'),
('US', 'OH', 'Ohio'),
('US', 'OK', 'Oklahoma'),
('US', 'OR', 'Oregon'),
('US', 'PW', 'Palau'),
('US', 'PA', 'Pennsylvania'),
('US', 'PR', 'Puerto Rico'),
('US', 'RI', 'Rhode Island'),
('US', 'SC', 'South Carolina'),
('US', 'SD', 'South Dakota'),
('US', 'TN', 'Tennessee'),
('US', 'TX', 'Texas'),
('US', 'AE', 'US Armed Forces Europe'),
('US', 'AP', 'US Armed Forces Pacific'),
('US', 'UT', 'Utah'),
('US', 'VT', 'Vermont'),
('US', 'VI', 'Virgin Islands'),
('US', 'VA', 'Virginia'),
('US', 'WA', 'Washington'),
('US', 'DC', 'Washington, D.C.'),
('US', 'WV', 'West Virginia'),
('US', 'WI', 'Wisconsin'),
('US', 'WY', 'Wyoming');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`country_code`,`state_code`),
  ADD KEY `state_name` (`state_name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
