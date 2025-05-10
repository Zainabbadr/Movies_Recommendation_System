# 🎬 Movie Recommendation System

A Java-based application that reads movie and user data from input files, validates the information, and recommends movies to users based on genres of previously liked movies. The project is rigorously tested using various **white-box testing** techniques and adheres to structured software testing principles.

---

## 📁 Project Structure

```yaml
MovieRecommendationSystem:
  src:
    main:
      java:
        FileHandler.java: "Handles file reading/writing"
        Main.java: "Entry point (if used)"
        MovieManager.java: "Manages movie validation and genre handling"
        RecommendationManager.java: "Orchestrates recommendation logic"
        UserManager.java: "Validates and stores user data"
  test:
    java:
      BasisPathCoverage.java: "Basis path testing for logical paths"
      BranchCoverage.java: "Branch testing (true/false branches)"
      BvaTest.java: "Boundary Value Analysis (if applied)"
      ConditionCoverage.java: "Tests compound conditions individually"
      DataFlowCoverage.java: "Covers all def-use pairs in logic"
      DecisionTableTesting.java: "Tests rules/inputs using decision tables"
      FileHandlerTest.java: "Unit tests for file operations"
      IntegrationTesting.java: "System-level integration tests"
      MovieManagerTest.java: "Movie validation and genre lookup tests"
      RecommendationManagerTest.java: "End-to-end recommendation logic tests"
      StatementCoverage.java: "Ensures all lines of code are executed"
      TestingWithFileHandlerMock.java: "Tests using mocks for controlled file I/O"
      UserManagerTest.java: "User data validation tests"
      invalidMovies.txt: "Sample invalid input file for test cases"
```
---

## 🧠 Features

- ✅ Validates movie titles (e.g., capitalization rules)
- ✅ Validates movie IDs (prefix and uniqueness of digits)
- ✅ Validates user names and IDs (format and uniqueness)
- ✅ Recommends movies based on genres liked by each user
- ✅ Logs the first validation error in the output file (`recommendations.txt`)
- ✅ Comprehensive white-box testing suite

---

## 📌 Input Format

### `movies.txt`
The Matrix,TM123
action,sci-fi
### `users.txt`
Alice Smith,12345678A
TM123

---

## 📤 Output Format (`recommendations.txt`)

Each user will have:
- Line 1: `Name,UserId`
- Line 2: Comma-separated recommended movie titles (or `No Recommendations`)

Errors are also written here if validation fails.

---

## 🧪 Testing Techniques Used

This project rigorously follows white-box testing methodologies:

| Technique              | File                                  |
|------------------------|----------------------------------------|
| ✅ Statement Coverage   | `StatementCoverage.java`              |
| ✅ Branch Coverage      | `BranchCoverage.java`                 |
| ✅ Condition Coverage   | `ConditionCoverage.java`              |
| ✅ Basis Path Coverage  | `BasisPathCoverage.java`              |
| ✅ Data Flow Testing    | `DataFlowCoverage.java`               |
| ✅ Integration Testing  | `IntegrationTesting.java`             |
| ✅ BVA / Decision Table | `BvaTest.java`, `DecisionTableTesting.java` |

---

## 🛠️ How to Run

1. Clone the repo and import it into your Java IDE (e.g., IntelliJ, Eclipse).
2. Add JUnit 5 dependencies if not already included.
3. Run any test suite or file to validate application behavior.
4. Place your input files in the correct format and location, then execute the app.

---

## 📚 Academic Context

This project was created as part of the **Software Testing (CSE337s)** course at **Ain Shams University – Faculty of Engineering**. It focuses on hands-on application of software testing theories and practices.

---

## 🙌 Contributors

- Student Group — Spring 2025  

---

