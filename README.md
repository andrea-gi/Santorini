![Santorini](/src/main/resources/images/santorini.jpg)

# Software Engineering - Final Examination 2019-2020
**Group PSP034**

> This final examination project consists in an online multiplayer implementation of the board game Santorini, realised using Java.

## Implemented Features
### Standard Features
* Complete Rules
  * 2 or 3 players game
  * Simple gods (1-10, except Hermes)
* Socket Connection
* CLI
* GUI (JavaFX)

### Advanced Features
* Advanced gods (5):
  * Hera
  * Hestia
  * Limus
  * Triton
  * Zeus

## Documentation
The project documentation includes UML diagrams (both high level design and detailed design), JavaDoc and JUnit4 test coverage.

### UML
TODO

### JavaDoc
TODO

### Test Coverage
TODO

## How to start JARs
:warning: Java JRE version 9 or higher is required.

:warning: A terminal with full ANSI escape sequences, CSI sequences and UTF-8 support is required to run the terminal (CLI client and server log) properly. UNIX terminal, Windows Terminal or ConEmu are suggested.

Both `client.jar` and `server.jar` contain all the necessary dependencies (JavaFX) for Windows, Linux and MacOS.

### Run Server
Download `server.jar` from FOLDER. TODO

In order to start server listening to socket connections on the default port (`2020`)  run:
```
java -jar server.jar
```

Otherwise, to start a server listening to socket connections on a specified valid `PORT` (otherwise `2020` will be used) run:
```
java -jar server.jar PORT
```

### Run Client
Download `client.jar` from FOLDER. TODO

#### CLI
:warning: Make sure that the terminal font is monospaced and that the terminal size is at least 230x50.

In order to start a Command Line Interface (CLI) client run:
```
java -jar client.jar cli
```
If you are experiencing troubles, change the font size until it looks like this:

![Screen Test](/deliveries/cli_screentest.jpg)

![Title](/deliveries/cli_intro.jpg)

#### GUI
In order to start a GUI client, you can just double-click `client.jar`, or run:
```
java -jar client.jar
```

## How to create JARs
If you would rather create your own JARs, you can use the included Maven profiles:
* `Server` profile generates `server.jar` (active by default).
* `Client` profile generates `client.jar`.

### Instructions (from JetBrains IntelliJ IDEA)
:warning: Only a single profile should be active when creating a package.

1. Open Maven sidebar
1. Open Profiles and Santorini → Lifecycle
1. Select a profile
1. Run Santorini → Lifecycle → Clean
1. Run Santorini → Lifecycle → Package
1. Repeat for each desired profile

### Made by:
* ![Giarduz Andrea](https://github.com/andrea-gi/)
* ![Grosso Veronica](https://github.com/Veronica-gg/)
* ![Guerrieri Lorenzo](https://github.com/Lorenzo-Guerrieri-98)
