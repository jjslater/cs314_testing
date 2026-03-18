
//Javafx imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;

//Non-javafx imports
import java.io.*;
import java.net.*;

public class FileServer extends Application {
	private final int port = 5000;

	private Stage stage;
	private Scene scene;
	private BorderPane mainWindow;

	public void start(Stage primaryStage) {
		this.stage = primaryStage;

		mainWindow = new BorderPane();
		mainWindow.setPrefWidth(200);
		mainWindow.setPrefHeight(400);

		scene = new Scene(mainWindow, 400, 200);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
