DROP DATABASE IF EXISTS supervillainsecurity;

CREATE DATABASE supervillainsecurity;

USE supervillainsecurity;
-- -----------------------------------------------------
-- SuperPower
-- -----------------------------------------------------
CREATE TABLE SuperPower (
  powerId INT PRIMARY KEY auto_increment,
  `description` VARCHAR(120) NULL,
  `name` VARCHAR(45) NULL
);
-- -----------------------------------------------------
-- Location
-- -----------------------------------------------------
CREATE TABLE Location (
  locationId INT PRIMARY KEY auto_increment,
  `name` VARCHAR(120) NULL,
  `description` VARCHAR(45) NULL,
  streetName VARCHAR(45) NULL,
  city VARCHAR(45) NOT NULL,
  state VARCHAR(45) NULL,
  zipCode VARCHAR(45) NULL,
  longitude VARCHAR(20) NULL,
  latitude VARCHAR(20) NULL
);

-- -----------------------------------------------------
-- Organization
-- -----------------------------------------------------
CREATE TABLE `Organization` (
  organizationId INT PRIMARY KEY auto_increment,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(200) NULL,
  phone varchar(20) null,
  locationId INT NOT NULL,
  CONSTRAINT `fk_organization_loacationId`
    FOREIGN KEY (locationId)
    REFERENCES Location (locationId)
    );
    
-- -----------------------------------------------------
-- SuperVillain
-- -----------------------------------------------------
CREATE TABLE SuperVillain (
  villainId INT PRIMARY KEY auto_increment,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(300) NULL,
  powerId INT  null,

  CONSTRAINT fk_villain_powerId
    FOREIGN KEY (powerId)
    REFERENCES SuperPower (powerId)
);

-- -----------------------------------------------------
-- Sighting
-- -----------------------------------------------------
CREATE TABLE Sighting (
  sightingId INT PRIMARY KEY auto_increment,
  `date` DATE NULL,
  locationId INT NOT NULL,
  CONSTRAINT fk_sighting_loacationId
    FOREIGN KEY (`locationId`)
    REFERENCES Location (locationId)
  );

-- -----------------------------------------------------
-- OrganizationVillain
-- -----------------------------------------------------
CREATE TABLE OrganizationVillain (
   villainId INT not NULL,
  organizationId INT not NULL,
PRIMARY KEY pk_OrganizationVillain (villainId, organizationId),
    FOREIGN KEY fk_OrganizationVillain__villain (villainId)
    REFERENCES SuperVillain (villainId),
  FOREIGN KEY  fk_OrganizationVillain_organization (organizationId)
    REFERENCES `Organization` (organizationId)

);


-- -----------------------------------------------------
-- SuperVillainSighting
-- -----------------------------------------------------
CREATE TABLE SuperVillainSighting (
  villainId int not null,
  sightingId INT not null,
  PRIMARY KEY  pk_SuperVillainSighting (villainId, sightingId),
  CONSTRAINT fk_sighting_SuperVillain
    FOREIGN KEY (villainId)
    REFERENCES SuperVillain (villainId),

  CONSTRAINT fk_SuperVillain__sighting
    FOREIGN KEY (sightingId)
    REFERENCES Sighting (sightingId)
);

create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));

insert into `user`(`id`,`username`,`password`,`enabled`)
    values(1,"admin", "password", true),
        (2,"user","password",true);

insert into `role`(`id`,`role`)
    values(1,"ROLE_ADMIN"), (2,"ROLE_USER");
    
insert into `user_role`(`user_id`,`role_id`)
    values(1,1),(1,2),(2,2);
    
update user set password = '$2a$10$JpdO/qL.hqZVYteoQcWVQuHDdAqpNzxcTyfpziX4Z8DqJz9o87JLW' where id = 1;
update user set password = '$2a$10$JpdO/qL.hqZVYteoQcWVQuHDdAqpNzxcTyfpziX4Z8DqJz9o87JLW' where id = 2;