# csye6225-fall2018
## Team members of Group-
* Rohan Mandhanya, mandhanya.r@husky.neu.edu
* Kiran Bilgundi, bilgundi.k@husky.neu.edu

### Prerequisite for building web app
1. Install mysql (dnf install mysql-community-server)
2. Install Intelij Idea

### Starting SQL server 
mysql.server start
mysql -u root

### Create user Database and create a DB user
create database db_example;
create user 'springuser'@'localhost' identified by 'ThePassword';
grant all on db_example.* to 'springuser'@'localhost';
USE db_example;
show tables;

### Running application

The Spring boot application generates a jar file which can be run using the following command 

java -jar cloudClass1/target/cloudClass1-1.0-SNAPSHOT.jar

