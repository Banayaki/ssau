package gui;

import functions.FunctionImpl;
import functions.TabulatedFunctionImpl;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Класс задающий модель таблицы точек
 *
 * @see DefaultTableModel
 */
public class FunctionTableModel extends DefaultTableModel {

    /**
     * Хедер таблицы
     */
    private static final String[] col_names = {"X", "Y"};
    @SuppressWarnings("FieldCanBeLocal")
    /** Количество столбцов */
    private final int COLUMN_COUNT = 2;
    /**
     * Родительский компонент
     */
    private Component parent;
    /**
     * Функция
     */
    private TabulatedFunctionImpl function;

    public FunctionTableModel(Component parent) {
        super(col_names, 0);
        this.parent = parent;
    }

    public FunctionTableModel(TabulatedFunctionImpl function, Component parent) {
        this.function = function;
        this.parent = parent;
    }

    @Override
    public int getRowCount() {
        return super.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Double.class;
    }

    @Override
    public String getColumnName(int column) {
        return (String) this.columnIdentifiers.get(column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
    }

    public FunctionImpl getFunction() {
        return function;
    }
}
