
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
	private ServerListenTask myServer = null;

	private Stage stage;
	private Scene scene;
	private BorderPane mainWindow;

	private Button startServer;
	private Button closeServer;
	private TextField portNum;

	private ScrollPane fileUpdates;
	private VBox fileUpdateBox;

	private Label fileUpdate;

	public void start(Stage primaryStage) {
		this.stage = primaryStage;

		mainWindow = new BorderPane();
		mainWindow.setPrefWidth(300);
		mainWindow.setPrefHeight(400);

		mainWindow.setTop(serverSetup());
		mainWindow.setCenter(fileUpdate());

		events();

		scene = new Scene(mainWindow, 300, 400);
		stage.setScene(scene);
		stage.show();
	}

	// -----------------------------------------------------------------------------------
	// GUI FUNCTIONS
	// -----------------------------------------------------------------------------------

	private VBox serverButtons() {
		startServer = new Button("Start Server");

		closeServer = new Button("Close Server");
		closeServer.setDisable(true);

		VBox tmp = new VBox();
		VBox.setMargin(tmp, new Insets(10));
		tmp.getChildren().addAll(closeServer, startServer);

		return tmp;
	}

	private HBox serverSetup() {
		portNum = new TextField();
		portNum.setPromptText("Enter Port Number");

		HBox tmp = new HBox();
		HBox.setMargin(tmp, new Insets(10));
		tmp.getChildren().add(portNum);
		tmp.getChildren().addAll(serverButtons().getChildren());

		return tmp;
	}

	private VBox fileUpdate() {
		fileUpdate = new Label("No file downloading");
		VBox tmp = new VBox(fileUpdate);
		VBox.setMargin(tmp, new Insets(10));

		return tmp;
	}

	// -----------------------------------------------------------------------------------
	// EVENT HANDLING
	// -----------------------------------------------------------------------------------
	private void events() {
		startServer.setOnAction(e -> initServer());
		closeServer.setOnAction(e -> butShutServer());
		return;
	}

	private void initServer() {
		if (myServer != null) {
			return;
		}

		String port = portNum.getText();
		int portNumber = 0;

		if (port == null || port.isBlank()) {
			// TODO: ERROR
			System.out.println("ERROR 1");
			return;
		}
		try {
			portNumber = Integer.parseInt(port);
			if (portNumber < 0 || portNumber > 65535) {
				// TODO: ERROR
				System.out.println("ERROR 2");
				return;
			}
		} catch (NumberFormatException e) {
			// TODO: ERROR
			System.out.println("ERROR 3");
			return;
		}

		Platform.runLater(() -> {
			startServer.setDisable(true);
			closeServer.setDisable(false);
		});
		myServer = new ServerListenTask(this, portNumber);
		myServer.setOnCancelled(e -> shutdownServer());
		new Thread(myServer).start();
	}

	private void butShutServer() {
		// TODO: WARNING
		myServer.cancel();
	}

	public void shutdownServer() {
		myServer = null;
		Platform.runLater(() -> {
			startServer.setDisable(false);
			closeServer.setDisable(true);
		});
	}

	// -----------------------------------------------------------------------------------
	// HELPER FUNCTION
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// MAIN LOOP
	// -----------------------------------------------------------------------------------

	public static void main(String[] args) {
		launch(args);
	}
}
