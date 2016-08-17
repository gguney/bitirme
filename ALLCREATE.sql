
drop table `bil496`.`institutions`;
drop table `bil496`.`authors`;
drop table `bil496`.`subjects`;
drop table `bil496`.`references`;
drop table `bil496`.`categories`;
drop table `bil496`.`countries`;
drop table `bil496`.`cities`;
drop table `bil496`.`titles`;
drop table `bil496`.`abstracts`;


drop table `bil496`.`aas`;
drop table `bil496`.`asr`;
drop table `bil496`.`aac`;
drop table `bil496`.`ais`;
drop table `bil496`.`aic`;
drop table `bil496`.`auCoAu`;


drop table `bil496`.`instAnalysis`;
drop table `bil496`.`subAnalysis`;
drop table `bil496`.`auAnalysis`;
drop table `bil496`.`catAnalysis`;
drop table `bil496`.`coAnalysis`;
drop table `bil496`.`ciAnalysis`;



CREATE  TABLE `bil496`.`institutions` (
  `IID` INT NOT NULL AUTO_INCREMENT ,
  `IName` TEXT 

NOT NULL ,
  `ICityID` VARCHAR

(45)  NULL ,
  `ICountryID` VARCHAR(45) NULL ,
  `TCount` INT 

NULL ,

  `IMore1` TEXT NULL ,
  `IMore2` TEXT NULL ,
	

PRIMARY KEY 

(`IID`) ,
  INDEX `IName` (`IName`(20) ASC) );






CREATE  TABLE `bil496`.`authors` (
  `AUID` INT NOT NULL 

AUTO_INCREMENT,
  `AUAddressID` INT 

NULL ,
  `AUName` TEXT NOT NULL ,
  `AUFullAddress` TEXT NULL ,
  `TCount` INT NULL ,
  `AUMore1` TEXT NULL ,
`AUMore2` TEXT NULL ,

PRIMARY KEY 

(`AUID`), INDEX `AUName` (`AUName`(30) ASC));



CREATE  

TABLE `bil496`.`subjects` (
  `SID` INT NOT NULL AUTO_INCREMENT,
  `SName` TEXT NULL 

,
  `TCount` INT NULL ,
  PRIMARY KEY (`SID`), INDEX `SName` (`SName`(30) ASC));



CREATE  TABLE 

`bil496`.`categories` (
  `CID` INT NOT NULL AUTO_INCREMENT,
  `CName` TEXT 

NULL ,
  `TCount` INT NULL ,
  PRIMARY KEY (`CID`), INDEX `CName` (`CName`(30) ASC));


CREATE  TABLE 

`bil496`.`references` (
  `RID` INT NOT NULL AUTO_INCREMENT ,
  `RName` TEXT 

NULL ,
  `RDOI` VARCHAR(200),  `RAID` INT NULL  ,PRIMARY KEY (`RID`),INDEX `RName` (`RName`(50) ASC) );



CREATE  TABLE 

`bil496`.`countries` (
  `CoID` INT NOT NULL AUTO_INCREMENT ,
  `CoName` TEXT 

NULL ,
  `TCount` INT ,PRIMARY KEY (`CoID`),INDEX `CoName` (`CoName`(20) ASC) );


CREATE  TABLE `bil496`.`cities` (
 

 `CiID` INT NOT NULL AUTO_INCREMENT ,
  `CiName` TEXT 

NULL ,
  `TCount` INT ,PRIMARY KEY (`CiID`),INDEX `CiName` (`CiName`(20) ASC) );





CREATE  TABLE `bil496`.`titles` (
  `AID` INT NOT NULL,
 `TI` TEXT 

NULL, `PY` INT 

NULL ,PRIMARY KEY (`AID`) ,INDEX `TI` (`TI`(20) ASC) );

CREATE  TABLE `bil496`.`abstracts` (
  `AID` INT NOT NULL,
  `AB` TEXT 

NULL ,
PRIMARY KEY (`AID`) ,INDEX `AB` (`AB`(20) ASC) );



