# JAVA TESTNG MAVEN PROJECT

## This is a clipboard assignment test built using maven,java & selenium


## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Reports](#reports)
- [Docker](#docker)
- [Issues](#issues)

## Installation

Before running below command(s), set the value of `environment` to `local` under `test/resources/config.properties`.

Please run the below commands sequentially from the current/present working directory of the project to download all the dependencies required for the project.

```
mvn clean install
```
The above command should automatically run the selenium tests. If not, please run the below command

```
mvn test
```

## Usage

Below folder hierarchy is used in building the project.

```
java-testng-maven-project
├── src 
|   ├── main This directory contains required details for building the framework.
|   |   ├── java   This directory contains the list of java packages.
|   |   |   ├── com.ui.framework 
|   |   |   |   ├── basePage 
|   |   |   |   |   └── basePage - This Class contains the list of reusable methods used for performing various actions on WebElement.
|   |   |   |   ├── enums
|   |   |   |   |   ├── DriverType - Chrome & Firefox enums are defined.
|   |   |   |   |   └── EnvironmentType - Local & Remote enums are defined. 
|   |   |   |   ├── manager
|   |   |   |   |   ├── DriverManager - This Class lists out methods required for creating ChromeDriver, RemoteDriver & Closing the browser.
|   |   |   |   |   └── FileReaderManager - This Class list out method to retrieve configs from the properties.
|   |   |   |   ├── reports
|   |   |   |   |   ├── ExtentTestNGIReporterListener This class generates the custom `html` reporter when tests are run.
|   |   |   |   |   └── ExtentTestNGListener - This class generates the custom `html` reporter when tests are run.(using Extent)
|   |   |   |   ├── utility
|   |   |   |   |   └── ConfigFileReader - This Class retrieves properties from the config file `config.properties`.
|   |   |
|   ├── test
|   |   ├── java
|   |   |   ├── com.ui.framework
|   |   |   |   ├── baseTest    
|   |   |   |   |   └── basePage    This Class has methods to setup & teardown browser when tests are run.    
|   |   |   |   ├── customListener
|   |   |   |   |   └── Listener    This Class has methods to report the events required for custom reports.
|   |   |   |   ├── pageObjects
|   |   |   |   |   ├── HomePage    This Class has collection of Web Elements along with methods as part of HomePage.
|   |   |   |   |   ├── ResultPage  This Class has collection of Web Elements along with methods as part of ResultPage.
|   |   |   |   |   └── TelevisionPage This Class has collection of Web Elements along with methods as part of TelevisionPage.
|   |   |   |   ├── testCases
|   |   |   |   |   └── Department  This Class has one test to run against the application under test.
|   |   ├── resources     # This directory contains all the properties required for executing the test(s) in `DEV` environment.
|   |   |   ├── config.properties This file have details such as applicationUrl, browser, environment & implicitlyWait to be used for application under test.
|   |   |   ├── log4j2.properties This file have details related to logging of application while tests are running.
|   |   |   └── testNG.xml  # This `XML` file has details such as input required for running tests & generating reports.
```

Currently, we have set log-level to `info`.
Change the value of `rootLogger.level` to either `debug` to get debug logs OR `error` to get error logs.
Logs are generated on both `console` & to a file(i.e, `logs/propertieslogs.log`)

## Reports

- Default Testng reports are generated under `targets/surefire-reports/index.html`, `emailable-report.html`.
- Custom Reports are generated under `targets/customReporter` with filenames being `index.html` & `Extent.html`

![Alt text](/screenshots/emailable_Report.png?raw=true "Reports")
![Alt text](/screenshots/Extent_Report.png?raw=true "Reports")
![Alt text](/screenshots/Report.png?raw=true "Reports")
![Alt text](/screenshots/Testng_Report.png?raw=true "Reports")

## Docker

All actions are performed from the root directory of the project.

Before building docker image set the value of `environment` to `remote` under `test/resources/config.properties`.

- Running tests(via docker) involves creating a docker image
  - Create a Docker Image using the command `docker build -t java-testng-maven-project:latest -f Dockerfile .`
  - Once built successfully, tag the image locally to refer from `docker-compose.yml` file using below command
    - command : `docker tag java-testng-maven-project:latest healthcare:latest`
  - Once the above commands are executed, run the command `docker-compose run test` to run test within docker container.

## Issues

- Unable to run the selenium tests from Docker.(Getting below issue while running tests).Tried looking into issue but couldn't figure out the issue.
  `(The process started from chrome location /usr/bin/google-chrome is no longer running, so ChromeDriver is assuming that Chrome has crashed.)`

