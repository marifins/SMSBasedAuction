-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 30, 2010 at 08:50 PM
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
  `harga_awal` int(10) NOT NULL,
  `harga_jual` int(10) NOT NULL DEFAULT '0',
  `waktu_mulai` datetime NOT NULL,
  `waktu_selesai` datetime NOT NULL,
  `status_barang` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`kode_barang`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `harga_awal`, `harga_jual`, `waktu_mulai`, `waktu_selesai`, `status_barang`) VALUES
('P01', 'ponsel Nokia N70', 1050000, 0, '2010-04-25 09:00:00', '2010-04-25 17:01:00', 1),
('A90', 'LCD TV SONY 32 INC', 2800000, 0, '2010-04-02 09:00:00', '2010-04-01 17:00:00', 0),
('P02', 'BB Bold', 2500000, 0, '2010-04-01 09:00:00', '2010-04-01 17:00:00', 1),
('P03', 'asas', 9000, 0, '2010-04-02 10:00:00', '2010-04-02 18:00:00', 0),
('P04', 'asas', 9000, 0, '2010-04-02 10:00:00', '2010-04-02 18:00:00', 0);

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
('123427', '628972826523', 'Denis Afrianto', 'Jl. Kasuari', '5346'),
('123', '234234', 'werwer', 'jl.werwerwerwer', '9307'),
('1234', '628126002850', 'Pak Andri Budiman', 'Jl.', '4142'),
('1233', '6281375263750', 'Pak Syahriol', 'Jl.', '2701'),
('1231', '628126521623', 'Bu Dian', 'Jl.', '2059'),
('123428', '6285658521305', 'Desfa Maulani', 'Jl.', '8446'),
('909090', '090909', 'asd', 'qwe', '243'),
('123421', '6285261036037', 'Anggi Rivai', 'Jl.', '4524'),
('123422', '6285275913743', 'Ara', 'Binjai', '7469'),
('123426', '6285276611552', 'Bobby Medana', 'Jl.', '2120'),
('123425', '6285276674222', 'Atika Sari', 'Jl.', '230'),
('123424', '6281370818359', 'Arta', 'Jl.', '7591'),
('1232', '628126091706', 'Bu Maya', 'Jl.', '115'),
('123423', '6285275901045', 'Arsyad', 'Jl.', '3164'),
('12341', '6285765172394', 'M. Reza Pahlepi', 'Jl. Negara Aksara', '8164'),
('12342', '6285761310149', 'Mhd. Azemi', 'Jl. Kayu Putih', '2076'),
('12343', '6281396107106', 'Azhari', 'Batu Bara', '1090'),
('12344', '626176352054', 'M. Ahyal Husna', 'Marendal', '3652'),
('12345', '6285760900204', 'Alfarisi', 'Binjai', '5355'),
('12346', '628566337523', 'Philipus Telaumbanua', 'Perumnas Simalingkar', '9772'),
('12347', '6281396838366', 'Rifki', 'Serdang', '9319'),
('12348', '628566299002', 'Andika Novaldy', 'Titi Kuning', '9801'),
('12349', '6281375022222', 'Bagoes Harsono', 'Jl.', '2442'),
('123410', '6285760686808', 'Bambang Budiarto', 'Menteng', '1814'),
('123411', '6285762444718', 'Melvani Hardi', 'Jl.', '992'),
('123412', '6285761437930', 'Dameria', 'Jl.', '3487'),
('123413', '6281265981065', 'Lia Amalia', 'Jl. D.I. Panjaitan', '8220'),
('123414', '6287868595258', 'Habrul Leni', 'Jl. Setia Budi', '1109'),
('123415', '6287869468649', 'Winda', 'Jl.', '3118'),
('123416', '6285270880265', 'Mariani Valentina', 'Jl.', '6951'),
('123417', '6281396521253', 'Adhal Huda', 'Jl.', '8443'),
('123418', '6281375271001', 'Aidil Akbar', 'Tanjung Morawa', '7411'),
('123419', '6285761086757', 'Alvin', 'Jl.', '8191'),
('123420', '6281396090229', 'Ane', 'Jl.', '4686'),
('123429', '6285261710051', 'Eky', 'Jl.', '6826'),
('123729', '6285276441123', 'Fadliansyah Nst', 'Jl.', '7507'),
('123430', '6285261959501', 'Farabi', 'Jl.', '1780'),
('123431', '6285760984725', 'Ferry', 'Jl.', '3279'),
('123432', '6281990577574', 'Hadianto', 'Jl.', '9145'),
('123433', '6281361576107', 'Inggou David', 'Jl.', '6982'),
('123434', '6281396399322', 'Jannah', 'Jl.', '7157'),
('123435', '6285361045726', 'Makmur', 'Jl.', '4839'),
('123436', '6285296499123', 'Suharsono', 'Jl.', '1720'),
('123437', '6281376888144', 'Panji', 'Jl.', '4834'),
('123438', '6285275850480', 'Riki', 'Jl.', '6085'),
('123439', '6281396137343', 'Rudi Tanaka', 'Jl.', '9174'),
('123440', '6281933407474', 'Ruri Handayani', 'Jl.', '207'),
('123441', '6281933427345', 'Surya Ajen', 'Jl.', '538'),
('123442', '6281361077317', 'Tuti', 'Jl.', '8128'),
('123443', '6285262662687', 'Teddy', 'Jl.', '2804'),
('123444', '6281264299558', 'Uti', 'Jl.', '5687');

-- --------------------------------------------------------

--
-- Table structure for table `inbox`
--

CREATE TABLE IF NOT EXISTS `inbox` (
  `id_inbox` int(11) NOT NULL AUTO_INCREMENT,
  `no_pengirim` varchar(20) NOT NULL,
  `pesan` text,
  `status_inbox` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_inbox`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=57 ;

--
-- Dumping data for table `inbox`
--

INSERT INTO `inbox` (`id_inbox`, `no_pengirim`, `pesan`, `status_inbox`) VALUES
(49, '6287869238905', 'LELANG AKTIF', 1),
(48, '6287869238905', 'LELANG MENANG P01', 1),
(51, '6287869238905', '1234 P02 2600000', 1);

-- --------------------------------------------------------

--
-- Table structure for table `operator`
--

CREATE TABLE IF NOT EXISTS `operator` (
  `nip` char(18) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(25) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `operator`
--

INSERT INTO `operator` (`nip`, `username`, `password`, `nama_lengkap`) VALUES
('123456789123456789', 'anca', 'bd754cb79946b582127e2ddee', 'Muhammad Arifin Siregar');

-- --------------------------------------------------------

--
-- Table structure for table `outbox`
--

CREATE TABLE IF NOT EXISTS `outbox` (
  `id_outbox` int(8) NOT NULL AUTO_INCREMENT,
  `no_tujuan` varchar(15) NOT NULL,
  `pesan` varchar(160) NOT NULL,
  `status_outbox` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_outbox`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=192 ;

--
-- Dumping data for table `outbox`
--

INSERT INTO `outbox` (`id_outbox`, `no_tujuan`, `pesan`, `status_outbox`) VALUES
(191, '6287869238905', 'Penawaran 2600000 utk barang P02 tidak berhasil. \nSilahkan ajukan penawaran yg lebih tinggi.', 2),
(190, '6287869238905', 'P01, ponsel Nokia N70(1050000)\nP02, BB Bold(2500000)\n', 2);

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
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

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
(10, '6287869238905', 'P02', 2700000, '2010-04-21', '17:35:34', 0);
