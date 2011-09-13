package org.grails.plugin.gcomet

import groovyx.gpars.actor.DefaultActor

class Component extends DefaultActor {

	def clients = []	

	void act() {
		loop {
			react {message ->
				switch (message) {
					case RegisterMessage:
						clients << message.client
						break
					case UnregisterMessage:
						clients.remove(message.client)
						break
					case UpdateStateMessage:
						clients.each {it << message.state}
						break
				}
			}
		}
	}
	
	def register(ComponentClient client) {
		this << new RegisterMessage(client: client)
	}
	
	def unregister(ComponentClient client) {
		this << new UnregisterMessage(client: client)
	}
	
	def update(ComponentState state) {
		this << new UpdateStateMessage(state: state.clone())
	}
}
