package org.fsn.framework.core.security.user.token;


import org.fsn.framework.core.security.user.Token;

public class TokenUtil {
    private static ThreadLocal<Token> localToken = new ThreadLocal<>();

    public static void setToken(Token token) {
        localToken.set(token);
    }

    public static Token getToken() {
        return localToken.get();
    }

    public static void removeToken() {
        localToken.remove();
    }

    public static String getPhone() {
        Token token = localToken.get();
        if (token == null) {
            throw new RuntimeException("token is not exist");
        }
        return token.getPhoneNumber();
    }

    public static Integer getUid() {
        Token token = localToken.get();
        if (token == null) {
            throw new RuntimeException("token is not exist");
        }

        return token.getUid();
    }
}
