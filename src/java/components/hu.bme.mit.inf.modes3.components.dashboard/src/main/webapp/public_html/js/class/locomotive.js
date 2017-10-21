/**
 * 
 * @param {LocomotiveController}
 *            locomotive
 * @returns {undefined}
 */
function LocomotiveController(locomotiveConfig, controller) {
	// setting up instance variables
	this.config = locomotiveConfig;
	this.speed = 0;
	this.isAnimationInProgress = false;
	this.maxSpeed = 128;
	this.position = {
		x : 0,
		y : 0
	};
	this.controller = controller;

	// then create DOM in trains list
	this.createDOMrepresentation();

	// add svg representation as well
	this.svgElemGroup = $('<g />').attr('id', this.config.svgGroup);

	// adding position circle
	this.svgElemPosition = $('<circle />').attr({
		id : 'position',
		style : window.settings.locomotiveCircleStyle,
		cx : 0,
		cy : 0,
		r : 100.91698
	});
	this.svgTitle = $('<text />').attr({
		id : "title",
		style : window.settings.locomotiveTextStyle,
		x : 120,
		y : 0
	}).append($('<tspan />').text(this.config.name));
	this.svgSpeedText = $('<text />').attr({
		id : "title",
		style : window.settings.locomotiveSpeedTextStyle,
		x : 120,
		y : 120
	}).append($('<tspan />').text('0.00 m/s'));
	//	
	// adding background to all of these
	var background = $('<rect />').attr({
		id : "bg-" + this.config.svgGroup,
		x : 115,
		y : -125,
		width : 0,
		height : 0,
		style : "fill:#FFF;fill-opacity:0.6;"
	});

	this.svgElemGroup.attr('transform', 'translate(100,100)');
	this.svgElemGroup.append(background);
	this.svgElemGroup.append(this.svgElemPosition);
	this.svgElemGroup.append(this.svgTitle);
	this.svgElemGroup.append(this.svgSpeedText);

	// adding svgElement to it parent
	$('#layer4').append(this.svgElemGroup);
};

LocomotiveController.prototype.positionInformationReceived = function(info) {
	this.position = info.realposition;

	// transform coordinates to the dimensions of the viewbox
	// also, positions are received in cm, but the SVG is designed with mm range
	// thus it need's to be multipled by 10
	this.position.x *= 10 * 8858.2677 / 2500.0;
	this.position.y *= 10 * 4960.63 / 1400.0;

	var pos = 'translate(' + this.position.x + ',' + this.position.y + ')';
	this.svgElemGroup.attr('transform', pos);

	// CV is sending position information with a fix rate even if objects are
	// not tracked at the moment. If that occurs, we show on the UI with
	// different style
	if (info.tracked[0]) {
		this.svgElemPosition.attr('style', window.settings.locomotiveCircleStyle);
	} else {
		this.svgElemPosition.attr('style', window.settings.locomotiveNotTrackedCircleStyle);
	}
};

LocomotiveController.prototype.pushSpeed = function(speed) {
	if (speed < 0) {
		log("Pushing train speed: " + speed);
		this.controller.pushTrainSpeed(this.config.address, Math.abs(speed), 1);

	} else {
		log("Pushing train speed: " + speed);
		this.controller.pushTrainSpeed(this.config.address, speed, 0);
	}
};

LocomotiveController.prototype.setSpeed = function(speed) {
	this.speed = speed;
	this.rangeInput.val(speed);

	// If speed is a negative number (in case of reverse movement), show
	// positive number and an R letter instead of a negative number. Also,
	// maximum speed of the trains are defined to be 48 (previously it was 50),
	// and makes a percentage value based on the full speed.
	if (speed < 0) {
		this.indicatorElem.text((speed / this.maxSpeed * -100) + "% (R)");
	} else {
		this.indicatorElem.text(speed / this.maxSpeed * 100 + "%");
	}

};

LocomotiveController.prototype.createDOMrepresentation = function() {
	var container = $('<div />').addClass('train-container').attr("id", "train-" + this.config.address);
	var header = $('<h2 />').text(this.config.name);
	var removebtn = $('<button />').addClass('remove-train').text('X');
	var image = $('<img />').attr('src', 'images/locomotives/' + this.config.image);
	this.rangeInput = $('<input />').attr({
		type : 'range',
		min : -this.maxSpeed,
		max : +this.maxSpeed,
		value : 0,
		step : this.maxSpeed/4
	});
	this.indicatorElem = $('<div />').addClass('train-control-speed-indicator').text('0');
	var controls = $('<div />').addClass('train-control-speed').append(this.rangeInput).append(this.indicatorElem);
	container.append(header).append(removebtn).append(image).append(controls);
	image.wrap($('<div />').addClass('train-control-image'));
	this.rangeInput.wrap($('<div />').addClass('train-control-speed-input'));
	$('#train-control').append(container);

	// add event handler for range input
	this.rangeInput.bind('change', {
		_this : this
	}, function(event) {
		var speed = $(this).val();
		event.data._this.setSpeed(speed);
		event.data._this.pushSpeed(speed);

	});
	this.rangeInput.bind('mousemove', {
		_this : this
	}, function(event) {
		event.data._this.setSpeed($(this).val());
	});
};

LocomotiveController.prototype.DOMUpdatedCallback = function() {
	this.svgElemGroup = $('#layout').find("#" + this.config.svgGroup);
	this.svgTitle = this.svgElemGroup.find("#title");
	this.svgElemPosition = this.svgElemGroup.find('#position');

	var bg = $(this.svgElemGroup).find("#bg-" + this.config.svgGroup);
	console.log('background', bg);

	// im not sure why I should do this, but in that way the background
	// will fill the whole textual area
	var w = $(this.svgElemGroup).width() * 8858.2677 / 2500.0 * 1.35;
	var h = $(this.svgElemGroup).height() * 4960.63 / 1400.0 * 1.5;

	bg.attr('width', w).attr('height', h);

};
