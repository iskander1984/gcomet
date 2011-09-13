function GCometComponent(id, updateHandler){
	this.id = id;
	this.updateHandler = updateHandler;
	this.version = 0;
}

GCometComponent.prototype.update = function(version, changeSet){
	this.version = version;
	eval(this.updateHandler + "('" + changeSet + "')");
}

var components = new Array()

function longPoolRequest(){
	$.getJSON(
		'pullMessages',
		function(data){
			updateComponents(data);
			longPoolRequest();
		}
	);
}

function updateComponents(data){
	if (!data) return;
	$.each(data, function(index, value) {
		components[0].update(1, $.toJSON(value.storage));
	}
	);
}

function registerGCometComponents(){
	var components = $("span[type='gcomet']");
	$.each(components, function(index, value){
		var id = $(value).attr("id");
		var updateHandler = $(value).attr("updateHandler");
		registerGCometComponent(id, updateHandler);
	});
}

function registerGCometComponent(id, updateHandler){
	var component = new GCometComponent(id, updateHandler);
	$.ajax({url: "subscribe"});
	components.push(component);
}

$(document).ready(function() {
	registerGCometComponents();
	setTimeout('longPoolRequest()', 1000);
});