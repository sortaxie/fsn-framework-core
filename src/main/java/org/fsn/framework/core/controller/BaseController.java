package org.fsn.framework.core.controller;


import org.fsn.framework.core.security.user.Token;
import org.fsn.framework.core.security.user.token.TokenUtil;

public class BaseController {

    private Token token;

    public Token getToken() {

        return TokenUtil.getToken();
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
