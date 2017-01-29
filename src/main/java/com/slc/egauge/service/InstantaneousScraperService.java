/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.service;

import com.slc.egauge.data.dao.DataDAO;
import com.slc.egauge.data.dao.DeviceDAO;
import com.slc.egauge.data.entities.Data_Entity;
import com.slc.egauge.data.entities.Device_Entity;
import com.slc.egauge.data.xml.instantaneous.Data;
import com.slc.egauge.utils.ApplicationProperties;
import com.slc.egauge.utils.DatabaseUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Service class implements a quartz Job to get data
 * from eGauge endpoint to get stored in DB
 * 
 * @author srostantkritikos06
 */
public class InstantaneousScraperService implements Job {
    EntityManagerFactory emf;
    DataDAO datadao;
    DeviceDAO devicedao;
    Data_Entity prevDevice;
    Properties appProperties;

    public InstantaneousScraperService() {
        this.emf = DatabaseUtils.getEntityManager();
        this.datadao =  new DataDAO(emf);
        this.devicedao  = new DeviceDAO(emf);
        ApplicationProperties props = new ApplicationProperties();
        this.appProperties = props.getApplicationProperties();
    }
    
    

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("SCRAPING DATA");
        getInstData();
    }

    private void getInstData() {
        try {
            // Build XML Enitty Data class from url stream. 
            URL url = new URL(this.appProperties.getProperty("url.dataendpoint"));
            JAXBContext jc = JAXBContext.newInstance(Data.class);
            Unmarshaller um = jc.createUnmarshaller();
            Data inputData = (Data) um.unmarshal(url);
            
            //Convert secs into ms then create Date object out of value
            long timeMills = inputData.getTimeStamp().getTime() * 1000;
            Date date = new Date(timeMills);

            // Get a list of devices from db 
            // For each device filter out the register with the same name and execute insert
            List<Device_Entity> devices = devicedao.findDevice_EntityEntities();
            devices.stream().forEach(device -> 
                    inputData.getRegisters().stream().filter(register -> 
                        register.getName().equals(device.getDeviceName())
                    ).findAny()
                     .ifPresent(foundRegister -> 
                        datadao.insertDataIntoTable(device, foundRegister.getValue().getValue(), date, foundRegister.getRate().getRate()))
            );

        } catch (MalformedURLException | JAXBException ex) {
            Logger.getLogger(InstantaneousScraperService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Inst data service error thrown");
        }
        System.out.println("FINISHED SCRAPPING DATA ");
    }
}
