package org.fsn.framework.core.util;

import org.fsn.framework.common.utils.DateUtil;
import org.fsn.framework.common.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdUtil {

    @Value("${fsn.snowflake.workerId:1}")
    private long workerId;
    @Value("${fsn.snowflake.dataCenterId:1}")
    private long dataCenterId;


    public String  getId(){
        SnowflakeIdWorker  snowflakeIdWorker = SnowflakeIdWorker.getInstance(workerId,dataCenterId);
        return  snowflakeIdWorker.nextId();
    }

    public String getId(String prefix){
        SnowflakeIdWorker  snowflakeIdWorker = SnowflakeIdWorker.getInstance(workerId,dataCenterId);
        String id = new StringBuilder(prefix)
                .append(snowflakeIdWorker.nextId()).toString();
        return id;
    }

    public String getDatePreId(){
        SnowflakeIdWorker  snowflakeIdWorker = SnowflakeIdWorker.getInstance(workerId,dataCenterId);
        String id = new StringBuilder(DateUtil.ymd())
                .append(snowflakeIdWorker.nextId()).toString();
        return id;
    }

}
