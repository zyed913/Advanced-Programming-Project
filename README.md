# Rescue Robot Pathfinder

## Project Overview

Rescue Robot Pathfinder is a Java-based simulation game that demonstrates artificial intelligence, pathfinding algorithms, recursion, sorting, file handling, and object-oriented programming.

The player controls a rescue robot in a 10x10 grid environment filled with obstacles and survivors. The robot can be controlled manually or can automatically navigate using the BFS (Breadth-First Search) algorithm to rescue survivors.

---

## Project Objectives

- Create an interactive rescue simulation.
- Implement AI pathfinding using BFS.
- Demonstrate recursion in a practical application.
- Implement sorting algorithms.
- Practice file handling with Save and Load functionality.
- Apply Object-Oriented Programming concepts.

---

## Features

### Robot Navigation
- Manual movement using direction buttons.
- Automatic movement using BFS AI.

### Grid Environment
- 10x10 game board.
- Obstacles block movement.
- Survivors must be rescued.

### BFS Pathfinding
- Finds the shortest path to the nearest survivor.
- Displays the calculated path.

### Recursion
- Scan Area feature uses recursion.
- Counts reachable safe cells from the robot's position.

### Save and Load
- Saves game progress to a file.
- Restores saved progress.

### Leaderboard
- Displays scores in descending order.
- Uses Bubble Sort.

### Statistics
- Tracks:
  - Moves
  - Score
  - Survivors Rescued

---

## Algorithms Used

### Breadth-First Search (BFS)

BFS is used to find the shortest path between the robot and the nearest survivor.

Steps:
1. Start from the robot's current position.
2. Explore neighboring cells.
3. Avoid obstacles.
4. Continue until a survivor is found.
5. Reconstruct the shortest path.

### Recursion

The Scan Area feature recursively explores all reachable cells.

Example:

```java
recursiveScan(row - 1, col, visited);
recursiveScan(row + 1, col, visited);
recursiveScan(row, col - 1, visited);
recursiveScan(row, col + 1, visited);
```

### Bubble Sort

Used for sorting leaderboard scores from highest to lowest.

Steps:
1. Compare adjacent values.
2. Swap if needed.
3. Repeat until sorted.

---

## Object-Oriented Programming Structure

### Main.java
Handles the graphical user interface.

### Robot.java
Stores robot position and movement.

### Cell.java
Represents individual grid cells.

### Grid.java
Stores and manages the game board.

### PathFinder.java
Implements BFS pathfinding.

### GameManager.java
Coordinates game logic.

### Statistics.java
Tracks score, moves, and rescues.

### SaveManager.java
Handles file saving operations.

---

## File Handling

Game data is stored in:

```
save.txt
```

Saved information includes:
- Robot position
- Score
- Moves
- Survivors rescued
- Survivor status

---

## User Interface

The GUI contains:

- Game Grid
- Information Panel
- BFS AI Button
- Scan Area Button
- Save Button
- Load Button
- Leaderboard Button
- Reset Button

---

## Testing

The following features were tested successfully:

| Feature | Status |
|----------|----------|
| Robot Movement | Passed |
| BFS AI | Passed |
| Obstacle Detection | Passed |
| Survivor Rescue | Passed |
| Save Game | Passed |
| Load Game | Passed |
| Recursion Scan | Passed |
| Leaderboard Sorting | Passed |

---

## Screenshots

Include:

1. Main Screen
2. BFS AI Running
3. Scan Area Feature
4. Save Feature
5. Load Feature
6. Leaderboard

---

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- BFS Algorithm
- Recursion
- Bubble Sort
- File Handling

---

## Conclusion

The Rescue Robot Pathfinder project successfully demonstrates multiple programming concepts including object-oriented programming, search algorithms, recursion, sorting algorithms, graphical user interfaces, and file handling. The project provides an interactive simulation where a robot intelligently rescues survivors while avoiding obstacles.
