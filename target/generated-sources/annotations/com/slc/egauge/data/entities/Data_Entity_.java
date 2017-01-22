package com.slc.egauge.data.entities;

import com.slc.egauge.data.entities.Device_Entity;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-30T16:00:58")
@StaticMetamodel(Data_Entity.class)
public class Data_Entity_ { 

    public static volatile SingularAttribute<Data_Entity, BigDecimal> instPower;
    public static volatile SingularAttribute<Data_Entity, String> dataId;
    public static volatile SingularAttribute<Data_Entity, Date> timeRecorded;
    public static volatile SingularAttribute<Data_Entity, BigDecimal> power;
    public static volatile SingularAttribute<Data_Entity, Device_Entity> deviceId;

}