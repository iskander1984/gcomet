package org.grails.plugin.gcomet;

import groovyx.gpars.actor.DefaultActor;

class GCometChannel extends DefaultActor {

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
						subscribers.each {it << message.subscriber}
						break
				}
			}
		}
	}
	
	def subscribe(GCometChannelSubscriber subscriber) {
		this << new SubscribeMessage(subscriber: subscriber)
	}
	
	def unsubscribe(GCometChannelSubscriber subscriber) {
		this << new UnsubscribeMessage(subscriber: subscriber)
	}
	
	def update(GCometComponentState state) {
		this << new UpdateComponentStateMessage(state: state.clone())
	}
}
