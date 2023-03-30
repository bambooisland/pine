package io.github.bambooisland.pine.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.bambooisland.pine.base.Column;
import io.github.bambooisland.pine.base.Element;
import io.github.bambooisland.pine.base.Field;
import io.github.bambooisland.pine.base.FieldType;
import io.github.bambooisland.pine.base.Table;
import io.github.bambooisland.pine.base.VoidOperationException;
import io.github.bambooisland.pine.base.values.BoolValue;
import io.github.bambooisland.pine.base.values.DoubleValue;
import io.github.bambooisland.pine.base.values.FieldValue;
import io.github.bambooisland.pine.base.values.StringValue;

public class PoiTableBuilder {

    public enum FileType {
        XLS, XLSX,
    }

    public static Table getTable(File target, int index) throws IOException {
        if (target.getPath().endsWith("xlsx")) {
            return getTable(target, index, FileType.XLSX);
        } else if (target.getPath().endsWith("xls")) {
            return getTable(target, index, FileType.XLS);
        } else {
            throw new VoidOperationException("unsupported format");
        }
    }

    private static Table getTable(File target, int index, FileType type) throws IOException {
        Sheet sheet = initSheet(target, index, type);
        Column[] columns = initColumns(sheet);
        Element[] elements = initElements(sheet, columns);
        return new Table(columns, elements);
    }

    private static Sheet initSheet(File target, int index, FileType type) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(target)) {
            Sheet sheet = workbook.getSheetAt(index);
            return sheet;
        }
    }

    private static Column[] initColumns(Sheet sheet) throws VoidOperationException {
        if (sheet.getPhysicalNumberOfRows() <= 1) {
            throw new VoidOperationException("table is empty");
        }

        Row row = sheet.getRow(0);

        Column[] columns = new Column[row.getLastCellNum()];
        for (int i = 0; i < row.getLastCellNum(); i++) {
            columns[i] = getColumn(row, i);
        }
        return columns;
    }

    private static Element[] initElements(Sheet sheet, Column[] columns) throws VoidOperationException {
        Element[] elements = new Element[sheet.getPhysicalNumberOfRows() - 1];
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            elements[i - 1] = getElement(columns, sheet.getRow(i));
        }
        return elements;
    }

    private static Column getColumn(Row row, int index) throws VoidOperationException {
        String name = row.getCell(index).getStringCellValue();
        CellType type = row.getSheet().getRow(1).getCell(index).getCellType();
        switch (type) {
        case STRING:
            return new Column(name, FieldType.STRING);
        case NUMERIC:
            return new Column(name, FieldType.DOUBLE);
        case BOOLEAN:
            return new Column(name, FieldType.BOOL);
        default:
            throw new VoidOperationException("unsupported cell type: " + type.toString());
        }
    }

    private static Element getElement(Column[] columns, Row row) throws VoidOperationException {
        Field<? extends FieldValue>[] fields = new Field<?>[row.getPhysicalNumberOfCells()];
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            fields[i] = getField(row.getCell(i), columns[i]);
        }
        return new Element(fields);
    }

    private static Field<? extends FieldValue> getField(Cell cell, Column column) throws VoidOperationException {
        switch (column.getType()) {
        case STRING:
            return new Field<>(column, new StringValue(cell.getStringCellValue()));
        case DOUBLE:
            return new Field<>(column, new DoubleValue(cell.getNumericCellValue()));
        case BOOL:
            return new Field<>(column, new BoolValue(cell.getBooleanCellValue()));
        default:
            throw new VoidOperationException("unsupported cell type");
        }
    }

    public static File save(Table table, File file, FileType type) throws IOException {
        switch (type) {
        case XLSX:
            return saveAsExcel(table, file, new XSSFWorkbook());
        case XLS:
            return saveAsExcel(table, file, new HSSFWorkbook());
        default:
            throw new VoidOperationException("unsupported format");
        }
    }

    private static File saveAsExcel(Table table, File file, Workbook workbook) throws IOException {
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < 1 + table.getNumberOfElements(); i++) {
            sheet.createRow(sheet.getLastRowNum() + 1);
        }
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            Row row = sheet.getRow(0);
            Cell cell;
            cell = row.createCell(i);
            cell.setCellValue(table.getColumn(i).getName());
            for (int j = 0; j < table.getNumberOfElements(); j++) {
                row = sheet.getRow(j + 1);
                Element element = table.getElement(j);
                switch (table.getColumn(i).getType()) {
                case STRING:
                    cell = row.createCell(i);
                    cell.setCellValue(((StringValue) element.getField(i).getValue()).getString());
                    break;
                case DOUBLE:
                    cell = row.createCell(i);
                    cell.setCellValue(((DoubleValue) element.getField(i).getValue()).getDouble());
                    break;
                case BOOL:
                    cell = row.createCell(i);
                    cell.setCellValue(((BoolValue) element.getField(i).getValue()).getBool());
                    break;
                }
            }
        }
        workbook.write(new FileOutputStream(file));
        workbook.close();
        return file;
    }
}
