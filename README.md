# QuizAI Certification System

QuizAI is a Spring Boot application for creating quizzes, optionally generating questions with Groq, calculating scores, and issuing verifiable PDF certificates for passing users.

## Run immediately

Requirements: Java 17+ and Maven 3.8+.

```bash
mvn clean test
mvn spring-boot:run
```

Then open <http://localhost:8080>. The default configuration uses a local file-based H2 database and seeds a built-in question bank, so MySQL and an AI key are not required for the core quiz flow.

## Optional integrations

Set `GROQ_API_KEY` to enable AI question generation. To use MySQL instead of H2, set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `DB_DRIVER`. Full details are in [SETUP_INSTRUCTIONS.txt](SETUP_INSTRUCTIONS.txt), and placeholder values are in `application-example.properties`.

## Main routes

| Route | Purpose |
| --- | --- |
| `/` | Home page |
| `/generate` | Start a quiz and optionally generate AI questions |
| `/history` | Leaderboard |
| `/certificate/verify` | Verify a certificate number |
| `/certificate/download/{id}` | Download a generated PDF certificate |

## Verification performed

The project has been rebuilt from a clean state, its Spring context test passes, all primary pages respond successfully, the built-in quiz flow calculates a score correctly, certificate generation is idempotent, and the generated PDF is a valid one-page A4 document.
