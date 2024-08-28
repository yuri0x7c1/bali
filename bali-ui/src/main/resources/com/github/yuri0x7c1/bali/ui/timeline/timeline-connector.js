window.com_github_yuri0x7c1_bali_ui_timeline_Timeline = function() {

    var connector = this;
	var container = document.createElement('timeline-container');
	var items = {};
	var options = {
		orientation: 'top'
	};
  	var timeline = new vis.Timeline(container, items, options);
  	this.getElement().appendChild(container);

    // Handle changes from the server-side
    this.onStateChange = function() {
		timeline.setData(this.getState().data);
    };

    /*

byEmployeeTimeline.addCustomTime(today, 'draft');
byEmployeeTimeline.setCustomTimeMarker('Draft Shifts', 'draft', false);
byEmployeeTimeline.setCustomTimeTitle('Draft Shifts', 'draft');*/

    this.addCustomTime = function(time, id) {
		timeline.addCustomTime(time, id);
	}

	this.setCustomTimeMarker = function(title, id, editable) {
		timeline.setCustomTimeMarker(title, id, editable);
	}

	this.setCustomTimeTitle = function(title, id) {
		timeline.setCustomTimeTitle(title, id);
	}

	this.getCustomTime = function(id) {
		return timeline.getCustomTime(id);
	}
};
