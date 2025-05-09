-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DROP DATABASE IF EXISTS`restaurante2`;
create database if not exists `restaurante2` ;
use `restaurante2`;

-- -----------------------------------------------------
-- Table `cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cliente` ;

CREATE TABLE IF NOT EXISTS `cliente` (
  `dni` VARCHAR(8) NOT NULL,
  `nombre` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`dni`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `restaurante`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurante` ;

CREATE TABLE IF NOT EXISTS `restaurante` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ruc` VARCHAR(15) CHARACTER SET 'utf8mb3' NOT NULL,
  `nombre` VARCHAR(255) CHARACTER SET 'utf8mb3' NOT NULL,
  `telefono` VARCHAR(11) CHARACTER SET 'utf8mb3' NOT NULL,
  `direccion` VARCHAR(100) CHARACTER SET 'utf8mb3' NOT NULL,
  `mensaje` VARCHAR(255) CHARACTER SET 'utf8mb3' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `sala`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sala` ;

CREATE TABLE IF NOT EXISTS `sala` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) CHARACTER SET 'utf8mb3' NOT NULL,
  `mesas` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `usuario` ;

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(200) CHARACTER SET 'utf8mb3' NOT NULL,
  `correo` VARCHAR(200) CHARACTER SET 'utf8mb3' NOT NULL,
  `pass` VARCHAR(50) CHARACTER SET 'utf8mb3' NOT NULL,
  `rol` VARCHAR(20) CHARACTER SET 'utf8mb3' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `restaurante`.`pedido`
-- -----------------------------------------------------
/*TRUNCATE TABLE `pedido`;*/
DROP TABLE IF EXISTS `pedido` ;

CREATE TABLE IF NOT EXISTS `pedido` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `id_sala` INT NOT NULL,
  `id_cliente` VARCHAR(8) NOT NULL,
  `num_mesa` INT NOT NULL,
  `fecha` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `total` DECIMAL(10,2) NOT NULL,
  `estado` ENUM('PENDIENTE', 'FINALIZADO') CHARACTER SET 'utf8mb3' NOT NULL DEFAULT 'PENDIENTE',
  PRIMARY KEY (`id`),
  INDEX `id_sala` (`id_sala` ASC) VISIBLE,
  INDEX `fk_pedidos_usuarios1_idx` (`id_usuario` ASC) VISIBLE,
  INDEX `fk_pedido_cliente1_idx` (`id_cliente` ASC) VISIBLE,
  CONSTRAINT `pedidos_ibfk_1`
    FOREIGN KEY (`id_sala`)
    REFERENCES `sala` (`id`),
  CONSTRAINT `fk_pedidos_usuarios1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_cliente1`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `cliente` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `restaurante`.`plato`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `plato` ;

CREATE TABLE IF NOT EXISTS `plato` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(200) CHARACTER SET 'utf8mb3' NOT NULL,
  `precio` DECIMAL(10,2) NOT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  `activo` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `detalle_pedido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `detalle_pedido` ;

CREATE TABLE IF NOT EXISTS `detalle_pedido` (
  `id_pedido` INT NOT NULL,
  `id_plato` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `comentario` VARCHAR(250) NULL,
  PRIMARY KEY (`id_pedido`, `id_plato`),
  INDEX `fk_pedidos_has_platos_platos1_idx` (`id_plato` ASC) VISIBLE,
  INDEX `fk_pedidos_has_platos_pedidos1_idx` (`id_pedido` ASC) VISIBLE,
  CONSTRAINT `fk_pedidos_has_platos_pedidos1`
    FOREIGN KEY (`id_pedido`)
    REFERENCES `pedido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedidos_has_platos_platos1`
    FOREIGN KEY (`id_plato`)
    REFERENCES `restaurante`.`plato` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Inserts 
-- -----------------------------------------------------
INSERT INTO `usuario` VALUES (1,'VICTOR GUTIERREZ','gutie001@hotmail.com','admin','Administrador');
INSERT INTO `cliente` VALUES ('44213735','VICTOR ERNESTO GUTIERREZ BIROT'),('46788638','JOSE WILDER FUSTAMANTE BENAVIDES');
INSERT INTO `sala` VALUES (1,'SALA PRINCIPAL',15),(2,'SEGUNDO PISO',10);
INSERT INTO `plato` VALUES (1,'ARROZ CON POLLO',18.00,'2025-04-17',1),(2,'ARROZ CHAUFA',22.00,'2025-04-17',1),(3,'LOMO SALTADO',15.00,'2025-04-17',1),(4,'GASEOSA COCA COLA 1.5 LITROS',8.00,'2025-04-17',1);
INSERT INTO `pedido` (`id_usuario`,`id_sala`,`id_cliente`,`num_mesa`,`fecha`,`total`,`estado`) VALUES (1,1,'44213735',12,'2025-04-18 12:31:52',78.00,'FINALIZADO'),(1,2,'46788638', 10,'2022-04-18 12:45:20',23.00,'FINALIZADO'),(1,2,'44213735',8,'2025-04-19 13:11:52',78.00,'PENDIENTE'),(1,2,'46788638', 2,'2022-04-19 12:45:20',68.00,'PENDIENTE');
INSERT INTO `detalle_pedido` (`id_pedido`,`id_plato`,`cantidad`,`comentario`) VALUES (1,1,2,''),(1,2,1,'POLLO EXTRA'),(1,4,1,''),(2,1,1,''),(2,3,1,'CARNE TERMINO MEDIO'),(2,4,2,'CON HIELO'),(3,1,2,''),(3,2,2,''),(3,4,1,''),(4,1,1,''),(4,3,1,'CARNE TERMINO MEDIO'),(4,4,2,'CON HIELO');


