# Chain Reaction

A console-based **Chain Reaction** board game built in Java. 2–6 players take turns placing atoms on an N×N grid — cells explode when they reach capacity, cascading chain reactions into neighboring cells and converting them to your color. Last player standing wins!

## Features

- 2–6 player support with color-coded atoms
- Configurable grid size (4×4 to 15×15)
- Animated chain reaction explosions with ANSI colored output
- Player elimination and win detection mid-explosion
- Cross-platform color support via [JANSI](https://github.com/fusesource/jansi)

## How to Play

1. Choose grid size and number of players
2. Each player picks a color
3. On your turn, enter row and column (1-based) to place an atom
4. You can only place on empty cells or cells you own
5. When a cell reaches its capacity (corners: 2, edges: 3, middle: 4), it explodes into orthogonal neighbors
6. Explosions convert neighbors to your ownership and can trigger further chain reactions
7. A player with no cells remaining after the first round is eliminated
8. The last player with cells on the board wins!

## Running the Game

### Option 1: Docker (Recommended)

No Java installation required — just Docker:

```sh
docker pull nimitsajal/chain-reaction
docker run -it nimitsajal/chain-reaction
```

### Option 2: Build from Source

Requires Java 21+.

```sh
javac -cp lib/jansi-2.4.1.jar src/*.java -d out
java -cp "out;lib/jansi-2.4.1.jar" Main        # Windows
java -cp "out:lib/jansi-2.4.1.jar" Main         # Linux/macOS
```

### Option 3: Docker Build Locally

```sh
docker build -t chain-reaction .
docker run -it chain-reaction
```

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