CREATE  TABLE `bil496`.`aas` (
  `AID` INT NOT NULL ,
  `AUID` INT NOT NULL ,
  `SID` INT NOT 

NULL ,INDEX `AID` (`AID` ASC),INDEX `SID` (`SID` ASC),INDEX `AUID` (`AUID` ASC))
COMMENT = 'Article Author Subject Connection Table';





CREATE  

TABLE `bil496`.`asr` (
  `AID` INT NOT NULL ,
 `SID` INT NOT NULL ,
  `RID` INT NOT 

NULL ,INDEX `AID` (`AID` ASC) ,INDEX `SID` (`SID` ASC) ,INDEX `RID` (`RID` ASC))
COMMENT = 'Article Subject Reference Connection Table';


CREATE  TABLE 

`bil496`.`aac` (
  `AID` INT NOT NULL ,
 `AUID` INT NOT NULL ,
  `CID` INT NOT 

NULL ,INDEX `AID` (`AID` ASC))
COMMENT = 'Author Category Connection Table';

CREATE  TABLE `bil496`.`ais` (
  

`AID` INT NOT NULL ,
 `IID` INT NOT NULL ,
  `SID` INT NOT 

NULL,INDEX `AID` (`AID` ASC))
COMMENT = 'Article Institution Subject Connection Table';

CREATE  TABLE `bil496`.`aic` (
  `AID` INT 

NOT NULL ,
 `IID` INT NOT NULL ,
  `CID` INT NOT 

NULL,INDEX `AID` (`AID` ASC))
COMMENT = 'Article Institution Category Connection Table';

/*
CREATE  TABLE `bil496`.`auCoAu` (
  `AUID` INT NOT 

NULL ,
 `CoAUID` INT NOT NULL,INDEX `AUID` (`AUID` ASC),INDEX `AUID` (`AUID` ASC)  )
COMMENT = 'Authors and CoAuthors Connection Table';
*/

CREATE  TABLE `bil496`.`auCoAu` (
  `AUID` INT NOT NULL ,
 `CoAUID` INT NOT NULL,Primary KEY (`AUID`,`CoAUID`)  )
COMMENT = 'Authors and CoAuthors Connection Table';


CREATE  TABLE `bil496`.`instAnalysis` (
  `IID` INT NOT NULL ,
  `PY` INT NOT NULL ,`TCount` INT NOT NULL,INDEX `IID` (`IID` ASC) )
COMMENT = 'How many records have the institutions';


CREATE  TABLE `bil496`.`subAnalysis` (
  `SID` INT NOT NULL ,
 `PY` INT NOT NULL ,
  `TCount` 

INT NOT NULL ,INDEX `SID` (`SID` ASC))
COMMENT = 'How many subjects in years';





CREATE  TABLE `bil496`.`auAnalysis` (
  `AUID` INT NOT NULL ,
 `PY` INT NOT NULL ,
  `TCount` 

INT NOT NULL, INDEX `AUID` (`AUID` ASC))
COMMENT = 'How many records have the 

authors';

CREATE  TABLE `bil496`.`catAnalysis` (
  `CID` INT NOT NULL ,
 `PY` INT NOT NULL ,
  `TCount` 

INT NOT NULL,INDEX `CID` (`CID` ASC) )
COMMENT = 'How many categories in years';

CREATE  TABLE 

`bil496`.`coAnalysis` (
  `CoID` INT NOT NULL ,
 `PY` INT NOT NULL ,
  `TCount` 

INT NOT NULL ,INDEX `CoID` (`CoID` ASC))
COMMENT = 'How many records have the countries';

CREATE  TABLE `bil496`.`ciAnalysis` (
  `CiID` INT 

NOT NULL ,
 `PY` INT NOT NULL ,
  `TCount` 

INT NOT NULL ,INDEX `CiID` (`CiID` ASC))
COMMENT = 'How many records have the cities';


