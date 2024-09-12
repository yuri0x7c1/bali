package com.github.yuri0x7c1.bali.ui.timeline;

import java.lang.reflect.Method;

import com.github.yuri0x7c1.bali.ui.timeline.shared.EventProperties;
import com.vaadin.util.ReflectTools;

/**
 * Listener for double-click events on the timeline.
 */
public interface DoubleClickListener {
  /**
   * The handler method in the listener.
   */
  static final Method METHOD =
      ReflectTools.findMethod(DoubleClickListener.class, "doubleClick",
      DoubleClickEvent.class);

  /**
   * The handler method called when the timeline is double left-clicked.
   *
   * @param evt the event details
   */
  void doubleClick(DoubleClickEvent evt);

  public static class DoubleClickEvent extends ClickListener.ClickEvent {

    public DoubleClickEvent(Timeline source, Object itemId,
        EventProperties props) {
      super(source, itemId, props);
    }
  }

}
