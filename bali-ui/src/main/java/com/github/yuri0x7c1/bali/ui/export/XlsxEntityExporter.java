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

package com.github.yuri0x7c1.bali.ui.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class XlsxEntityExporter<T> {
	public static final String XLSX_FILE_EXTENSION = "xlsx";
	public static final int DEFAULT_EXPORT_PROGRESS_ITERATION_SIZE = 1000;
	public static final int DEFAULT_PAGE_SIZE = 5000;
	public static final float HUNDRED_PERCENT_PROGRESS = 100f;
	public static final int MAX_ENTITY_COUNT = 1000000;
	public static final String MAX_ENTITY_COUNT_MESSAGE = "Too many entities: %d. Maximum allowed entity count is: %d";

	@FunctionalInterface
	public interface PageProvider<T> {
		public Page<T> getPage(Pageable pageable);
	}

	@FunctionalInterface
	public interface ProgressListener {
		public void onProgress(Float progressInPercents);
	}

	Class<T> entityClass;
	List<EntityProperty<T>> properties;
	List<T> entities;
	PageProvider<T> pageProvider;
	Direction sortDirection;
	String sortProperty;
	Integer pageSize;
	ProgressListener progressListener;

	public XlsxEntityExporter(Class<T> entityType, List<EntityProperty<T>> properties, List<T> entities,
			PageProvider<T> pageProvider, Direction sortDirection, String sortProperty, Integer pageSize,
			ProgressListener progressListener) {

		if (entities == null && pageProvider == null) {
			throw new RuntimeException("List of entities or page provider must be set!");
		}
		this.entityClass = entityType;
		this.properties = properties;
		this.entities = entities;
		this.pageProvider = pageProvider;
		this.sortDirection = sortDirection;
		this.sortProperty = sortProperty;
		this.pageSize = pageSize;
		this.progressListener = progressListener;
	}

	private void setCellValue(Row row, int cellIndex, Object value) {
		if (value != null) {
            if (value instanceof String) {
            	row.createCell(cellIndex).setCellValue((String) value);
            }
            else if (value instanceof Long) {
            	row.createCell(cellIndex).setCellValue((Long) value);
            }
            else if (value instanceof Integer) {
            	row.createCell(cellIndex).setCellValue((Integer) value);
            }
            else if (value instanceof Double) {
            	row.createCell(cellIndex).setCellValue((Double) value);
            }
            else if (value instanceof Byte) {
            	row.createCell(cellIndex).setCellValue((Byte) value);
            }
            else if (value instanceof Float) {
            	row.createCell(cellIndex).setCellValue((Float) value);
            }
            else {
            	row.createCell(cellIndex).setCellValue(String.valueOf(value));
            }
		}
	}

	public Workbook getWorkbook() throws Exception {
        Workbook workbook = new SXSSFWorkbook(1000);
        Sheet sheet = workbook.createSheet();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        int rowNumber = 0;

        // header row
        Row headerRow = sheet.createRow(rowNumber++);
        for (int i = 0; i < properties.size(); i++) {
        	Cell cell = headerRow.createCell(i);
        	cell.setCellStyle(headerStyle);
        	cell.setCellValue(properties.get(i).getCaption());
        }

        int entityNumber = 0;
        if (pageProvider != null) {
	       	Pageable pageable = PageRequest.of(0, pageSize, sortDirection, sortProperty);
	        do {
	        	Page<T> currentPage = pageProvider.getPage(pageable);
	        	if (currentPage.getTotalElements() > MAX_ENTITY_COUNT) {
	        		String msg = String.format(MAX_ENTITY_COUNT_MESSAGE, currentPage.getTotalElements(), MAX_ENTITY_COUNT);
	        		log.error(msg);
	        		Row row = sheet.createRow(rowNumber++);
					row.createCell(0).setCellValue(msg);
	        		break;
	        	}
				for (T entity : currentPage.getContent()) {
					entityNumber++;
					Row row = sheet.createRow(rowNumber++);
					for (int i = 0; i < properties.size(); i++) {
						setCellValue(row, i, properties.get(i).getValue(entity));
					}
					if (entityNumber % pageSize == 0) {
						log.debug("Exported {} rows of {}!", entityNumber, currentPage.getTotalElements());
						if (progressListener != null) {
							progressListener.onProgress(100f / currentPage.getTotalElements() * entityNumber);
						}
					}
				}
				if (!currentPage.hasNext()) break;
				pageable = currentPage.nextPageable();
	        }
	        while (true);
        }
        else {
        	if (entities.size() > MAX_ENTITY_COUNT) {
        		String msg = String.format(MAX_ENTITY_COUNT_MESSAGE, entities.size(), MAX_ENTITY_COUNT);
        		log.error(msg);
        		Row row = sheet.createRow(rowNumber++);
				row.createCell(0).setCellValue(msg);
        	}
        	else {
				for (T item : entities) {
					entityNumber++;
					Row row = sheet.createRow(rowNumber++);
					for (int i = 0; i < properties.size(); i++) {
						setCellValue(row, i, properties.get(i).getValue(item));
					}
					if (entityNumber % DEFAULT_EXPORT_PROGRESS_ITERATION_SIZE == 0) {
						log.debug("Exported {} rows of {}!", entityNumber, DEFAULT_EXPORT_PROGRESS_ITERATION_SIZE);
						if (progressListener != null) {
							progressListener.onProgress(100f / entities.size() * entityNumber);
						}
					}
				}
        	}
        }

		log.debug("Export finished. Exported {} rows!", entityNumber);
		if (progressListener != null) {
			progressListener.onProgress(HUNDRED_PERCENT_PROGRESS);
		}

		return workbook;
	}

	public byte[] getByteArray() throws Exception, IOException {
		Workbook workbook = getWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
	}
}