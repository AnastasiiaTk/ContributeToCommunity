package com.contributetocommunity.service.impl;

import com.contributetocommunity.listener.RunApplicationListener;
import com.contributetocommunity.service.InitialDataService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InitialDataServiceImpl implements InitialDataService {

    Logger logger = LoggerFactory.getLogger(InitialDataServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    private static final String QUERY_GENERAL_PATTERN = "INSERT INTO %1$s %2$s %3$s";
    private static final String QUERY_VALUES_PATTERN = "VALUES %s";
    private static final String SINGLE_VALUE_PATTERN = "(%s)";
    private static final String DELIMITER = ",";

    @Override
    @Transactional
    public void loadDataFromExcel(String filePath) {
        {
            try {
                InputStream sourceFile = getClass().getResourceAsStream(filePath);
                XSSFWorkbook wb = new XSSFWorkbook(sourceFile);
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    String tableName = sheet.getSheetName();
                    Iterator rowIterator = sheet.rowIterator();
                    List<String> values = new LinkedList<>();
                    while (rowIterator.hasNext()) {
                        Row currentRow = (Row) rowIterator.next();
                        List<String> rowValuesSet = new LinkedList<>();
                        Iterator cellsIterator = currentRow.cellIterator();
                        cellsIterator.forEachRemaining(cell -> rowValuesSet.add(formatCEllValue((XSSFCell) cell)));
                        if (!CollectionUtils.isEmpty(rowValuesSet.stream()
                                .filter(v -> Objects.nonNull(v))
                                .collect(Collectors.toSet()))) {
                            values.add(String.format(SINGLE_VALUE_PATTERN,
                                    rowValuesSet.stream().collect(Collectors.joining(DELIMITER))));
                        }
                    }
                    String header = extractHeader(values);
                    values.remove(header);
                    String allValues = values.stream().collect(Collectors.joining(DELIMITER));
                    String query = String.format(QUERY_GENERAL_PATTERN, tableName,
                            header, String.format(QUERY_VALUES_PATTERN, allValues));
                    Query q = entityManager.createNativeQuery(query);
                    q.executeUpdate();
                }
            } catch (Exception e) {
                logger.error("ERROR during load initial data. Application is run without uploading");
                logger.error(e.getMessage());
            }
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

    private String extractHeader(List<String> values) {
        String header = values.iterator().next();
        values.remove(header);
        return header.replace("\'", "");
    }
}
