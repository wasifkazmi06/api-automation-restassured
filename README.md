# API Automation Testing Framework 
A robust API Automation Testing framework built using Java, TestNG, Maven, and RestAssured for seamless and efficient API testing. 
---
## Features :sparkles: 
• REST Assured Integration - Simplified testing for RESTful APIs. 
• TestNG Support - Easy test execution and reporting. 
• Maven for Dependency Management - Effortless build and dependency handling. 
• Custom Utilities - Designed for handling authentication, headers, requests, and response validations. 
• Configurable Setup - Supports environment-specific configurations (e.g., dev, qa, prod). 
• Detailed Reporting with Allure - Visualization for test run results and performance metrics. 
---
## Prerequisites :hammer_and_wrench: 
Ensure the following are installed on your local machine: 
1. Java (JDK 11 or higher) 
2. Maven (latest version) 
3. Allure Reporting: Follow the Allure Installation Guide to set up Allure in your environment. 
---
## Getting Started :rocket: 
### 1. Clone the Repository 
bash
git clone https://github.com/<your-username>/<repository-name>.git  
cd <repository-name>  
### 2. Import the Project into your IDE 
• Open your favorite IDE. 
• Import the project as a Maven Project. 
### 3. Install Dependencies 
Run the following Maven command to download and install dependencies: 
bash
mvn clean install  
  
---
## Framework Structure :open_file_folder: 
📦src  
 ┣ 📂main  
 ┃ ┗ 📂java  
 ┃    ┗ 📜utilities/Pojos (common utility classes like RequestBuilder, etc.)  
 ┣ 📂test  
 ┃ ┗ 📂java  
 ┃    ┣ 📜tests/ (your TestNG testing classes)  
 ┗ 📝 testng.xml  
 
📜pom.xml (Maven configuration)  
  
---
## Running Tests :white_check_mark: 
### Using the IDE 
1. Navigate to the testng.xml file. 
2. Right-click -> Run the test suite. 
### Using Maven 
Execute the following command in your terminal: 
bash
mvn test  
---
## Reporting with Allure :bar_chart: 
The framework is integrated with Allure Reporting for enhanced visualization of test results. 
### Steps to Generate Allure Report: 
1. Run the test suite: 
    
bash
    mvn test  
    
  
2. Generate Allure reports: 
    
bash
    allure serve target/allure-results  
    
  
This will start a local server and open the Allure report in your default browser. 
### Example Allure Features: 
• Visualizations: Graphs for passed/failed/skipped tests. 
• Categorization: Grouping failures by error type. 
• Test Steps: Detailed step-by-step execution and logs. 
Ensure you’ve installed the Allure Command Line Tool. Refer to the official Allure documentation for installation instructions. 
---
## Libraries & Tools :hammer_and_wrench: 
- Java - Core programming language. 
- TestNG - For organizing and running test cases. 
- Maven - Build tool and dependency management. 
- REST Assured - For writing expressive API tests. 
- Allure - For generating detailed and interactive test reports. 
---
## Configuration :gear: 
All configurations are stored in an external properties file (e.g., config.properties) located in the resources folder. Update according to your environment: 
base.url=https://api.example.com  
api.auth.token=your-token  
timeout=5000  
  
---
## Test Folder Structure :card_index_dividers: 
### Inside tests Package 
• Functional Tests - Verifies API functionalities. 
• Regression Tests - Ensures no existing functionality is broken. 
• Negative Tests - Handles invalid scenarios ensuring proper error handling. 
---
## Sample Code Snippet :wrench: 
Here’s how an API test function would look in the framework: 
```java
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
allurereport.orgallurereport.org
Introduction
Official Documentation for Allure Report — Open-source HTML automation test reporting tool

import org.testng.annotations.Test;
public class ExampleApiTest {
    @Test
    public void validateGetEndpoint() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.example.com")
                .when()
                .get("/endpoint");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
        Assert.assertTrue(response.getBody().asString().contains("success"), "Response body validation failed!");
    }
}
``` 
---
## Contributing :hammer_and_wrench: 
Contributions are welcome! Follow these steps to contribute: 
1. Fork the repository. 
2. Create a new branch (feature/your-feature-name). 
3. Commit changes (git commit -m 'Added feature xyz'). 
4. Push to the branch (git push origin feature/your-feature-name). 
5. Create a Pull Request. 
---
## Contact & Support :mailbox: 
Author: Wasif Kazmi 
- Email: wasifkazmi06@gmail.com 
- LinkedIn: www.linkedin.com/in/wasif-kazmi-30798219
-  
Feel free to open an issue for suggestions, bugs, or queries! :rocket: 
