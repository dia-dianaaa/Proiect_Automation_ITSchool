# Proiect_Automation_ITSchool

UI test automation project for the IT School course: **Selenium 4**, **TestNG**, **Maven**, **Log4j**, **ChainTest**, and **GitHub Actions**. The framework is site-agnostic; change `baseUrl` when you automate your final project site.

Related API tests live in [Automation_API](https://github.com/dia-dianaaa/Automation_API).

## Prerequisites

| Tool | Version |
|------|---------|
| JDK | 26 (Eclipse Temurin recommended) |
| Maven | 3.9+ |
| Browser | Chrome or Edge (drivers via Selenium Manager) |
| MySQL | Optional, for JDBC helpers later |

Set `JAVA_HOME` to your JDK 26 installation and ensure `java` and `mvn` are on your `PATH`.

## IDE setup (Cursor / VS Code)

Open this folder in Cursor. Install the recommended extensions when prompted (see [`.vscode/extensions.json`](.vscode/extensions.json)):

- Extension Pack for Java (TestNG runner, Maven, debugger)
- XML (POM, TestNG suites, `log4j2.xml`)
- GitHub Actions
- SQLTools + MySQL driver (optional)

Workspace Java home is configured in [`.vscode/settings.json`](.vscode/settings.json). Reload the window after installing extensions.

IntelliJ IDEA is also supported; shared project metadata is under `.idea/` (user-specific IDE files are gitignored).

## Configuration

| File | Purpose |
|------|---------|
| [`src/test/resources/config.properties`](src/test/resources/config.properties) | `baseUrl`, MySQL JDBC settings |
| [`src/test/resources/config.properties.example`](src/test/resources/config.properties.example) | Template / documentation |

For your **final site**, update `baseUrl` in `config.properties`. Use `config.local.properties` for secrets or machine-specific values (that file is **gitignored**). Do not commit real database passwords.

## Project structure

```
src/test/java/
  HelperMethods/     # Element, alert, database helpers
  Logger/            # Log4j utilities
  ObjectData/        # POJOs for XML test data
  Pages/             # Page Object Model (add your pages here)
  SharedData/        # ConfigReader, browser factory, ShareDataDriver base
  Tests/             # TestNG test classes
  XmlReader/         # XML data loader
src/test/resources/  # config, log4j2, chaintest
Suites/              # SmokeSuite.xml, RegresionSuite.xml
.github/workflows/   # CI workflow
```

Tests extend [`ShareDataDriver`](src/test/java/SharedData/Browser/ShareDataDriver.java), which opens the browser, navigates to `baseUrl`, and quits after each method.

## Running tests locally

Default smoke suite (headed Chrome):

```bash
mvn test
```

Custom suite and browser:

```bash
mvn clean test -PCustomSuite -DsuiteXmlFile=Suites/RegresionSuite -Dbrowser=edge
```

Headless (same as CI):

```bash
mvn clean test -PCustomSuite -DsuiteXmlFile=Suites/SmokeSuite -Dci_cd=true -Dbrowser=chrome
```

Reports and logs:

- ChainTest HTML: `target/chaintest/Index.html`
- Per-test logs: `target/logs/suite/`
- Merged log: `target/logs/RegressionLogs.log`

## GitHub Actions

Workflow: [`.github/workflows/maven.yml`](.github/workflows/maven.yml)

1. Open **Actions** → **Automation ITSchool** → **Run workflow**
2. Choose **SmokeSuite** or **RegresionSuite** and **chrome** or **edge**
3. Download artifacts **Run results** for `chaintest` and `logs`

## Git ignore notes

Build output (`target/`), local secrets (`config.local.properties`, `.env`), Cursor (`.cursor/`), and most user-specific IDE files are ignored. Committed workspace files include `.vscode/extensions.json`, `.vscode/settings.json`, and shared `.idea` project settings.

## License

Course / personal educational project.
