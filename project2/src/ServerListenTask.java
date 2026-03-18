import javafx.concurrent.Task;

import java.io.*;
import java.net.*;

public class ServerListenTask extends Task<Void> {
	private int port;
	private FileServer parent;
	private volatile boolean active = false;

	private ServerSocket gateway;
	private Socket senderConnection;
	private SenderHandler workHorse;

	public ServerListenTask(FileServer parent, int port) {
		this.parent = parent;
		this.port = port;
	}

	@Override
	protected Void call() throws Exception {
		// TODO: main server listening code
		gateway = new ServerSocket(this.port);
		gateway.setSoTimeout(1000);

		// Receives a connection
		while (!this.isCancelled()) {
			if (!active) {
				try {
					senderConnection = gateway.accept();
					active = true;

					workHorse = new SenderHandler(
							senderConnection,
							new FileReceiveListener() {
								public void onConnection(String senderInfo) {
									System.out.println("Connected: " + senderInfo);
								}

								public void updateProgress(String filename,
										long numBytesRead, long fileSize) {
									System.out.println("Progress: " + numBytesRead
											+ "/" + fileSize);
								}

								public void onCompletion(String filename) {
									System.out.println("Completed: " + filename);
									active = false;
								}

								public void tossError(String errMsg) {
									System.out.println("Error: " + errMsg);
									active = false;
								}
							});

					new Thread(workHorse).start();

					continue;
				} catch (SocketTimeoutException e) {
					continue;
				}
			} else {
				continue;
			}
		}
		gateway.close();
		senderDisconnect();
		return null;
	}

	public void senderDisconnect() {
		try {
			if (senderConnection != null)
				senderConnection.close();
		} catch (Exception ignored) {
		}
		senderConnection = null;
		active = false;
	}

	public int getPort() {
		return this.port;
	}
}
