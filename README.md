Training project. CS-DOUGH<br/>
-------------------
CS-DOUGH web application is CS:GO 1v1 tournaments platform. Users can participate or organize their own tournaments here. 
There is money system in the app to make tournaments more competitive. It's possible to earn in-app-money either creating 
a tournament or winning it.<br/>
*******************
### Technologies Used:<br/>
* Storing data with MySQL database.<br/>
* Logging with Log4j.<br/>
* App units are tested with JUnit4.<br/>
* Usage of JSP pages are implemented with JSTL and EL, there is no scriptlets in the project.<br/>
* UI is created with bootstrap library and some raw js and jquery scripts.<br/>
----------------
### Main Features:<br/>
* EN-RU localization implemented.<br/>
* Twitch.tv API is used for random match online view. <br/>
* Web filter implements cross site scripting protection.<br/>
* "F5"-protection enabled.<br/>
* Input data validation present both on the client and server side.<br/>
-----------------
### How to start the project:<br/>
- Requirements:<br/>
    * Docker 18.06.0+<br/>
    * Maven 3+<br/>
- Steps to start:<br/>
    * Run `maven clean install`.<br/>
    * Run `docker-compose up`.<br/>


