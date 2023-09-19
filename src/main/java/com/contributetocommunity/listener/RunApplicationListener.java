package com.contributetocommunity.listener;

import com.contributetocommunity.service.InitialDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class RunApplicationListener {

    Logger logger = LoggerFactory.getLogger(RunApplicationListener.class);
    @Autowired
    private InitialDataService initialDataService;

    @Value("${initial.data.file.path}")
    String excelFilePath;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadData() {
        initialDataService.loadDataFromExcel(excelFilePath);
    }

}
