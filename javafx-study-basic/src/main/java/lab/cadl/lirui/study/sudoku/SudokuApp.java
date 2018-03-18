package lab.cadl.lirui.study.sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import lab.cadl.lirui.study.calculator.CalculatorPane;

public class SudokuApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("数独");

        primaryStage.setScene(SudokuPane.createScene());
        primaryStage.show();
}
}
