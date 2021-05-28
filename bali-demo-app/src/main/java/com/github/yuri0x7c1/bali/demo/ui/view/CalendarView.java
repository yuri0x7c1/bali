package com.github.yuri0x7c1.bali.demo.ui.view;

import java.time.LocalDate;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.github.yuri0x7c1.bali.demo.ui.view.main.MainView;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "calendar", layout = MainView.class)
@PageTitle("Calendar")
public class CalendarView extends CommonView {
	public CalendarView() {
		setHeaderText("Calendar");

		// Create a new calendar instance and attach it to our layout
		FullCalendar calendar = FullCalendarBuilder.create().build();
		calendar.setHeight(500);
		add(calendar);
		setFlexGrow(1, calendar);

		// Create a initial sample entry
		Entry entry = new Entry();
		entry.setTitle("Some event");
		entry.setStart(LocalDate.now().atTime(10, 0), calendar.getTimezone());
		entry.setEnd(entry.getStart().plusHours(2), calendar.getTimezone());
		entry.setColor("#ff3333");

		calendar.addEntry(entry);
	}
}