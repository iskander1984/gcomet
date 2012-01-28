package gcomet

import org.grails.plugin.gcomet.GCometComponentState
import grails.converters.*

class GcometController {
	def channelService
	
	def startChannel = {
		def componentId = params.id
		def channel = applicationContext.getBean("${componentId}GCometComponent")
		channel.start()
		render  ' '
	}
    def subscribe = {
		def componentId = params.id
		def channel = applicationContext.getBean("${componentId}GCometComponent")
		channelService.subscribeCurrentUserTo(channel)
		render ' '
	}
		
	def update = {
		println "CURRENT SESSION" + session.id
		def componentId = params.id
		def channel = applicationContext.getBean("${componentId}GCometComponent")
		def state = new GCometComponentState()
		state.componentId = componentId
		println "COMPONENTID" + componentId;
		state.login = session.id.substring(0, 4)
		state.message = params.chatMessage
		channel.update(state)
		render ' '
	}
	
	def pullMessages = {
		def states = channelService.retrieveStateUpdatesForUser()
		println "RETRIEVED STATES" + (states as JSON).toString()
		render (states as JSON).toString()
	}

}
