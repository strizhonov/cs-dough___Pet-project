SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cs_dough_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cs_dough_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cs_dough_db` DEFAULT CHARACTER SET utf8 ;
USE `cs_dough_db` ;

-- -----------------------------------------------------
-- Table `cs_dough_db`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`user_account` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `account_avatar` LONGBLOB NULL DEFAULT NULL,
  `username` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `password_key` VARCHAR(145) NOT NULL,
  `user_email` VARCHAR(45) NOT NULL,
  `user_type` ENUM('USER', 'ADMIN') NULL DEFAULT 'USER',
  `lang` ENUM('EN', 'RU') NULL DEFAULT 'EN',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 64
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`player` (
  `player_id` INT(11) NOT NULL AUTO_INCREMENT,
  `player_photo` LONGBLOB NULL DEFAULT NULL,
  `player_nickname` VARCHAR(45) NOT NULL,
  `player_name` VARCHAR(45) NULL DEFAULT NULL,
  `player_surname` VARCHAR(45) NULL DEFAULT NULL,
  `total_won` DECIMAL(10,0) NULL DEFAULT '0',
  `user_account_id` INT(11) NOT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE INDEX `player_id_UNIQUE` (`player_id` ASC) VISIBLE,
  UNIQUE INDEX `player_nickname_UNIQUE` (`player_nickname` ASC) VISIBLE,
  UNIQUE INDEX `user_account_id_UNIQUE` (`user_account_id` ASC) VISIBLE,
  INDEX `user_account_id_idx` (`user_account_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `cs_dough_db`.`user_account` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 47
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`organizer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`organizer` (
  `organizer_id` INT(11) NOT NULL AUTO_INCREMENT,
  `organizer_logo` LONGBLOB NULL DEFAULT NULL,
  `organizer_name` VARCHAR(45) NOT NULL,
  `user_account_id` INT(11) NOT NULL,
  PRIMARY KEY (`organizer_id`),
  UNIQUE INDEX `organizer_id_UNIQUE` (`organizer_id` ASC) VISIBLE,
  UNIQUE INDEX `organizer_name_UNIQUE` (`organizer_name` ASC) VISIBLE,
  UNIQUE INDEX `user_account_id_UNIQUE` (`user_account_id` ASC) VISIBLE,
  INDEX `user_account_id_idx` (`user_account_id` ASC) VISIBLE,
  CONSTRAINT `user_acc_id`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `cs_dough_db`.`user_account` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`tournament`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`tournament` (
  `tournament_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tournament_logo` LONGBLOB NULL DEFAULT NULL,
  `tournament_name` VARCHAR(45) NOT NULL,
  `prize_pool` DECIMAL(10,0) NULL DEFAULT '0',
  `organizer_reward_rate` DECIMAL(10,0) NULL DEFAULT '0',
  `buy_in` DECIMAL(10,0) NULL DEFAULT '0',
  `participants_number` INT(11) NULL DEFAULT '0',
  `tournament_status` ENUM('UPCOMING', 'ONGOING', 'FINISHED') NULL DEFAULT 'UPCOMING',
  `organizer_id` INT(11) NOT NULL,
  PRIMARY KEY (`tournament_id`),
  UNIQUE INDEX `tournament_id_UNIQUE` (`tournament_id` ASC) VISIBLE,
  UNIQUE INDEX `tournament_name_UNIQUE` (`tournament_name` ASC) VISIBLE,
  INDEX `org_id_idx` (`organizer_id` ASC) VISIBLE,
  CONSTRAINT `organizer_id`
    FOREIGN KEY (`organizer_id`)
    REFERENCES `cs_dough_db`.`organizer` (`organizer_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 73
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`game` (
  `game_id` INT(11) NOT NULL AUTO_INCREMENT,
  `bracket_index` INT(11) NOT NULL,
  `start_time` DATETIME NULL DEFAULT NULL,
  `end_time` DATETIME NULL DEFAULT NULL,
  `first_player_id` INT(11) NULL DEFAULT NULL,
  `second_player_id` INT(11) NULL DEFAULT NULL,
  `tournament_id` INT(11) NOT NULL,
  PRIMARY KEY (`game_id`),
  UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC) VISIBLE,
  INDEX `first_player_idx` (`first_player_id` ASC) VISIBLE,
  INDEX `second_player_idx` (`second_player_id` ASC) VISIBLE,
  INDEX `tournament_id_idx` (`tournament_id` ASC) VISIBLE,
  CONSTRAINT `first_player`
    FOREIGN KEY (`first_player_id`)
    REFERENCES `cs_dough_db`.`player` (`player_id`),
  CONSTRAINT `second_player`
    FOREIGN KEY (`second_player_id`)
    REFERENCES `cs_dough_db`.`player` (`player_id`),
  CONSTRAINT `tournament_id`
    FOREIGN KEY (`tournament_id`)
    REFERENCES `cs_dough_db`.`tournament` (`tournament_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 82
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`gameserver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`gameserver` (
  `game_server_id` INT(11) NOT NULL AUTO_INCREMENT,
  `points_to_win` INT(11) NULL DEFAULT '16',
  `player_one_points` INT(11) NULL DEFAULT '0',
  `player_two_points` INT(11) NULL DEFAULT '0',
  `server_path` VARCHAR(70) NULL DEFAULT NULL,
  `game_id` INT(11) NOT NULL,
  PRIMARY KEY (`game_server_id`),
  UNIQUE INDEX `game_server_id_UNIQUE` (`game_server_id` ASC) VISIBLE,
  UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC) VISIBLE,
  INDEX `game_id_idx` (`game_id` ASC) VISIBLE,
  CONSTRAINT `game_id`
    FOREIGN KEY (`game_id`)
    REFERENCES `cs_dough_db`.`game` (`game_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`participant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`participant` (
  `participant_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tournament_id` INT(11) NOT NULL,
  `player_id` INT(11) NOT NULL,
  PRIMARY KEY (`participant_id`),
  UNIQUE INDEX `participants_id_UNIQUE` (`participant_id` ASC) VISIBLE,
  INDEX `player_id_idx` (`player_id` ASC) VISIBLE,
  INDEX `id_of_tournament_idx` (`tournament_id` ASC) VISIBLE,
  CONSTRAINT `id_of_player`
    FOREIGN KEY (`player_id`)
    REFERENCES `cs_dough_db`.`player` (`player_id`),
  CONSTRAINT `id_of_tournament`
    FOREIGN KEY (`tournament_id`)
    REFERENCES `cs_dough_db`.`tournament` (`tournament_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cs_dough_db`.`wallet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cs_dough_db`.`wallet` (
  `wallet_id` INT(11) NOT NULL AUTO_INCREMENT,
  `balance` DECIMAL(10,0) NULL DEFAULT '0',
  `currency` ENUM('EUR', 'USD', 'BYN') NULL DEFAULT 'USD',
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`wallet_id`),
  UNIQUE INDEX `wallet_id_UNIQUE` (`wallet_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `user_act_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_accnt_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cs_dough_db`.`user_account` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- AppWallet (and SuperUser for it)
-- -----------------------------------------------------

INSERT INTO cs_dough_db.user_account (user_id, username, user_password, password_key, user_email, user_type)
VALUES (1, 'admin', 'admin', 'admin', 'admin', 'ADMIN');

INSERT INTO wallet (wallet_id, user_id)
VALUES (36, 1);