package gcomet

import org.grails.plugin.gcomet.GCometComponentState
import grails.converters.*

class GcometController {
	def channelService
	
    def subscribe = {
		def componentId = params.id
		def component = applicationContext.getBean("${componentId}GCometComponent")
		channelService.subscribeCurrentUserTo(component)
		render ' '
	}
		
	def update = {
		def componentId = params.id
		def channel = applicationContext.getBean("${componentId}GCometComponent")
		def state = new GCometComponentState()
		state.componentId = componentId
		params.each { key, value -> 
			state."$key" = value
		}
		channelService.update(state, channel)
		render ' '
	}
	
	def pullMessages = {
		def states = channelService.retrieveStateUpdatesForUser()
		render (states as JSON).toString()
	}

}
