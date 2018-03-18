package lab.cadl.lirui.study;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AlertBox {
    public static void alert(String title, String message) {
        Stage stage = new Stage();
        stage.setTitle(title);

        StackPane layout = new StackPane();
        Label label = new Label(message);
        layout.getChildren().add(label);
        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.showAndWait();
    }
}
