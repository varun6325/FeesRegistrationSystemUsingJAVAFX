# FeesRegistration System Using JAVAFX
This is a simple Fees Registration System built using javafx+fxml, that can be used by anyone for basic student registration and mantaining their records and sending notifications on due date.

## What it does :
You can do the following:
* Add/Update a student 
* Add/Update a course
* Register a student with respect to the course
* Adding installations with respect to a registration and assigning due date to an installation
* Check registrations for which installation is due in the specified period

## Tools required: 
* Java 11 - version used for development and testing is 11.0.7
* maven - version used for development is 3.6.2
* mariadb - version used is 10.4.13

## How to run: 
This is divided into 2 parts
### 1. setting up database:
* Install mariadb server. You can also use any other sql server, but if you change the db then you might have to make changes in [persistence.xml](../src/main/resources/META-INF/persistence.xml) file.
* Remember the user, password and port that you use to setup the mariadb server and update that in [persistence.xml](./src/main/resources/META-INF/persistence.xml) file. The default userid is root, password is password and port is 3306. 
* Login to the mariadb server and create database named FeesRegistration using the following command 
`create database FeesRegistration;`
* Import the db schema using file [FeesRegistration.sql](./mysql-dump/FeesRegistration.sql) and the following command `>mysql -u username -p FeesRegistration < <path to>\FeesRegistration.sql`

### 2. compiling and running java code:
This can be done using
`mvn javafx:compile javafx:run`
