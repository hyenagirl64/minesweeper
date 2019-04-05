package minesweeper.controller;

import javafx.scene.layout.GridPane;
import minesweeper.model.MineSweeperGame;
import minesweeper.model.Space;

import java.util.List;

public class MineSweeperGameController {

    private final MineSweeperGame game;
    private MineSweeperButton[][] buttons;

    public MineSweeperGameController(GridPane playArea, MineSweeperGame newGame) {
        this.game = newGame;

        buttons = new MineSweeperButton[game.getGridXSize()][game.getGridYSize()];
        List<Space> spaces = this.game.getAllSpaces();

        for(Space space: spaces) {
            MineSweeperButton button = new MineSweeperButton(this, space);
            buttons[space.x][space.y] = button;
            playArea.add(button, space.x, space.y);
        }
    }

    public void reveal(Space space) {

        List<Space> updates = game.reveal(space);

        for(Space toUpdate : updates) {
            buttons[toUpdate.x][toUpdate.y].reveal();
        }
    }
}
