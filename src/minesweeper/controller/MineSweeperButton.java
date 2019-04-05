package minesweeper.controller;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import minesweeper.model.Space;

public class MineSweeperButton extends Button {

    public static final int MIN_WIDTH = 30;
    public static final int MIN_HEIGHT = 30;

    public final Space represents;

    public MineSweeperButton(final MineSweeperGameController game, Space space) {
        super();
        this.represents = space;
        this.setOnMouseClicked((MouseEvent event) -> {
            if(event.isSecondaryButtonDown() || event.isControlDown()) {
                if(this.getText().equals("!")) {
                    this.setText("");
                } else {
                    this.setText("!");
                }
            } else {
                game.reveal(space);
            }
        });

        this.setMinSize(MIN_WIDTH, MIN_HEIGHT);
    }

    public void reveal() {
        if(represents.isMine()) {
            this.setText("M");
        } else if (represents.getSurroundingMines() > 0) {
            this.setText(represents.getSurroundingMines() + "");
        }

        this.setDisabled(true);
    }
}
