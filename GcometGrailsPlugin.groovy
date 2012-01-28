import org.grails.plugin.gcomet.GCometComponentArtefactHandler
import org.grails.plugin.gcomet.GCometChannel
import org.apache.commons.logging.LogFactory

class GcometGrailsPlugin {
    // the plugin version
    def version = "0.1.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        'grails-app/views/error.gsp',
		'grails-app/gcometcomponents/**/*',
		'grails-app/conf/*',
		'test/functional/*',
		'test/unit/*',
		'test/integration/*',
    ]

    // TODO Fill in these fields
    def title = "Gcomet Plugin" // Headline display name of the plugin
    def author = "Oleksandr Pochapskyy, Vitalii Osaulenko"
    def authorEmail = "olexandr.pochapskiy@gmail.com"
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/gcomet"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/iskander1984/gcomet" ]

	def artefacts = [GCometComponentArtefactHandler]
	
	def watchedResources = ["file:./grails-app/gcometcomponents/**/*"] 
	
	def log = LogFactory.getLog(GcometGrailsPlugin)
	
	static final GCOMET_COMPONENT_BEANS = { gcometComponent ->
	    "${gcometComponent.name}GCometComponent"(gcometComponent.clazz) { bean ->
            bean.singleton = true
            bean.autowire = "byName"
        } 
	}
	
    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        for(gcometComponent in application.getArtefacts(GCometComponentArtefactHandler.TYPE)) { 
			gcometComponent.metaClass.mixin(GCometChannel)
			def callable = GCOMET_COMPONENT_BEANS.curry(gcometComponent)
			callable.delegate = delegate
            callable.call() 
			log.debug "Registered GComet component: ${gcometComponent.name}"	
			
		}
		log.debug delegate.beanDefinitions
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
		
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
