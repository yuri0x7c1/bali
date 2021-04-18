package com.github.yuri0x7c1.bali.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.yuri0x7c1.bali.ui.i18n.MessageProvider;
import com.github.yuri0x7c1.bali.ui.i18n.ResourceBundleMessageProvider;

@Configuration
public class I18nConfiguration {
	@Bean
	MessageProvider messageProvider() {
		return new ResourceBundleMessageProvider("messages");
	}
}