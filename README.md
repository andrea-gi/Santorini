<p align="center">
  <img src="/src/main/resources/images/santorini.jpg" />
</p>

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
The project documentation includes UML diagrams, JavaDoc and JUnit4 test coverage.

### UML
Both [high level design](/deliveries/final/uml/Concise) and [detailed design]((/deliveries/final/uml/Detailed)) diagrams were created.

The [first one](/deliveries/final/uml/Concise/GeneralUML.svg) is an updated version of the initial UML, featuring a concise and schematic representation of the model-controller implementation.

Meanwhile, the latter consists in multiple diagrams, each one with a specific scope, collapsing redundant information:

* [General packages structure](/deliveries/final/uml/Detailed/Packages.svg) and [messages implemented](/deliveries/final/uml/Detailed/Messages.svg).

* Client
  * [Detailed client application](/deliveries/final/uml/Detailed/Client/DetailedClient.svg). CLI and GUI packages collapsed.
  * [Detailed CLI client](/deliveries/final/uml/Detailed/Client/DetailedCLIClient.svg). Printables and Scenes packages collapsed.
  * [Expanded CLI hierarchy](/deliveries/final/uml/Detailed/Client/ExpandedCLIHierarchy.svg). Focuses on CLI sub-packages hierarchy.
  * [Detailed GUI client](/deliveries/final/uml/Detailed/Client/DetailedGUIClient.svg).

* Server
  * [Detailed controller package](/deliveries/final/uml/Detailed/Server/DetailedController.svg).
  * [Detailed model package](/deliveries/final/uml/Detailed/Server/DetailedModel.svg). Gods package collapsed.
  * [Detailed server package](/deliveries/final/uml/Detailed/Server/DetailedServer.svg).
  * [Model-controller relationships](/deliveries/final/uml/Detailed/Server/ModelControllerRelationships.svg).
  * [Server-controller relationships](/deliveries/final/uml/Detailed/Server/ServerControllerRelationships.svg).
  * [Server application classes relationships](/deliveries/final/uml/Detailed/Server/ServerClassesRelationships.svg).
  * [Expanded gods hierarchy](/deliveries/final/uml/Detailed/Server/GodsHierarchy.svg). Gods classes in model package are expanded and clarified.
  * [Expanded gods-model relationships](/deliveries/final/uml/Detailed/Server/ExpandedGodsModelRelationships.svg).
  

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
:warning: Make sure that the terminal font is monospaced and that the terminal size is **at least 230x50**.

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

## Miscellaneous
### Error codes

Error Code | Description
---------- | -------------
**CXXX** | **Client Errors**
C001 | IOException while client is sending a message. Usually caused by server unreachable.
C002 | IOException while client is waiting for a message. Usually caused by server unreachable.
C003 | Fatal error in client. Game cannot continue.
C004 | Client could not establish a connection to the server.
**SXXX** | **Server Errors**
S001 | Another client has disconnected. Game has ended.
S002 | Server is not responding. Heartbeat timeout expired.
S003 | Severe server error. A message received by the game server was irreparably corrupted.

### Server Log
The best way to run the server is via terminal interface (with ANSI colors support), since it features a detailed log, useful for debug reasons (e.g. it contains messages exchanged, along with their content).
The implemented colors are:

![Send](https://via.placeholder.com/15/00ff00/000000?text=+) when a message is sent by the server

![Receive](https://via.placeholder.com/15/0000ff/000000?text=+) when a message is received by the server

![Red](https://via.placeholder.com/15/ff0000/000000?text=+) or <ins>underline</ins> when an important state change occurs (e.g. current player changes or server restarts)

Clients are assigned a temporary random numeric ID as soon as they connect to the server. They are identified by their ID until name and color are chosen.
Message sender or recipient can be easily recognised by the player color chosen during the registration to the server, possible colors are:

![Red](https://via.placeholder.com/15/ff0000/000000?text=+) Red

![Blue](https://via.placeholder.com/15/0000ff/000000?text=+) Blue

![Magenta](https://via.placeholder.com/15/ff00ff/000000?text=+) Magenta

Headless server works continuously in background (not suggested, as it cannot be shut down without killing JVM process and there is no visible log).



## Made by:
* [Giarduz Andrea](https://github.com/andrea-gi/)
* [Grosso Veronica](https://github.com/Veronica-gg/)
* [Guerrieri Lorenzo](https://github.com/Lorenzo-Guerrieri-98)
