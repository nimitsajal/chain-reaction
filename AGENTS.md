# AGENTS.md

## Project Overview

Console-based **Chain Reaction** board game in Java (no build tool, no frameworks). 2–6 players take turns placing atoms on an N×N grid; cells explode when they reach capacity, cascading into neighbors.

## Architecture

All logic lives in `src/` with no packages — files are in the default package:

- **Main.java** — Entry point and entire game engine (grid init, game loop, explosion recursion, terminal UI rendering)
- **Cell.java** — Grid cell state: `value` (atom count) and owning `Player`
- **CellType.java** — Enum defining cell capacity by position: `CORNER(2)`, `EDGE(3)`, `MIDDLE(4)`
- **Player.java** — Player identity: `id` + `TextColor`
- **Move.java** — Row/col pair with validation (0-based internally, 1-based user input)
- **TextColor.java** — Enum mapping ANSI escape codes for colored terminal output

## Key Mechanics

- **Explosion recursion**: `Main.incrementValue()` is recursive — when a cell's value reaches its `CellType` capacity, it resets to 0 and increments all orthogonal neighbors, each call re-rendering the grid with a 100ms sleep for animation.
- **Cell ownership**: A player can only place on empty cells or cells they own. Explosions convert neighbor cells to the current player's ownership.
- **Grid coordinates**: User inputs are 1-based; internal arrays are 0-based. Conversion happens in `getMoveFromUser()`.

## Build & Run

No Maven/Gradle. JANSI jar lives in `lib/`. Compile and run:

```sh
javac -cp lib/jansi-2.4.1.jar src/*.java -d out
java -cp "out;lib/jansi-2.4.1.jar" Main
```

### Docker

```sh
docker build -t chain-reaction .
docker run -it chain-reaction
```

Multi-stage `Dockerfile` uses `eclipse-temurin:21`. Note: classpath separator is `;` on Windows, `:` on Linux (Dockerfile uses `:`).

## Conventions

- No package declarations — all classes in default package
- All game logic is in static methods within `Main.java`
- Terminal UI uses ANSI escape codes (`\033[H\033[2J` for clear screen, `TextColor` enum for colors)
- Input validation loops use `try/catch InputMismatchException` pattern with `sc.nextLine()` to clear bad input
- Model classes (`Cell`, `Player`, `Move`) are plain Java beans with getters/setters

## Known Gaps

- `isGameOver` is never set to `true` — the win condition is not implemented
- No tests exist
- No separation between game logic and UI rendering

