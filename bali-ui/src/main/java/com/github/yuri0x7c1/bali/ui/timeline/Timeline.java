package com.github.yuri0x7c1.bali.ui.timeline;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"vis-timeline-graph2d.min.js", "timeline-connector.js"})
@StyleSheet({"vis-timeline-graph2d.min.css"})
public class Timeline extends AbstractJavaScriptComponent {

	public Data getData() {
		return getState().data;
	}

	public void setData(Data data) {
		getState().data = data;
	}

	@Override
    protected TimelineState getState() {
        return (TimelineState) super.getState();
    }

	public void addCustomTime(String time, String id) {
		callFunction("addCustomTime", time, id);
	}

	public void setCustomTimeMarker(String title, String id, Boolean editable) {
		callFunction("setCustomTimeMarker", title, id, editable);
	}

	public void setCustomTimeTitle(String title, String id) {
		callFunction("setCustomTimeTitle", title, id);
	}


}
