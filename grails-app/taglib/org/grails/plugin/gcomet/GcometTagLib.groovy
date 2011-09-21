package org.grails.plugin.gcomet

class GcometTagLib {
	static namespace = "gcomet"
	
	def includes = { attrs, body ->
		out << "<script type=\"text/javascript\" src=\"/grails-comet-gpars/js/gcomet.js\"></script>" 
		out << "<script type=\"text/javascript\" src=\"/grails-comet-gpars/js/jquery/jquery.json-2.2.min.js\"></script>" 
	}
	
	def component = { attrs, body ->
		out << "<span id='${attrs.id}' type='gcomet' updateHandler='${attrs.updateHandler}'>" + body() + "</span>"
	}

}
