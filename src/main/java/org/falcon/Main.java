package org.falcon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {

    public Main() {}

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {

		@SuppressWarnings("ConstantConditions")
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("layouts/app.fxml"));

		primaryStage.setScene(new Scene(root, Config.DISPLAY_MODE_WIDTH, Config.DISPLAY_MODE_HEIGHT));
		primaryStage.setTitle("Falcon");
		primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
