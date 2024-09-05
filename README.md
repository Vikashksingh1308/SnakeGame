# Snake Game 🐍

This is a simple Snake Game implemented in Java using the `javax.swing` package. The game follows the classic snake mechanics where the player controls a snake to eat apples, causing the snake to grow in size. The game ends when the snake collides with the walls or with itself.

## Features
- **Randomly placed apples**: Apples spawn in random locations on the screen for the snake to eat.
- **Growing snake**: Each apple eaten increases the length of the snake.
- **Collision detection**: The game ends when the snake runs into itself or the walls.
- **Score display**: The current score (number of apples eaten) is displayed on the screen.
- **Game over screen**: A "Game Over" message appears when the game ends, along with the final score.

## How to Play
Use the arrow keys to control the direction of the snake:
- **Up Arrow**: Move up
- **Down Arrow**: Move down
- **Left Arrow**: Move left
- **Right Arrow**: Move right

The snake cannot reverse its direction directly (e.g., moving left when the snake is moving right).

# Project Structure
- **GameFrame.java**: The main frame that holds the game panel.
- **GamePanel.java**: Contains the game logic, including snake movement, apple spawning, collision detection, and score tracking.
- **SnakeGame.java**: The entry point for running the game.

# Technologies Used
- Java
- Swing (for the GUI)
- AWT (for handling graphics and events)

## Installation & Running the Game
Clone the repository to your local machine:

```bash
git clone https://github.com/Vikashksingh1308/snake-game.git