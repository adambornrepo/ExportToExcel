package com.abtech.aboutexcel;

import com.abtech.exception.ConflictException;
import com.abtech.exception.DataExportException;
import com.abtech.exception.DirectoryCreationException;
import com.abtech.exception.MissingArgumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public abstract class AbstractExcelExporter {
    protected abstract String prepareDirectoriesAndExcelFilePath(String fileName);

    protected abstract CellStyle buildHeaderStyle(Workbook workbook);

    protected abstract CellStyle buildDataCellStyle(Workbook workbook);

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractExcelExporter.class);

    public <T> void writeToExcel(List<T> exportDataList, String fileName, String sheetName) {

        checkMethodParameters(exportDataList, fileName, sheetName);

        String excelFilePath = prepareDirectoriesAndExcelFilePath(fileName);

        try (
                FileOutputStream fos = new FileOutputStream(excelFilePath);
                Workbook workbook = new XSSFWorkbook();
        ) {


            Sheet sheet = workbook.createSheet(sheetName);
            CellStyle headerStyle = buildHeaderStyle(workbook);
            CellStyle dataCellStyle = buildDataCellStyle(workbook);

            Row header = sheet.createRow(0);
            int cellIndex = 0;
            List<Field> fields = getIndexedAnnotatedFields(exportDataList.get(0).getClass(), ExportToExcel.class);
            for (Field field : fields) {
                ExportToExcel annotation = field.getAnnotation(ExportToExcel.class);
                String columnName = annotation.headerText();
                int columnWidth = annotation.width();

                sheet.setColumnWidth(cellIndex, columnWidth);

                Cell headerCell = header.createCell(cellIndex++);
                headerCell.setCellValue(columnName);
                headerCell.setCellStyle(headerStyle);
            }


            int rowIndex = 1;
            for (T exportData : exportDataList) {
                Row dataRow = sheet.createRow(rowIndex++);
                cellIndex = 0;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(exportData);

                    Cell dataCell = dataRow.createCell(cellIndex++);
                    dataCell.setCellValue(String.valueOf(value));
                    dataCell.setCellStyle(dataCellStyle);
                }
            }

            workbook.write(fos);
        } catch (IOException e) {
            log.error("Excel data export error : {}", e.getMessage());
            throw new DataExportException(e.getMessage());
        } catch (IllegalAccessException e) {
            log.error("Excel data export error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected <T> void checkMethodParameters(List<T> exportDataList, String fileName, String sheetName) {
        if (exportDataList == null) {
            throw new MissingArgumentException("Export to Excel data missing. Data list null");
        }
        if (exportDataList.isEmpty()) {
            throw new MissingArgumentException("There is no data to export to Excel. Data list empty");
        }
        if (fileName == null || sheetName == null) {
            throw new MissingArgumentException("Appropriate filename and page name must be provided. File name or Sheet name null");
        }
    }

    protected void createDirectories(String path) {
        Path directoryPath = Paths.get(path);

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                log.error("Directory creation error: {}", e.getMessage());
                throw new DirectoryCreationException(String.format("Creation Error: Could not create folder in the file path : %s", e.getMessage()));
            }
        }
    }

    protected List<Field> getIndexedAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Field> annotatedFields = new ArrayList<>();
        Set<Integer> indexSet = new HashSet<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                int index = field.getAnnotation(ExportToExcel.class).index();
                if (indexSet.contains(index)) {
                    throw new ConflictException("ExportToExcel.class duplicate index value: " + index);
                } else if (index == -1) {
                    throw new MissingArgumentException("ExportToExcel.class index value missing: " + index);
                }
                annotatedFields.add(field);
                indexSet.add(index);
            }
        }

        annotatedFields.sort(Comparator.comparingInt(field -> field.getAnnotation(ExportToExcel.class).index()));
        return annotatedFields;
    }

}


