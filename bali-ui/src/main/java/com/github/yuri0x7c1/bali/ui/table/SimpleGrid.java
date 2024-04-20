package com.github.yuri0x7c1.bali.ui.table;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.domain.Sort;

import com.vaadin.data.SelectionModel;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;
import com.vaadin.ui.components.grid.NoSelectionModel;
import com.vaadin.ui.components.grid.SingleSelectionModelImpl;

import lombok.Getter;
import lombok.Setter;

public class SimpleGrid<T> extends Table {

	public static class Column<T, V> {
		private final SimpleGrid<T> grid;
        private final ValueProvider<T, V> valueProvider;
        private String id;
        private String caption;
        private boolean sortable = true;

        public String getId() {
            return id;
        }

        public Column<T, V> setId(String id) {
            Objects.requireNonNull(id, "Column ID cannot be null!");
			if (this.id != null) {
				throw new IllegalStateException("Column ID cannot be changed!");
			}

			if (grid.getColumns().stream().anyMatch(c -> c.getId().equals(id))){
                throw new IllegalArgumentException("Duplicate ID for columns!");
            }

            this.id = id;
            return this;
        }

        public Column<T, V> setCaption(String caption) {
            Objects.requireNonNull(caption, "Header caption can't be null");
            this.caption = caption;
            return this;
        }

        public Column<T, V> setSortable(boolean sortable) {
            if (this.sortable != sortable) {
                this.sortable = sortable;
                // updateSortable();
            }
            return this;
        }

		public Column(SimpleGrid<T> grid, ValueProvider<T, V> valueProvider) {
			super();
			this.grid = grid;
			this.valueProvider = valueProvider;
		}
	}

	private final Class<T> beanType;

    private final List<Column<T, ?>> columns = new ArrayList<>();

    @Setter
    private SelectionMode selectionMode = SelectionMode.NONE;

    private Sort sort;

    @Getter
    @Setter
    private List<T> items = new ArrayList<>();

	public SimpleGrid(Class<T> beanType) {
		this.beanType = beanType;
	}

	protected List<Column<T, ?>> getColumns() {
		return Collections.unmodifiableList(columns);
	}

   public Column<T, ?> getColumn(String columnId) {
       return columns.stream().filter(c -> c.getId().equals(columnId)).findFirst().get();
   }

   private Column<T, ?> getColumnOrThrow(String columnId) {
       Objects.requireNonNull(columnId, "Column id cannot be null");
       Column<T, ?> column = getColumn(columnId);
       if (column == null) {
           throw new IllegalStateException(
                   "There is no column with the id " + columnId);
       }
       return column;
   }

    public Column<T, ?> addColumn(String propertyName) {
    	ValueProvider<T, ?> valueProvider = new ValueProvider<T, String>() {

			@Override
			public String apply(T source) {
				try {
					return (String) PropertyUtils.getProperty(source, propertyName);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}
		};
    	Column<T, ?> column = new Column<>(this,  null);
    	columns.add(column);
    	return column;
    }

    public Column<T, ?> addColumn(ValueProvider<T, ?> valueProvider) {
    	Column<T, ?> column = new Column<>(this,  valueProvider);
    	columns.add(column);
    	return column;
    }

    public <V extends Component> Column<T, V> addComponentColumn(
            ValueProvider<T, V> componentProvider) {
        return new Column<>(this, null);
    }

    public void removeColumn(Column<T, ?> column) {
    	columns.remove(column);
    }

    public void removeColumn(String columnId) {
        removeColumn(getColumnOrThrow(columnId));
    }

    public void removeAllColumns() {
        for (Column<T, ?> column : getColumns()) {
            removeColumn(column);
        }
    }

    protected void setColumnId(String id, Column<T, ?> column) {
    	column.setId(id);
    }

    public SelectionModel<T> getSelectionModel() {
    	if (SelectionMode.NONE.equals(selectionMode)) {
    		return new NoSelectionModel<>();
    	}
    	else if (SelectionMode.SINGLE.equals(selectionMode)) {
    		return new SingleSelectionModelImpl<>();
    	}
       	else if (SelectionMode.MULTI.equals(selectionMode)) {
    		return new MultiSelectionModelImpl<>();
       	}
    	throw new RuntimeException("Unknown SelectionModel!");
    }

	public void addSortListener(SortListener<GridSortOrder<T>> listener) {
	}

	public void sort(Sort sort) {

	}
}
