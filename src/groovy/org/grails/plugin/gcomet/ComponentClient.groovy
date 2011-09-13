package org.grails.plugin.gcomet

import groovyx.gpars.actor.DefaultActor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit


class ComponentClient extends DefaultActor {

	private Queue states = new LinkedBlockingQueue<ComponentState>()
	
	void act() {
		loop {
			react {message ->
				states.put(message)
			}
		}
	}

	def getStates(timeout) {
		def result = new ArrayList<ComponentState>()
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
