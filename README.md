This file details the necessary steps to configure, build, and run this project.

CONFIGURATION

This project was built and run with the following programs:
JDK 1.8u152
Maven 3.5.2
JCE 1.8 (should match JDK version)
MySQL 5.7.20

All the programmatic configuration settings are stored in the \src\main\resources\application.properties file. By default, the DB settings already match the bootstrap SQL script settings, so they only need to be changed if you don't want to use the default DB name, user, and tables. Optionally, the log file settings can be changed, but the log file is only included for demonstration purporses, and does not impact runtime if not set to point to a valid hard drive location (the log file is merely not created).

JCE 1.8 SETUP

The Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files can be downloaded here:

http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

The JCE is needed for AES 256 encryption at runtime. The 2 jars, local_policy.jar, and US_export_policy.jar, must be copied into the JRE's \lib\security folder (not the JDK, as these are jars needed for runtime and not compile time). For example, when installing JDK 1.8u152 in Windows, the default folder for the JRE is C:\Program Files\Java\jre1.8.0_152. Therefore, these 2 jars should be copied into the C:\Program Files\Java\jre1.8.0_152\lib\security directory in this example.

DB SETUP

To setup the DB schema for this project, run the \sqlscripts\bootstrap.sql file using a user with root priviledges. This will create the database, user, and tables used by the project. If you want to change the DB and user names, be sure to also change them in the application.properties file as well before building.

BUILD

To build the project, go to the project root directory where the pom file resides, and use the command (assuming that maven is in your path):

mvn clean package

RUN

To run the executable jar, go to the target directory where the jar was built and use the command:

java -jar spring-boot-assignment-0.0.1-SNAPSHOT.jar

TESTING

The 2 users and passwords created are:
ufliao1/pfliao1
ufliao2/pfliao2

The URLs are:

GET http://localhost:8080/assignment/witness-statement (Get all withness statements)
GET http://localhost:8080/assignment/witness-statement/{id} (Get a witness statement)
POST http://localhost:8080/assignment/witness-statement (Add a witness statement)

All messages require basic authentication using one of the usernames and passwords provided above. The POST message also requires an application/json specification in the header.

An example for the body of the POST message would be:

{
    "firstName": "First1",
    "middleName": "Middle1",
    "lastName": "Last1",
    "gender": "M",
    "dateOfBirth": "1/1/2000",
    "address": "Some address1",
    "email": "id1@provider.com",
    "phone": "111-111-1111",
    "witnessStatement": "i saw something 1"
}

You can use whatever tool you want to test it, such as curl, but a Postman project is included in the \postman\Assignment.postman_collection.json file which can be imported into Postman for easy testing. The Postman project contains the following messages that can be used for testing:
- Incorrect basic authentication settings
- Add messages for ufliao1 and ufliao2
- Get all messages for ufliao1 and ufliao2
- Get message for ufliao1 and ufliao2

Get all and get will only return results of the user that created them. For example, sending a get all using ufliao1 will only return entries that ufliao1 added to the DB. Any entries created by ufliao2 will not show. Sending a get using ufliao1 will only return an entry if ufliao1 added that entry. If ufliao2 added the entry, then no result is returned.
