/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - apmd_new
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`apmd_new` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `apmd_new`;

/*Table structure for table `answers` */

DROP TABLE IF EXISTS `answers`;

CREATE TABLE `answers` (
  `Answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `Question_id` int(11) DEFAULT NULL,
  `Student_id` int(11) DEFAULT NULL,
  `Answer` varchar(1000) DEFAULT NULL,
  `Mark` int(11) DEFAULT NULL,
  PRIMARY KEY (`Answer_id`),
  KEY `Answer_id` (`Answer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `answers` */

insert  into `answers`(`Answer_id`,`Question_id`,`Student_id`,`Answer`,`Mark`) values 
(1,1,1,'',0),
(2,1,1,'hello',0),
(3,1,1,'',0),
(4,1,1,'',0),
(5,1,1,'',0),
(6,2,1,'',0),
(7,1,1,'',0),
(8,1,1,'hello',1),
(9,2,1,'hi',0);

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `Complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) NOT NULL,
  `Title` varchar(1000) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `Reply` varchar(1000) DEFAULT NULL,
  `Date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Complaint_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_type` varchar(100) NOT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values 
(1,'anu','anu','teacher'),
(2,'admin','admin','admin'),
(4,'liya','liya','student');

/*Table structure for table `questions` */

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `Question_id` int(11) NOT NULL AUTO_INCREMENT,
  `Test_id` int(11) DEFAULT NULL,
  `Question` varchar(500) DEFAULT NULL,
  `Answer` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`Question_id`),
  KEY `Question_id` (`Question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `questions` */

insert  into `questions`(`Question_id`,`Test_id`,`Question`,`Answer`) values 
(1,1,'speak','hello'),
(2,1,'second','hai'),
(3,NULL,NULL,NULL);

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(200) DEFAULT NULL,
  `lname` varchar(200) DEFAULT NULL,
  `phone` varchar(500) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  KEY `student_id` (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `students` */

insert  into `students`(`student_id`,`login_id`,`fname`,`lname`,`phone`,`email`) values 
(1,4,'liya','liya','8435093693','liya@gmail.com');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `Teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `Login_id` int(11) DEFAULT NULL,
  `Fname` varchar(200) DEFAULT NULL,
  `Lname` varchar(200) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `qualification` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Teacher_id`),
  KEY `Teacher_id` (`Teacher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `teacher` */

insert  into `teacher`(`Teacher_id`,`Login_id`,`Fname`,`Lname`,`phone`,`email`,`qualification`) values 
(1,1,'anu','joseph','87654324567','anu@gmail.com','btech');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `Test_id` int(11) NOT NULL AUTO_INCREMENT,
  `Teacher_id` int(11) DEFAULT NULL,
  `Test_name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Test_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `test` */

insert  into `test`(`Test_id`,`Teacher_id`,`Test_name`) values 
(1,1,'basic');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
