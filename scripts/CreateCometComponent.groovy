
includeTargets << grailsScript("Init")
includeTargets << grailsScript("_GrailsCreateArtifacts")

target(main: "Creates a new GComet component") {
    depends(checkVersion, parseArguments)

    def type = "GCometComponent"
    promptForName(type: type)
    
    def name = argsMap["params"][0]
   
	createArtifact(name: name, suffix: type, type: type, path: "grails-app/gcometcomponents")			
}

setDefaultTarget(main)