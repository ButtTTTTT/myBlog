create database myBlog;
use myBlog;
-- MySQL dump 10.13  Distrib 8.0.24, for Linux (x86_64)
--
-- Host: localhost    Database: myBlog
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ad`
--

DROP TABLE IF EXISTS `ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad` (
  `ad_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广告id',
  `ad_type_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告类型',
  `ad_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告标题',
  `ad_img_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告的图片url地址',
  `ad_link_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告跳转连接',
  `ad_sort` int DEFAULT NULL COMMENT '广告排序，越小越排在前面',
  `ad_begin_time` datetime DEFAULT NULL COMMENT '广告开始时间',
  `ad_end_time` datetime DEFAULT NULL COMMENT '广告结束时间',
  `ad_add_time` datetime DEFAULT NULL COMMENT '添加广告的时间',
  PRIMARY KEY (`ad_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='广告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad`
--

LOCK TABLES `ad` WRITE;
/*!40000 ALTER TABLE `ad` DISABLE KEYS */;
/*!40000 ALTER TABLE `ad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_type`
--

DROP TABLE IF EXISTS `ad_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad_type` (
  `ad_type_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广告类型id',
  `ad_type_title` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告类型名称',
  `ad_type_tag` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告类型标识（首页顶部广告，轮播图广告，文章详情广告）',
  `ad_type_sort` int unsigned DEFAULT '10' COMMENT '广告类型排序，越小越靠前',
  `ad_type_add_time` datetime DEFAULT NULL COMMENT '广告类型添加时间',
  PRIMARY KEY (`ad_type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='广告类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_type`
--

LOCK TABLES `ad_type` WRITE;
/*!40000 ALTER TABLE `ad_type` DISABLE KEYS */;
INSERT INTO `ad_type` VALUES ('0be3c8f15d70b5efffb66a6891fc7aad','首页轮播图广告','homeAd',0,'2021-12-25 10:23:35'),('e055bfbbcb4e35d92e1535654efb318e','文章页面广告','articleAd',1,'2021-12-25 10:23:35');
/*!40000 ALTER TABLE `ad_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章id',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `article_add_time` datetime NOT NULL COMMENT '文章添加时间',
  `article_context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文章内容',
  `article_cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章封面url',
  `article_hot` int DEFAULT NULL COMMENT '是否是热门文章 0否，1是',
  `article_watch_times` int DEFAULT NULL COMMENT '观看次数',
  `article_faverite_no` int DEFAULT NULL COMMENT '点赞次数',
  `user_Id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `article_faver_rate` int DEFAULT NULL COMMENT '收藏次数',
  `article_type_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章分类id',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES ('cn.hutool.crypto.digest.MD5@1235151c','文章标题：4','2024-01-15 22:11:41','文章内容：378',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@14447be','文章标题：0','2024-01-15 22:11:41','文章内容：747',NULL,NULL,1,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@15d0d6c9','文章标题：1','2024-01-14 17:28:27','文章内容：659',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@18dd5ed3','文章标题：1','2024-01-14 17:28:27','文章内容：579',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@1ca3d25b','文章标题：1','2024-01-14 17:28:27','文章内容：404',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@1f87607c','文章标题：1','2024-01-14 16:32:10','文章内容：911',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@2287395','文章标题：2','2024-01-14 17:28:27','文章内容：412',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@24043ec5','文章标题：3','2024-01-14 17:28:27','文章内容：844',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@307e4c44','文章标题：0','2024-01-14 16:32:10','文章内容：658',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@373e6b9d','文章标题：4','2024-01-14 16:32:10','文章内容：594',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@38f981b6','文章标题：4','2024-01-14 17:28:27','文章内容：123',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@3a4aadf8','文章标题：5','2024-01-14 17:28:27','文章内容：727',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@3ab35b9c','文章标题：2','2024-01-14 17:28:27','文章内容：923',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@3aed69dd','文章标题：0','2024-01-14 17:28:27','文章内容：27',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@3d620a1','文章标题：4','2024-01-15 22:11:41','文章内容：253',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@3e33b52e','文章标题：4','2024-01-14 17:28:27','文章内容：730',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4130a648','文章标题：5','2024-01-14 17:28:27','文章内容：101',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@472d0f4','文章标题：0','2024-01-14 16:32:10','文章内容：723',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4a50d04a','文章标题：1','2024-01-15 22:11:41','文章内容：198',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4aa2877c','文章标题：5','2024-01-14 16:32:10','文章内容：489',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4aa31ffc','文章标题：3','2024-01-14 16:32:10','文章内容：22',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4b862408','文章标题：2','2024-01-14 16:32:10','文章内容：619',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4c19d42e','文章标题：3','2024-01-14 16:32:10','文章内容：393',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4c6a4ffd','文章标题：5','2024-01-14 17:28:27','文章内容：711',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4cc65c2','文章标题：0','2024-01-15 22:11:41','文章内容：696',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4d09cade','文章标题：0','2024-01-14 16:32:10','文章内容：931',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@4f486211','文章标题：5','2024-01-15 22:11:41','文章内容：260',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@500051c5','文章标题：1','2024-01-14 16:32:10','文章内容：411',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@5099c59b','文章标题：2','2024-01-15 22:11:41','文章内容：65',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@52454457','文章标题：4','2024-01-14 17:28:27','文章内容：274',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@535a518c','文章标题：3','2024-01-14 17:28:27','文章内容：638',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@5529ff44','文章标题：5','2024-01-15 22:11:41','文章内容：997',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@574059d5','文章标题：1','2024-01-15 22:11:41','文章内容：39',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@5a1f778','文章标题：5','2024-01-15 22:11:41','文章内容：485',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@5cd96b41','文章标题：1','2024-01-15 22:11:41','文章内容：678',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@5eabff6b','文章标题：0','2024-01-15 22:11:41','文章内容：931',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@5ff6dd3c','文章标题：0','2024-01-14 17:28:27','文章内容：56',NULL,NULL,1,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@6042d663','文章标题：2','2024-01-14 17:28:27','文章内容：673',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@61ff6a49','文章标题：0','2024-01-14 17:28:27','文章内容：403',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@6393bf8b','文章标题：1','2024-01-14 16:32:10','文章内容：78',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@65629ac6','文章标题：5','2024-01-14 16:32:10','文章内容：415',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@67770b37','文章标题：3','2024-01-15 22:11:41','文章内容：821',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@6d672bd4','文章标题：2','2024-01-15 22:11:41','文章内容：986',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@6ddee60f','文章标题：3','2024-01-14 16:32:10','文章内容：204',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@6e829e50','文章标题：4','2024-01-14 16:32:10','文章内容：436',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@72906e','文章标题：4','2024-01-15 22:11:41','文章内容：735',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@73041b7d','文章标题：3','2024-01-15 22:11:41','文章内容：899',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@73c31181','文章标题：3','2024-01-15 22:11:41','文章内容：282',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@76d7881e','文章标题：2','2024-01-14 16:32:10','文章内容：581',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@7741d346','文章标题：3','2024-01-14 17:28:27','文章内容：633',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@7945b206','文章标题：4','2024-01-14 16:32:10','文章内容：284',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@7d42542','文章标题：5','2024-01-14 16:32:10','文章内容：748',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@7e1ffe70','文章标题：2','2024-01-15 22:11:41','文章内容：410',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597'),('cn.hutool.crypto.digest.MD5@ec7b5de','文章标题：2','2024-01-14 16:32:10','文章内容：268',NULL,NULL,0,0,'0eaf1e5f28c24532976da41d0c58e621',0,'2b897e397c6a058507f66d3b8ee27597');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_tag`
--

DROP TABLE IF EXISTS `article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_tag` (
  `article_tag_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标签ID',
  `article_tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章标签名称',
  `article_tag_add_time` datetime DEFAULT NULL COMMENT '文章标签添加时间',
  PRIMARY KEY (`article_tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_tag`
--

LOCK TABLES `article_tag` WRITE;
/*!40000 ALTER TABLE `article_tag` DISABLE KEYS */;
INSERT INTO `article_tag` VALUES ('022ddaa2bea63a7fd8aac6000dee07ed','文章标签：8','2024-01-14 17:28:27'),('079961d15d5f69e9e126ad33c986ad13','文章标签：3','2024-01-15 22:11:42'),('0906aca1d4581bac110e9d70ea7bf5d0','2341241','2024-01-13 20:46:45'),('0a5a5b4a23d3d1a2c682b3241b73fe71','文章标签：6','2024-01-15 22:11:42'),('141cfdf703863e60e04a8aedf78eb219','文章标签：2','2024-01-14 17:28:27'),('2cbf428f227141632c70293e2333ca51','文章标签：0','2024-01-14 16:32:10'),('4028bc80ce846449ba74719a8d4c6dca','文章标签：4','2024-01-14 17:28:27'),('429a68b9327cbab47cf4e89588985295','文章标签：8','2024-01-14 16:32:10'),('4b4a4f4682290e837a7727af0538670f','文章标签：5','2024-01-14 16:32:10'),('5b9f9b87cded125857847db59f03688c','文章标签：0','2024-01-14 17:28:27'),('62faa91dd5ab7d6bf0509dbc8b9ccb65','文章标签：8','2024-01-15 22:11:42'),('636a0534653b0d6965745866ef0c5ab0','文章标签：2','2024-01-14 16:32:10'),('65a079090ca105ee8ab0af39a46fdb2e','文章标签：2','2024-01-15 22:11:42'),('6f1d1e7d4f7d9b69728362abfa494ff2','文章标签：7','2024-01-15 22:11:42'),('7443899466400726e5e00171e9b850b1','文章标签：7','2024-01-14 16:32:10'),('7e529fad66d385703ab14e6c4f461073','文章标签：0','2024-01-15 22:11:42'),('8a9f85ef6848a36eee7621f8cfb280e4','文章标签：7','2024-01-14 17:28:27'),('9add5e4f37eaa469ea89855ca84f7331','文章标签：3','2024-01-14 16:32:10'),('aef959dd00cc898dde0ff53113aa658e','123123','2024-01-13 20:46:28'),('b17ff94217243fd433d02e838d8e1e4b','文章标签：3','2024-01-14 17:28:27'),('b3a745e2400847b34324536e4f98070e','文章标签：5','2024-01-15 22:11:42'),('b3d8a749b63b4f6a115500e81f30db12','文章标签：6','2024-01-14 17:28:27'),('bd227d4ab0c915d5c845d633ef2118ef','文章标签：9','2024-01-14 16:32:10'),('c2855f1a7da1e0586d0803cfe28ea3ba','文章标签：1','2024-01-14 17:28:27'),('c5087697afa8532febf20158cb41f2fc','文章标签：4','2024-01-14 16:32:10'),('c55fd3f983c448f6768f4f977dd05f57','文章标签：6','2024-01-14 16:32:10'),('e55bcf1c182aa67c42c00f2b5a83f037','文章标签：9','2024-01-15 22:11:42'),('e576588f2a50c6b0d2584c664f2a8666','文章标签：4','2024-01-15 22:11:42'),('ec955f0739ddaa0cdae213a4554fbb7f','文章标签：1','2024-01-15 22:11:42'),('ef4ba8030b7870fa2aa699f2ab596783','文章标签：1','2024-01-14 16:32:10'),('f0f7235136895d8122474128fd09f946','文章标签：5','2024-01-14 17:28:27'),('f135d972a8e7ab6152bd0273acb05307','文章标签：9','2024-01-14 17:28:27');
/*!40000 ALTER TABLE `article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_tag_list`
--

DROP TABLE IF EXISTS `article_tag_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_tag_list` (
  `article_tag_list_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章对应标签ID',
  `article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章ID',
  `article_tag_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章标签ID',
  PRIMARY KEY (`article_tag_list_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章对应标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_tag_list`
--

LOCK TABLES `article_tag_list` WRITE;
/*!40000 ALTER TABLE `article_tag_list` DISABLE KEYS */;
INSERT INTO `article_tag_list` VALUES ('018a961b9aa88107bd30f45ab7d61dd5','cn.hutool.crypto.digest.MD5@4a50d04a','e576588f2a50c6b0d2584c664f2a8666'),('04642d576a7ac6415420fe058c2cb445','cn.hutool.crypto.digest.MD5@3aed69dd','022ddaa2bea63a7fd8aac6000dee07ed'),('08297696354b3396125a79346c6b4f41','cn.hutool.crypto.digest.MD5@3d620a1','65a079090ca105ee8ab0af39a46fdb2e'),('0a156de7b8030c69fd38c4bf66ad3f88','cn.hutool.crypto.digest.MD5@6393bf8b','636a0534653b0d6965745866ef0c5ab0'),('0e7b4bea6a221a9558889350af9bef47','cn.hutool.crypto.digest.MD5@73c31181','7e529fad66d385703ab14e6c4f461073'),('0ea150f66c90cdc95692d061f6fda1e7','cn.hutool.crypto.digest.MD5@38f981b6','8a9f85ef6848a36eee7621f8cfb280e4'),('0f12483ef981532821d8538bafefd7f2','cn.hutool.crypto.digest.MD5@3e33b52e','141cfdf703863e60e04a8aedf78eb219'),('1743eddbf0d4cd0252482f71da63a445','cn.hutool.crypto.digest.MD5@5ff6dd3c','b17ff94217243fd433d02e838d8e1e4b'),('1873c16f5b204ffb29fe6a3d1a83be79','cn.hutool.crypto.digest.MD5@ec7b5de','bd227d4ab0c915d5c845d633ef2118ef'),('18d44ea07870e00ca0ea291d82deca00','cn.hutool.crypto.digest.MD5@500051c5','c55fd3f983c448f6768f4f977dd05f57'),('1d272696d06af700231528cfbb7f7b83','cn.hutool.crypto.digest.MD5@3aed69dd','f0f7235136895d8122474128fd09f946'),('1f2969cb3a05a2cb3f0aa41dfa09780d','cn.hutool.crypto.digest.MD5@5ff6dd3c','022ddaa2bea63a7fd8aac6000dee07ed'),('25b7ed3e153e10de19e0ea6e67de4154','cn.hutool.crypto.digest.MD5@5cd96b41','ec955f0739ddaa0cdae213a4554fbb7f'),('291b85bc191535adcf907eda226c2c8d','cn.hutool.crypto.digest.MD5@73c31181','079961d15d5f69e9e126ad33c986ad13'),('291e375d8c6ad76e851cff8da9cd1394','cn.hutool.crypto.digest.MD5@5529ff44','7e529fad66d385703ab14e6c4f461073'),('2b10ca73bbed0b3156f9787d51cf550d','cn.hutool.crypto.digest.MD5@76d7881e','ef4ba8030b7870fa2aa699f2ab596783'),('2d2ff28b7ee55e2970e884832f2596ca','cn.hutool.crypto.digest.MD5@4d09cade','c5087697afa8532febf20158cb41f2fc'),('2fd3122c0704ad62f404aa4cd616bbca','cn.hutool.crypto.digest.MD5@65629ac6','ef4ba8030b7870fa2aa699f2ab596783'),('318a922547df827f8da3ba5eea1c0071','cn.hutool.crypto.digest.MD5@6d672bd4','e55bcf1c182aa67c42c00f2b5a83f037'),('31c042997f635dc9079d0d17099354a8','cn.hutool.crypto.digest.MD5@4a50d04a','b3a745e2400847b34324536e4f98070e'),('34ac3e80c343b12462d7abb8a6ab3dac','cn.hutool.crypto.digest.MD5@373e6b9d','9add5e4f37eaa469ea89855ca84f7331'),('37cfff7a1f321fb8a4beefd0627ec287','cn.hutool.crypto.digest.MD5@5eabff6b','079961d15d5f69e9e126ad33c986ad13'),('3beddfb79980f14d30668a067714b5de','cn.hutool.crypto.digest.MD5@38f981b6','022ddaa2bea63a7fd8aac6000dee07ed'),('425ff8b31a246438649284f4b9af8492','cn.hutool.crypto.digest.MD5@3e33b52e','b3d8a749b63b4f6a115500e81f30db12'),('426745a7f014fe4417805a65f3677bf6','cn.hutool.crypto.digest.MD5@3e33b52e','4028bc80ce846449ba74719a8d4c6dca'),('44727c3946a472fbb9e1b52c1184456e','cn.hutool.crypto.digest.MD5@7e1ffe70','e576588f2a50c6b0d2584c664f2a8666'),('466f1cb627dc28c2cdd6e9a595a672a0','cn.hutool.crypto.digest.MD5@4f486211','7e529fad66d385703ab14e6c4f461073'),('480d7c898881405da5562f27aa062566','cn.hutool.crypto.digest.MD5@307e4c44','4b4a4f4682290e837a7727af0538670f'),('49d2d9171a3c253b05402a01bf4625bf','cn.hutool.crypto.digest.MD5@7e1ffe70','e55bcf1c182aa67c42c00f2b5a83f037'),('4cc3a9295970f4a024a8376f51e1097f','cn.hutool.crypto.digest.MD5@4aa31ffc','4b4a4f4682290e837a7727af0538670f'),('4e1f73943de4f23690f0a8b7971f138d','cn.hutool.crypto.digest.MD5@5eabff6b','6f1d1e7d4f7d9b69728362abfa494ff2'),('54b86cb44f5d11bd699668e4124f67e1','cn.hutool.crypto.digest.MD5@4b862408','636a0534653b0d6965745866ef0c5ab0'),('59739fbf583ef016c3bd12b033f60703','cn.hutool.crypto.digest.MD5@76d7881e','c55fd3f983c448f6768f4f977dd05f57'),('5deaef93ca6dfffba671f36325b546e3','cn.hutool.crypto.digest.MD5@5ff6dd3c','4028bc80ce846449ba74719a8d4c6dca'),('6043b61495d66d324ebee4a36f58aefe','cn.hutool.crypto.digest.MD5@3aed69dd','c2855f1a7da1e0586d0803cfe28ea3ba'),('635b3bc1061f84dc1296931f7ca96302','cn.hutool.crypto.digest.MD5@76d7881e','636a0534653b0d6965745866ef0c5ab0'),('6673784e4a9615855b1a2cf07f29dc6d','cn.hutool.crypto.digest.MD5@5529ff44','b3a745e2400847b34324536e4f98070e'),('6b59b017c97447067ffab078856ebaab','cn.hutool.crypto.digest.MD5@52454457','f135d972a8e7ab6152bd0273acb05307'),('72850bb5a68af8ad7903fd0e91dcd1b4','cn.hutool.crypto.digest.MD5@61ff6a49','f135d972a8e7ab6152bd0273acb05307'),('72ad09c8a1825545e6298cca8bab104f','cn.hutool.crypto.digest.MD5@7d42542','2cbf428f227141632c70293e2333ca51'),('72d16baa457196f0dab587ed45cc28d6','cn.hutool.crypto.digest.MD5@373e6b9d','bd227d4ab0c915d5c845d633ef2118ef'),('7309f12bf71969eabe7878838a1e2ea4','cn.hutool.crypto.digest.MD5@5529ff44','e55bcf1c182aa67c42c00f2b5a83f037'),('738f184a1ac5c159e18032b9d3ef1d8d','cn.hutool.crypto.digest.MD5@65629ac6','c5087697afa8532febf20158cb41f2fc'),('78535a70b8b70ac232d28d9167d29cff','cn.hutool.crypto.digest.MD5@14447be','62faa91dd5ab7d6bf0509dbc8b9ccb65'),('7ac590cece3e2f5a36127ba7bc3c7e93','cn.hutool.crypto.digest.MD5@7741d346','141cfdf703863e60e04a8aedf78eb219'),('7b11092c901802f2ff934a746eb418f3','cn.hutool.crypto.digest.MD5@3e33b52e','f0f7235136895d8122474128fd09f946'),('82e0f9d1053f267e2368c18d7528d740','cn.hutool.crypto.digest.MD5@574059d5','65a079090ca105ee8ab0af39a46fdb2e'),('838070441d425f238a6d5afbd9202c6f','cn.hutool.crypto.digest.MD5@3e33b52e','4028bc80ce846449ba74719a8d4c6dca'),('8b246e02388297926baf6b4f88a3ec4f','cn.hutool.crypto.digest.MD5@72906e','62faa91dd5ab7d6bf0509dbc8b9ccb65'),('8b279db0de2a83d978efdb7568b90d4a','cn.hutool.crypto.digest.MD5@76d7881e','4b4a4f4682290e837a7727af0538670f'),('8f0df910e4626eeb8b77e4732a6dbf3a','cn.hutool.crypto.digest.MD5@5ff6dd3c','f135d972a8e7ab6152bd0273acb05307'),('91adb79a01aa899d0e27f011ffa244fc','cn.hutool.crypto.digest.MD5@73041b7d','0a5a5b4a23d3d1a2c682b3241b73fe71'),('946192656c9271f6cabbac2770f4e79d','cn.hutool.crypto.digest.MD5@52454457','5b9f9b87cded125857847db59f03688c'),('9850e24b7a59f69373eabfa91568f8f0','cn.hutool.crypto.digest.MD5@61ff6a49','f0f7235136895d8122474128fd09f946'),('98935752de5e38e962022337cc78eb2c','cn.hutool.crypto.digest.MD5@4c6a4ffd','b3d8a749b63b4f6a115500e81f30db12'),('99779ffd1f9e00614c476bdbbd71c90a','cn.hutool.crypto.digest.MD5@73041b7d','e576588f2a50c6b0d2584c664f2a8666'),('9fe50e24dd100f9ada31b9926f0a8c23','cn.hutool.crypto.digest.MD5@373e6b9d','7443899466400726e5e00171e9b850b1'),('a0ff3e92e6b4713dcd9c1f5405c71f01','cn.hutool.crypto.digest.MD5@14447be','6f1d1e7d4f7d9b69728362abfa494ff2'),('a8d44127464b2eaa19d1b39cd59d0e8c','cn.hutool.crypto.digest.MD5@3ab35b9c','b17ff94217243fd433d02e838d8e1e4b'),('aa1b1e76ebd005b3ca427e7dfd540fbb','cn.hutool.crypto.digest.MD5@307e4c44','429a68b9327cbab47cf4e89588985295'),('b2b6a9c104e22900b2be8e64028da0f4','cn.hutool.crypto.digest.MD5@4cc65c2','62faa91dd5ab7d6bf0509dbc8b9ccb65'),('b3291f9ba1c77843d06b058846303af9','cn.hutool.crypto.digest.MD5@76d7881e','2cbf428f227141632c70293e2333ca51'),('b3ad1baad864005d4e1ffc32b5e28dab','cn.hutool.crypto.digest.MD5@5ff6dd3c','b3d8a749b63b4f6a115500e81f30db12'),('b59e97b3d4f91839a31e4cd99c497217','cn.hutool.crypto.digest.MD5@4d09cade','bd227d4ab0c915d5c845d633ef2118ef'),('b90caf17f2e54b4f4677c2600d468260','cn.hutool.crypto.digest.MD5@4aa2877c','9add5e4f37eaa469ea89855ca84f7331'),('bd2e6a0594bf2f1f40a13f850202bd4d','cn.hutool.crypto.digest.MD5@15d0d6c9','141cfdf703863e60e04a8aedf78eb219'),('be6f7ccafb68759debb8c1f3f480e34e','cn.hutool.crypto.digest.MD5@7945b206','429a68b9327cbab47cf4e89588985295'),('c092402a946ad0759ba0708202cce6e9','cn.hutool.crypto.digest.MD5@3ab35b9c','c2855f1a7da1e0586d0803cfe28ea3ba'),('c1f58bb0aaa5950cc62685abd197d79b','cn.hutool.crypto.digest.MD5@ec7b5de','c55fd3f983c448f6768f4f977dd05f57'),('c2cacfcb2a7e0d1ad274683ad9854a6d','cn.hutool.crypto.digest.MD5@5099c59b','0a5a5b4a23d3d1a2c682b3241b73fe71'),('c5f54fa0e8a695101713d5de64a38e95','cn.hutool.crypto.digest.MD5@307e4c44','2cbf428f227141632c70293e2333ca51'),('c950cca44493fca467208dbae17fcac9','cn.hutool.crypto.digest.MD5@4aa2877c','ef4ba8030b7870fa2aa699f2ab596783'),('cecabff6d542560f8dbe57c7988785c4','cn.hutool.crypto.digest.MD5@3e33b52e','8a9f85ef6848a36eee7621f8cfb280e4'),('cfed49019109772703ddf0c82111711e','cn.hutool.crypto.digest.MD5@2287395','c2855f1a7da1e0586d0803cfe28ea3ba'),('d1b50f29d3c4245e9ab7e9bc9eaef9db','cn.hutool.crypto.digest.MD5@65629ac6','7443899466400726e5e00171e9b850b1'),('d1d18b86c128f0d2b6299016710f2bc2','cn.hutool.crypto.digest.MD5@7d42542','9add5e4f37eaa469ea89855ca84f7331'),('d37325cfa5dd74e0ac4e58e2b22d4c54','cn.hutool.crypto.digest.MD5@7e1ffe70','0a5a5b4a23d3d1a2c682b3241b73fe71'),('d65ea2ce8bd8d30738439d20f749c524','cn.hutool.crypto.digest.MD5@6393bf8b','429a68b9327cbab47cf4e89588985295'),('d77c29cd825cd8bd068dc6c540340a5f','cn.hutool.crypto.digest.MD5@3e33b52e','5b9f9b87cded125857847db59f03688c'),('d79e700fe57b73c4e36a88d0af6e305d','cn.hutool.crypto.digest.MD5@5a1f778','ec955f0739ddaa0cdae213a4554fbb7f'),('db5841f755f899d49ed645a37fbb1da6','cn.hutool.crypto.digest.MD5@1ca3d25b','5b9f9b87cded125857847db59f03688c'),('dd3d5913ae63b1cab8d0782c5c569b38','cn.hutool.crypto.digest.MD5@4f486211','6f1d1e7d4f7d9b69728362abfa494ff2'),('e51bf35cee36d0db378d8f3cff6564c5','cn.hutool.crypto.digest.MD5@5a1f778','65a079090ca105ee8ab0af39a46fdb2e'),('e61205bc607c571e02a22ef8a5950bd6','cn.hutool.crypto.digest.MD5@5cd96b41','079961d15d5f69e9e126ad33c986ad13'),('e94c5b63779c16bae35ad8dfddcbfcc3','cn.hutool.crypto.digest.MD5@4b862408','c5087697afa8532febf20158cb41f2fc'),('e9e2386267eefbf14105b10c75f1546a','cn.hutool.crypto.digest.MD5@3aed69dd','8a9f85ef6848a36eee7621f8cfb280e4'),('ea7582e8cce7bf1f68271c68469de3ff','cn.hutool.crypto.digest.MD5@5ff6dd3c','b17ff94217243fd433d02e838d8e1e4b'),('f1ea8643ca8f512e1189d6f9b0658b4a','cn.hutool.crypto.digest.MD5@4aa31ffc','7443899466400726e5e00171e9b850b1'),('f92eca06b088794e8da98803c9c6065c','cn.hutool.crypto.digest.MD5@5099c59b','ec955f0739ddaa0cdae213a4554fbb7f'),('fe52b312cce190a783092e93654360a3','cn.hutool.crypto.digest.MD5@67770b37','b3a745e2400847b34324536e4f98070e');
/*!40000 ALTER TABLE `article_tag_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_type`
--

DROP TABLE IF EXISTS `article_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_type` (
  `article_type_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章分类ID',
  `article_type_parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章分类父ID',
  `article_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章分类名称',
  `article_type_sort` int DEFAULT NULL COMMENT '文章分类排序，越小越靠前',
  `article_type_add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`article_type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章分类i';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_type`
--

LOCK TABLES `article_type` WRITE;
/*!40000 ALTER TABLE `article_type` DISABLE KEYS */;
INSERT INTO `article_type` VALUES ('18f4b2d41b944252ac5f970aa234d348','3b52dbbdad61b445663361ebf8f467ad','vue',40,'2022-01-03 10:08:31'),('1c2ab73282ada9223fe68b3a09415cb9','','游戏',20,'2022-01-03 10:07:16'),('1d26965e15911c3e020208245e9d8e7e',NULL,'文章分类0',10,'2024-01-14 17:28:26'),('2b897e397c6a058507f66d3b8ee27597','449333fdae67b307a71cf96caf52b0fa','java',0,'2022-01-03 10:07:33'),('33369b86e2349bc18f83c84d464b2f1b',NULL,'文章分类2',10,'2024-01-14 17:28:26'),('3b52dbbdad61b445663361ebf8f467ad','','前端',10,'2021-12-23 21:26:27'),('42244994d4f68b3a0217e4531b229e7e',NULL,'文章分类1',10,'2024-01-15 22:11:40'),('449333fdae67b307a71cf96caf52b0fa','','后端',0,'2022-01-03 10:07:05'),('533d2f2d20b4c5eb3247f0cca1d2b4d0','1c2ab73282ada9223fe68b3a09415cb9','方舟生存进化',0,'2022-01-03 10:08:45'),('5db9f9c18a9b5fa3afcf20e564eab2e5','','生活',30,'2022-01-03 10:07:22'),('611bb6ed477d06bedb771b6f3b2380b9','1c2ab73282ada9223fe68b3a09415cb9','英雄联盟',10,'2022-01-03 10:09:01'),('664b900047cf47b4775338032e83d444','449333fdae67b307a71cf96caf52b0fa','c++',10,'2022-01-03 10:07:43'),('73a9ec302e7c76e0607da916f490bfec',NULL,'文章分类1',10,'2024-01-15 22:24:18'),('7538fc44439f419569ee2b3831a58f66',NULL,'文章分类2',10,'2024-01-15 22:11:40'),('76e3f123bf157c34c8c792fded17c2eb',NULL,'文章分类1',10,'2024-01-14 16:32:09'),('7f8cec327b7b1ae8a847454f020b3ace',NULL,'文章分类0',10,'2024-01-15 22:09:44'),('aa3c0f40618a7c6f3adc1d2ef78c2ffc',NULL,'文章分类0',10,'2024-01-14 16:32:09'),('ab6fa67c2ba644440f3b361050800547','3b52dbbdad61b445663361ebf8f467ad','css',10,'2022-01-03 10:08:04'),('b05971db80e2142930e9407a1da527e5',NULL,'文章分类1',10,'2024-01-15 22:09:44'),('b2b4b05192a560a56e35fd8426e23ca0',NULL,'文章分类0',10,'2024-01-15 22:11:40'),('b59233147619026b7394dd80594ba26c','5db9f9c18a9b5fa3afcf20e564eab2e5','我是穷逼',10,'2022-01-06 15:44:03'),('c48f06e34824bcf91483b59b45770098',NULL,'文章分类0',10,'2024-01-15 22:24:18'),('cd828766c007af527ea9f57213d4689b',NULL,'文章分类2',10,'2024-01-14 16:32:09'),('cedb98aa9e90fe95d1de6c5c666f210e','3b52dbbdad61b445663361ebf8f467ad','javascript',20,'2022-01-03 10:08:14'),('ddbd9f589fa8881b6d7dfce5836bac94',NULL,'文章分类2',10,'2024-01-15 22:24:18'),('f21998f1d4bd8593a178317cff1e2eb6','3b52dbbdad61b445663361ebf8f467ad','jquery',30,'2022-01-03 10:08:22'),('f6f10439ba39da2430081eb89460db9d','3b52dbbdad61b445663361ebf8f467ad','html',0,'2022-01-03 10:07:56'),('f7bad22dca445d465824cd61ecfc2819',NULL,'文章分类1',10,'2024-01-14 17:28:26'),('f9e0747d6c3396590bd382186e0d48e5',NULL,'文章分类2',10,'2024-01-15 22:09:44');
/*!40000 ALTER TABLE `article_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章评论ID',
  `article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章ID',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ID（评论人）',
  `comment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章评论内容',
  `comment_time` datetime DEFAULT NULL COMMENT '评论时间',
  `comment_good_number` int DEFAULT NULL COMMENT '点赞次数',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_reply`
--

DROP TABLE IF EXISTS `comment_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_reply` (
  `comment_reply_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论回复ID',
  `comment_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论ID',
  `reply_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回复评论的人ID',
  `reply_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论回复内容',
  `secondly_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '继续回复的人ID',
  `comment_reply_time` datetime DEFAULT NULL COMMENT '评论回复的时间',
  PRIMARY KEY (`comment_reply_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='评论回复';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_reply`
--

LOCK TABLES `comment_reply` WRITE;
/*!40000 ALTER TABLE `comment_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_link`
--

DROP TABLE IF EXISTS `friend_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_link` (
  `link_id` varchar(255) NOT NULL COMMENT '友情链接ID',
  `link_url` varchar(255) DEFAULT NULL COMMENT '友情链接地址',
  `link_title` varchar(255) DEFAULT NULL COMMENT '友情链接标题',
  `link_logo_url` varchar(255) DEFAULT NULL COMMENT '友情链接logo url',
  `link_sort` int DEFAULT NULL COMMENT '友情链接的排序，越小越靠前',
  `link_add_time` datetime DEFAULT NULL COMMENT '添加友情链接的时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_link`
--

LOCK TABLES `friend_link` WRITE;
/*!40000 ALTER TABLE `friend_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link`
--

DROP TABLE IF EXISTS `link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `link` (
  `link_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '友情连接id',
  `link_title` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '友情连接标题',
  `link_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '友情连接的地址',
  `link_logo_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '友情连接logo',
  `link_sort` int unsigned DEFAULT '10' COMMENT '友情连接排序，越小越考前',
  `link_add_time` datetime DEFAULT NULL COMMENT '添加友情连接的时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='友情连接';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link`
--

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
INSERT INTO `link` VALUES ('2698265b7ae39dc59d3cdff5c5d6dd06','百度','https://www.baidu.com','https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png',2,'2021-12-25 10:22:08'),('5395b905b2b73e791a2fbba044dec51c','阿里矢量图标库','https://www.iconfont.cn/','https://img.alicdn.com/imgextra/i3/O1CN01Mn65HV1FfSEzR6DKv_!!6000000000514-55-tps-228-59.svg',5,'2021-12-25 10:31:29');
/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unknn`
--

DROP TABLE IF EXISTS `unknn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unknn` (
  `unknn_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员ID',
  `unknn_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '管理员名称',
  `unknn_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`unknn_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unknn`
--

LOCK TABLES `unknn` WRITE;
/*!40000 ALTER TABLE `unknn` DISABLE KEYS */;
INSERT INTO `unknn` VALUES ('0634d2e3b20b11eeae9ac01803840418','UNKNNOW','0ea52f36cc99131bdeaf2bc9ac1ca915');
/*!40000 ALTER TABLE `unknn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upload_file_list`
--

DROP TABLE IF EXISTS `upload_file_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upload_file_list` (
  `upload_file_list_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上传文件列表ID',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小，作为文件唯一标识',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件路径url',
  `upload_file_time` datetime DEFAULT NULL COMMENT '文件上传时间',
  PRIMARY KEY (`upload_file_list_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upload_file_list`
--

LOCK TABLES `upload_file_list` WRITE;
/*!40000 ALTER TABLE `upload_file_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `upload_file_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密码',
  `user_register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `user_status` int DEFAULT NULL COMMENT '是否冻结，1正常，0冻结（冻结后无法登陆）',
  `user_publishable` int DEFAULT NULL COMMENT '是否可以发布文章 0不能，1可以发布',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0eaf1e5f28c24532976da41d0c58e621','test01','8a41a93366facb8185bcf0f2ac0fe9ce','2024-01-13 19:18:19',1,1),('20486e76a7a04b6db061a2e933bd1708','1111111','6571f5f1744f78f3ca94b6e79f80afa0','2024-01-15 22:37:14',1,1),('5a7cce4aec2b4c558e4f526c072922ad','456456','27221eebbd290efb1dea9805e3661b93','2024-01-20 11:19:50',1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_collection_article`
--

DROP TABLE IF EXISTS `user_collection_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_collection_article` (
  `user_collection_article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户收藏的文章ID',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ID',
  `article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章ID',
  `user_collection_article_time` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`user_collection_article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户收藏的文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collection_article`
--

LOCK TABLES `user_collection_article` WRITE;
/*!40000 ALTER TABLE `user_collection_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_collection_article` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-04 15:54:15
