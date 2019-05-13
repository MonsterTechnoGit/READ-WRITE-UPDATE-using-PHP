-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: fdb20.awardspace.net
-- Generation Time: May 13, 2019 at 04:17 PM
-- Server version: 5.7.20-log
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `3044752_entc`
--

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `question` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_yes` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_no` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_one` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_two` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_three` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_four` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `question`, `answer_yes`, `answer_no`, `answer_one`, `answer_two`, `answer_three`, `answer_four`) VALUES
(1, '1. Do you have pain?', 'YES', 'NO', 'null', 'null', 'null', 'null'),
(2, '2. Do you have swelling?', 'YES', 'NO', 'null', 'null', 'null', 'null'),
(3, '3.Where is the pain?', 'null', 'null', 'Knee', 'Figure of hand', 'Joints', 'null'),
(4, '4. What does the pain feel like?', 'null', 'null', 'Dull', 'Achy', 'Sharp', 'Intense'),
(5, '5. How long have you had the pain?', 'null', 'null', 'One week', 'One month', 'Six month', '1 year'),
(6, '6.Is inflammation present in a joint?', 'YES', 'NO', 'null', 'null', 'null', 'null'),
(7, '7.Is loss of joint range motion?', 'YES', 'NO', 'null', 'null', 'null', 'null'),
(8, '8. Are you feeling depress?', 'YES', 'NO', 'null', 'null', 'null', 'null');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
