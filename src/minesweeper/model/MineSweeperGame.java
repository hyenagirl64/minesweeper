package minesweeper.model;

import java.util.LinkedList;
import java.util.List;

public class MineSweeperGame {

    private int remainingSpaces;
    private MineGrid gameGrid;
    private boolean failure = false;

    public MineSweeperGame(int sizeX, int sizeY, int numMines) throws Exception {
        remainingSpaces = (sizeX * sizeY) - numMines;

        if(remainingSpaces <= 0) {
            throw new Exception("Invalid Game: too many mines");
        }

        gameGrid = new MineGrid(sizeX, sizeY, numMines);
    }

    public List<Space> reveal(Space space) {
        return reveal(space.x, space.y);
    }

    //given a request to reveal the space at x, y, return all newly revealed spaces
    //as an added effect, the game tracks how many non-mine spaces remain to be revealed
    //if a mine is hit, or the last non-mine space is revealed, all remaining spaces update
    //this method contains most of game logic.
    public List<Space> reveal(int x, int y) {

        List<Space> updates = new LinkedList<>();
        Space revealedSpace = gameGrid.getSpace(x, y);

        //if this space is a mine, the game ends in failure and we reveal all remaining spaces
        if(revealedSpace.isMine()) {
            failure = true;
            remainingSpaces = 0;
            //we don't need to update anything else if this happens.  It will be caught by the game-over check
            // at the end of the method because there are no spaces left to reveal


        // if this space is not a mine, either return an empty update if it is already revealed
        // or, return a list of all the spaces revealed by revealing this space
        } else {

            LinkedList<Space> stack = new LinkedList<>();
            stack.push(revealedSpace);

            //once the stack is empty, we know we have enqueued all updates
            while(stack.size() > 0) {
                Space check = stack.pop();

                // if we've already revealed this space, ignore it
                if(!check.isRevealed()) {
                    updates.add(check);

                    // by marking this space as revealed now, we avoid backtracking to it from it's neighbors
                    check.reveal();

                    //if this space also has no mines around it, reveal all neighboring spaces
                    if (check.getSurroundingMines() == 0) {
                        for (Space toAdd : gameGrid.getSpacesSurrounding(check.x, check.y)) {
                            // if the surrounding space is revealed already, don't bother pushing
                            // this isn't strictly necessary to do here, since we check again later anyways, but it
                            // helps prune the stack
                            if (!toAdd.isRevealed()) {
                                stack.push(toAdd);
                            }
                        }
                    }
                }
            }

            //however many spaces we have revealed during this check, subtract them from the remaining spaces
            remainingSpaces = remainingSpaces - updates.size();
        }

        //after everything else has been done, if the game is over, reveal all remaining spaces
        if(isGameOver()) {
            for(Space space : gameGrid.getAllSpaces()) {
                if(!space.isRevealed()) {
                    updates.add(space);
                    // this reveal is unnecessary, but it keeps things clean
                    space.reveal();
                }
            }
        }

        return updates;
    }

    public List<Space> getAllSpaces() {
        return gameGrid.getAllSpaces();
    }

    public int getGridXSize() {
        return gameGrid.xSize;
    }

    public int getGridYSize() {
        return gameGrid.ySize;
    }

    //returns true if there are no spaces left to reveal that aren't mines
    public boolean isGameOver() {
        return remainingSpaces == 0;
    }

    // returns true if and only if the game is over and the game has been won
    public boolean isGameWon() {
        return isGameOver() && !failure;
    }

    //returns true if and only if the game is over and the game has been lost
    public boolean isGameLost() {
        return isGameOver() && failure;
    }
}
