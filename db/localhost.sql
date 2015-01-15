-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 31, 2010 at 10:21 PM
-- Server version: 5.1.33
-- PHP Version: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sms`
--
CREATE DATABASE `sms` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sms`;

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `kode_barang` char(3) NOT NULL,
  `nama_barang` varchar(35) NOT NULL,
  `status_barang` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`kode_barang`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `status_barang`) VALUES
('P01', 'ponsel Nokia N70', 1),
('A90', 'LCD TV SONY 32 INC', 1),
('P02', 'BB Bold', 1),
('P03', 'asas', 0),
('P04', 'asas', 0),
('P05', 'Honda Supra X 2006', 0),
('P06', 'Honda Supra Vit 2005', 0),
('P07', '1 Cincin 15 Karat 2,2 gr', 0),
('P08', '1 Kalung Rantai 15 Karat 2,3 gr', 0),
('P09', '1 Kalung 15 Karat 1 gr', 0),
('P10', '1 Kalung Rantai 15 Karat 1,6 gr', 0),
('P11', '1 Gelang 14 Karat 2,1 gr', 0),
('P12', '1 Liontin 15 Karat 2,4 gr', 0);

-- --------------------------------------------------------

--
-- Table structure for table `bidder`
--

CREATE TABLE IF NOT EXISTS `bidder` (
  `no_ktp` char(16) NOT NULL,
  `no_ponsel` varchar(15) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `pin` varchar(20) NOT NULL,
  PRIMARY KEY (`no_ktp`),
  UNIQUE KEY `no_ponsel` (`no_ponsel`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bidder`
--

INSERT INTO `bidder` (`no_ktp`, `no_ponsel`, `nama`, `alamat`, `pin`) VALUES
('1271193010880001', '6287869238905', 'Muhammad Arifin Siregar', 'Jl. Darussalam', '81dc9bdb52d04dc20036'),
('123427', '628972826523', 'Denis Afrianto', 'Jl. Kasuari', '81dc9bdb52d04dc20036'),
('1234', '628126002850', 'Pak Andri Budiman', 'Jl.', '81dc9bdb52d04dc20036'),
('1233', '6281375263750', 'Pak Syahriol', 'Jl.', '81dc9bdb52d04dc20036'),
('1231', '628126521623', 'Bu Dian', 'Jl.', '81dc9bdb52d04dc20036'),
('123428', '6285658521305', 'Desfa Maulani', 'Jl.', '81dc9bdb52d04dc20036'),
('909090', '090909', 'asd', 'qwe', '243'),
('123421', '6285261036037', 'Anggi Rivai', 'Jl.', '81dc9bdb52d04dc20036'),
('123422', '6285275913743', 'Ara', 'Binjai', '81dc9bdb52d04dc20036'),
('123426', '6285276611552', 'Bobby Medana', 'Jl.', '81dc9bdb52d04dc20036'),
('123425', '6285276674222', 'Atika Sari', 'Jl.', '81dc9bdb52d04dc20036'),
('123424', '6281370818359', 'Arta', 'Jl.', '81dc9bdb52d04dc20036'),
('1232', '628126091706', 'Bu Maya', 'Jl.', '81dc9bdb52d04dc20036'),
('123423', '6285275901045', 'Arsyad', 'Jl.', '81dc9bdb52d04dc20036'),
('12341', '6285765172394', 'M. Reza Pahlepi', 'Jl. Negara Aksara', '81dc9bdb52d04dc20036'),
('12342', '6285761310149', 'Mhd. Azemi', 'Jl. Kayu Putih', '81dc9bdb52d04dc20036'),
('12343', '6281396107106', 'Azhari', 'Batu Bara', '81dc9bdb52d04dc20036'),
('12344', '626176352054', 'M. Ahyal Husna', 'Marendal', '81dc9bdb52d04dc20036'),
('12345', '6285760900204', 'Alfarisi', 'Binjai', '81dc9bdb52d04dc20036'),
('12346', '628566337523', 'Philipus Telaumbanua', 'Perumnas Simalingkar', '81dc9bdb52d04dc20036'),
('12347', '6281396838366', 'Rifki', 'Serdang', '81dc9bdb52d04dc20036'),
('12348', '628566299002', 'Andika Novaldy', 'Titi Kuning', '81dc9bdb52d04dc20036'),
('12349', '6281375022222', 'Bagoes Harsono', 'Jl.', '81dc9bdb52d04dc20036'),
('123410', '6285760686808', 'Bambang Budiarto', 'Menteng', '81dc9bdb52d04dc20036'),
('123411', '6285762444718', 'Melvani Hardi', 'Jl.', '81dc9bdb52d04dc20036'),
('123412', '6285761437930', 'Dameria', 'Jl.', '81dc9bdb52d04dc20036'),
('123413', '6281265981065', 'Lia Amalia', 'Jl. D.I. Panjaitan', '81dc9bdb52d04dc20036'),
('123414', '6287868595258', 'Habrul Leni', 'Jl. Setia Budi', '81dc9bdb52d04dc20036'),
('123415', '6287869468649', 'Winda', 'Jl.', '81dc9bdb52d04dc20036'),
('123416', '6285270880265', 'Mariani Valentina', 'Jl.', '81dc9bdb52d04dc20036'),
('123417', '6281396521253', 'Adhal Huda', 'Jl.', '81dc9bdb52d04dc20036'),
('123418', '6281375271001', 'Aidil Akbar', 'Tanjung Morawa', '81dc9bdb52d04dc20036'),
('123419', '6285761086757', 'Alvin', 'Jl.', '81dc9bdb52d04dc20036'),
('123420', '6281396090229', 'Ane', 'Jl.', '81dc9bdb52d04dc20036'),
('123429', '6285261710051', 'Eky', 'Jl.', '81dc9bdb52d04dc20036'),
('123729', '6285276441123', 'Fadliansyah Nst', 'Jl.', '81dc9bdb52d04dc20036'),
('123430', '6285261959501', 'Farabi', 'Jl.', '81dc9bdb52d04dc20036'),
('123431', '6285760984725', 'Ferry', 'Jl.', '81dc9bdb52d04dc20036'),
('123432', '6281990577574', 'Hadianto', 'Jl.', '81dc9bdb52d04dc20036'),
('123433', '6281361576107', 'Inggou David', 'Jl.', '81dc9bdb52d04dc20036'),
('123434', '6281396399322', 'Jannah', 'Jl.', '81dc9bdb52d04dc20036'),
('123435', '6285361045726', 'Makmur', 'Jl.', '81dc9bdb52d04dc20036'),
('123436', '6285296499123', 'Suharsono', 'Jl.', '81dc9bdb52d04dc20036'),
('123437', '6281376888144', 'Panji', 'Jl.', '81dc9bdb52d04dc20036'),
('123438', '6285275850480', 'Riki', 'Jl.', '81dc9bdb52d04dc20036'),
('123439', '6281396137343', 'Rudi Tanaka', 'Jl.', '81dc9bdb52d04dc20036'),
('123440', '6281933407474', 'Ruri Handayani', 'Jl.', '81dc9bdb52d04dc20036'),
('123441', '6281933427345', 'Surya Ajen', 'Jl.', '81dc9bdb52d04dc20036'),
('123442', '6281361077317', 'Tuti', 'Jl.', '81dc9bdb52d04dc20036'),
('123443', '6285262662687', 'Teddy', 'Jl.', '81dc9bdb52d04dc20036'),
('123444', '6281264299558', 'Uti', 'Jl.', '81dc9bdb52d04dc20036'),
('645', '456', '45', '456', '202cb962ac59075b964b');

-- --------------------------------------------------------

--
-- Table structure for table `detail_barang`
--

CREATE TABLE IF NOT EXISTS `detail_barang` (
  `kode_barang` char(3) NOT NULL,
  `harga_awal` int(10) NOT NULL,
  `harga_jual` int(10) NOT NULL DEFAULT '0',
  `tanggal_lelang` date NOT NULL,
  PRIMARY KEY (`kode_barang`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detail_barang`
--

INSERT INTO `detail_barang` (`kode_barang`, `harga_awal`, `harga_jual`, `tanggal_lelang`) VALUES
('P01', 1050000, 0, '2010-04-25'),
('A90', 2800000, 0, '2010-04-02'),
('P02', 2500000, 0, '2010-04-01'),
('P03', 9000, 0, '2010-04-02'),
('P04', 9000, 0, '2010-04-02'),
('P05', 5250000, 0, '2010-06-01'),
('P06', 4500000, 0, '2010-06-01'),
('P07', 300000, 0, '2010-06-01'),
('P08', 370000, 0, '2010-06-01'),
('P09', 160000, 0, '2010-06-01'),
('P10', 300000, 0, '2010-06-01'),
('P11', 320000, 0, '2010-06-01'),
('P12', 450000, 0, '2010-06-01');

-- --------------------------------------------------------

--
-- Table structure for table `inbox`
--

CREATE TABLE IF NOT EXISTS `inbox` (
  `id_inbox` int(11) NOT NULL AUTO_INCREMENT,
  `no_pengirim` varchar(20) NOT NULL,
  `pesan` text,
  `status_inbox` int(1) NOT NULL DEFAULT '0',
  `waktu_terima` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_inbox`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=100 ;

--
-- Dumping data for table `inbox`
--

INSERT INTO `inbox` (`id_inbox`, `no_pengirim`, `pesan`, `status_inbox`, `waktu_terima`) VALUES
(99, '6281265888055', 'LELANG AKTIF', 1, '2010-05-31 20:51:21'),
(98, '6281265888055', 'LELANG AKTIF', 1, '2010-05-31 20:49:22');

-- --------------------------------------------------------

--
-- Table structure for table `outbox`
--

CREATE TABLE IF NOT EXISTS `outbox` (
  `id_outbox` int(8) NOT NULL AUTO_INCREMENT,
  `no_tujuan` varchar(15) NOT NULL,
  `pesan` varchar(160) NOT NULL,
  `status_outbox` int(1) NOT NULL DEFAULT '0',
  `waktu_kirim` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_outbox`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=435 ;

--
-- Dumping data for table `outbox`
--

INSERT INTO `outbox` (`id_outbox`, `no_tujuan`, `pesan`, `status_outbox`, `waktu_kirim`) VALUES
(434, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:51:21'),
(433, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:49:22'),
(432, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:48:33'),
(431, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:47:49'),
(430, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:47:00'),
(429, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:46:10'),
(428, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:43:13'),
(427, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:41:10'),
(426, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:38:33'),
(425, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:35:44'),
(424, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:34:22'),
(423, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:32:27'),
(422, '6281265888055', 'P01, ponsel Nokia N70(1050000)\nA90, LCD TV SONY 32 INC(2800000)\nP02, BB Bold(2500000)\n', 2, '2010-05-31 20:29:12');

-- --------------------------------------------------------

--
-- Table structure for table `pemenang`
--

CREATE TABLE IF NOT EXISTS `pemenang` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `no_ponsel` varchar(15) NOT NULL,
  `kode_barang` char(3) NOT NULL,
  `harga_terjual` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode_barang` (`kode_barang`),
  UNIQUE KEY `no_ponsel_2` (`kode_barang`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `pemenang`
--

INSERT INTO `pemenang` (`id`, `no_ponsel`, `kode_barang`, `harga_terjual`) VALUES
(4, '6281265888055', 'P01', 1450000);

-- --------------------------------------------------------

--
-- Table structure for table `penawaran`
--

CREATE TABLE IF NOT EXISTS `penawaran` (
  `id_penawaran` int(5) NOT NULL AUTO_INCREMENT,
  `no_ponsel` varchar(15) NOT NULL,
  `kode_barang` char(3) NOT NULL,
  `harga` int(10) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `status_penawaran` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_penawaran`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `penawaran`
--

INSERT INTO `penawaran` (`id_penawaran`, `no_ponsel`, `kode_barang`, `harga`, `date`, `time`, `status_penawaran`) VALUES
(8, '6287869238905', 'P02', 2550000, '2010-04-21', '16:54:57', 0),
(4, '6287869238905', 'P01', 1250000, '2010-04-20', '16:33:31', 0),
(5, '6287869238905', 'P01', 1260000, '2010-04-20', '17:07:57', 0),
(6, '6287869238905', 'P01', 1280000, '2010-04-20', '17:11:34', 0),
(7, '6287869238905', 'P01', 1285500, '2010-04-20', '17:14:26', 0),
(9, '6287869238905', 'P02', 2600000, '2010-04-21', '17:00:08', 0),
(10, '6287869238905', 'P02', 2700000, '2010-04-21', '17:35:34', 0),
(11, '6281265888055', 'P01', 1450000, '2010-05-30', '16:33:31', 0),
(12, '0812123456', 'P01', 1400000, '2010-05-30', '16:00:01', 0);

-- --------------------------------------------------------

--
-- Table structure for table `pengaturan`
--

CREATE TABLE IF NOT EXISTS `pengaturan` (
  `waktu_selesai` varchar(5) NOT NULL DEFAULT '17:00'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengaturan`
--

INSERT INTO `pengaturan` (`waktu_selesai`) VALUES
('19:43');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `nip` char(18) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(25) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`nip`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`),
  UNIQUE KEY `username_3` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`nip`, `username`, `password`, `nama_lengkap`, `level`) VALUES
('123456789123456789', 'anca', 'bd754cb79946b582127e2ddee', 'Muhammad Arifin Siregar', 2),
('123', 'test2', '81dc9bdb52d04dc20036dbd83', 'test', 1),
('999', 'mascode', '81dc9bdb52d04dc20036dbd83', 'Mhd. Arifin Srg', 1);
