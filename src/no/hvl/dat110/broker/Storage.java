package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;

	// data structure for managing currently connected clients
	// maps from user to corresponding client session object

	protected ConcurrentHashMap<String, ClientSession> clients;
	

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user

	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {
		// TODO: add corresponding client session to the storage
		clients.putIfAbsent(user, new ClientSession(user, connection));
		Logger.log("Client sessions : " + clients.size());

	}

	public void removeClientSession(String user) {
		// TODO: remove client session for user from the storage
		clients.get(user).disconnect();
		clients.remove(user);
		
		// fjern fra subscriptions
		Enumeration<String> keys = subscriptions.keys();
		while (keys.hasMoreElements()) {
			String topic = keys.nextElement();
			subscriptions.get(topic).removeIf(u -> u.equals(user));
		}
		// Printer ut antall client sesjoner:
		Logger.log("Client sessions : " + clients.size());

	}

	public void createTopic(String topic) {
		// TODO: create topic in the storage
		subscriptions.putIfAbsent(topic, new HashSet<String>());
		Logger.log("Topic : " + subscriptions.size());

	}

	public void deleteTopic(String topic) {
		// TODO: delete topic from the storage
		subscriptions.remove(topic);
		Logger.log("Topic : " + subscriptions.size());

	}

	public void addSubscriber(String user, String topic) {
		// TODO: add the user as subscriber to the topic
		System.out.println(user + " vil subscribe på " + topic);
		subscriptions.get(topic).add(user);
		Logger.log("Subscribers: " + topic + " : " + subscriptions.get(topic).size());

	}

	public void removeSubscriber(String user, String topic) {
		// TODO: remove the user as subscriber to the topic
		subscriptions.get(topic).remove(user);
		Logger.log("Subscribers: " + topic + " : " + subscriptions.get(topic).size());
	}
}
