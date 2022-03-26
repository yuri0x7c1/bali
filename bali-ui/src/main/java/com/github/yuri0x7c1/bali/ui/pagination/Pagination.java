/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.ui.pagination;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by basakpie on 2017-05-18.
 * @author yuri0x7c1
 */
@SuppressWarnings("unused")
public class Pagination extends HorizontalLayout {
    final I18N i18n;

    final List<PaginationChangeListener> listeners = new ArrayList<>();

    private PaginationResource paginationResource;

    HorizontalLayout itemsPerPage;
    HorizontalLayout pageControls;

    final MLabel summaryLabel = new MLabel();

    final ComboBox<Integer> itemsPerPageSelect = new ComboBox();

    final TextField currentPageTextField = new TextField(null, "1");
    final Label totalPageLabel = new Label();

    final MButton firstButton;
    final MButton previousButton;
    final MButton nextButton;
    final MButton lastButton;


    public Pagination(I18N i18n, PaginationResource paginationResource) {
    	this.i18n = i18n;
        setWidth("100%");
        setSpacing(true);

		firstButton = new MButton(VaadinIcons.ANGLE_DOUBLE_LEFT)
			.withDescription(i18n.get("Pagination.first"))
			.withStyleName(BaliStyle.BUTTON_PRIMARY_FIX, ValoTheme.BUTTON_PRIMARY);
		previousButton = new MButton(VaadinIcons.ANGLE_LEFT)
			.withDescription(i18n.get("Pagination.prev"))
			.withStyleName(BaliStyle.BUTTON_PRIMARY_FIX, ValoTheme.BUTTON_PRIMARY);
		nextButton = new MButton(VaadinIcons.ANGLE_RIGHT)
			.withDescription(i18n.get("Pagination.next"))
			.withStyleName(BaliStyle.BUTTON_PRIMARY_FIX, ValoTheme.BUTTON_PRIMARY);
		lastButton = new MButton(VaadinIcons.ANGLE_DOUBLE_RIGHT)
			.withDescription(i18n.get("Pagination.last"))
			.withStyleName(BaliStyle.BUTTON_PRIMARY_FIX, ValoTheme.BUTTON_PRIMARY);

        init(paginationResource);
    }

    protected void init(PaginationResource resource) {
        if(getComponentCount()>0) {
            removeAllComponents();
        }
        paginationResource = resource;
        updateSummaryLabelValue();
        itemsPerPage = createItemsPerPage();
        pageControls = createPageControlFields();
        addComponents(summaryLabel, pageControls, itemsPerPage);
        setComponentAlignment(pageControls, Alignment.MIDDLE_CENTER);
        setComponentAlignment(itemsPerPage, Alignment.MIDDLE_RIGHT);
        setExpandRatio(summaryLabel, 0.33f);
        setExpandRatio(itemsPerPage, 0.33f);
        setExpandRatio(pageControls, 0.33f);
        buttonsEnabled();
    }

    public void setItemsPerPage(int... perPage) {
        List<Integer> items = new ArrayList<>();
        for(int page : perPage) {
            items.add(page);
        }
        itemsPerPageSelect.setItems(items);
        itemsPerPageSelect.setSelectedItem(this.paginationResource.limit());
        if(!itemsPerPageSelect.isSelected(this.paginationResource.limit())) {
            throw new IllegalArgumentException("itemsPerPageSelect.isSelected(paginationResource.size()) not found!");
        }
    }

    public void setTotalCount(long total) {
    	paginationResource.setTotal(total);
    	totalPageLabel.setValue(String.valueOf(paginationResource.totalPage()));
    	updateSummaryLabelValue();
        buttonsEnabled();
    }

    public void setItemsPerPageEnabled(boolean enabled) {
        itemsPerPage.setEnabled(enabled);
    }

    public void setItemsPerPageVisible(boolean enabled) {
        itemsPerPage.setVisible(enabled);
        setPageControlsAlignment(Alignment.MIDDLE_CENTER);
    }

    public void addPageChangeListener(PaginationChangeListener listener) {
        listeners.add(listener);
    }

    public void removePageChangeListener(PaginationChangeListener listener) {
        listeners.remove(listener);
    }

    public void setCurrentPage(int page) {
        currentPageTextField.setValue(String.valueOf(page));
    }

    public void firstClick() {
        firstButton.click();
    }

    public void previousClick() {
        previousButton.click();
    }

    public void nextClick() {
        nextButton.click();
    }

    public void lastClick() {
        lastButton.click();
    }

	private void setItemsPerPageAlignment(Alignment alignment) {
        setComponentAlignment(itemsPerPage, alignment);
    }

    private void setPageControlsAlignment(Alignment alignment) {
        setComponentAlignment(pageControls, alignment);
    }

