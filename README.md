# SwagLabsAutomationProject

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://shields.io/) [![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Overview

This project automates end-to-end testing for the Swag Labs web application using Selenium WebDriver, TestNG, and Allure
reporting. It covers login, landing, cart, checkout, overview, and order finishing workflows.

## Features

- Automated UI testing for all major workflows
- Page Object Model for maintainable code
- Data-driven testing support
- Allure reporting integration
- Screenshots and logs on failure
- Modular test suites (Login, Cart, Checkout, etc.)

## Technologies Used

- Java 8+
- Maven
- Selenium WebDriver
- TestNG
- Allure Reporting
- Log4j2

## Folder Structure

- `pom.xml` - Maven configuration file.
- `src/main/java/` - Main source code:
    - `DriverFactory/` - WebDriver setup and management.
    - `Pages/` - Page Object Model classes for each app page.
    - `Utils/` - Utility classes (data, logging, general helpers).
- `src/test/java/` - Test source code:
    - `Listeners/` - TestNG listeners for reporting and logging.
    - `Tests/` - Test classes for different suites.
- `src/test/resources/TestData/` - Test data files.
- `test-outputs/` - Output files (Allure results, logs, screenshots).
- `TestRunner/` - XML suite files for running grouped tests.

## Prerequisites

- Java 8 or higher
- Maven 3.6+
- Chrome/Firefox browser (WebDriver binaries should be configured)

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repo-url>
   ```
2. Navigate to the project directory:
   ```
   cd SwagLabsAutomationProject
   ```
3. Install dependencies:
   ```
   mvn clean install
   ```

## How to Run Tests

- To run all tests:
  ```
  mvn test
  ```
- To run a specific suite:
  ```
  mvn test -DsuiteXmlFile=TestRunner/LoginSuite.xml
  ```
  (Replace with desired suite XML file)

## Reporting

- Allure results are generated in `test-outputs/allure-results/`.
- To generate and view Allure report:
    1. Install Allure commandline (https://docs.qameta.io/allure/)
    2. Run:
       ```
       allure serve test-outputs/allure-results
       ```
- Logs and screenshots are saved in `test-outputs/Logs/` and `test-outputs/ScreenShots/`.

## Troubleshooting

- **WebDriver not found:** Ensure ChromeDriver/GeckoDriver is in your PATH or configured in the project.
- **Allure command not recognized:** Install Allure CLI and add it to your system PATH.
- **Tests not running:** Check Java and Maven versions, and ensure dependencies are installed.

## FAQ

**Q: How do I add a new test?**  
A: Create a new class in `src/test/java/Tests/` and follow the Page Object Model structure.

**Q: How do I change browser settings?**  
A: Update the WebDriver configuration in `DriverFactory/DriverFactory.java`.

**Q: Where are test results stored?**  
A: Allure results, logs, and screenshots are in the `test-outputs/` folder.

## Contribution Guidelines

Feel free to fork and submit pull requests. Please follow the Page Object Model and keep code modular.

## License

This project is for educational and demonstration purposes.
