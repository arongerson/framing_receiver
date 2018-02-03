package framing_receiver;

public class ByteCountReceiver implements Receiver {

	@Override
	public String getPacket(String frame) {
		int byteCount = findByteCount(frame);
		int receivedByteCount = frame.length();
		if (byteCount != receivedByteCount) {
			throw new DamagedFrameException("we are expecting " + byteCount + " bytes but the frame has " + receivedByteCount + " bytes");
		}
		return frame.substring(1);
	}
	
	private int findByteCount(String frame) {
		String firstByte = frame.substring(0, 1);
		return Integer.parseInt(firstByte);
	}

}
