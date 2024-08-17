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
};
