SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cs_dough_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cs_dough_db` DEFAULT CHARACTER SET utf8 ;
USE `cs_dough_db` ;

-- -----------------------------------------------------
-- Table `cs_dough_db`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`team` (
  `team_id` INT(11) NOT NULL,
  `team_logo` BLOB NULL DEFAULT NULL,
  `team_name` VARCHAR(45) NOT NULL,
  `total_points` INT(11) NOT NULL,
  PRIMARY KEY (`team_id`),
  UNIQUE INDEX `team_id_UNIQUE` (`team_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`wallet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`wallet` (
  `wallet_id` INT(11) NOT NULL AUTO_INCREMENT,
  `currency` ENUM('EUR', 'USD', 'BYN') NOT NULL,
  `balance` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`wallet_id`),
  UNIQUE INDEX `wallet_id_UNIQUE` (`wallet_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`user_account` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `account_avatar` BLOB NULL DEFAULT NULL,
  `user_login` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `user_email` VARCHAR(45) NOT NULL,
  `user_role` ENUM('UNCERTAIN', 'PLAYER', 'ORGANIZER', 'CAPTAIN_PLAYER') NOT NULL,
  `wallet_id` INT(11) NOT NULL,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`user_login` ASC) VISIBLE,
  INDEX `wallet_id_idx` (`wallet_id` ASC) VISIBLE,
  CONSTRAINT `wallet_id`
    FOREIGN KEY (`wallet_id`)
    REFERENCES `cs_dough_db`.`wallet` (`wallet_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`organizer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`organizer` (
  `organizer_id` INT(11) NOT NULL AUTO_INCREMENT,
  `organizer_name` VARCHAR(45) NOT NULL,
  `organizer_logo` BLOB NULL DEFAULT NULL,
  `user_account_id` INT(11) NOT NULL,
  PRIMARY KEY (`organizer_id`),
  UNIQUE INDEX `organizer_id_UNIQUE` (`organizer_id` ASC) VISIBLE,
  UNIQUE INDEX `organizer_name_UNIQUE` (`organizer_name` ASC) VISIBLE,
  INDEX `user_account_id_idx` (`user_account_id` ASC) VISIBLE,
  CONSTRAINT `user_account_id`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `cs_dough_db`.`user_account` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`tournament`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`tournament` (
  `tournament_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tournament_name` VARCHAR(45) NOT NULL,
  `tournament_logo` BLOB NULL DEFAULT NULL,
  `prize_pool` INT(11) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `bracket_type` ENUM('PLAY_OFFS', 'TABLE', 'SWISS') NOT NULL,
  `tournament_status` ENUM('UPCOMING', 'REG_IS_OPEN', 'REG_IS_CLOSED', 'ONGOING', 'FINISHED') NOT NULL,
  `organizer_id` INT(11) NOT NULL,
  PRIMARY KEY (`tournament_id`),
  UNIQUE INDEX `tournament_id_UNIQUE` (`tournament_id` ASC) VISIBLE,
  INDEX `org_id_idx` (`organizer_id` ASC) VISIBLE,
  CONSTRAINT `organizer_id`
    FOREIGN KEY (`organizer_id`)
    REFERENCES `cs_dough_db`.`organizer` (`organizer_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`match`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`match` (
  `match_id` INT(11) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `match_type` ENUM('BO1', 'BO3') NOT NULL,
  `first_team_id` INT(11) NOT NULL,
  `second_team_id` INT(11) NOT NULL,
  `tournament_id` INT(11) NOT NULL,
  `winner_team_id` INT(11) NOT NULL,
  PRIMARY KEY (`match_id`),
  UNIQUE INDEX `match_id_UNIQUE` (`match_id` ASC) VISIBLE,
  INDEX `first_team_id_idx` (`first_team_id` ASC) VISIBLE,
  INDEX `tournament_id_idx` (`tournament_id` ASC) VISIBLE,
  INDEX `second_team_id_idx` (`second_team_id` ASC) VISIBLE,
  CONSTRAINT `first_team_id`
    FOREIGN KEY (`first_team_id`)
    REFERENCES `cs_dough_db`.`team` (`team_id`),
  CONSTRAINT `idtournament`
    FOREIGN KEY (`tournament_id`)
    REFERENCES `cs_dough_db`.`tournament` (`tournament_id`),
  CONSTRAINT `second_team_id`
    FOREIGN KEY (`second_team_id`)
    REFERENCES `cs_dough_db`.`team` (`team_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`participants`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`participants` (
  `participants_id` INT(11) NOT NULL,
  `team_id` INT(11) NOT NULL,
  `tournament_id` INT(11) NOT NULL,
  PRIMARY KEY (`participants_id`),
  UNIQUE INDEX `participants_id_UNIQUE` (`participants_id` ASC) VISIBLE,
  INDEX `idteam_idx` (`team_id` ASC) VISIBLE,
  INDEX `tournament_id_idx` (`tournament_id` ASC) VISIBLE,
  CONSTRAINT `idteam`
    FOREIGN KEY (`team_id`)
    REFERENCES `cs_dough_db`.`team` (`team_id`),
  CONSTRAINT `tournament_id`
    FOREIGN KEY (`tournament_id`)
    REFERENCES `cs_dough_db`.`tournament` (`tournament_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`player` (
  `player_id` INT(11) NOT NULL AUTO_INCREMENT,
  `player_name` VARCHAR(45) NULL DEFAULT NULL,
  `player_surname` VARCHAR(45) NULL DEFAULT NULL,
  `player_nickname` VARCHAR(45) NOT NULL,
  `player_photo` BLOB NULL DEFAULT NULL,
  `player_country` VARCHAR(45) NULL DEFAULT NULL,
  `user_account_id` INT(11) NOT NULL,
  `team_id` INT(11) NOT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE INDEX `player_id_UNIQUE` (`player_id` ASC) VISIBLE,
  INDEX `user_account_id_idx` (`user_account_id` ASC) VISIBLE,
  INDEX `team_id_idx` (`team_id` ASC) VISIBLE,
  CONSTRAINT `team_id`
    FOREIGN KEY (`team_id`)
    REFERENCES `cs_dough_db`.`team` (`team_id`),
  CONSTRAINT `user_acc_id`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `cs_dough_db`.`user_account` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
