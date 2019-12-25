package org.fsn.framework.core.controller;

import com.netflix.discovery.EurekaClient;
import org.fsn.framework.common.pojo.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {


    @Value("${framework.secrety.user:''}")
    private String suer;
    @Value("${framework.secrety.pwd:''}")
    private String pwd;

    @Autowired
    private EurekaClient eurekaClient;



    @PostMapping(value = "/shutdown")
    public BaseResponse shutdown(String user,String password){
       BaseResponse response =  new BaseResponse();

        if(StringUtils.isEmpty(user)||StringUtils.isEmpty(password)||StringUtils.isEmpty(suer)||StringUtils.isEmpty(pwd)){
            response.setStatus(BaseResponse.Status.FAILED);
            return response;
        }
        if(suer.equals(user)&&pwd.equals(password)){
            eurekaClient.shutdown();
            System.exit(0);
        }else{
            response.setStatus(BaseResponse.Status.FAILED);
        }

        return response;
    }
}
