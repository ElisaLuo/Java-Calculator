/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.math.BigDecimal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class FXMLDocumentController extends Application {

    private double left;
    private String selectedOperator;
    private boolean numberInputting;

    @FXML
    private TextField display;

    public FXMLDocumentController() {
        this.selectedOperator = "";
        this.numberInputting = false;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculator");
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });
        stage.setResizable(false);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main.fxml"))));
        stage.show();
    }

    @FXML
    protected void handleOnAnyButtonClicked(ActionEvent evt) {
        Button button = (Button) evt.getSource();
        final String buttonText = button.getText();
        if (buttonText.equals("C") || buttonText.equals("AC")) {
            if (buttonText.equals("AC")) {
                left = 0;
            }
            selectedOperator = "";
            numberInputting = false;
            display.setText("0");;
        }
        if (buttonText.matches("[0-9\\.]")) {
            if (!numberInputting) {
                numberInputting = true;
                display.clear();
            }
            display.appendText(buttonText);
            return;
        }
        if (buttonText.matches("[＋－×÷]")) {
            left = Double.parseDouble(display.getText());
            selectedOperator = buttonText;
            numberInputting = false;
            return;
        }
        if (buttonText.equals("=")) {
            final double right = numberInputting ? Double.parseDouble(display.getText()) : left;
            left = calculate(selectedOperator, left, right);
            display.setText(Double.toString(left));
            numberInputting = false;
        }
    }

    static double calculate(String operator, double left, double right) {
        switch (operator) {
            case "＋":
                return left + right;
            case "－":
                return left - right;
            case "×":
                return left * right;
            case "÷":
                return left / right;
            default:
        }
        return right;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
