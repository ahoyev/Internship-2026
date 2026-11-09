# SwimTeamManager

SwimTeamManager is a Java Spring Boot application that helps swim coaches manage
swimmers and daily attendance in a browser. Inspired by my experience as an
assistant swim coach, the project turns an existing Java console application into
a web application with a dashboard and persistent local file storage.

## Features

- Dashboard with total swimmers, training group count, and swimmers present today.
- Team roster showing swimmer names, ages, and training groups.
- Add swimmers with server-side validation for names, groups, and ages from 1–120.
- Search by full name without capitalization affecting results.
- Edit a swimmer's age and group while keeping their attendance linked.
- Remove a swimmer through a confirmation page, including their attendance records.
- Mark swimmers Present or Absent and view attendance for a selected date.
- Save swimmers, group assignments, and dated attendance automatically to an XML file.
- Keep a backup of the previous save and reload records after restarting.
- Responsive layouts and keyboard-accessible form controls.

## Technologies and structure

Java 21, Spring Boot 4.1.2, Spring MVC, Thymeleaf, Maven, HTML, CSS, and JUnit.

Controllers handle web requests, services manage swimmer and attendance operations,
and `TeamFileStore` reads and writes team data. Templates and CSS provide the web
interface. Automated tests cover swimmer management, attendance, validation, and
file persistence, including reloading records and handling failed writes.

```text
src/main/java/SwimTeamManager/       Application, controllers, services, file storage
src/main/resources/templates/      Thymeleaf pages
src/main/resources/static/css/     Dashboard and form styling
src/test/java/SwimTeamManager/      Automated tests
data/                              Local records, created on first save and ignored by Git
```

## Run in Eclipse on Windows

1. Install JDK 21 or newer and Eclipse with Maven integration.
2. Clone this repository, then choose **File → Import → Maven → Existing Maven Projects**.
3. Select this `SwimTeamManagerWeb` folder, which contains `pom.xml`, and click **Finish**.
4. Open `src/main/java → SwimTeamManager → SwimteammanagerApplication.java`.
5. Right-click the class and choose **Run As → Java Application**.
6. Visit [http://localhost:8080/swimteam-web/](http://localhost:8080/swimteam-web/).

The Maven project targets Java 21. No standalone Maven installation is required.

Alternatively, in PowerShell from this folder:

```powershell
.\mvnw.cmd spring-boot:run
```

Run automated tests with:

```powershell
.\mvnw.cmd test
```

## Website

[Launch the live demo](https://swimteam.onrender.com/). No download is required.

Hosted on Render's free plan for internship portfolio demonstrations. The service may take about a minute to wake after inactivity. Visitors share one roster: use fictional data only. Online data can reset after a restart or deployment. The demo starts with sample swimmers when no saved roster is available. Local file persistence still works when running on your own computer.

## Saved data

By default, records are saved in `data/team-data.xml`, relative to the working
directory used to start the application. Running from this folder puts `data`
beside `pom.xml`. Keep the working directory consistent between runs.
The previous save is stored as `team-data.xml.bak`. Both files stay outside the
build output, and the `data` folder is excluded from Git.

To use a fixed location, set the `SWIMTEAM_DATA_FILE` environment variable to an
absolute filename, or change `swimteam.data-file` in `application.properties`.
In Eclipse, set an environment variable under **Run → Run Configurations →
Java Application → your application → Environment**, then restart.

Back up the data folder. Run one application instance per data file. Stop the app
before restoring a backup. If a saved file cannot be read, startup fails and leaves
the file untouched rather than replacing it with an empty roster.

Groups are currently stored with swimmers; independent empty groups are not yet
supported. The application is a local portfolio project and does not yet include
user accounts, authentication, or a database. Use sample data when demonstrating it.

## What this project demonstrates

The project applies object-oriented Java, MVC separation, form handling, input
validation, persistent file storage, and automated testing to a practical coaching
workflow. It shows the progression from a console menu to a browser application
while preserving the original roster and attendance functionality.
