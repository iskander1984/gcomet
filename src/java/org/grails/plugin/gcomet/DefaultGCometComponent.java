package org.grails.plugin.gcomet;

import org.codehaus.groovy.grails.commons.AbstractInjectableGrailsClass;

class DefaultGCometComponent extends AbstractInjectableGrailsClass {
	public static final String GCOMET_COMPONENT = "GCometComponent";
	
	public DefaultGCometComponent(Class clazz){
		super(clazz, GCOMET_COMPONENT);
	}
}
