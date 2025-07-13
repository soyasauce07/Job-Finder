# Job Finder

Job Finder is a modern Java-based desktop application that scrapes internship listings from Internshala based on user-defined keywords. It presents results in a visually styled GUI with dynamic theme switching and gradient backgrounds.

## Features

- Keyword-based job search
- Internshala job scraping
- Light and dark mode toggle
- Live gradient UI with a modern design
- Transparent job cards with stipend and duration info
- Clickable application links
- Loading animation during search


## Technologies Used

- Java Swing
- FlatLaf (for modern UI themes)
- Jsoup (for HTML scraping)

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- Git Bash (recommended to run the app)

### Clone the Repository

```bash
git clone https://github.com/soyasauce07/job-finder.git
cd job-finder
```

### Run the Project

Use Git Bash, VS Code terminal, or any CLI that supports Maven commands:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.jobaggregator.Main"
```

### Project Structure

job-finder/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── jobaggregator/
│                   ├── Main.java
│                   ├── Job.java
│                   ├── Scraper.java
│                   └── JobDAO.java
├── pom.xml


