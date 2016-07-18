/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.service;

import com.slc.egauge.data.dao.DataDAO;
import com.slc.egauge.data.dao.DeviceDAO;
import com.slc.egauge.data.entities.Device_Entity;
import com.slc.egauge.utils.DBDeviceNames;
import com.slc.egauge.utils.DatabaseUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Service to handle large inserts into tables
 * 
 * @author srostantkritikos06
 */
public class DataDumpService implements Job {
    EntityManagerFactory emf;
    DeviceDAO deviceDAO;
    DataDAO dataDAO;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    File file = new File("data.csv");

    public DataDumpService() {
        this.emf = DatabaseUtils.getEntityManager();
        this.deviceDAO = new DeviceDAO(emf);
        this.dataDAO = new DataDAO(emf);
    }
    
    public void insertDataIntoDB() {
        try {
            System.out.println("FILE AT " + file.getAbsolutePath());
            BufferedReader csvFile = new BufferedReader(new FileReader(file));
            
            CSVParser parser = new CSVParser(csvFile, CSVFormat.EXCEL);
            List<CSVRecord> records = parser.getRecords();
            
            try {
                for (int i = 1; i < records.size(); i++) {
                    List<String> dataList = getNeededData(records.get(i));
                    Date insertDate = df.parse(dataList.get(0));
                    List<String> dbDeviceNames = DBDeviceNames.getCampusDBNames(null);
                    for (int x = 1; x < dataList.size(); x++) {
                        System.out.println("DATA FOR " + dbDeviceNames.get(x-1) +  "  DATA IS  "+  dataList.get(x));
                        Device_Entity tempDevice = deviceDAO.getDeviceByName(dbDeviceNames.get(x-1));
                        // Get inst power in watts by multiplying the value by 10000 since its in KW
                        BigDecimal instPower = new BigDecimal(dataList.get(x)).multiply(new BigDecimal(10000));
                        dataDAO.insertDataIntoTable(tempDevice, null , insertDate, instPower);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataDumpService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataDumpService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<String> getNeededData(CSVRecord record) {

        List<String> rtVl = new ArrayList<>();
        
        //Get date
        rtVl.add(record.get(0));
        // Get KingstonWard1
        rtVl.add(record.get(10));
        // Get KingstonWard2
        rtVl.add(record.get(12));
        // Get Total Ktown Power
        rtVl.add(record.get(14));
        // Get Cornwall power
        rtVl.add(record.get(15));
        // Get Borckville power
        rtVl.add(record.get(16));
        
        return rtVl;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("EXECUTING DATA DUMP FROM SCV FILE");
        insertDataIntoDB();
    }

}
