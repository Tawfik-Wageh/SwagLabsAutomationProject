Swag Labs Web Test Automation Project

Overview
This project provides automated test coverage for the Swag Labs web application (https://www.saucedemo.com/). The framework is built using Selenium WebDriver with Java and follows industry best practices for maintainable and scalable test automation.

Key Features
- Page Object Model (POM) Design Pattern: Organized structure for easy maintenance
- Data-Driven Testing: Supports multiple test data sets via external files
- Allure Reporting: Comprehensive HTML reports with screenshots and step logging
- TestNG Framework: Powerful test execution and parallel testing capabilities
- Log4j Integration: Detailed logging for debugging and analysis
- Cross-Browser Testing: Support for Chrome, Firefox, Edge, etc.
- CI/CD Ready: Designed for easy integration with Jenkins/GitHub Actions

Technologies Used
- Java 8+
- Selenium WebDriver 4.x
- TestNG 7.x
- Allure Report 2.x
- Log4j 2.x
- Maven (for dependency management)

Project Structure
swag-labs-automation/
├── src/main/java/
│   ├── pages/          # Page Object classes
│   ├── utils/          # Helper/utility classes
│   ├── listeners/      # TestNG listeners
│   └── constants/      # Constant values
├── src/test/java/
│   ├── tests/          # Test classes
│   ├── runners/        # Test runners
│   └── dataproviders/  # Data provider classes
├── src/test/resources/
│   ├── testdata/       # Test data files (JSON/CSV/Excel)
│   ├── config/         # Configuration files
│   └── log4j2.xml      # Logging configuration
├── pom.xml             # Maven dependencies
└── README.md           # Project documentation

Prerequisites
- Java JDK 8 or higher
- Maven 3.6.0 or higher
- Chrome/Firefox browser installed
- Allure commandline tools (for report generation)

Setup Instructions
1. Clone the repository:
   git clone https://github.com/yourusername/swag-labs-automation.git
2. Navigate to project directory:
   cd swag-labs-automation
3. Install dependencies:
   mvn clean install

Running Tests
To run all tests:
mvn clean test

To run tests with specific TestNG suite:
mvn test -Dsurefire.suiteXmlFiles=testng.xml

To run tests in parallel (example with 2 threads):
mvn test -DthreadCount=2

Generating Reports
After test execution, generate Allure report:
allure serve allure-results

Configuration
Edit config.properties file to modify:
- Application URL
- Browser settings
- Timeout values
- User credentials

Test Data Management
Test data is stored in:
- /src/test/resources/testdata/ directory
- Supports JSON, CSV, and Excel formats

Logging
Logs are generated in:
- Console output
- logs/automation.log file
- Allure report attachments

CI/CD Integration
Sample Jenkins pipeline script included in jenkinsfile

Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

License
[MIT](https://choosealicense.com/licenses/mit/)
