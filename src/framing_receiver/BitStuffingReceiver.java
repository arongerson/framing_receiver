package framing_receiver;

public class BitStuffingReceiver implements Receiver {

	private static final String FLAG = "01111110";
	private static final char ESCAPE_BIT = '0';
	private static final char ONE_BIT = '1';
	private static final int MAX_SUCCESSIVE_ONES = 5;
	private int countOnes;
	
	@Override
	public String getPacket(String frame) {
		countOnes = 0;
		throwExceptionIfNotCorrectFormat(frame);
		String stuffedPacket = extractBitStuffedPacket(frame);
		StringBuffer packet =  new StringBuffer();
		for (int i = 0; i < stuffedPacket.length(); i++) {
			char bit = stuffedPacket.charAt(i);
			throwExceptionIfInvalidBit(bit);
			updateSuccessiveOneCount(bit);
			i = processBitIfMaxSuccessiveOneReached(stuffedPacket, i);
			packet.append(bit);
 		}
		return packet.toString();
	}

	private int processBitIfMaxSuccessiveOneReached(String stuffedPacket, int i) {
		if (countOnes == MAX_SUCCESSIVE_ONES) {
			countOnes = 0;
			if (i + 1 < stuffedPacket.length()) {
				char nextBit = stuffedPacket.charAt(i + 1);
				if (nextBit != ESCAPE_BIT) {
					throw new DamagedFrameException("illegal escape character.");
				}
				i++; // escape this character if it is a valid escape character
			}
		}
		return i;
	}

	private void updateSuccessiveOneCount(char bit) {
		if (bit == ONE_BIT) {
			countOnes++;
		} else {
			countOnes = 0;
		}
	}

	private void throwExceptionIfInvalidBit(char bit) {
		if (!isAcceptableBit(bit)) {
			throw new DamagedFrameException("unrecognizable character.");
		}
	}

	private String extractBitStuffedPacket(String frame) {
		String stuffedPacket = frame.substring(8, frame.length() - 8);
		return stuffedPacket;
	}

	private void throwExceptionIfNotCorrectFormat(String frame) {
		if (!isAcceptedFormat(frame)) {
			throw new DamagedFrameException("the frame has been damaged");
		}
	}

	private boolean isAcceptedFormat(String frame) {
		if (frame.length() < 16) {
			return false;
		} else if (!frame.startsWith(FLAG)) {
			return false;
		} else if (!frame.endsWith(FLAG)) {
			return false;
		}
		return true;
	}
	
	private boolean isAcceptableBit(char bit) {
		return bit == ONE_BIT || bit == ESCAPE_BIT;
	}

}
