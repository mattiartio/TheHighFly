-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 20, 2019 alle 14:23
-- Versione del server: 10.1.38-MariaDB
-- Versione PHP: 7.1.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `highfly`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `booking`
--

CREATE TABLE `booking` (
  `booking_id` int(10) NOT NULL,
  `user_id` int(30) NOT NULL,
  `transport_id` int(30) NOT NULL,
  `booking_date_from` date NOT NULL,
  `booking_date_to` date NOT NULL,
  `booking_price_tot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `role`
--

CREATE TABLE `role` (
  `role_id` int(10) NOT NULL,
  `role_type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `role`
--

INSERT INTO `role` (`role_id`, `role_type`) VALUES
(1, 'admin'),
(2, 'basic_user');

-- --------------------------------------------------------

--
-- Struttura della tabella `transport`
--

CREATE TABLE `transport` (
  `transport_id` int(20) NOT NULL,
  `transport_type` int(20) NOT NULL,
  `max_seats` int(5) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `company` varchar(30) DEFAULT NULL,
  `user_role` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `name`, `surname`, `email`, `company`, `user_role`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', 'admin.admin@admin.admin', 'admin', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `vehicle`
--

CREATE TABLE `vehicle` (
  `vehicle_id` int(10) NOT NULL,
  `vehicle_type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `vehicle`
--

INSERT INTO `vehicle` (`vehicle_id`, `vehicle_type`) VALUES
(1, 'airplane'),
(3, 'car'),
(2, 'train');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `transport_id` (`transport_id`);

--
-- Indici per le tabelle `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `type` (`role_type`);

--
-- Indici per le tabelle `transport`
--
ALTER TABLE `transport`
  ADD PRIMARY KEY (`transport_id`),
  ADD KEY `transport_type` (`transport_type`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `user_role` (`user_role`);

--
-- Indici per le tabelle `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`vehicle_id`),
  ADD UNIQUE KEY `vehicle_type` (`vehicle_type`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `booking`
--
ALTER TABLE `booking`
  MODIFY `booking_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `transport`
--
ALTER TABLE `transport`
  MODIFY `transport_id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `vehicle_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`transport_id`) REFERENCES `transport` (`transport_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `transport`
--
ALTER TABLE `transport`
  ADD CONSTRAINT `transport_ibfk_1` FOREIGN KEY (`transport_type`) REFERENCES `vehicle` (`vehicle_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_role`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
