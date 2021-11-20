package com.github.yuri0x7c1.bali.ui.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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

	public interface PageProvider<T> {
		public Page<T> getPage(Pageable pageable);
	}

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

	public XlsxEntityExporter(Class<T> sourceClass, List<EntityProperty<T>> properties, List<T> entities,
			PageProvider<T> pageProvider, Direction sortDirection, String sortProperty, Integer pageSize,
			ProgressListener progressListener) {

		if (entities == null && pageProvider == null) {
			throw new RuntimeException("List of entities or page provider must be set!");
		}
		this.entityClass = sourceClass;
		this.properties = properties;
		this.entities = entities;
		this.pageProvider = pageProvider;
		this.sortDirection = sortDirection;
		this.sortProperty = sortProperty;
		this.pageSize = pageSize;
		this.progressListener = progressListener;
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
				for (T entity : currentPage.getContent()) {
					entityNumber++;
					Row row = sheet.createRow(rowNumber++);
					for (int i = 0; i < properties.size(); i++) {
						row.createCell(i).setCellValue(getPropertyValue(entity, properties.get(i)));
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
			for (T item : entities) {
				entityNumber++;
				Row row = sheet.createRow(rowNumber++);
				for (int i = 0; i < properties.size(); i++) {
					row.createCell(i).setCellValue(getPropertyValue(item, properties.get(i)));
				}
				if (entityNumber % DEFAULT_EXPORT_PROGRESS_ITERATION_SIZE == 0) {
					log.debug("Exported {} rows of {}!", entityNumber, DEFAULT_EXPORT_PROGRESS_ITERATION_SIZE);
					if (progressListener != null) {
						progressListener.onProgress(100f / entities.size() * entityNumber);
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

	private String getPropertyValue(T entity, EntityProperty<T> p) {
		String propertyValue = "";
		try {
			if (p.getValueProvider() == null) {
				propertyValue = BeanUtils.getProperty(entity, p.getName());
			}
			else {
				propertyValue = p.getValueProvider().apply(entity);
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return propertyValue;
	}

	public byte[] getByteArray() throws Exception, IOException {
		Workbook workbook = getWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
	}
}