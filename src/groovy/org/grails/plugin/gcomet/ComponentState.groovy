package org.grails.plugin.gcomet

class ComponentState {	
	def storage = [:]	
	def propertyMissing(String name, value) { storage[name] = value }
	def propertyMissing(String name) { storage[name] }	
	def clone() { new ComponentState(storage.clone()) }
}
