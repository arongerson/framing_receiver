package framing_receiver;

public class ByteStuffingReceiver implements Receiver {

	private static final char FLAG = 'F';
	private static final char ESCAPE = 'E';
	
	@Override
	public String getPacket(String frame) {
		if (!isAcceptedFormat(frame)) {
			throw new DamagedFrameException("the frame has been damaged");
		}
		StringBuffer packet = new StringBuffer();
		for (int i = 1; i < frame.length() - 1; i++) {
			char character = frame.charAt(i);
			if (isEscape(character)) {
				i++;
				if (i < frame.length() - 1) {
					processEscapedCharacter(frame, packet, i);
				}
			} else if (isFlag(character)) {
				throw new DamagedFrameException("the frame has been damaged");
			} else {
				packet.append(character);
			}
		}
		return packet.toString();
	}

	private void processEscapedCharacter(String frame, StringBuffer packet, int i) {
		char specialCharacter = frame.charAt(i);
		if (!isFlagOrEscape(specialCharacter)) {
			throw new DamagedFrameException("the frame has been damaged");
		}
		packet.append(specialCharacter);
	}

	private boolean isAcceptedFormat(String frame) {
		if (frame.length() < 2) {
			return false;
		} else if (!frame.startsWith(Character.toString(FLAG))) { 
			return false;
		} else if (!frame.endsWith(Character.toString(FLAG))) {
			return false;
		}
		return true;
	}
	
	private boolean isFlagOrEscape(char character) {
		return character == FLAG || character == ESCAPE;
	}
	
	private boolean isEscape(char character) {
		return character == ESCAPE;
	}
	
	private boolean isFlag(char character) {
		return character == FLAG;
	}
}
