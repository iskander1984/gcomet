package org.grails.plugin.gcomet;

import groovyx.gpars.actor.DefaultActor;

class GCometComponent extends DefaultActor {
	def name
	def subscribers = []	

	void act() {
		loop {
			react {message ->
				switch (message) {
					case SubscribeMessage:
						subscribers << message.subscriber
						break
					case UnsubscribeMessage:
						subscribers.remove(message.subscriber)
						break
					case UpdateComponentStateMessage:
						subscribers.each {it << new PutComponentStateMessage(state: message.state)}
						break
				}
			}
		}
	}
}
