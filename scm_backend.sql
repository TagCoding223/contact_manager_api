-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2025 at 06:28 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scm_backend`
--

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_CUSTOM');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `about` text DEFAULT NULL,
  `cloudinary_image_public_id` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `email_token` varchar(255) DEFAULT NULL,
  `email_verified` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `phone_verified` bit(1) NOT NULL,
  `profile_picture` longtext DEFAULT NULL,
  `provider` enum('FACEBOOK','GITHUB','GOOGLE','SELF') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `about`, `cloudinary_image_public_id`, `email`, `email_token`, `email_verified`, `enabled`, `name`, `password`, `phone_number`, `phone_verified`, `profile_picture`, `provider`) VALUES
('25267fc4-8115-4d3a-b264-f594fa5e9f4c', 'It is an admin account and user is vishal.', 'scm/t1jp4mnbw9ba8dir9tmb', 'vishal@gmail.com', NULL, b'1', b'1', 'Vishal Chouhan', '$2a$10$E5RhPc6k0xXWoc8dbY83K.LPGoZT6fybms5fTQV10UtkX1CDoMW/m', '+917861230000', b'0', 'https://res.cloudinary.com/dcvg6aj9u/image/upload/v1745468695/scm/t1jp4mnbw9ba8dir9tmb.png', 'SELF'),
('f090c7f1-1a89-441f-b7d5-23d34e5b5e6d', 'It is a normal account and user is kumkum.', 'scm/mekmpfb21duuhhxogjst', 'kumkum@gmail.com', NULL, b'1', b'1', 'Kumkum Dubey', '$2a$10$AYM6OqfyyajYr15LiB8OCeH2GrztEMuCz0wuTZA7JdlgSJTtJ2hUm', '+917861230000', b'0', 'https://res.cloudinary.com/dcvg6aj9u/image/upload/v1745467180/scm/mekmpfb21duuhhxogjst.png', 'SELF');

-- --------------------------------------------------------

--
-- Table structure for table `user_contacts`
--

CREATE TABLE `user_contacts` (
  `id` varchar(255) NOT NULL,
  `address` varchar(500) DEFAULT NULL,
  `cloudinary_image_public_id` varchar(255) DEFAULT NULL,
  `description` longtext NOT NULL,
  `email` varchar(255) NOT NULL,
  `facebook_link` varchar(255) DEFAULT NULL,
  `favourite` bit(1) NOT NULL,
  `linkedin_link` varchar(255) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `picture_url` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_contacts`
--

INSERT INTO `user_contacts` (`id`, `address`, `cloudinary_image_public_id`, `description`, `email`, `facebook_link`, `favourite`, `linkedin_link`, `name`, `phone_number`, `picture_url`, `user_id`) VALUES
('b3115aa9-32a0-4f45-8044-799c9d0d2e1e', 'dev Street Lover', 'scm/abeiq1zgytqsovixgmnb', 'It\'s dev das account about.', 'dev@gmail.com', 'https://facebook.com', b'0', 'https://linkedin.in', 'Dev Das Lover', '7894561230', 'https://res.cloudinary.com/dcvg6aj9u/image/upload/v1745468116/scm/abeiq1zgytqsovixgmnb.jpg', 'f090c7f1-1a89-441f-b7d5-23d34e5b5e6d');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` varchar(255) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
('25267fc4-8115-4d3a-b264-f594fa5e9f4c', 1),
('f090c7f1-1a89-441f-b7d5-23d34e5b5e6d', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_contacts`
--
ALTER TABLE `user_contacts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK2gitr1xke8nbvykhdbflq278l` (`email`),
  ADD KEY `FKqgbpf3rh5b6i7npvr2rf776rd` (`user_id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_contacts`
--
ALTER TABLE `user_contacts`
  ADD CONSTRAINT `FKqgbpf3rh5b6i7npvr2rf776rd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
