package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.TODO;

public class DisplayDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		System.out.println("Display starting ...");

		// TODO - START

		// create a client object and use it to
		Client c = new Client("disp", Common.BROKERHOST, Common.BROKERPORT);

		// - connect to the broker
		c.connect();
		// - create the temperature topic on the broker
		c.createTopic(Common.TEMPTOPIC);
		// - subscribe to the topic
		c.subscribe(Common.TEMPTOPIC);
		// - receive messages on the topic
		for (int i = 0; i < COUNT; i++) {
			PublishMsg msg = (PublishMsg) c.receive();
			System.out.println("DISPLAY: " + msg.getMessage());
		}
		
		System.out.println("Display stopping ... ");

		// - unsubscribe from the topic
		c.unsubscribe(Common.TEMPTOPIC);
		// - disconnect from the broker
		c.disconnect();

		// TODO - END


	}
}
