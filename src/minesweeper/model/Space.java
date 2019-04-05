package minesweeper.model;

public class Space {

    public final int x;
    public final int y;
    private boolean isMine = false;
    private int surroundingMines = 0;
    private boolean isRevealed = false;

    public Space(int xcoord, int ycoord) {
        this.x = xcoord;
        this.y = ycoord;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    protected void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    protected void addSurroundingMine() {
        surroundingMines++;
    }

    protected void reveal() {
        isRevealed = true;
    }
}
