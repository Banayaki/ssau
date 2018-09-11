package gui;

import functions.TabulatedFunctionImpl;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FunctionTableModel extends DefaultTableModel {

    private static final String[] col_names = {"X", "Y"};
    @SuppressWarnings("FieldCanBeLocal")
    private final int COLUMN_COUNT = 2;
    private Component parent;
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
}
