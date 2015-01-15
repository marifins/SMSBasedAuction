-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 02, 2010 at 07:47 PM
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
  `id_jenis` char(1) NOT NULL,
  `nama_barang` varchar(35) NOT NULL,
  `status_barang` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`kode_barang`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_barang`, `id_jenis`, `nama_barang`, `status_barang`) VALUES
('K01', 'K', 'Honda Supra X 2006', 1),
('K02', 'K', 'Honda Supra Vit 2005', 1),
('P07', 'P', '1 Cincin 15 Karat 2,2 gr', 0),
('P08', 'P', '1 Kalung Rantai 15 Karat 2,3 gr', 0),
('P09', 'P', '1 Kalung 15 Karat 1 gr', 0),
('P10', 'P', '1 Kalung Rantai 15 Karat 1,6 gr', 0),
('P11', 'P', '1 Gelang 14 Karat 2,1 gr', 2),
('P12', 'P', '1 Liontin 15 Karat 2,4 gr', 1);

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
  `tanggal_terdaftar` date NOT NULL,
  PRIMARY KEY (`no_ktp`),
  UNIQUE KEY `no_ponsel` (`no_ponsel`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bidder`
--

INSERT INTO `bidder` (`no_ktp`, `no_ponsel`, `nama`, `alamat`, `pin`, `tanggal_terdaftar`) VALUES
('1271193010880001', '6281265888055', 'Muhammad Arifin Siregar', 'Jl. Darussalam Darussalam Darussalam', '81dc9bdb52d04dc20036', '2010-06-04'),
('123427', '628972826523', 'Denis Afrianto', 'Jl. Kasuari', '81dc9bdb52d04dc20036', '2010-06-04'),
('1234', '628126002850', 'Pak Andri Budiman', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('1233', '6281375263750', 'Pak Syahriol', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('1231', '628126521623', 'Bu Dian', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123428', '6285658521305', 'Desfa Maulani', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123421', '6285261036037', 'Anggi Rivai', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123422', '6285275913743', 'Ara', 'Binjai', '81dc9bdb52d04dc20036', '2010-05-01'),
('123426', '6285276611552', 'Bobby Medana', 'Jl. Medan', '81dc9bdb52d04dc20036', '2010-06-01'),
('123425', '6285276674222', 'Atika Sari', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123424', '6281370818359', 'Arta', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('1232', '628126091706', 'Bu Maya', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123423', '6285275901045', 'Arsyad', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('12341', '6285765172394', 'M. Reza Pahlepi', 'Jl. Negara Aksara', '81dc9bdb52d04dc20036', '2010-05-01'),
('12342', '6285761310149', 'Mhd. Azemi', 'Jl. Kayu Putih', '81dc9bdb52d04dc20036', '2010-05-01'),
('12343', '6281396107106', 'Azhari', 'Batu Bara', '81dc9bdb52d04dc20036', '2010-06-01'),
('12344', '626176352054', 'M. Ahyal Husna', 'Marendal', '81dc9bdb52d04dc20036', '2010-05-01'),
('12345', '6285760900204', 'Alfarisi', 'Binjai', '81dc9bdb52d04dc20036', '2010-05-01'),
('12346', '628566337523', 'Philipus Telaumbanua', 'Perumnas Simalingkar', '81dc9bdb52d04dc20036', '2010-05-01'),
('12347', '6281396838366', 'Rifki', 'Serdang', '81dc9bdb52d04dc20036', '2010-05-01'),
('12348', '628566299002', 'Andika Novaldy', 'Titi Kuning', '81dc9bdb52d04dc20036', '2010-05-01'),
('12349', '6281375022222', 'Bagoes Harsono', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123410', '6285760686808', 'Bambang Budiarto', 'Menteng', '81dc9bdb52d04dc20036', '2010-05-01'),
('123411', '6285762444718', 'Melvani Hardi', 'Jl. Medan', '81dc9bdb52d04dc20036', '2010-06-01'),
('123412', '6285761437930', 'Dameria', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123413', '6281265981065', 'Lia Amalia', 'Jl. D.I. Panjaitan', '81dc9bdb52d04dc20036', '2010-05-01'),
('123414', '6287868595258', 'Habrul Leni', 'Jl. Setia Budi', '81dc9bdb52d04dc20036', '2010-05-01'),
('123415', '6287869468649', 'Winda', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123416', '6285270880265', 'Mariani Valentina', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123417', '6281396521253', 'Adhal Huda', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123418', '6281375271001', 'Aidil Akbar', 'Tanjung Morawa', '81dc9bdb52d04dc20036', '2010-05-01'),
('123419', '6285761086757', 'Alvin', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123420', '6281396090229', 'Ane', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123429', '6285261710051', 'Eky', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123729', '6285276441123', 'Fadliansyah Nst', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123430', '6285261959501', 'Farabi', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123431', '6285760984725', 'Ferry', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123432', '6281990577574', 'Hadianto', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123433', '6281361576107', 'Inggou David', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123434', '6281396399322', 'Jannah', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123435', '6285361045726', 'Makmur', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123436', '6285296499123', 'Suharsono', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123437', '6281376888144', 'Panji', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123438', '6285275850480', 'Riki', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123439', '6281396137343', 'Rudi Tanaka', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123440', '6281933407474', 'Ruri Handayani', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123441', '6281933427345', 'Surya Ajen', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123442', '6281361077317', 'Tuti', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123443', '6285262662687', 'Teddy', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('123444', '6281264299558', 'Uti', 'Jl.', '81dc9bdb52d04dc20036', '2010-05-01'),
('645', '456', '45', '456', '202cb962ac59075b964b', '2010-05-01');

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
('K01', 5250000, 0, '2010-06-01'),
('K02', 4500000, 0, '2010-06-02'),
('P07', 300000, 0, '2010-06-01'),
('P08', 370000, 0, '2010-06-01'),
('P09', 160000, 0, '2010-06-25'),
('P10', 300000, 0, '2010-06-22'),
('P11', 320000, 0, '2010-06-01'),
('P12', 450000, 0, '2010-06-02');

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=133 ;

--
-- Dumping data for table `inbox`
--

INSERT INTO `inbox` (`id_inbox`, `no_pengirim`, `pesan`, `status_inbox`, `waktu_terima`) VALUES
(130, '6281265888055', '123456781234567', 1, '2010-06-02 17:57:01'),
(131, '6281265888055', '1234567', 1, '2010-06-02 18:03:43'),
(132, '6281265888055', '12345678', 1, '2010-06-02 18:04:13');

-- --------------------------------------------------------

--
-- Table structure for table `jenis_barang`
--

CREATE TABLE IF NOT EXISTS `jenis_barang` (
  `id_jenis` char(1) NOT NULL,
  `jenis` varchar(35) NOT NULL,
  PRIMARY KEY (`id_jenis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jenis_barang`
--

INSERT INTO `jenis_barang` (`id_jenis`, `jenis`) VALUES
('P', 'Perhiasan'),
('K', 'Kendaraan Bermotor'),
('E', 'Elektronik');

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
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=570 ;

--
-- Dumping data for table `outbox`
--

INSERT INTO `outbox` (`id_outbox`, `no_tujuan`, `pesan`, `status_outbox`, `waktu_kirim`) VALUES
(539, '6281265888055', 'Penawaran 123456 utk barang K01\ntidak berhasil. \nSilahkan ajukan penawaran \nyg lebih tinggi.', 2, '2010-06-02 08:54:15'),
(538, '6281265888055', 'K01, Honda Supra X 2006(5250000)\nK02, Honda Supra Vit 2005(4500000)\nP12, 1 Liontin 15 Karat 2,4 gr(450000)\n', 2, '2010-06-01 17:32:44'),
(537, '6281265888055', 'Penawaran terakhir :\nK01(Honda Supra X 2006, 5400000)\nK02(Honda Supra Vit 2005, 4500000)\nP12(1 Liontin 15 Karat 2,4 gr, 450000)', 2, '2010-06-01 09:05:44'),
(536, '6281265888055', 'Penawaran anda untuk barang K01\nsebesar 5400000 telah diproses.', 2, '2010-06-01 09:04:32'),
(535, '6281265888055', 'Penawaran terakhir :\nK01(Honda Supra X 2006, 5300000)\nK02(Honda Supra Vit 2005, 4500000)\nP12(1 Liontin 15 Karat 2,4 gr, 450000)', 2, '2010-06-01 09:03:45'),
(533, '6281265888055', 'Penawaran anda untuk barang K01\nsebesar 5300000 telah diproses.', 2, '2010-06-01 09:24:17'),
(534, '6281265888055', 'Penawaran 5250000 utk barang K01\ntidak berhasil. \nSilahkan ajukan penawaran \nyg lebih tinggi.', 2, '2010-06-01 09:01:42'),
(532, '6281265888055', 'Penawaran 530000 utk barang K01\ntidak berhasil. \nSilahkan ajukan penawaran \nyg lebih tinggi.', 2, '2010-06-01 09:19:22'),
(531, '6281265888055', 'K01, Honda Supra X 2006(5250000)\nK02, Honda Supra Vit 2005(4500000)\nP12, 1 Liontin 15 Karat 2,4 gr(450000)\n', 2, '2010-06-01 09:13:33'),
(530, '6281265888055', 'K01, Honda Supra X 2006(5250000)\nK02, Honda Supra Vit 2005(4500000)\nP12, 1 Liontin 15 Karat 2,4 gr(450000)\n', 2, '2010-06-01 09:12:13'),
(529, '6281265888055', 'Penawaran anda untuk barang P05\nsebesar 5300000 telah diproses.', 2, '2010-06-09 06:23:39'),
(528, '6281265888055', 'Penawaran 5300000 utk barang P05\ntidak berhasil. \nSilahkan ajukan penawaran \nyg lebih tinggi.', 2, '2010-06-09 06:23:16'),
(527, '6281265888055', 'Penawaran terakhir :\nP05(Honda Supra X 2006, 5400000)\nP06(Honda Supra Vit 2005, 4700000)\nP12(1 Liontin 15 Karat 2,4 gr, 450000)', 2, '2010-06-01 09:27:09'),
(526, '6281265888055', 'Lelang berikutnya :\nP09(1 Kalung 15 Karat 1 gr->2010-06-25)\nP10(1 Kalung Rantai 15 Karat 1,6 gr->2010-06-22)', 2, '2010-06-01 16:58:42'),
(524, '6281265888055', 'Penawaran anda untuk barang P05\nsebesar 5400000 telah diproses.', 2, '2010-06-01 09:02:27'),
(525, '6281265888055', 'Lelang berikutnya :\nP09(1 Kalung 15 Karat 1 gr->2010-06-25)\nP10(1 Kalung Rantai 15 Karat 1,6 gr->2010-06-22)', 2, '2010-06-01 16:58:48'),
(523, '6281265888055', 'P05, Honda Supra X 2006(5250000)\nP06, Honda Supra Vit 2005(4500000)\nP12, 1 Liontin 15 Karat 2,4 gr(450000)\n', 2, '2010-06-01 09:01:02'),
(522, '6281265888055', 'Lelang berikutnya :\nP09(1 Kalung 15 Karat 1 gr->2010-06-25)\nP10(1 Kalung Rantai 15 Karat 1,6 gr->2010-06-22)', 2, '2010-06-01 09:03:53'),
(521, '6281265888055', 'Pemenang terakhir :\nP05(Honda Supra X 2006, 5300000)\nP06(Honda Supra Vit 2005, 4700000)\nP12(1 Liontin 15 Karat 2,4 gr, 450000)', 2, '2010-06-01 09:02:45'),
(520, '6281265888055', 'Penawaran 5250000 utk barang P05\ntidak berhasil. \nSilahkan ajukan penawaran \nyg lebih tinggi.', 2, '2010-06-01 09:01:42'),
(519, '6281265888055', 'Penawaran anda untuk barang P05\nsebesar 5300000 telah diproses.', 2, '2010-06-01 09:00:43'),
(518, '6281265888055', 'P05, Honda Supra X 2006(5250000)\nP06, Honda Supra Vit 2005(4500000)\nP12, 1 Liontin 15 Karat 2,4 gr(450000)\n', 2, '2010-06-01 08:59:39'),
(517, '6281265888055', 'P06, Honda Supra Vit 2005(4500000)\nP12, 1 Liontin 15 Karat 2,4 gr(450000)\n', 2, '2010-06-01 08:59:10'),
(516, '6281265888055', 'Pemenang terakhir :\nP05(Honda Supra X 2006, 5300000)\nP06(Honda Supra Vit 2005, 4700000)\nP12(1 Liontin 15 Karat 2,4 gr, 450000)', 2, '2010-06-02 11:28:08'),
(515, '6281265888055', 'Pemenang terakhir :\nP06(Honda Supra Vit 2005, 4700000)\nP06(Honda Supra Vit 2005, 4700000)\nP05(Honda Supra X 2006, 5300000)', 2, '2010-06-02 00:37:11');

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
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `pemenang`
--

INSERT INTO `pemenang` (`id`, `no_ponsel`, `kode_barang`, `harga_terjual`) VALUES
(7, '6281265888055', 'K01', 5400000);

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
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=36 ;

--
-- Dumping data for table `penawaran`
--


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
('17:00');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `nip` char(18) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(25) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `level` int(1) NOT NULL DEFAULT '1',
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
('999', 'mascode', '81dc9bdb52d04dc20036dbd83', 'Mhd. Arifin Srg', 1),
('0123456789', 'marifins', 'bd754cb79946b582127e2ddee', 'Mhd. Arifin Srg', 2);
