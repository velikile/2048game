Game_part4
==========
This is a 2048 Game repo with a undo save and load buttons added
There is also A small Maze Puzzle
in addition there is an AI solver for the 2048 Game 
The Solver is running as a Server so that many differnet clients can connect to it And recieve AI solution or a hint
patterns:
MVP is used in the corrolation between the Game Data And the GUI side a presenter is sitting in between and managing the process
The gui and the model are observable type and the presenter is a observer
the Observer pattern is used for the update of the board 
