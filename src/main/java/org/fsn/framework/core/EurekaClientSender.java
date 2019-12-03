package org.fsn.framework.core;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class EurekaClientSender {

    @Autowired
    private EurekaClient eurekaClient;

    @PreDestroy
    public void offLine(){
        eurekaClient.shutdown();
    }

}
