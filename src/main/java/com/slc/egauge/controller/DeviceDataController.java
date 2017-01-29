/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.controller;

import com.google.gson.Gson;
import com.slc.egauge.model.Device;
import com.slc.egauge.service.DeviceService;
import com.slc.egauge.utils.DBDeviceNames;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Steven
 */
@Path("devices")
public class DeviceDataController {
    private DeviceService ds = new DeviceService();
    private DateFormat dfDateTime = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    /**
     * Creates a new instance of DeviceDataController
     */
    public DeviceDataController() {
    }
    
    /**
     * getData give request parameters
     */
    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public String getData( 
            @QueryParam("campus") List<String> campusParam,
            @QueryParam("startDate") String startDateParam,
            @QueryParam("endDate") String endDateParam
        ) {
        Gson gson = new Gson();
        Calendar cal = Calendar.getInstance();
        List<String> campus = null;
        String returnData = null;
        Date startDate = null;
        Date endDate = null;
                
        try {
            // populate campus array
            if (!campusParam.isEmpty() && !campusParam.get(0).isEmpty()) {
                campus = DBDeviceNames.getCampusDBNames(campusParam);
            } else {
                campus = new ArrayList<>();
                for (DBDeviceNames deviceName : DBDeviceNames.values()) {
                    campus.add(deviceName.getEntityName());
                }
            }
            // determine values of start and end Date
            if (startDateParam == null || startDateParam.isEmpty()) {
                startDate = new Date();
            } else {
                startDate = dfDateTime.parse(startDateParam);
            }
            
            if (endDateParam == null || endDateParam.isEmpty()) {
                cal.setTime(startDate);
                cal.add(Calendar.DAY_OF_MONTH, -90);
                endDate = cal.getTime();
            } else {
                endDate = dfDateTime.parse(endDateParam);
            }
            
            // make sure end date is after start date
            if (endDate.after(startDate)) {                
                cal.setTime(startDate);
                cal.add(Calendar.DAY_OF_MONTH, -90);
                endDate = cal.getTime();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            returnData = gson.toJson(e.toString());
            return returnData;
        }
        // Build json object and assign string value to returnData String
        returnData = gson.toJson(ds.getDevicesByNameAndDate(campus, endDate, startDate));
        
        return returnData;
    }
    
    @GET
    @Path("/instdata")
    @Produces(MediaType.APPLICATION_JSON)
    public String getInstData( @QueryParam("campus") List<String> campusParam) {
        Gson gson = new Gson();
        List<String> campus = null;
        String returnData = null;
        try {
            // populate campus array
            if (!campusParam.isEmpty() && !campusParam.get(0).isEmpty()) {
                campus = DBDeviceNames.getCampusDBNames(campusParam);
            } else {
                campus = new ArrayList<>();
                for (DBDeviceNames deviceName : DBDeviceNames.values()) {
                    campus.add(deviceName.getEntityName());
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            returnData = gson.toJson(e.toString());
            return returnData;
        }
        // Build json object and assign string value to returnData String
        returnData = gson.toJson(ds.getInstReadings(campus));
        
        return returnData;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDevices(@QueryParam("campus") String campusParam) {
        String rtVl = null;
        Gson gson = new Gson();
        try {
            if (campusParam == null) {
                List<Device> devices =  new ArrayList();
                for (String deviceName : DBDeviceNames.getCampusDBNames(null)) {
                    devices.add( ds.getDeviceByName(deviceName) );
                }
                rtVl = gson.toJson(devices);
            } else {
                String campus = DBDeviceNames.getDBName(campusParam);
                if (!campus.isEmpty()){
                  Device device = ds.getDeviceByName(campus);
                  rtVl = gson.toJson(device);
                } else {
                  rtVl = gson.toJson("failed to parse campus");
                }
            }
        } catch (Exception e) {
          e.printStackTrace();
            rtVl = gson.toJson(e.toString());
            System.out.println(e.toString());
            return rtVl;
        }
        return rtVl;
    }   
}
