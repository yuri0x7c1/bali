package com.github.yuri0x7c1.bali.ui.timeline;

import java.util.Date;

public interface CustomTime {
	public Date getTime();
	public String getId();
	public String getTitle();
	public boolean isMarker();
	public boolean isEditable();
}
