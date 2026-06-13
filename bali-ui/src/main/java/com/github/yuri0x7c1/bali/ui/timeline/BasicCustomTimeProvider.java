package com.github.yuri0x7c1.bali.ui.timeline;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BasicCustomTimeProvider implements CustomTimeProvider {

	@Getter
	@Setter
	private List<CustomTime> customTimes = new ArrayList<>();

}
