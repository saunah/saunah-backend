## 🌴 Branch Names

The `main` branch of the repository is protected and code can only be added via pull-requests.
Code should always be added on a feature branch according to the following guideline:

- **`feature/<branch-name>`** for new features
- **`patch/<branch-name>`** for small changes in the code (e.g. typos, bugs)
- **`refactor/<branch-name>`** for big changes in the architecture
- **`doc/<branch-name>`** for documentation

The `<branch-name>` has to be in kebab-case (lowercase and words seperated by dashes).

## 📝 Testing Strategy

The app is tested with unit tests that should cover all logic within the application.

To check for appropriate test-coverage, SonarCould Quality Gates are evaluated on every push, which check for sufficient coverage.

To provide a datasource when running tests, the project is configured to use a non-persistant in-memory database (H2) for tests. It should, however, be possible to keep the functionality defined in repository classes the same as if the regular PostgreSQL datasource is used, due to the datasource abstraction layer of Spring Boot.
