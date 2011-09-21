package org.grails.plugin.gcomet

class GCometComponentState {	
	def storage = [:]	
	def propertyMissing(String name, value) { storage[name] = value }
	def propertyMissing(String name) { storage[name] }	
	def clone() { new GCometComponentState(storage.clone()) }
}
