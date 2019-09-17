package org.fsn.framework.core.interceptor;


import org.fsn.framework.common.exception.error.BaseBusinessModuleException;
import org.fsn.framework.common.exception.error.DefaultError;
import org.fsn.framework.core.security.annotation.IgnoreOverTimeChecking;
import org.fsn.framework.core.security.annotation.IgnoreTokenChecking;
import org.fsn.framework.core.security.user.Token;
import org.fsn.framework.core.security.user.token.EncryptedToken;
import org.fsn.framework.core.security.user.token.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Configuration
public class CheckTokenInterceptor extends HandlerInterceptorAdapter {

    @Value("${framework.secrety.secretKey}")
    private String secretKey;
    @Value("${framework.secrety.algorithm:DES}")
    private String algorithm;
    @Value("${framework.secrety.overtime:-1}")
    private Long overtime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        if (method.getAnnotationsByType(IgnoreTokenChecking.class).length > 0) {
            return true;
        }



        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            throw new BaseBusinessModuleException(DefaultError.TOKEN_NOT_FOUND);
        }

        Token t = new EncryptedToken(secretKey, algorithm, token);

        if(method.getAnnotationsByType(IgnoreOverTimeChecking.class).length > 0){
            TokenUtil.setToken(t);
            return true;
        }

        if (overtime > 0 && System.currentTimeMillis() - t.getCreatedTime() >= overtime) {
            throw new BaseBusinessModuleException(DefaultError.TOKEN_OVERTIME);
        }

        TokenUtil.setToken(t);
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenUtil.setToken(null);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
