Installation
------------
*installation requires Maven to be installed, this can be found at:
https://maven.apache.org/install.html

Install project by navigating to project folder and running:
mvn package

Running
-------
To run the project navigate to project folder and run commmand:
java -jar target/mariner-partner-0.0.1-SNAPSHOT.jar

Tools/Libraries
---------------
Maven:
Used for managing libraries and building project.  Libraries are inserted into project
as dependencies which are tracked by the POM.xml file in main folder.  This file serves
as both a dependency tracker and a guide for maven to build the executable jar file.
Source: https://maven.apache.org/install.html

JSON Simple:
JSON.simple is a simple java toolkit for JSON, used in this project for simple parsing
of reports.JSON allowing for each field to be pulled as a value for interpreting.
Source: https://github.com/fangyidong/json-simple

OpenCSV:
OpenCSV is a very simple csv parser library for Java.  Used in this project for converting
List of ArrayList objects in Java to a csv file.  This was used rather than looping
through each ArrayList to save code space and allow for an efficient solution with 
collections sorting in Java.
Source: http://opencsv.sourceforge.net/

Apache Commons:
Required Library with use of Maven.
Source: https://commons.apache.org/