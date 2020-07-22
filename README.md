Details regarding total lines of code written:
1. Lines of code considering all Java-Servlet files: 5279 Lines of code approximately
2. Lines of code for javascript: 430 Lines of code
3. Recommender Python Notebook: 70 Lines of code

***************************************************************************************************************

Features implemented in project:
We have 2 roles Customer and Manager

1. Home page having header,left navigation bar, content and footer.
2. Add event to cart, carousel for recommended products, place an order and payment Transaction.
3. Login, Register, View Order, View Account.
4. Add, Update, Delete products done by the Manager.
5. Offering discount and manufacturer rebate while buying a product.
6. Having good amount of data in terms of events.
7. All accounts login information is stored in MYSQL database and All customer's orders stored in SQL database (MySQL).
8. Customer is able to submit product reviews.
9. Customer/Manager is able to view submitted product reviews.
10. Product reviews are stored in NoSQL database (MongoDB).
11. Add Trending, Inventory Report and Sales Report feature using google bar chart and for some cases displaying information in table on UI.
12. Auto-complete-feature for searching a events (auto-complete feature placed in AjaxUtility.java).
13. Deal matches guarantee and producd recommendation feature.

********************************************************************************************************************

Pre-requisites for running web-site:
1. Need to have a latest web browser installed in the machine.
2. Need to have java "jdk1.7.0_40" and "jre7" installed in the machine in C drive's "Program Files" folder.
3. Need to have "apache-tomcat-7.0.34" installed in C drive and it should be running.
4. Need to have following jar files in the "C:\Program Files\Java\jre7\lib\ext" folder. (You can copy this following mentioned jar files from the "C:\apache-tomcat-7.0.34\lib" folder.)
	i.   tomcat-dbcp.jar
	ii.	 servlet-api.jar
	iii. jsp-api.jar
	iv.	 el-api.jar
5. Need to have MySql installed and configured in the machine in "C:\Program Files".
6. Need to have Mongodb installed and configured in the machine in "C:\Program Files". Make sure whenever you run Mongodb, it runs on port number "27017".
7. Need to have following jar files in the "C:\apache-tomcat-7.0.34\lib" folder which is installed in the C drive in above step number 3.
	i. 	    mongo-java-driver-3.2.2.jar
	ii. 	mysql-connector-java-5.1.47-bin.jar
	iii. 	gson-2.3.1.jar
8. Open "env-setup-for-tomcat" file available in the "C:\apache-tomcat-7.0.34" directory in text editor and place the following content in the file and save the changes made to the file.
Content that needs to be pasted in the "env-setup-for-tomcat" file:

set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_40

set PATH="C:\Program Files\Java\jdk1.7.0_40\bin",%PATH%

set CLASSPATH=.;C:\apache-tomcat-7.0.34\lib\servlet-api.jar;C:\apache-tomcat-7.0.34\lib\jsp-api.jar;C:\apache-tomcat-7.0.34\lib\el-api.jar;C:\apache-tomcat-7.0.34\lib\commons-beanutils-1.8.3.jar;C:\apache-tomcat-7.0.34\lib\mysql-connector-java-5.1.47-bin.jar;C:\apache-tomcat-7.0.34\lib\mongo-java-driver-3.2.2.jar;C:\apache-tomcat-7.0.34\lib\gson-2.3.1.jar;C:\apache-tomcat-7.0.34\lib\mail.jar;C:\apache-tomcat-7.0.34\lib\activation.jar;C:\apache-tomcat-7.0.34\lib\smtp-1.4.4.jar

set ANT_HOME=c:\apache-tomcat-7.0.34

set TOMCAT_HOME=C:\apache-tomcat-7.0.34

set CATALINA_HOME=C:\apache-tomcat-7.0.34

*******************************************************************************************************************************

Check the MySQL file "sportshub.sql" for all MySQL tables.

*******************************************************************************************************************************

•MongoDb Server : 

•To use MongoDb server you need to first run the mongod.exe file from your c drive. After running mongod.exe file you need to run mongo.exe file.
•You can see “>” arrow like this. Now to go to database file you need to write command as given below.

•Use SportshubReviews

Then create collection  using below command
•db.createCollection("myReviews")

To see collection in the database you need to write command given below.

•db.myReviews.find()

*******************************************************************************************************************************

•Project Configuration Steps:

•To setup the project in your local machine, first of all you need to put the folder named sportshub under your webapps folder.
•After putting folder in the webapps you need to open cmd there and go to sportshub/WEB-INF/classes using cmd command and after reaching there you can run below command to compile all java files.

•Javac *.java

•After Compiling it start tomcat server then you can run the project in your machine. 
You need to open browser and type URL like localhost:8080/sportshub/home which redirects you to Home page of application.


*******************************************************************************************************************************
Please open Screenshot.pdf file for the screenshots.





