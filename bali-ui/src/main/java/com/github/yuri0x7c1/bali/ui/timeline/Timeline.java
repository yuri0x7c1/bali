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
}
