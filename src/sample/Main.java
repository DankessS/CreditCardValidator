package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {

    private Desktop desktop = Desktop.getDesktop();
    private Label insertCreditCardsLabel;
    private TextArea creditCardNums;
    private FileChooser fileChooser;
    private Button openButton;
    private Button validateButton;
    private TextArea validCreditCarNums;
    private Label validCardList;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        buildGridPane(grid);

        insertCreditCardsLabel = new Label("Insert credit card numbers:");
        buildInsertCreditCardLabel(grid, insertCreditCardsLabel);

        creditCardNums = new TextArea();
        buildCreditCardNumbesTextArea(grid, creditCardNums);

        fileChooser = new FileChooser();
        openButton = new Button("Load from file");
        buildFileChooserAction(primaryStage, fileChooser, openButton);

        validateButton = new Button("Validate");
        buildValidateButtonAction(validateButton);

        validCreditCarNums = new TextArea();
        buildValidCardNumbersTextArea(validCreditCarNums);

        validCardList = new Label("Valid credit card numbers:");
        GridPane.setConstraints(validCardList, 5, 3);
        grid.getChildren().addAll(openButton, validateButton, validCreditCarNums, validCardList);
        primaryStage.setTitle("Credit Card Validator");
        primaryStage.setScene(new Scene(grid, 450, 320));
        primaryStage.show();
    }

    private void buildValidateButtonAction(Button validateButton) {
        GridPane.setConstraints(validateButton, 3, 4);
        validateButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(creditCardNums.getText().split("\n"))
                    .filter(n -> !"".equals(n) && validateCardNum(n))
                    .forEach(n -> sb.append(n).append("\n"));
            validCreditCarNums.setText(sb.toString());
        });
    }

    private boolean validateCardNum(String num) {
        int digits = num.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(num.charAt(count) + "");
            } catch (NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) { // not
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return (sum != 0) && (sum % 10 == 0);
    }

    private void buildGridPane(GridPane grid) {
        grid.setHgap(15);
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setVgap(15);
    }

    private void buildValidCardNumbersTextArea(TextArea validCreditCarNums) {
        validCreditCarNums.setPrefColumnCount(10);
        GridPane.setConstraints(validCreditCarNums, 5, 4);
    }

    private void buildInsertCreditCardLabel(GridPane grid, Label callCountLabel) {
        GridPane.setConstraints(callCountLabel, 2, 3);
        grid.getChildren().add(callCountLabel);
    }

    private void buildFileChooserAction(Stage primaryStage, FileChooser fileChooser, Button openButton) {
        openButton.setOnAction(
                e -> {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null)
                            sb.append(line).append("\n");
                        creditCardNums.setText(sb.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TEXT", "*.txt")
        );
        GridPane.setConstraints(openButton, 2, 5);
    }

    private void buildCreditCardNumbesTextArea(GridPane grid, TextArea creditCardNums) {
        creditCardNums.setPrefColumnCount(10);
        creditCardNums.getText();
        creditCardNums.setPrefRowCount(10);
        creditCardNums.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[\\d\\r\\n]+")) {
                creditCardNums.setText(newValue.replaceAll("[^\\d\\n]", ""));
            }
        });
        GridPane.setConstraints(creditCardNums, 2, 4);
        grid.getChildren().add(creditCardNums);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
