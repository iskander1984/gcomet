package gcomet

import org.grails.plugin.gcomet.GCometComponentState
import grails.converters.*

class GcometController {
	def componentService
	
    def subscribe = {
		def componentId = params.id
		def component = applicationContext.getBean("${componentId}GCometComponent")
		componentService.subscribeCurrentUserTo(component)
		render ' '
	}
		
	def update = {
		def componentId = params.id
		def component = applicationContext.getBean("${componentId}GCometComponent")
		def state = new GCometComponentState()
		state.componentId = componentId
		params.each { key, value -> 
			state."$key" = value
		}
		componentService.update(state, component)
		render ' '
	}
	
	def pullMessages = {
		def states = componentService.retrieveStateUpdatesForUser()
		render (states as JSON).toString()
	}

}
