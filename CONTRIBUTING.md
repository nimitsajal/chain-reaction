# Contributing to Chain Reaction

Thanks for your interest in contributing! This is a simple project and all contributions are welcome.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/<your-username>/chain-reaction.git`
3. Create a branch: `git checkout -b my-feature`
4. Make your changes
5. Test by compiling and running:
   ```sh
   javac -cp lib/jansi-2.4.1.jar src/*.java -d out
   java -cp "out;lib/jansi-2.4.1.jar" Main
   ```
6. Commit and push: `git push origin my-feature`
7. Open a Pull Request

## Project Structure

All source files live in `src/` with no packages (default package). See [AGENTS.md](AGENTS.md) for architecture details.

## Guidelines

- Keep all classes in the default package (no `package` declarations)
- Game logic stays as static methods in `Main.java`
- Use ANSI colors via the `TextColor` enum and `printTextWithColor()` — no direct escape codes elsewhere
- Input validation should follow the existing `try/catch InputMismatchException` pattern with `sc.nextLine()` to clear bad input
- No build tools — compile with `javac`, dependency jars go in `lib/`

## Ideas for Contribution

- Add an AI/bot player
- Add unit tests
- Separate game logic from UI rendering
- Add a replay/undo feature
- Improve grid rendering (Unicode atoms, better spacing)

## Reporting Issues

Open a GitHub issue with steps to reproduce and your environment (OS, Java version).

