-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 31, 2020 at 07:50 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ampify`
--

-- --------------------------------------------------------

--
-- Table structure for table `album`
--

CREATE TABLE `album` (
  `alb_id` varchar(100) NOT NULL,
  `year` year(4) NOT NULL,
  `image` mediumblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `album`
--

INSERT INTO `album` (`alb_id`, `year`, `image`) VALUES
('Test', 2020, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `album_membership`
--

CREATE TABLE `album_membership` (
  `alb_id` varchar(100) NOT NULL,
  `s_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `album_membership`
--

INSERT INTO `album_membership` (`alb_id`, `s_id`) VALUES
('Test', '0hVXuCcriWRGvwMV1r5Yn9');

-- --------------------------------------------------------

--
-- Table structure for table `artist`
--

CREATE TABLE `artist` (
  `name` varchar(100) NOT NULL,
  `image` mediumblob DEFAULT NULL,
  `a_id` varchar(100) NOT NULL,
  `active_from` year(4) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `artist`
--

INSERT INTO `artist` (`name`, `image`, `a_id`, `active_from`, `email`) VALUES
('Justin Bieber', NULL, 'a1', 2011, 'justin@fan.com'),
('Shawn Mendens', NULL, 'a10', 2011, 'shawn@fan.com'),
('Marshmello', NULL, 'a11', 2014, 'marsh@fan.com'),
('5 sec of summer', NULL, 'a12', 2018, 'summer@fan.com'),
('Alec Benjamin', NULL, 'a13', 2010, 'ABenjamin@fan.com'),
('Lil Nas X', NULL, 'a14', 2015, 'lil@fan.com'),
('Chainsmokers', NULL, 'a15', 2011, 'chainsmokers@fan.com\r\n'),
('Harry Styles', NULL, 'a16', 2011, 'styles@fan.com'),
('Maroon 5', NULL, 'a17', 2012, 'maroon5@fan.com'),
('Ed Sheeran', NULL, 'a2', 2010, 'Ed@fan.com'),
('Post Malone', NULL, 'a3', 2010, 'PMalone@fan.com'),
('The Weeknd', NULL, 'a4', 2010, 'Weeknd@fan.com'),
('Billie Eilish', NULL, 'a5', 2010, 'billie@fan.com'),
('Queen', NULL, 'a6', 2010, 'queen@fan.com'),
('Tones and I', NULL, 'a7', 2011, 'tones@fan.com'),
('Panic! At The Disco', NULL, 'a8', 2011, 'panic@fan.com'),
('Eminem', NULL, 'a9', 2011, 'eminem@fan.com');

-- --------------------------------------------------------

--
-- Table structure for table `artist_membership`
--

CREATE TABLE `artist_membership` (
  `a_id` varchar(100) NOT NULL,
  `s_id` varchar(100) NOT NULL,
  `year` year(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `artist_membership`
--

INSERT INTO `artist_membership` (`a_id`, `s_id`, `year`) VALUES
('a1', '0hVXuCcriWRGvwMV1r5Yn9', 2019),
('a2', '0hVXuCcriWRGvwMV1r5Yn9', 2019),
('a3', '0RiRZpuVRbi7oqRdSMwhQY', 2019),
('a4', '0sf12qNH5qcw8qpgymFOqD', 2019),
('a5', '0u2P5u6lvoDfwTYjAADbn4', 2018),
('a3', '0Yde1VrWbGF2Tus2AQhMuT', 2019),
('a6', '1lCRw5FEZ1gPDNPzy1K4zW', 1977),
('a7', '1rgnBhdG2JDFTbYkYRZAku', 2019),
('a8', '1rqqCSm0Qe4I9rUvWncaom', 2018),
('a9', '1v7L65Lzy0j0vdpRjJewt1', 2004),
('a10', '2bT1PH7Cw3J9p3t7nlXCdh', 2019),
('a11', '2dpaYNEQHiRxtZbfNsse99', 2018),
('a12', '2iUXsYOEPhVqEBwsqP70rE', 2018),
('a13', '2qxmye6gAegTMjLKEBoR3d', 2018),
('a14', '2YpeDb67231RjR0MgVLzsG', 2019),
('a15', '3g0mEQx3NTanacLseoP0Gw', 2019),
('a16', '3jjujdWJ72nww5eGnfs2E7', 2019),
('a2', '6fxVffaTuwjgEk5h9QyRjy', 2013),
('a15', '6RUKPb4LETWmmr3iAEQktW', 2017),
('a17', '7fa9MBXhVfQ8P8Df9OEbD8', 2018),
('a5', '73SpzrcaHk0RQPFP73vqVR', 2020);

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `g_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`g_id`, `name`, `date`) VALUES
('18290b6e-ec1e-4436-a841-b9ad84c5925e', '', '2020-10-30 11:52:21'),
('3d38db7c-ad5c-4567-a636-65773e8a6fdd', 'dsfg', '2020-10-31 02:24:17'),
('3dbbf191-777e-43c7-970a-c750249a93b5', 'yo', '2020-10-31 02:20:00'),
('67229809-c896-44bf-b801-7dbf97a49b83', 'testGroup', '2020-10-14 19:13:32'),
('923213aa-ad0d-4d35-8a62-3f5d29677044', 'hi', '2020-10-30 11:52:31'),
('98225b2d-8dc9-4dab-bda4-d1010c1d0a7e', '', '2020-10-30 11:50:21'),
('a38ceb49-767b-4648-8af7-2ab5e0b75f2e', 'hi', '2020-10-30 11:54:12'),
('b256b692-875d-4f9d-b301-ab6853fe5a9e', 'hi', '2020-10-30 11:50:36'),
('bd650028-5df1-4d4a-8d4c-416703e9ffe0', '', '2020-10-30 11:54:07'),
('c1634fd0-5a3d-421f-b8ac-061514698e61', 'hi', '2020-10-30 11:50:31'),
('ce008065-c1b4-4a69-9975-011bc518b4a8', '', '2020-10-30 11:51:56'),
('e9ab9027-4dd1-4aba-8cac-0ab86817b505', '', '2020-10-30 11:55:01'),
('f187538d-2ef2-4b6a-b4aa-d563ca6b3fd1', '', '2020-10-30 11:51:07'),
('f895a97d-32d7-44cd-8ac7-af7397604a73', 'lkjhg', '2020-10-31 02:26:19'),
('g1', 'Softablitz', '2020-10-30 10:34:08');

-- --------------------------------------------------------

--
-- Table structure for table `group_membership`
--

CREATE TABLE `group_membership` (
  `u_id` varchar(100) NOT NULL,
  `g_id` varchar(100) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` tinyint(4) NOT NULL DEFAULT -1,
  `isAdmin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `group_membership`
--

INSERT INTO `group_membership` (`u_id`, `g_id`, `date`, `status`, `isAdmin`) VALUES
('d665d7b6-4034-4ace-99da-88210e892a73', '3d38db7c-ad5c-4567-a636-65773e8a6fdd', '2020-10-31 02:24:17', 1, 1),
('d665d7b6-4034-4ace-99da-88210e892a73', '3dbbf191-777e-43c7-970a-c750249a93b5', '2020-10-31 02:20:00', 1, 1),
('d665d7b6-4034-4ace-99da-88210e892a73', '67229809-c896-44bf-b801-7dbf97a49b83', '2020-10-30 10:35:54', 1, 0),
('d665d7b6-4034-4ace-99da-88210e892a73', 'a38ceb49-767b-4648-8af7-2ab5e0b75f2e', '2020-10-30 11:54:12', 1, 1),
('d665d7b6-4034-4ace-99da-88210e892a73', 'bd650028-5df1-4d4a-8d4c-416703e9ffe0', '2020-10-30 11:54:07', 1, 1),
('d665d7b6-4034-4ace-99da-88210e892a73', 'e9ab9027-4dd1-4aba-8cac-0ab86817b505', '2020-10-30 11:55:01', 1, 1),
('d665d7b6-4034-4ace-99da-88210e892a73', 'f895a97d-32d7-44cd-8ac7-af7397604a73', '2020-10-31 02:26:19', 1, 1),
('d665d7b6-4034-4ace-99da-88210e892a73', 'g1', '2020-10-30 10:35:34', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `group_playlist_mem`
--

CREATE TABLE `group_playlist_mem` (
  `g_id` varchar(100) NOT NULL,
  `p_id` varchar(100) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `likes_membership`
--

CREATE TABLE `likes_membership` (
  `s_id` varchar(100) NOT NULL,
  `u_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `playlist`
--

CREATE TABLE `playlist` (
  `p_id` varchar(100) CHARACTER SET latin1 NOT NULL,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `date` timestamp NULL DEFAULT current_timestamp(),
  `type` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `playlist`
--

INSERT INTO `playlist` (`p_id`, `name`, `date`, `type`) VALUES
('1c807ac0-6c17-455e-911e-5495721b0da0', 'hi', '2020-10-31 02:23:02', 2),
('1e36a6c7-b3fe-41d3-b24f-e8a31fb2c84d', 'hi', '2020-10-31 02:20:16', 1),
('7a1eaad7-e0ce-4d34-ace9-534804b84f54', 'okiujyhtr', '2020-10-31 02:26:30', 1),
('893aaefd-e12c-4024-8a35-e96a4d3f5ee7', 'nunu', '2020-10-31 02:23:28', 1),
('a363bdf1-8afd-497a-b3e4-3c9eabde50dc', 'testing', '2020-10-31 05:20:31', 1),
('c0b4c6d3-b7a4-4c7b-9dde-1abaf8dfd671', 'Test playlist', '2020-10-31 02:18:31', 1);

-- --------------------------------------------------------

--
-- Table structure for table `song`
--

CREATE TABLE `song` (
  `s_id` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `genre` varchar(100) NOT NULL,
  `image` longblob DEFAULT NULL,
  `year` year(4) NOT NULL,
  `duration` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `song`
--

INSERT INTO `song` (`s_id`, `title`, `genre`, `image`, `year`, `duration`) VALUES
('0hVXuCcriWRGvwMV1r5Yn9', 'I Don\'t Care', 'Pop', NULL, 2019, 0),
('0RiRZpuVRbi7oqRdSMwhQY', 'Sunflower - Spider-Man: Into the Spider-Verse', 'Dfw Rap', NULL, 2019, 2.38),
('0sf12qNH5qcw8qpgymFOqD', 'Blinding Lights', 'Canadian Contemporary R&b', NULL, 2019, 3.19),
('0u2P5u6lvoDfwTYjAADbn4', 'lovely (with Khalid)', 'Electropop', NULL, 2018, 3.2),
('0Yde1VrWbGF2Tus2AQhMuT', 'Circles', 'Dfw Rap\r\n', NULL, 2019, 3.35),
('1lCRw5FEZ1gPDNPzy1K4zW', 'We Are The Champions - Remastered 2011', 'Glam Rock', NULL, 1977, 3.08),
('1rgnBhdG2JDFTbYkYRZAku', 'Dance Monkey', 'Australian Pop', NULL, 2019, 3.27),
('1rqqCSm0Qe4I9rUvWncaom', 'High Hopes', 'Baroque Pop', NULL, 2018, 3.12),
('1v7L65Lzy0j0vdpRjJewt1', 'Lose Yourself - From \"8 Mile\" Soundtrack', 'Detroit Hip Hop', NULL, 2004, 5.25),
('2bT1PH7Cw3J9p3t7nlXCdh', 'If I Can\'t Have You', 'Canadian Pop', NULL, 2019, 3.1),
('2dpaYNEQHiRxtZbfNsse99', 'Happier', 'Brostep', NULL, 2018, 3.38),
('2iUXsYOEPhVqEBwsqP70rE', 'Youngblood', 'Boy Band', NULL, 2018, 3.25),
('2qxmye6gAegTMjLKEBoR3d', 'Let Me Down Slowly', 'Pop', NULL, 2018, 2.49),
('2YpeDb67231RjR0MgVLzsG', 'Old Town Road - Remix', 'Country Rap', NULL, 2019, 2.37),
('3g0mEQx3NTanacLseoP0Gw', 'Takeaway', 'Takeaway', NULL, 2019, 3.25),
('3jjujdWJ72nww5eGnfs2E7', 'Adore You', 'Pop', NULL, 2019, 3.25),
('6fxVffaTuwjgEk5h9QyRjy', 'Photograph', 'Pop', NULL, 2013, 4.25),
('6RUKPb4LETWmmr3iAEQktW', 'Something Just Like This', 'Electropop', NULL, 2017, 4.07),
('73SpzrcaHk0RQPFP73vqVR', 'No Time To Die', 'Electropop', NULL, 2020, 4.02),
('7fa9MBXhVfQ8P8Df9OEbD8', 'Girls Like You (feat. Cardi B)', 'Pop', NULL, 2018, 3.53);

-- --------------------------------------------------------

--
-- Table structure for table `song_mem`
--

CREATE TABLE `song_mem` (
  `p_id` varchar(100) CHARACTER SET latin1 NOT NULL,
  `s_id` varchar(100) CHARACTER SET latin1 NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `song_mem`
--

INSERT INTO `song_mem` (`p_id`, `s_id`, `date`) VALUES
('1c807ac0-6c17-455e-911e-5495721b0da0', '1rgnBhdG2JDFTbYkYRZAku', '2020-10-31 06:00:29'),
('1c807ac0-6c17-455e-911e-5495721b0da0', '6fxVffaTuwjgEk5h9QyRjy', '2020-10-31 06:00:29');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `u_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `image` longblob DEFAULT NULL,
  `DOJ` timestamp NOT NULL DEFAULT current_timestamp(),
  `isAval` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`u_id`, `name`, `email`, `password`, `image`, `DOJ`, `isAval`) VALUES
('13c044e7-6d72-476c-8750-ab5d4c804f78', 'sdfghjASDF@1234.com', 'sdfghjASDF@1234.com', 'sdfghjASDF@1234.com', NULL, '2020-10-26 16:10:10', 0),
('55e2fa11-66e2-4887-abab-a0c32c78fb4d', 'vbA4543G@gmail.com', 'vbA4543G@gmail.com', 'vbA4543G@gmail.com', NULL, '2020-10-26 16:17:06', 1),
('7bc78bc1-59c0-4db9-96ec-4aa5f107e0f7', 'lodu', 'lodu@gmail.com', '12345678', NULL, '2020-10-16 11:41:04', 0),
('858ade9d-2e59-43ca-a2e3-92735dec266a', 'arun', 'arun@gmail.com', 'Arun@123', NULL, '2020-10-30 09:15:49', 0),
('bcb714e6-0b49-46f3-b4a2-9623d05a62f7', 'a', 'a@gmail.com', 'Qwerty@123', NULL, '2020-10-16 21:04:47', 0),
('d665d7b6-4034-4ace-99da-88210e892a73', 'Ishan', 'ishan@mnnit.ac.in', '12345678', NULL, '2020-10-16 11:23:27', 0),
('de1eb506-a20a-433b-a1d9-a7e027a421ea', '', '', '', NULL, '2020-10-16 11:22:53', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_playlist_mem`
--

CREATE TABLE `user_playlist_mem` (
  `p_id` varchar(100) CHARACTER SET latin1 NOT NULL,
  `u_id` varchar(100) CHARACTER SET latin1 NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `role` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_playlist_mem`
--

INSERT INTO `user_playlist_mem` (`p_id`, `u_id`, `date`, `role`) VALUES
('1c807ac0-6c17-455e-911e-5495721b0da0', 'd665d7b6-4034-4ace-99da-88210e892a73', '2020-10-31 02:23:02', 1),
('1e36a6c7-b3fe-41d3-b24f-e8a31fb2c84d', 'd665d7b6-4034-4ace-99da-88210e892a73', '2020-10-31 02:20:16', 1),
('7a1eaad7-e0ce-4d34-ace9-534804b84f54', 'd665d7b6-4034-4ace-99da-88210e892a73', '2020-10-31 02:26:31', 1),
('893aaefd-e12c-4024-8a35-e96a4d3f5ee7', 'd665d7b6-4034-4ace-99da-88210e892a73', '2020-10-31 02:23:28', 2),
('a363bdf1-8afd-497a-b3e4-3c9eabde50dc', 'd665d7b6-4034-4ace-99da-88210e892a73', '2020-10-31 05:20:31', 1),
('c0b4c6d3-b7a4-4c7b-9dde-1abaf8dfd671', 'd665d7b6-4034-4ace-99da-88210e892a73', '2020-10-31 02:18:31', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `album`
--
ALTER TABLE `album`
  ADD PRIMARY KEY (`alb_id`);

--
-- Indexes for table `album_membership`
--
ALTER TABLE `album_membership`
  ADD PRIMARY KEY (`alb_id`,`s_id`),
  ADD KEY `album_membership_song_s_id_fk` (`s_id`);

--
-- Indexes for table `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`a_id`);

--
-- Indexes for table `artist_membership`
--
ALTER TABLE `artist_membership`
  ADD KEY `artist_membership_artist__fk` (`a_id`),
  ADD KEY `artist_membership_song__fk` (`s_id`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`g_id`);

--
-- Indexes for table `group_membership`
--
ALTER TABLE `group_membership`
  ADD PRIMARY KEY (`u_id`,`g_id`),
  ADD KEY `u_id` (`u_id`),
  ADD KEY `g_id` (`g_id`);

--
-- Indexes for table `likes_membership`
--
ALTER TABLE `likes_membership`
  ADD PRIMARY KEY (`s_id`,`u_id`),
  ADD KEY `likes_membership_song__fk` (`s_id`),
  ADD KEY `likes_membership_user__fk` (`u_id`);

--
-- Indexes for table `playlist`
--
ALTER TABLE `playlist`
  ADD PRIMARY KEY (`p_id`);

--
-- Indexes for table `song`
--
ALTER TABLE `song`
  ADD PRIMARY KEY (`s_id`);

--
-- Indexes for table `song_mem`
--
ALTER TABLE `song_mem`
  ADD PRIMARY KEY (`p_id`,`s_id`),
  ADD KEY `song_mem_fk_song` (`s_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`u_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_playlist_mem`
--
ALTER TABLE `user_playlist_mem`
  ADD PRIMARY KEY (`p_id`,`u_id`),
  ADD KEY `user_playlist_mem_fk_user` (`u_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `album_membership`
--
ALTER TABLE `album_membership`
  ADD CONSTRAINT `album_membership_album__fk` FOREIGN KEY (`alb_id`) REFERENCES `album` (`alb_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `album_membership_song_s_id_fk` FOREIGN KEY (`s_id`) REFERENCES `song` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `artist_membership`
--
ALTER TABLE `artist_membership`
  ADD CONSTRAINT `artist_membership_artist__fk` FOREIGN KEY (`a_id`) REFERENCES `artist` (`a_id`),
  ADD CONSTRAINT `artist_membership_song__fk` FOREIGN KEY (`s_id`) REFERENCES `song` (`s_id`);

--
-- Constraints for table `group_membership`
--
ALTER TABLE `group_membership`
  ADD CONSTRAINT `group_membership_ibfk_1` FOREIGN KEY (`g_id`) REFERENCES `groups` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_membership_ibfk_2` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `likes_membership`
--
ALTER TABLE `likes_membership`
  ADD CONSTRAINT `likes_membership_song__fk` FOREIGN KEY (`s_id`) REFERENCES `song` (`s_id`),
  ADD CONSTRAINT `likes_membership_user__fk` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`);

--
-- Constraints for table `song_mem`
--
ALTER TABLE `song_mem`
  ADD CONSTRAINT `song_mem_fk_playlist` FOREIGN KEY (`p_id`) REFERENCES `playlist` (`p_id`),
  ADD CONSTRAINT `song_mem_fk_song` FOREIGN KEY (`s_id`) REFERENCES `song` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_playlist_mem`
--
ALTER TABLE `user_playlist_mem`
  ADD CONSTRAINT `user_playlist_mem_fk_playlist` FOREIGN KEY (`p_id`) REFERENCES `playlist` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_playlist_mem_fk_user` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
