package org.grails.plugin.gcomet

import groovyx.gpars.actor.DefaultActor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit


class GCometChannelSubscriber extends DefaultActor {

	private Queue states = new LinkedBlockingQueue<GCometComponentState>()
	
	void act() {
		loop {
			react {message ->
				states.put(message)
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
