# COMP250_Mondrian-Blocky_Game
🟥🟨🟩🟦Each player is randomly assigned their own goal to work towards: either to create the largest connected ”blob” of a given color or to put as much of a given color on the outer perimeter as possible.
This assignment consists of implementing a visual game in which players apply operations such as rotations to a recursive structure in order to work towards a goal. The main data structure
can be represented with a quad-tree

<img src="" height="250" width="250">

## Assignment 3 March 23, 2023
### Goals and Scoring
At the beginning of the game, each player is assigned a randomly-generated goal. There are two
types of goal:

• Blob goal. The player must aim for the largest “blob” of a given color c. A blob is a group
of orthogonally connected blocks with the same color. That is, two blocks are considered
connected if their sides touch; touching corners does not count. The player’s score is the
number of unit cells in the largest blob of color c.

• Perimeter goal. The player must aim to put the most possible units of a given color c on the
outer perimeter of the board. The player’s score is the total number of unit cells of color c
that are on the perimeter. There is a premium on corner cells: they count twice towards the
score.

Notice that both goals are relative to a particular color. We will call that the target color for the
goal.

# PART - I: Set up the board
With a good understanding of the data structure, you are now ready to start working on the
completion of the class Block.

# PART - II: Make the game playable
Time to make the game real by allowing players to be able to select a block from the board and
apply a move of their choice.

# PART - III: Implementing scoring 
Now that player can play the game, let’s get our scoring system working.
