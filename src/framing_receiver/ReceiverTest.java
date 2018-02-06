package framing_receiver;

public class ReceiverTest {

	public static void main(String[] args) {
		Receiver byteCountReceiver = new ByteCountReceiver();
		Receiver byteStuffingReceiver = new ByteStuffingReceiver();
		Receiver bitStuffingReceiver = new BitStuffingReceiver();
		try {
			System.out.println(byteCountReceiver.getPacket("4abc")); 
			System.out.println(byteStuffingReceiver.getPacket("FABDEEF"));
			System.out.println(bitStuffingReceiver.getPacket("011111101111101111101111110"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