    private HorizontalLayout createItemsPerPage() {
        final Label itemsPerPageLabel = new Label(i18n.get("Pagination.itemsPerPage"));
        itemsPerPageSelect.setTextInputAllowed(false);
        itemsPerPageSelect.setEmptySelectionAllowed(false);
        itemsPerPageSelect.setWidth("80px");
        itemsPerPageSelect.setStyleName(ValoTheme.COMBOBOX_SMALL);
        itemsPerPageSelect.addValueChangeListener(event -> {
            int pageSize = (Integer)event.getValue();
            if(pageSize== paginationResource.limit()) return;
            paginationResource.setLimit((Integer)event.getValue());
            paginationResource.setPage(1);
            firePagedChangedEvent();
        });
        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.addComponents(itemsPerPageLabel, itemsPerPageSelect);
        return layout;
    }

    @SuppressWarnings("serial")
	private HorizontalLayout createPageControlFields() {

        firstButton.addClickListener(e -> {
            PaginationResource first = paginationResource.first();
            buttonClickEvent(first);
        });

        previousButton.addClickListener(e -> {
            PaginationResource previous = paginationResource.previous();
            buttonClickEvent(previous);
        });

        nextButton.addClickListener(e -> {
            PaginationResource next = paginationResource.next();
            buttonClickEvent(next);
        });

        lastButton.addClickListener(e -> {
            PaginationResource last = paginationResource.last();
            buttonClickEvent(last);
        });

        HorizontalLayout pageFields = createPageFields();

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.addComponents(firstButton, previousButton,  pageFields, nextButton, lastButton);
        return layout;
    }

    @SuppressWarnings("serial")
	private HorizontalLayout createPageFields() {
        currentPageTextField.setStyleName(ValoTheme.TEXTFIELD_SMALL);
        currentPageTextField.setValueChangeMode(ValueChangeMode.BLUR);

        currentPageTextField.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                currentPageChangedEvent();
            }
        });

        currentPageTextField.addValueChangeListener(event -> {
            currentPageChangedEvent();
        });

        currentPageTextField.setWidth("50px");
        currentPageTextField.setStyleName(ValoTheme.TEXTFIELD_SMALL);

        final Label pageLabel = new Label(i18n.get("Pagination.page"));
        final Label sepLabel = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
        totalPageLabel.setValue(String.valueOf(this.paginationResource.totalPage()));

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.addComponents(pageLabel, currentPageTextField, sepLabel, totalPageLabel);
        layout.setComponentAlignment(pageLabel, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(currentPageTextField, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(sepLabel, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(totalPageLabel, Alignment.MIDDLE_LEFT);
        return layout;
    }

    protected void currentPageChangedEvent() {
        try {
            Integer.parseInt(currentPageTextField.getValue());
            int currentPage = Integer.valueOf(currentPageTextField.getValue());
            int pageNumber = paginationResource.page();
            if (currentPage==pageNumber) return;
            paginationResource.setPage(currentPage);
            firePagedChangedEvent();
        } catch (Exception e) {
            return;
        }
    }

    protected void buttonClickEvent(PaginationResource change) {
        paginationResource.setTotal(change.total());
        paginationResource.setPage(change.page());
        paginationResource.setLimit(change.limit());
        paginationResource.setInitIndex(change.initIndex());
        firePagedChangedEvent();
    }

    protected void firePagedChangedEvent() {
        buttonsEnabled();

        updateSummaryLabelValue();

        currentPageTextField.setValue(String.valueOf(paginationResource.page()));
        totalPageLabel.setValue(String.valueOf(paginationResource.totalPage()));
        if (listeners != null) {
            for(int i =0; i < listeners.size(); i++) {
                PaginationChangeListener listener = listeners.get(i);
                listener.changed(paginationResource);
            }
        }
    }

    protected void buttonsEnabled() {
        firstButton.setEnabled(!this.paginationResource.isFirst());
        previousButton.setEnabled(this.paginationResource.hasPrevious());
        nextButton.setEnabled(this.paginationResource.hasNext());
        lastButton.setEnabled(!this.paginationResource.isLast());
    }

    protected void updateSummaryLabelValue() {
    	Integer fromItem = ((paginationResource.page()-1) * paginationResource.limit() + 1);
    	Integer remainder = paginationResource.toIndex() - fromItem;
        summaryLabel.setValue(i18n.get(
        	"Pagination.summary",
        	fromItem + " - "  +
        	(remainder < paginationResource.limit() ? fromItem + remainder : fromItem + paginationResource.limit()) + " / " +
        	paginationResource.total()
        ));
    }

    protected MButton getFirstButton() {
        return this.firstButton;
    }

    protected MButton getPreButton() {
        return this.previousButton;
    }

    protected MButton getNextButton() {
        return this.nextButton;
    }

    protected MButton getLastButton() {
        return this.lastButton;
    }

    protected PaginationResource getPaginationResource() {
        return this.paginationResource;
    }
}
