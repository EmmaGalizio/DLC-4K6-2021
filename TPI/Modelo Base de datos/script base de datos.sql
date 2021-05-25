-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema TPI_DLC_SearchEngine_Posteos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TPI_DLC_SearchEngine_Posteos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TPI_DLC_SearchEngine_Posteos` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `TPI_DLC_SearchEngine_Posteos` ;

-- -----------------------------------------------------
-- Table `TPI_DLC_SearchEngine_Posteos`.`documentoIndexado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TPI_DLC_SearchEngine_Posteos`.`documentoIndexado` (
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`url`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TPI_DLC_SearchEngine_Posteos`.`vocabulary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TPI_DLC_SearchEngine_Posteos`.`vocabulary` (
  `token` VARCHAR(100) NOT NULL,
  `maxTf` INT NOT NULL,
  `nr` INT NOT NULL,
  PRIMARY KEY (`token`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TPI_DLC_SearchEngine_Posteos`.`posteo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TPI_DLC_SearchEngine_Posteos`.`posteo` (
  `termino` VARCHAR(100) NOT NULL,
  `documento` VARCHAR(255) NOT NULL,
  `tf` INT NOT NULL,
  PRIMARY KEY (`termino`, `documento`),
  INDEX `posteo_documento_idx` (`documento` ASC) VISIBLE,
  CONSTRAINT `posteo_documento`
    FOREIGN KEY (`documento`)
    REFERENCES `TPI_DLC_SearchEngine_Posteos`.`documentoIndexado` (`url`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `posteo_vocabulary_fk`
    FOREIGN KEY (`termino`)
    REFERENCES `TPI_DLC_SearchEngine_Posteos`.`vocabulary` (`token`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
