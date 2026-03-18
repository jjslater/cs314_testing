import javafx.concurrent.Task;

import java.io.*;
import java.net.*;

public class ServerListenTask extends Task<Void> {
	private int port;
	private FileServer parent;

	public ServerListenTask(FileServer parent, int port) {
		this.parent = parent;
		this.port = port;
	}

	@Override
	protected Void call() throws Exception {
		// TODO: main server listening code
		return null;
	}

	public int getPort() {
		return this.port;
	}
}
