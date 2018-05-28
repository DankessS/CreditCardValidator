package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setVgap(15);
        grid.setHgap(15);
        final Label callCountLabel = new Label("Insert credit card number:");
        GridPane.setConstraints(callCountLabel, 2, 3);
        grid.getChildren().add(callCountLabel);
        final TextArea creditCardNums = new TextArea();
        creditCardNums.setPrefColumnCount(10);
        creditCardNums.getText();
        creditCardNums.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                creditCardNums.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        GridPane.setConstraints(creditCardNums, 2, 4);
        grid.getChildren().add(creditCardNums);
        primaryStage.setTitle("Credit Card Validator");
        primaryStage.setScene(new Scene(grid, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
