# API Automation Framework (Rest Assured + TestNG)

This project demonstrates automated **API functional testing** using the [ReqRes](https://reqres.in/) mock API.  
It covers **CRUD operations** (Create, Update, Delete, Get) with reporting.

---

## Approach
- Automated **API functional testing** (CRUD: Create → Update → Delete → Get)
- Implemented using **Rest Assured** with **TestNG** (`given/when/then` style)
- Test execution order controlled with 
  - **priority** → defines natural execution sequence
  - **dependsOnMethods** → ensures a test runs only if its prerequisite test passes
- **Static ID reuse** across tests to chain requests
- Validations on **status codes** & **response body fields**
- Unified **HTML report** generated after every run with ExtentReports

---

## Tools & Libraries
- **Java** – programming language
- **Maven** – build & dependency management
- **TestNG** – test framework (`@Test`, `testng.xml`)
- **Rest Assured** – API testing library
- **Hamcrest** – assertions (`equalTo`, `notNullValue`)
- **JSON-Simple** – build JSON payloads
- **ExtentReports** – generate HTML test reports (`Unified TestResult.html`)
- **ReqRes API** – mock API for testing



---

## How to Run Tests
### From IDE
- Right-click on `testng.xml` → **Run**


