-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql11.freemysqlhosting.net
-- Generation Time: Apr 23, 2024 at 12:29 PM
-- Server version: 5.5.62-0ubuntu0.14.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql11693485`
--

-- --------------------------------------------------------

--
-- Table structure for table `adherent`
--

CREATE TABLE `adherent` (
  `Adh_num` int(4) NOT NULL,
  `nom` varchar(42) DEFAULT NULL,
  `prenom` varchar(42) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `adherent`
--

INSERT INTO `adherent` (`Adh_num`, `nom`, `prenom`, `email`, `adresse`) VALUES
(1, 'Smith', 'John', 'johnsmith@example.com', '123 Main St, City'),
(2, 'Doe', 'Jane', 'janedoe@example.com', '456 Elm St, Town'),
(3, 'Johnson', 'Michael', 'michaeljohnson@example.com', '789 Oak St, Village'),
(4, 'Brown', 'Emily', 'emilybrown@example.com', '101 Pine St, Hamlet'),
(5, 'Taylor', 'David', 'davidtaylor@example.com', '202 Cedar St, Borough'),
(9, 'Steven', 'Steve', 'stevenuni@gmail.com', 'beach city'),
(10, 'test', 'test', 'test@gmaik.com', '12343');

-- --------------------------------------------------------

--
-- Table structure for table `auteur`
--

CREATE TABLE `auteur` (
  `Aut_num` int(4) NOT NULL,
  `nom` varchar(42) DEFAULT NULL,
  `prenom` varchar(42) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `auteur`
--

INSERT INTO `auteur` (`Aut_num`, `nom`, `prenom`, `date_naissance`, `description`) VALUES
(1, 'Dostoevsky', 'Fyodor', '1821-11-11', 'Russian novelist, short story writer, essayist, journalist and philosopher.'),
(2, 'Camus', 'Albert', '1913-11-07', 'French philosopher, author, and journalist.'),
(3, 'Roth', 'Veronica', '1988-08-19', 'American novelist and short story writer.'),
(4, 'Nietzsche', 'Friedrich', '1844-10-15', 'German philosopher, cultural critic, composer, poet, and philologist.'),
(5, 'Zweig', 'Stefan', '1881-11-28', 'Austrian novelist, playwright, journalist and biographer.'),
(6, 'Orwell', 'George', '1903-06-25', 'English novelist, essayist, journalist and critic.'),
(7, 'Tolkien', 'J.R.R.', '1892-01-03', 'English writer, poet, philologist, and university professor.'),
(8, 'Murakami', 'Haruki', '1949-01-12', 'Japanese writer.'),
(9, 'Rowling', 'J.K.', '1965-07-31', 'British author, philanthropist, film producer, television producer, and screenwriter.'),
(10, 'Saint-Exupéry', 'Antoine de', '1900-06-29', 'French writer, poet, aristocrat, journalist, and pioneering aviator.'),
(15, 'test', 'test', '2002-05-05', '1234 ');

-- --------------------------------------------------------

--
-- Table structure for table `emprunt`
--

CREATE TABLE `emprunt` (
  `id_emprunt` int(4) NOT NULL,
  `date_emprunt` date DEFAULT NULL,
  `date_retour` date DEFAULT NULL,
  `statut_emprunt` tinyint(1) DEFAULT NULL,
  `nmb_emprunt` int(4) DEFAULT NULL,
  `Adh_num` int(4) NOT NULL,
  `ISBN` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `emprunt`
--

INSERT INTO `emprunt` (`id_emprunt`, `date_emprunt`, `date_retour`, `statut_emprunt`, `nmb_emprunt`, `Adh_num`, `ISBN`) VALUES
(1, '2024-02-01', '2024-02-15', 0, 1, 1, 1),
(3, '2024-02-15', '2024-03-01', 0, 1, 3, 3),
(8, '2024-04-21', '2024-04-25', 1, NULL, 9, 11);

-- --------------------------------------------------------

--
-- Table structure for table `livre`
--

CREATE TABLE `livre` (
  `ISBN` int(4) NOT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `prix` int(4) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `nmb_livre` int(4) DEFAULT NULL,
  `Aut_num` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `livre`
--

INSERT INTO `livre` (`ISBN`, `titre`, `prix`, `genre`, `nmb_livre`, `Aut_num`) VALUES
(1, 'Le Joueur d\'échecs', 15, 'Fiction', 20, 1),
(2, 'L\'étranger', 12, 'Philosophical fiction', 15, 2),
(3, 'Divergent', 10, 'Young adult fiction', 25, 3),
(4, 'Thus Spoke Zarathustra', 18, 'Philosophy', 12, 4),
(5, 'Beyond Good and Evil', 20, 'Philosophy', 10, 4),
(6, 'The Metamorphosis', 14, 'Fiction', 18, 5),
(7, 'Nineteen Eighty-Four', 16, 'Dystopian', 221, 6),
(8, 'The Hobbit', 18, 'Fantasy', 202, 7),
(9, 'Norwegian Woods', 15, 'Novel', 16, 8),
(11, 'The Little Prince', 13, 'Novella', 30, 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adherent`
--
ALTER TABLE `adherent`
  ADD PRIMARY KEY (`Adh_num`);

--
-- Indexes for table `auteur`
--
ALTER TABLE `auteur`
  ADD PRIMARY KEY (`Aut_num`);

--
-- Indexes for table `emprunt`
--
ALTER TABLE `emprunt`
  ADD PRIMARY KEY (`id_emprunt`),
  ADD KEY `ISBN` (`ISBN`),
  ADD KEY `Adh_num` (`Adh_num`);

--
-- Indexes for table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`ISBN`),
  ADD KEY `Aut_num` (`Aut_num`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adherent`
--
ALTER TABLE `adherent`
  MODIFY `Adh_num` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `auteur`
--
ALTER TABLE `auteur`
  MODIFY `Aut_num` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `emprunt`
--
ALTER TABLE `emprunt`
  MODIFY `id_emprunt` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `livre`
--
ALTER TABLE `livre`
  MODIFY `ISBN` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `emprunt_ibfk_1` FOREIGN KEY (`ISBN`) REFERENCES `livre` (`ISBN`),
  ADD CONSTRAINT `emprunt_ibfk_2` FOREIGN KEY (`Adh_num`) REFERENCES `adherent` (`Adh_num`);

--
-- Constraints for table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `livre_ibfk_1` FOREIGN KEY (`Aut_num`) REFERENCES `auteur` (`Aut_num`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;