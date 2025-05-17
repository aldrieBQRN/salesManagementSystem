-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: May 17, 2025 at 11:52 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `salesmanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productID` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productID`, `name`, `category`, `price`, `stock`) VALUES
(1, 'Smartphone', 'Electronics', 499.99, 86),
(2, 'Laptop', 'Electronics', 799.99, 55),
(3, 'Headphones', 'Electronics', 99.99, 145),
(4, 'TV', 'Electronics', 899.99, 24),
(5, 'Tablet', 'Electronics', 299.99, 80),
(6, 'Camera', 'Electronics', 450.00, 40),
(7, 'Smartwatch', 'Electronics', 199.99, 119),
(8, 'Bluetooth Speaker', 'Electronics', 79.99, 200),
(9, 'Game Console', 'Electronics', 350.00, 20),
(10, 'Drone', 'Electronics', 150.00, 46),
(11, 'Jeans', 'Clothing', 39.99, 191),
(12, 'T-shirt', 'Clothing', 15.99, 250),
(13, 'Jacket', 'Clothing', 59.99, 100),
(14, 'Sweater', 'Clothing', 29.99, 150),
(15, 'Shoes', 'Clothing', 49.99, 120),
(16, 'Sweatpants', 'Clothing', 24.99, 180),
(17, 'Blouse', 'Clothing', 39.99, 110),
(18, 'Shorts', 'Clothing', 19.99, 160),
(19, 'Hat', 'Clothing', 14.99, 200),
(20, 'Scarf', 'Clothing', 18.99, 210),
(21, 'Sofa', 'Furniture', 499.99, 26),
(22, 'Dining Table', 'Furniture', 250.00, 11),
(23, 'Office Chair', 'Furniture', 120.00, 50),
(24, 'Bookshelf', 'Furniture', 89.99, 70),
(25, 'Bed Frame', 'Furniture', 350.00, 25),
(26, 'Coffee Table', 'Furniture', 99.99, 60),
(27, 'Couch', 'Furniture', 399.99, 19),
(28, 'Armchair', 'Furniture', 199.99, 80),
(29, 'Recliner', 'Furniture', 299.99, 45),
(30, 'Lamp', 'Furniture', 39.99, 150),
(31, 'Chocolate Bar', 'Food', 1.99, 500),
(32, 'Pizza', 'Food', 9.99, 300),
(33, 'Ice Cream', 'Food', 5.99, 400),
(34, 'Cereal', 'Food', 3.49, 450),
(35, 'Juice', 'Food', 2.99, 350),
(36, 'Pasta', 'Food', 4.49, 280),
(37, 'Cookies', 'Food', 2.99, 550),
(38, 'Bread', 'Food', 1.49, 600),
(39, 'Milk', 'Food', 1.29, 500),
(40, 'Cheese', 'Food', 4.99, 320),
(41, 'Action Figure', 'Toys', 19.99, 150),
(42, 'Doll', 'Toys', 12.99, 200),
(43, 'Toy Car', 'Toys', 9.99, 250),
(44, 'Board Game', 'Toys', 14.99, 120),
(45, 'Puzzle', 'Toys', 7.99, 180),
(46, 'Building Blocks', 'Toys', 11.99, 100),
(47, 'Stuffed Animal', 'Toys', 8.99, 120),
(48, 'Toy Train', 'Toys', 10.99, 167),
(49, 'Play-Doh', 'Toys', 5.99, 345),
(50, 'Kite', 'Toys', 6.99, 9),
(51, 'Toyo', 'Food', 12.00, 18),
(52, 'ZETH RAMZY ', 'Food', 200.00, 20);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `salesID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `salesperson` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `sale_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`salesID`, `productID`, `salesperson`, `quantity`, `total`, `sale_date`) VALUES
(1, 1, 'Aldrie Baquiran', 3, 150.00, '2025-02-01'),
(2, 9, 'John Doe', 4, 210.00, '2025-02-04'),
(3, 6, 'Michael Johnson', 2, 100.00, '2025-02-01'),
(4, 4, 'Emily Davis', 1, 90.00, '2025-02-04'),
(5, 10, 'John Doe', 2, 160.00, '2025-02-05'),
(6, 3, 'Emily Davis', 5, 250.00, '2025-02-03'),
(7, 8, 'Michael Johnson', 3, 150.00, '2025-02-03'),
(8, 4, 'Aldrie Baquiran', 4, 220.00, '2025-02-04'),
(9, 7, 'Michael Johnson', 5, 250.00, '2025-02-02'),
(10, 10, 'Michael Johnson', 1, 80.00, '2025-02-05'),
(11, 7, 'John Doe', 5, 250.00, '2025-02-02'),
(12, 2, 'Jane Smith', 1, 100.00, '2025-02-02'),
(13, 6, 'John Doe', 1, 80.00, '2025-02-01'),
(14, 1, 'Jane Smith', 3, 150.00, '2025-02-01'),
(15, 2, 'Aldrie Baquiran', 2, 200.00, '2025-02-02'),
(16, 2, 'Emily Davis', 2, 120.00, '2025-02-02'),
(17, 5, 'Jane Smith', 2, 110.00, '2025-02-05'),
(18, 5, 'Emily Davis', 3, 180.00, '2025-02-05'),
(19, 5, 'Aldrie Baquiran', 2, 120.00, '2025-02-05'),
(20, 1, 'Emily Davis', 4, 200.00, '2025-02-01'),
(21, 3, 'Aldrie Baquiran', 1, 100.00, '2025-02-03'),
(22, 3, 'Jane Smith', 4, 180.00, '2025-02-03'),
(23, 4, 'Jane Smith', 5, 250.00, '2025-02-04'),
(24, 8, 'John Doe', 3, 180.00, '2025-02-03'),
(25, 9, 'Michael Johnson', 4, 220.00, '2025-02-04'),
(26, 1, 'Default User', 5, 2499.95, '2024-02-08'),
(27, 4, 'John Doe', 2, 1799.98, '2023-02-10'),
(28, 27, 'Emily Davis', 10, 3999.90, '2022-02-18'),
(29, 2, 'Jane Smith', 1, 799.99, '2021-02-18'),
(30, 2, 'Aldrie Baquiran', 1, 799.99, '2025-02-02'),
(31, 10, 'Salesperson', 2, 300.00, '2023-12-01'),
(32, 10, 'Aldrie Baquiran', 2, 300.00, '2024-02-07'),
(33, 21, 'Aldrie Baquiran', 4, 1999.96, '2023-02-15'),
(34, 27, 'Aldrie Baquiran', 1, 399.99, '2022-02-11'),
(35, 4, 'Aldrie Baquiran', 2, 1799.98, '2021-02-18'),
(36, 50, 'Aldrie Baquiran', 1, 6.99, '2025-02-20'),
(37, 7, 'Aldrie Baquiran', 1, 199.99, '2025-02-13'),
(38, 11, 'Aldrie Baquiran', 1, 39.99, '2025-02-27'),
(39, 11, 'Aldrie Baquiran', 1, 39.99, '2025-02-27'),
(40, 47, 'Aldrie Baquiran', 10, 89.90, '2025-02-13'),
(42, 50, 'Aldrie Baquiran', 1, 6.99, '2025-02-13'),
(44, 50, 'Aldrie Baquiran', 1, 6.99, '2025-02-14'),
(45, 50, 'Aldrie Baquiran', 2, 13.98, '2025-02-05'),
(46, 50, 'Aldrie Baquiran', 3, 20.97, '2025-02-12'),
(52, 2, 'Emily Davis', 2, 1599.98, '2025-02-21'),
(53, 48, 'Emily Davis', 33, 296.67, '2025-02-28'),
(54, 3, 'Default User', 2, 199.98, '2025-02-06'),
(55, 2, 'Default User', 1, 799.99, '2025-02-19'),
(56, 49, 'Default User', 1, 5.99, '2025-02-14'),
(57, 50, 'Default User', 1, 6.99, '2025-02-13'),
(58, 50, 'Default User', 1, 6.99, '2025-02-20'),
(59, 49, 'Default User', 1, 5.99, '2025-02-19'),
(60, 3, 'Aldrie Baquiran', 1, 99.99, '2025-02-27'),
(61, 22, 'Aldrie Baquiran', 1, 250.00, '2025-02-20'),
(62, 1, 'Jane Smith', 1, 499.99, '2025-02-06'),
(63, 2, 'Michael Johnson', 1, 799.99, '2024-02-16'),
(65, 3, 'Default Salesperson', 1, 99.99, '2025-02-06'),
(66, 3, 'Default Salesperson', 1, 99.99, '2025-02-06'),
(67, 50, 'Aldrie Baquiran', 1, 6.99, '2025-02-14'),
(68, 49, 'Default User', 1, 5.99, '2025-02-07'),
(71, 1, 'Aldrie Baquiran', 2, 999.98, '2025-02-20'),
(72, 1, 'Jane Smith', 1, 499.99, '2023-02-10'),
(73, 49, 'John Doe', 2, 11.98, '2023-02-23'),
(74, 47, 'John Doe', 20, 179.80, '2024-02-09'),
(75, 51, 'Aldrie Baquiran', 2, 200.00, '2025-02-12'),
(76, 4, 'Aldrie Baquiran', 2, 1799.98, '2025-02-12');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `name`, `role`, `phone`, `email`, `password`) VALUES
(1, 'Admin', 'Admin', 'Admin', 'admin', 'admin'),
(2, 'Aldrie Baquiran', 'Salesperson', '09678677866', 'aldrie', 'aldrie15'),
(3, 'John Doe', 'Salesperson', '0987654321', 'john', 'john1234'),
(4, 'Jane Smith', 'Salesperson', '0912345678', 'jane', 'jane1234'),
(5, 'Michael Johnson', 'Salesperson', '0976543210', 'michael', 'michael1234'),
(6, 'Emily Davis', 'Salesperson', '0923456789', 'emily', 'emily1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`salesID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `salesID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
