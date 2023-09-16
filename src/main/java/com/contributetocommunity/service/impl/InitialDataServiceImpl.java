package com.contributetocommunity.service.impl;

import com.contributetocommunity.service.InitialDataService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InitialDataServiceImpl implements InitialDataService {

    @PersistenceContext
    private EntityManager entityManager;

    String excelFilePath = "C:\\Users\\Anastasiia\\IdeaProjects\\ContributeToCommunity\\src\\main\\resources\\data.xlsx";

    private static final String QUERY_GENERAL_PATTERN = "INSERT INTO %1$s %2$s %3$s";
    private static final String QUERY_VALUES_PATTERN = "VALUES %s";
    private static final String SINGLE_VALUE_PATTERN = "(%s)";
    private static final String DELIMITER = ",";

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadDataFromExcel() {
        try {
            FileInputStream sourceFile = new FileInputStream(excelFilePath);
            XSSFWorkbook wb = new XSSFWorkbook(sourceFile);
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                String tableName = sheet.getSheetName();
                Iterator rowIterator = sheet.rowIterator();
                List<String> valuesSet = new LinkedList<>();
                while (rowIterator.hasNext()) {
                    Row currentRow = (Row) rowIterator.next();
                    List<String> rowValuesSet = new LinkedList<>();
                    Iterator cellsIterator = currentRow.cellIterator();
                    cellsIterator.forEachRemaining(cell -> rowValuesSet.add(formatCEllValue((XSSFCell) cell)));
                    if (!CollectionUtils.isEmpty(rowValuesSet.stream()
                            .filter(v -> Objects.nonNull(v))
                            .collect(Collectors.toSet()))) {
                        valuesSet.add(String.format(SINGLE_VALUE_PATTERN,
                                rowValuesSet.stream().collect(Collectors.joining(DELIMITER))));
                    }
                }
                String header = valuesSet.iterator().next();
                valuesSet.remove(header);
                String allValues = valuesSet.stream().collect(Collectors.joining(DELIMITER));
                String query = String.format(QUERY_GENERAL_PATTERN, tableName, header.replace("\'", ""),
                        String.format(QUERY_VALUES_PATTERN, allValues));
                Query q = entityManager.createNativeQuery(query);
                q.executeUpdate();
            }
        } catch (IOException e) {

        }
    }

    private String formatCEllValue(XSSFCell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
            case FORMULA:
                return String.valueOf((int) cell.getNumericCellValue());
            case STRING:
                return "\'" + cell.getStringCellValue().replace("'", "\'\'") + "\'";
        }
        return null;
    }
}
