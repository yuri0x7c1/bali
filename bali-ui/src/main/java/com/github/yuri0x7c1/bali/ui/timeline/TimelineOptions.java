package com.github.yuri0x7c1.bali.ui.timeline;

import java.util.Date;

import com.github.yuri0x7c1.bali.ui.timeline.shared.TimelineState;

/**
 * The various options that can be configured on the timeline. Refer to
 * http://visjs.org/docs/timeline.html#Configuration_Options for detailed
 * documentation.
 *
 * @author mpilone
 */
public interface TimelineOptions {

  /**
   * When a Timeline is configured to be clickToUse, it will react to mouse and
   * touch events only when active. When active, a blue shadow border is
   * displayed around the Timeline. The Timeline is set active by clicking on
   * it, and is changed to inactive again by clicking outside the Timeline or by
   * pressing the ESC key.
   *
   * @return true if enabled
   */
  public boolean isClickToUse();

  /**
   * When a Timeline is configured to be clickToUse, it will react to mouse and
   * touch events only when active.
   *
   * @param enabled true to enable, false to disable
   */
  public void setClickToUse(boolean enabled);

  public boolean isMoveable();

  public void setMoveable(boolean moveable);

  public boolean isZoomable();

  public void setZoomable(boolean zoomable);

  public boolean isSelectable();

  public void setSelectable(boolean selectable);

  public ItemAlignment getAlign();

  public void setAlign(ItemAlignment align);

  public boolean isAutoResize();

  public void setAutoResize(boolean autoResize);

  public TimelineState.Editable getEditable();

  public void setEditable(TimelineState.Editable editable);

  public void setEditableAdd(boolean editAdd);
  public void setEditableRemove(boolean editRemove);
  public void setEditableUpdateGroup(boolean editUpdateGroup);
  public void setEditableUpdateTime(boolean editUpdateTime);

  public Date getEnd();

  public void setEnd(Date end);

  public void setFormatMajorLabels(TimelineState.FormatLabels formatLabels);

  public TimelineState.FormatLabels getFormatMajorLabels();

  public void setFormatMinorLabels(TimelineState.FormatLabels formatLabels);

  public TimelineState.FormatLabels getFormatMinorLabels();

  public String getGroupOrder();

  public void setGroupOrder(String groupOrder);

  public TimelineState.Margin getMargin();

  public String getMoment();

  public void setMoment(String moment);

  /**
   * Applies the same margin to the axis, item-horizontal, and item-vertical.
   *
   * @param margin the margin value
   */
  public void setMargin(int margin);
  public void setMargin(TimelineState.Margin margin);

  public Date getMax();

  public void setMax(Date max);

  public Date getMin();

  public void setMin(Date min);

  public TimelineState.Orientation getOrientation();

  public void setOrientation(TimelineState.Orientation orientation);

  public void setOrientationItem(ItemOrientation orientation);

  public void setOrientationAxis(TimeAxisOrientation orientation);

  /**
   * If true, the timeline shows a red, vertical line displaying the current
   * time. This time can be synchronized with a server via the method
   * setCurrentTime.
   *
   * @param visible true to enable the vertical time bar, false to disable
   */
  public void setShowCurrentTime(boolean visible);

  /**
   * Returns true if the current time is being shown on the client side.
   *
   * @return true if enabled
   */
  public boolean isShowCurrentTime();

  public boolean isShowMajorLabels();

  public void setShowMajorLabels(boolean showMajorLabels);

  public boolean isShowMinorLabels();

  public void setShowMinorLabels(boolean showMinorLabels);

  public boolean isStack();

  public void setStack(boolean stack);

  public Date getStart();

  public void setStart(Date start);

  public void setTimeAxisScale(TimeAxisScale scale);

  public TimeAxisScale getTimeAxisScale();

  public void setTimeAxisStep(int step);

  public int getTimeAxisStep();

  public ItemType getType();

  public void setType(ItemType type);

  public int getZoomMax();

  public void setZoomMax(int zoomMax);

  public int getZoomMin();

  public void setZoomMin(int zoomMin);

  public void setMultiselect(boolean multiselect);

  public boolean isMultiselect();

  /**
   * The orientation (i.e. location) of the time axis on the timeline.
   */
  public enum TimeAxisOrientation {
    NONE,
    BOTH,
    BOTTOM,
    TOP
  }

  /**
   * The orientation (i.e. location) of the item on the timeline.
   */
  public enum ItemOrientation {

    BOTTOM,
    TOP
  }

  /**
   * The type of the item which controls how it is rendered on the timeline.
   *
   * @author mpilone
   */
  public enum ItemType {

    BOX,
    POINT,
    RANGE,
    BACKGROUND
  }

  /**
   * The alignment of items with type {@link ItemType#BOX}.
   */
  public enum ItemAlignment {

    AUTO,
    LEFT,
    CENTER,
    RIGHT
  }

  public enum TimeAxisScale {

    MILLISECOND, SECOND, MINUTE, HOUR, WEEKEND, DAY, MONTH, YEAR
  }

}
