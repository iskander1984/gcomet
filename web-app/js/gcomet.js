function GCometComponent(id, updateHandler){
	this.id = id;
	this.updateHandler = updateHandler;
	this.version = 0;
}

GCometComponent.prototype.update = function(version, changeSet){
	this.version = version;
	if (changeSet!== undefined) eval(this.updateHandler + "('" + $.toJSON(changeSet) + "')");
}

var components = new Array()

function longPoolRequest(){
	$.getJSON(
		'gcomet/pullMessages',
		function(data){
			updateComponents(data);
			longPoolRequest();
		}
	);
}

function updateComponents(data){
	if (!data) return;
	$.each(data, function(index, value) {
		$.each(value, function(index, state){
			var storage = state.storage;
			var componentId = storage.componentId;
			getComponentById(componentId).update(1, storage);
		});
	});
}

function getComponentById(id){
	var component;
	$.each(components, function(index, value){
		if (value.id == id) {
			component = components[index];
			return false;
		}
	});
	return component;
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
	$.ajax({url: "gcomet/subscribe?id=" + id});
	components.push(component);
}

$(document).ready(function() {
	registerGCometComponents();
	setTimeout('longPoolRequest()', 1000);
});