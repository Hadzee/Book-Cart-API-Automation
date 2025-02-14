# Book Cart API Automation

This repository contains automation tests for the Book Cart Application.

Website: https://bookcart.azurewebsites.net/login

SwaggerUI: https://bookcart.azurewebsites.net/swagger/index.html

## Documentation

You can find the full documentation (Test Cases) for this project [here](https://github.com/Hadzee/Sauce-Demo-Automation/tree/master/Documentation).

## Getting Started

This project uses Java and RestAssured to automate the testing of the Book Cart API. Below are the steps to set up the environment and run the automated tests.

### Prerequisites

Java: This project requires Java 8 or higher. You can download and install Java from the official website: [Java Downloads](https://www.oracle.com/java/technologies/downloads/?er=221886).

Maven: If you don’t have Maven installed, you can download it from the official website: [Maven Downloads](https://maven.apache.org/download.cgi).

IDE: You can use any Java IDE, but IntelliJ IDEA or Eclipse are recommended for their built-in support for Maven projects.

### Setting Up the Environment

First, clone the repository to your local machine if you haven't already.

Once the repository is cloned, navigate to the project folder and install the necessary dependencies via Maven. Run the following command in the terminal:
`mvn install`

This will download and install all the dependencies specified in the pom.xml file, including RestAssured, JUnit, and others.

## Running the Automated Tests

Once the setup is complete, you can run the automated API tests using JUnit and Maven.

### Running Tests with Maven
To run all the tests, simply execute the following Maven command in the project root directory:
`mvn test`

This will run all test cases specified in the src/test/java folder. Maven will automatically pick up any tests with @Test annotations and execute them.

#### Running Specific Tests
If you want to run a specific test class, for example, LoginTest.java, you can use the following Maven command: `mvn -Dtest=LoginTest test`

This will run only the LoginTest class.

## Configuring the Application

The test suite uses the config.properties file to store necessary configurations like base URI, credentials, etc.

Steps to Configure:
Edit the config.properties file:

The following properties should be configured in the `src/main/resources/config.properties` file:

```bash
baseURI=http://bookcart.azurewebsites.net
userName=your-username
password=your-password
userId=your-user-id
bookId=your-book-id
token=your-valid-token
```

###Generate or Obtain the Token:

Use the Login API `(/api/Login)` to automate token retrieval as part of the tests.

###Missing Configuration: 
Ensure that the config.properties file contains the correct values for API endpoints and credentials.

##Conclusion
This concludes the documentation for setting up and running the automated API tests for the Book Cart API. Follow the steps above to get started with testing the application.