# Safety watcher

The purpose of this project is to give a overview of safety aspects of a school. This project contains 2 parts, web and desktop.


* Web
 
	It gives a overview of the safety aspects.

* Desktop

	It gives a overview of the safety aspects and able to manages the aspects.

## Requirements

* Maven
* JDK 8
* git

##  Install

1. download the source code

	```shell
		git clone https://github.com/YangSkyL/safetyWatcher
	
	```
2. build web part

	```shell
		cd /path_to_sourcecode/web
		mvn package
	```
	
3. run webapplication

	```shell
		mvn spring-boot:run
	```
	
4. access the webapplication at localhost:8080
	
5. build desktop part

	```shell
		cd /path_to_sourcecode/Desktop
		mvn package
	```
	
6. run desktop application
	
	```shell
		mvn spring-boot:run
	```

 