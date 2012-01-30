package gcomet

import org.grails.plugin.gcomet.GCometComponentState
import grails.converters.*

class GcometController {
	def gcometComponentService
	def userSubscriptionList
	
	def subscribe = {
		def component = resolveTargetComponent(params)
		gcometComponentService.subscribeCurrentUserTo(component)
		render ' '
	}
		
	def update = {
		def component = resolveTargetComponent(params)
		def state = createStateFrom(params)
		gcometComponentService.update(state, component)
		render ' '
	}
	
	def pullMessages = {
		def states = userSubscriptionList.retrieveStateUpdatesForUser()
		render (states as JSON).toString()
	}
	
	private resolveTargetComponent(params){
		def componentId = params.id
		def component = applicationContext.getBean("${componentId}GCometComponent")
		return component 
	}
	
	private createStateFrom(params){
		def state = new GCometComponentState()
		state.componentId = params.id
		params.each { key, value -> 
			state."$key" = value
		}
		return state
	}
	
	def beforeInterceptor = [action: this.&checkIfUserNotSubscribed, only: ['subscribe']]
	
	private checkIfUserNotSubscribed(){
		def component = resolveTargetComponent(params)
		return !userSubscriptionList.isSubscribedTo(component)
	}
}
