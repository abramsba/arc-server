# Arc

Arc is a Lineage 2 server emulator.

* Network code is MMOCORE from aCis.
* All characters and game objects are entities and their attributes are organized into components. 
* Collision detection is aCis geodata
* Visibility of objects is managed by a quad tree data structure
* Navigation from locations in the game world is managed by a bi-directional graph

In it's current state it supports multiple players and basic AI navigation. Combat, skills, and vanilla game services (castle sieges, 7 signs, etc) have been removed. 

Video play list can be found here:

https://www.youtube.com/playlist?list=PLvqcCDZNGKEp0wS2izyUlqH4HTcilAONp

## Compiling

Requirements:

* Ant
* Java 1.8
* L2D Geodata: https://acis.i-live.eu/index.php?topic=7428.0

To build the jar files, simply run `ant`. Ant will compile `game.jar` and `login.jar` and place them in the `dist/` folder.

## Configuration

Copy `config.example.json` to `config.json`. Edit `hostname` under `login` and `game` to bind the server to a certain port. For clients connecting to the server on the same network, `localHostname` will be used instead of `hostname`. 

## Running

Two processes need to the run, one for the login server and another for the game server. 

```
$ java -jar login.jar
$ java -jar game.jar
```

To connect, you can either modify `l2.ini` on the client to point to the server, or reroute `L2Authd.lineage2.com` via your hosts file.
