package lab.cadl.lirui.study.calculator;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
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
import lab.cadl.lirui.study.AlertBox;

public class CalculatorPane extends GridPane {
    private static final String opList = "+-*/^";
    private TextArea numberDisplay;
    private Label resultLabel;
    private String inputString = "";
    private double inputNumber = 0.0;
    private double result = 0;
    private String op = "";

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
        setConstraints(resultLabel, 3, 0, 2, 1);
        getChildren().addAll(numberDisplay, resultLabel);

        ButtonDesc[] buttonDescList = new ButtonDesc[]{
                new ButtonDesc("1", 1, 0, this::onNumberInput),
                new ButtonDesc("2", 1, 1, this::onNumberInput),
                new ButtonDesc("3", 1, 2, this::onNumberInput),
                new ButtonDesc("←", 1, 4, this::onC),
                new ButtonDesc("CE", 1, 3, this::onCE),

                new ButtonDesc("4", 2, 0, this::onNumberInput),
                new ButtonDesc("5", 2, 1, this::onNumberInput),
                new ButtonDesc("6", 2, 2, this::onNumberInput),
                new ButtonDesc("+", 2, 3, this::onOp),
                new ButtonDesc("-", 2, 4, this::onOp),

                new ButtonDesc("7", 3, 0, this::onNumberInput),
                new ButtonDesc("8", 3, 1, this::onNumberInput),
                new ButtonDesc("9", 3, 2, this::onNumberInput),
                new ButtonDesc("*", 3, 3, this::onOp),
                new ButtonDesc("/", 3, 4, this::onOp),

                new ButtonDesc("0", 4, 0, this::onNumberInput),
                new ButtonDesc(".", 4, 1, this::onNumberInput),
                new ButtonDesc("+/-", 4, 2, this::onNeg),
                new ButtonDesc("^", 4, 3, this::onOp),
                new ButtonDesc("=", 4, 4, this::onEq),
        };

        for (ButtonDesc buttonDesc : buttonDescList) {
            Button button = new Button(buttonDesc.text);
            button.setAlignment(Pos.CENTER);
            button.setPrefWidth(100);
            button.setFont(new Font(24));
            setConstraints(button, buttonDesc.col, buttonDesc.row);
            button.setOnAction(buttonDesc.handler);
            getChildren().add(button);
        }
    }

    private void onNumberInput(ActionEvent event) {
        Button button = (Button) event.getSource();
        String number = button.getText();
        boolean success = false;

        if (Strings.isNullOrEmpty(op) && numberDisplay.getText().contains("\n")) {
            return;
        }

        try {
            inputNumber = Double.parseDouble(inputString + number);
            success = true;
        } catch (NumberFormatException ignored) {
            AlertBox.alert("错误", String.format("数字格式错误：“%s”", inputNumber + number));
        }

        if (success) {
            inputString += number;
            numberDisplay.setText(numberDisplay.getText() + number);
        }
    }

    private void onCE(ActionEvent event) {
        result = 0;
        inputString = "";
        resultLabel.setText("0");
        numberDisplay.setText("");
    }

    private void onC(ActionEvent event) {
        if (!inputString.isEmpty()) {
            inputString = inputString.substring(0, inputString.length() - 1);
            String numberDisplayString = numberDisplay.getText();
            numberDisplay.setText(numberDisplayString.substring(0, numberDisplayString.length() - 1));
        }
    }

    private void onNeg(ActionEvent event) {

    }

    private void onOp(ActionEvent event) {
        calculate();

        op = ((Button) event.getSource()).getText();
        String[] parts = numberDisplay.getText().split("\n");
        if (parts.length == 0) {
            numberDisplay.setText(op);
        } else {
            String lastInput = parts[parts.length - 1];
            if (opList.contains(lastInput.trim())) {
                parts[parts.length - 1] = op + " ";
                numberDisplay.setText(Joiner.on("\n").join(parts));
            } else {
                numberDisplay.setText(String.format("%s\n%s ", numberDisplay.getText(), op));
            }
        }
    }

    private void onEq(ActionEvent event) {
        calculate();
    }

    private void calculate() {
        if (inputString.isEmpty()) {
            return;
        }

        if (op == null || op.isEmpty()) {
            result = inputNumber;
        } else {
            switch (op) {
                case "+":
                    result += inputNumber;
                    break;
                case "-":
                    result -= inputNumber;
                    break;
                case "*":
                    result *= inputNumber;
                    break;
                case "/":
                    result /= inputNumber;
                    break;
                case "^":
                    result = Math.pow(result, inputNumber);
                    break;
                default:
                    AlertBox.alert("错误", String.format("不支持该操作符：%s", op));
                    return;
            }

            numberDisplay.setText(String.format("%s\n= %s", numberDisplay.getText(), result));
        }

        inputNumber = 0.0;
        inputString = "";
        op = "";
        resultLabel.setText(String.valueOf(result));
    }

    public static Scene createScene() {
        return new Scene(new CalculatorPane(), 500, 400);
    }
}
