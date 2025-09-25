# API Automation Framework (Rest Assured + TestNG)

This project demonstrates automated **API functional testing** using the [ReqRes](https://reqres.in/) mock API.  
It covers **CRUD operations** (Create, Update, Delete, Get) with reporting and follows test automation best practices.

---

## Approach
- Automated **API functional testing** (CRUD: Create → Update → Delete → Get)
- Implemented using **Rest Assured** with **TestNG** (`given/when/then` style)
- Test execution order controlled with:
  - **class order in `testng.xml`** → defines the overall sequence of CRUD test classes
  - **dependsOnGroups** → ensures cross-class dependencies (e.g., update depends on create, delete depends on update, get depends on delete)
- **Shared ID reuse** across tests using **TestNG `ITestContext`** (instead of static fields) to support cleaner test flows and parallel safety
- Validations on **status codes** & **response body fields** using Hamcrest matchers
- Unified **HTML report** generated after every run with ExtentReports, automatically saved as **`Unified test result.html`** and opened in Chrome

---

## Tools & Libraries
- **Java** – programming language
- **Maven** – build & dependency management
- **TestNG** – test framework (`@Test`, `dependsOnGroups`, `testng.xml`)
- **Rest Assured** – API testing library
- **Hamcrest** – assertions (`equalTo`, `notNullValue`)
- **JSON-Simple** – build JSON payloads
- **ExtentReports** – generate unified HTML test report (`Unified test result.html`)
- **ReqRes API** – mock API for testing

---

## Project Structure
API_Automation
├── src
│ └── test
│ └── java
│ ├── base # BaseApiTest (shared setup, RequestSpecification)
│ ├── tests # Test classes (Create, Update, Delete, Get)
│ └── utils # ExtentReports setup and listeners
├── pom.xml # Maven dependencies
├── testng.xml # TestNG suite configuration
├── README.md # Project documentation
└── .gitignore # Ignore build, reports, and IDE files


---

## How to Run Tests
### From IDE
- Right-click on `testng.xml` → **Run**

### From Maven
```bash
mvn clean test -DsuiteXmlFile=testng.xml
