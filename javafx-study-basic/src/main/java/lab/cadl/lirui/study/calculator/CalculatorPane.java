package lab.cadl.lirui.study.calculator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class CalculatorPane extends GridPane {
    private TextArea numberDisplay;
    private Label resultLabel;
    private String inputString;
    private int result = 0;

    private static class ButtonDesc {
        String text;
        int row;
        int col;
        EventHandler<ActionEvent> handler;

        public ButtonDesc(String text, int row, int col, EventHandler<ActionEvent> handler) {
            this.text = text;
            this.row = row;
            this.col = col;
            this.handler = handler;
        }
    }

    public CalculatorPane() {
        setPadding(new Insets(10));
        setHgap(8);
        setVgap(8);

        numberDisplay = new TextArea();
        numberDisplay.setEditable(false);
        numberDisplay.setPrefHeight(100);
        setConstraints(numberDisplay, 0, 0, 3, 1);

        resultLabel = new Label("0");
        resultLabel.setPrefWidth(100);
        resultLabel.setFont(new Font(32));
        resultLabel.setAlignment(Pos.CENTER_RIGHT);
        setConstraints(resultLabel, 3, 0);
        getChildren().addAll(numberDisplay, resultLabel);

        ButtonDesc[] buttonDescList = new ButtonDesc[] {
                new ButtonDesc("1", 1, 0, this::onNumberInput),
                new ButtonDesc("2", 1, 1, this::onNumberInput),
                new ButtonDesc("3", 1, 2, this::onNumberInput),
                new ButtonDesc("CE", 1, 3, this::onCE),
                new ButtonDesc("4", 2, 0, this::onNumberInput),
                new ButtonDesc("5", 2, 1, this::onNumberInput),
                new ButtonDesc("6", 2, 2, this::onNumberInput),
                new ButtonDesc("+", 2, 3, this::onOp),
                new ButtonDesc("7", 3, 0, this::onNumberInput),
                new ButtonDesc("8", 3, 1, this::onNumberInput),
                new ButtonDesc("9", 3, 2, this::onNumberInput),
                new ButtonDesc("+", 3, 3, this::onOp),
                new ButtonDesc("*", 4, 0, this::onOp),
                new ButtonDesc("0", 4, 1, this::onNumberInput),
                new ButtonDesc("/", 4, 2, this::onOp),
                new ButtonDesc("=", 4, 3, this::onEq),
        };

        for (ButtonDesc buttonDesc : buttonDescList) {
            Button button = new Button(buttonDesc.text);
            button.setAlignment(Pos.CENTER);
            button.setFont(new Font(24));
            setConstraints(button, buttonDesc.col, buttonDesc.row);
            button.setOnAction(buttonDesc.handler);
            getChildren().add(button);
        }
    }

    private void onNumberInput(ActionEvent event) {
        Button button = (Button) event.getSource();
        String number = button.getText();

        inputString += number;
    }

    private void onCE(ActionEvent event) {
        result = 0;
        inputString = "";
        resultLabel.setText("0");
        numberDisplay.setText("");
    }

    private void onOp(ActionEvent event) {}

    private void onEq(ActionEvent event) {}

    public static Scene createScene() {
        Scene scene = new Scene(new CalculatorPane(), 500, 400);
        scene.getStylesheets().add(CalculatorPane.class.getResource("style.css").toExternalForm());
        return scene;
    }
}
