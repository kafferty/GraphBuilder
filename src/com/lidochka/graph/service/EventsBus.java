package com.lidochka.graph.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventsBus {

	private static ExecutorService executor = Executors.newFixedThreadPool(2);

	private static List<AppEventListener> listeners = new ArrayList<AppEventListener>();

	public static void subscribe(AppEventListener eventListener) {
		listeners.add(eventListener);
	}

	public static void publishEvent(Object event) {
		
		//Notify all listeners asynchronously 
		for (AppEventListener listener : listeners) {
			executor.execute(new Runnable() {

				public void run() {
					listener.onApplicationEvent(event);
				}
			});
		}

	}

}
