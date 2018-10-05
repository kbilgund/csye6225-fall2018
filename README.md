# csye6225-fall2018
## Team members of Group-
* Rohan Mandhanya, mandhanya.r@husky.neu.edu
* Kiran Bilgundi, bilgundi.k@husky.neu.edu

### Prerequisite for building web app
1. Install mysql (dnf install mysql-community-server)
2. Install Intelij Idea

### Starting SQL server 
Step1: start the mysql server

mysql.server start

Step2: login to sql server as root

mysql -u root

### Create user Database and create a DB user
Step1: create a database.

create database db_example;

Step2: create a database user for the web application

create user 'springuser'@'localhost' identified by 'ThePassword';

Step3: grant all the permissions to the user

grant all on db_example.* to 'springuser'@'localhost';


### Running application

The Spring boot application generates a jar file which can be run using the following command 

java -jar cloudClass1/target/cloudClass1-1.0-SNAPSHOT.jar

