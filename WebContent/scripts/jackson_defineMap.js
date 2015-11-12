/**
 * JacksonLi
 */
// Define the map
function Map() {
	this.container = {};
}

// put key-value into the map
Map.prototype.put = function(key, value) {
	try {

		if (key != null && key != "")
			this.container[key] = value;

	} catch (e) {
		return e;
	}
};

// Get the value by key
Map.prototype.get = function(key) {
	try {

		return this.container[key];

	} catch (e) {
		return e;
	}
};

// Judge weather the map contains the key
Map.prototype.containsKey = function(key) {

	try {
		for ( var p in this.container) {
			if (this.p == key)
				return true;
		}

		return false;

	} catch (e) {
		return e;
	}

};

// Judge weather the map contains the value
Map.prototype.containsValue = function(value) {
	try {

		for ( var p in this.container) {
			if (this.container[p] === value)
				return true;
		}

		return false;

	} catch (e) {
		return e;
	}
};

// Delete the key
Map.prototype.remove = function(key) {
	try {

		delete this.container[key];

	} catch (e) {
		return e;
	}
};

// clear the map
Map.prototype.clear = function() {
	try {
		delete this.container;
		this.container = {};

	} catch (e) {
		return e;
	}
};

// judge weather the map is null
Map.prototype.isEmpty = function() {

	if (this.keyArray().length == 0)
		return true;
	else
		return false;
};

// get the size of the map
Map.prototype.size = function() {

	return this.keyArray().length;
};

// return the key array
Map.prototype.keyArray = function() {

	var keys = new Array();
	for ( var p in this.container) {
		keys.push(p);
	}

	return keys;
};

// return the value array
Map.prototype.valueArray = function() {

	var values = new Array();
	var keys = this.keyArray();
	for (var i = 0; i < keys.length; i++) {
		values.push(this.container[keys[i]]);
	}

	return values;
};