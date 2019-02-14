+ The project is using minimax to create an AI chess player. The rule is like m tic-tac-toe in a n*n chess board.

+ The minimax class is dealing with the main portion of the searching algorithm. In the early experiments I found the speed of the pure minimax is too slow. As a result, I using alpha-beta pruning to reduce the branches of the search tree. 

+ After applying minimax and alpha-beta pruning, the running time is around 5 seconds per move. (depth = 4 and 17*17 board and 5 chess in a line is wining) The AI chess player is relatively weak by the search depth of 4. 

+ Recently I have applied the heuristic function to further help the pruning progress. It has a great effect on the performance of the AI. The running time has been reduced to round 10% of the previous version.
