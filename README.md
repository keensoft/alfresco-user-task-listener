User tasks synchronization with external systems for Alfresco (Summit 2013)
===========================================================================

Source code sample provided for Alfresco Summit 2013 (Barcelona Lighting Talk #2 Session). 

User tasks synchronization with external systems on Alfresco by using Activiti parsers and listeners.

This software is provided by keensoft - http://www.keensoft.es.

Description
-----------

This project includes one module which produces an Alfresco module (AMP).

Further detailed instructions will be added to the Wiki.

Compatibility
-------------

The module should be compatible with all Alfresco EE 4.2.x and Alfresco CE 4.2.d or later.

It has been verified to work with Windows 8 and Mac OS X (10.9).

Building
--------

This module is based on the Alfresco Maven SDK and requires Maven >= 3.0.4, a Java 1.6 compatible compiler. 
You will also need git to clone the repository. 
The master branch will always hold the latest stable version of the module. 

Cloning the repository:
* git clone git://github.com/keensoft/alfresco-user-task-listener.git

Building with maven:
* mvn clean package

Module is packaged as AMP and the resulting AMP will end up in ./alfresco-user-task-listener/target/

Configuration
-------------

You can develope your own implementation for ExternalTaskSystem interface in order to send user tasks status changes to external systems.

Installation
------------

To install this module in Alfresco use the Alfresco Module Management tool. *Note* The module is an Alfresco module and should only be installed in Alfresco. 

* Upload the AMP file to your amps directory in your Alfresco installation.
* Stop Alfresco
* Run java -jar bin/alfresco-mmt.jar install amps/alfresco-user-task-listener-x.x.x.amp tomcat/webapps/alfresco.war 
* Clean out temporary alfresco files and old unpackaded alfresco war (remove tomcat/webapps/alfresco and tomcat/work/Catalina)
* Start Alfresco

License
-------

This application is licensed under the LGPLv3 License.

Authors
-------

Angel Borroy - keensoft
