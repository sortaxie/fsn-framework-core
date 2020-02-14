package org.fsn.framework.core;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@ConditionalOnClass(EurekaClient.class)
public class EurekaClientSender {

    @Resource
    private EurekaClient eurekaClient;

    @PreDestroy
    public void offLine(){
        eurekaClient.shutdown();
    }

}
