package minesweeper.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MineGrid {

    private Space[][] grid;
    protected final int xSize;
    protected final int ySize;

    public MineGrid(int xSize, int ySize, int numMines) {

        grid = createEmptyGrid(xSize, ySize);
        this.xSize = xSize;
        this.ySize = ySize;

        Random generator = new Random();
        for(int i = 0; i < numMines; i++) {
            plantMine(generator);
        }
    }

    private Space[][] createEmptyGrid(int xSize, int ySize) {
        Space[][] grid = new Space[xSize][ySize];

        //populate the grid with empty, blank spaces that know their co-ordinates
        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[x].length; y++) {
                grid[x][y] = new Space(x, y);
            }
        }

        return grid;
    }

    private void plantMine(Random generator) {
        boolean minePlanted = false;

        while(!minePlanted) {
            int mineX = generator.nextInt(grid.length);
            int mineY = generator.nextInt(grid[mineX].length);

            Space randomSpace = getSpace(mineX, mineY);

            //we don't want to put a mine in a space that already contains one
            if (!randomSpace.isMine()) {
                randomSpace.setIsMine(true);

                //now inform the surrounding spaces their neighbor is a mine
                for(Space space : getSpacesSurrounding(randomSpace)) {
                    space.addSurroundingMine();
                }

                minePlanted = true;
            }
        }
    }

    public Space getSpace(int x, int y) {
        if(isPointInGrid(x, y)) {
            return grid[x][y];
        } else {
            throw new IndexOutOfBoundsException("Grid Co-Ordinates out of bounds");
        }
    }

    // given the co-ordinate of a space, get the 8 spaces around it
    public List<Space> getSpacesSurrounding(int x, int y) {
        List<Space> surroundingSpaces = new LinkedList<>();

        for(int subX = x - 1; subX <= x + 1; subX++) {
            for(int subY = y - 1; subY <= y + 1; subY++) {
                if(isPointInGrid(subX, subY) && !(subX == x && subY == y)) {

                    surroundingSpaces.add(grid[subX][subY]);

                }
            }
        }

        return surroundingSpaces;
    }

    public List<Space> getSpacesSurrounding(Space space) {
        return getSpacesSurrounding(space.x, space.y);
    }

    public List<Space> getAllSpaces() {
        List<Space> spaces = new LinkedList<>();

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[x].length; y++) {
                spaces.add(grid[x][y]);
            }
        }
        return spaces;
    }

    public boolean isRowInGrid(int x) {
        return x >= 0 && x < grid.length;
    }

    public boolean isPointInGrid(int x, int y) {
        return isRowInGrid(x) && y >= 0 && y < grid[x].length;
    }
}
