package com.github.yuri0x7c1.bali.ui.view;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <P>
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public  abstract class ParametrizedView<P> extends CommonView implements View {
	final Class<P> paramsType;

	@Getter
	final ObjectMapper mapper;

	@Getter
	P params;

	public ParametrizedView(Class<P> paramsType, ObjectMapper mapper) {
		super();
		this.paramsType = paramsType;
		this.mapper = mapper;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		try {
			if (StringUtils.isNotBlank(event.getParameters())) {
				this.params = mapper.readValue(URLDecoder.decode(event.getParameters(), StandardCharsets.UTF_8.name()),
						paramsType);
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		action();
	}

	public void action() {}
}
