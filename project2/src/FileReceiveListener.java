public interface FileReceiveListener {
	void onConnection(String senderInfo);

	void updateProgress(String filename, long numBytesRead, long fileSize);

	void onCompletion(String filename);

	void tossError(String errMsg);
}
