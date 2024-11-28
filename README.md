# ConnectFour
### Project Description
This project is a simple Connect Four game (variant 4x5). Each player during their turn has to put their piece in a column. Turns are displayed at the bottom of the screen. Player can either use mouse (drop by dragging) or keyboard (keys 1-5 for corresponding columns). The objective of the game is to place the pieces of the same colour four in a row - either horizontally, vertically or diagonally in both direction. When a player wins, they have the right to start the next round.

### Project Challenges
This project is implemented using JNI. The game logic is written in C++, while GUI is written purely in Java. Additionally, there is a ConnectFourTest.java class, which contains Unit Tests for winning conditions, proper board initialization and testing the game logic. Both projects (C++ and Java) are communicating in real time via shared library.
