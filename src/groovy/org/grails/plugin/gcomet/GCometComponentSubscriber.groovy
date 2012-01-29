package org.grails.plugin.gcomet

import groovyx.gpars.actor.DefaultActor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit


class GCometComponentSubscriber extends DefaultActor {
	def subscribedTo
	private Queue states = new LinkedBlockingQueue<GCometComponentState>()
	
	void act() {
		loop {
			react {message ->
				switch (message){
					case PutComponentStateMessage:
						states.put(message.state)
						break;
					case GetComponentStateMessage:
						reply getStates(3000)
						break;
				}
			}
		}
	}

	def getStates(timeout) {
		def result = new ArrayList<GCometComponentState>()
		if (states.size() > 0) {
			while (states.size() > 0) {
				result << states.take()
			}
		} else {
			def message = states.poll(timeout, TimeUnit.MILLISECONDS)
			if (message != null) {
				result << message
			}
		}
		return result
	}
}
