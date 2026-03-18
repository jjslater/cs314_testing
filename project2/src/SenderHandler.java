import java.io.*;
import java.net.*;

import javax.xml.crypto.Data;

public class SenderHandler implements Runnable {
	private Socket senderConnection;
	private FileReceiveListener carrierPigeon;
	private volatile boolean running = true;

	public SenderHandler(Socket socket, FileReceiveListener listener) {
		this.senderConnection = socket;
		this.carrierPigeon = listener;
	}

	@Override
	public void run() {
		try {
			// Run the file transfer stuff
			DataInputStream fromSender = new DataInputStream(senderConnection.getInputStream());

			carrierPigeon.onConnection(senderConnection.getInetAddress().toString());

			String filename = fromSender.readUTF();
			long fileSize = fromSender.readLong();

			FileOutputStream outFile = new FileOutputStream("incoming_" + filename);

			byte[] inputBuffer = new byte[4096];
			long totalBytesRead = 0;
			int bytesRead = 0;

			while (running && (bytesRead = fromSender.read(inputBuffer)) != -1) {
				outFile.write(inputBuffer, 0, bytesRead);
				totalBytesRead += bytesRead;

				carrierPigeon.updateProgress(filename, totalBytesRead, fileSize);

				if (totalBytesRead >= fileSize)
					break;
			}

			outFile.close();

			carrierPigeon.onCompletion(filename);

		} catch (Exception e) {
			carrierPigeon.tossError(e.getMessage());
		} finally {
			cleanup();
		}
	}

	public void stopTransfers() {
		running = false;
		cleanup();
	}

	public void cleanup() {
		try {
			if (senderConnection != null)
				senderConnection.close();
		} catch (Exception ignored) {
		}
	}
}
