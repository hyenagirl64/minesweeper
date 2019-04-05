package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import minesweeper.controller.MineSweeperGameController;
import minesweeper.model.MineGrid;
import minesweeper.model.MineSweeperGame;
import minesweeper.model.Space;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Minesweeper");

        int sizeX = 20;
        int sizeY = 20;
        int numMines = 20;

        MineSweeperGame game = new MineSweeperGame(sizeX, sizeY, numMines);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(5,5,5,5));

        MineSweeperGameController controller = new MineSweeperGameController(grid, game);

        primaryStage.setScene(new Scene(grid, 700, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
