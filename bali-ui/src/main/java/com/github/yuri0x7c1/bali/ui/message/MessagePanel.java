package com.github.yuri0x7c1.bali.ui.message;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MessagePanel extends HorizontalLayout {
	public enum MessageLevel {
		SUCCESS,
		WARNING,
		ERROR
	}
	
	private final MessageLevel level;
	
	private final MLabel iconLabel;
	
	private final MLabel messageLabel;
	
	private final MButton closeButton;
	
	public MessagePanel(MessageLevel level) {
		Objects.requireNonNull(level);
		this.level = level;
		
		// icon
		VaadinIcons icon = VaadinIcons.CHECK;
		if (MessageLevel.WARNING.equals(level)) {
			icon = VaadinIcons.WARNING;
		}
		else if (MessageLevel.ERROR.equals(level)) {
			icon = VaadinIcons.BAN;
		}
		
		// styles
		addStyleName(BaliStyle.MESSAGE_PANEL);
		if (MessageLevel.SUCCESS.equals(level)) {
			addStyleName(BaliStyle.MESSAGE_PANEL_SUCCESS);
		}
		else if (MessageLevel.WARNING.equals(level)) {
			addStyleName(BaliStyle.MESSAGE_PANEL_WARNING);
		}
		else if (MessageLevel.ERROR.equals(level)) {
			addStyleName(BaliStyle.MESSAGE_PANEL_ERROR);
		}
		
		iconLabel = new MLabel(icon.getHtml()).withContentMode(ContentMode.HTML).withStyleName("icon-label");
		messageLabel = new MLabel().withContentMode(ContentMode.PREFORMATTED).withStyleName("message-label").withFullSize();
		closeButton = new MButton(VaadinIcons.CLOSE, event -> {
			setVisible(false);
		})
		.withStyleName("close-button", ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_BORDERLESS);
		
		setSpacing(true);
		setSizeFull();
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
				
		addComponents(iconLabel, messageLabel, closeButton);
		setExpandRatio(messageLabel, 1.0f);
	}
	
	public void showMessages(List<String> messages) {
		messageLabel.setValue(StringUtils.join(messages, "\n"));
	}
	
}
