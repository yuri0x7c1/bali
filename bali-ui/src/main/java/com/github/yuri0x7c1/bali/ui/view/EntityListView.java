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

package com.github.yuri0x7c1.bali.ui.view;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.export.XlsxEntityExporter;
import com.github.yuri0x7c1.bali.ui.export.XlsxEntityExporter.PageProvider;
import com.github.yuri0x7c1.bali.ui.handler.CreateHandler;
import com.github.yuri0x7c1.bali.ui.handler.DeleteHandler;
import com.github.yuri0x7c1.bali.ui.handler.EditHandler;
import com.github.yuri0x7c1.bali.ui.handler.ShowHandler;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * Entity list view
 *
 * @author yuri0x7c1
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class EntityListView<T> extends CommonView {

    final Class<T> entityType;

	final I18N i18n;

	@Getter
	final DownloadButton exportButton;

	@Getter
	final EntityDataGrid<T> dataGrid;

	@Getter
	final MButton createButton;

	@Getter
	CreateHandler<T> createHandler;

	@Getter
	PageProvider<T> exportPageProvider;

	@Getter
	Supplier<String> exportFileNameProvider;

	public EntityListView(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super();
		this.entityType = entityType;
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		// create button
		createButton = new MButton(i18n.get("Create"))
			.withIcon(VaadinIcons.PLUS)
			.withStyleName(ValoTheme.BUTTON_PRIMARY)
			.withVisible(false);

		addHeaderComponent(createButton);

		// export button
		exportButton = new DownloadButton()
			.setFileNameProvider(() -> entityType.getSimpleName() + "_" + LocalDateTime.now() + "." + XlsxEntityExporter.XLSX_FILE_EXTENSION)
			.setCacheTime(0)
			.withCaption(i18n.get("Export"))
			.withIcon(VaadinIcons.DOWNLOAD)
			.withStyleName(ValoTheme.BUTTON_FRIENDLY)
			.withVisible(false);

		addHeaderComponent(exportButton);

		add(dataGrid);
	}

	public void setCreateHandler(CreateHandler<T> createHandler) {
		this.createHandler = createHandler;
		createButton.addClickListener(event -> {
			createHandler.onCreate();
		});
		createButton.setVisible(true);
	}

	public void setExportPageProvider(PageProvider<T> exportPageProvider) {
		this.exportPageProvider = exportPageProvider;
		exportButton.setVisible(true);
		exportButton.setWriter(out -> {
        	exportButton.setEnabled(false);
        	XlsxEntityExporter<T> exporter = new XlsxEntityExporter<T>(
    			entityType,
    			getDataGrid().getProperties(),
    			null,
    			exportPageProvider,
    			dataGrid.getOrderDirection(),
    			dataGrid.getOrderProperty(),
    			XlsxEntityExporter.DEFAULT_PAGE_SIZE,
    			null
        	);

            try {
                out.write(exporter.getByteArray());

            } catch (Exception ex) {
               log.error(ex.getMessage(), ex);
            }
            exportButton.setEnabled(true);
		});
	}

	public void setExportFileNameProvider(Supplier<String> exportFileNameProvider) {
		this.exportFileNameProvider = exportFileNameProvider;
		exportButton.setFileNameProvider(() -> exportFileNameProvider.get());
	}

	public void setShowHandler(ShowHandler<T> showHandler) {
		dataGrid.setShowHandler(showHandler);
	}

	public void setEditHandler(EditHandler<T> editHandler) {
		dataGrid.setEditHandler(editHandler);
	}

	public void setDeleteHandler(DeleteHandler<T> deleteHandler) {
		dataGrid.setDeleteHandler(deleteHandler);
	}

	@Override
	public void onEnter() {
		dataGrid.sortAndRefresh();
	}
}
