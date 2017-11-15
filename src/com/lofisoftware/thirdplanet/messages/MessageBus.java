package com.lofisoftware.thirdplanet.messages;

import java.util.ArrayList;
import java.util.List;

public class MessageBus {

	private static List<Handler> handlers = new ArrayList<Handler>();

	public static void publish(Message message){
		for (Handler handler : handlers)
			handler.handle(message);
	}

	public static void subscribe(Handler handler){
		handlers.add(handler);
	}

	public static void unsubscribe(Handler handler){
		handlers.remove(handler);
	}
}