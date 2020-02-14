package org.fsn.framework.core.security.user.token;


import org.fsn.framework.core.security.exception.BaseSecurityException;
import org.fsn.framework.core.security.user.Token;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.StringTokenizer;

/**
 * 采用对称算法加密实现的token机制
 * User: Sorta
 * Date: 16-8-28
 * Time: 下午10:46
 */

public class EncryptedToken extends Token {
    protected String tokenKey;
    protected String tokenAlgorithm;
    protected Key key;

    public EncryptedToken(String secretKey, String algorithm, Integer uid, Integer frnId, String phoneNumber, String clientId, ClientType clientType) {
        super(uid,frnId, phoneNumber, clientId, clientType);
        this.tokenKey = secretKey;
        this.tokenAlgorithm = algorithm;
        init();
    }

    public EncryptedToken(String secretKey, String algorithm, Integer uid, Integer frnId,Long sid, String phoneNumber, String clientId, ClientType clientType) {
        super(uid,frnId,sid, phoneNumber, clientId, clientType);
        this.tokenKey = secretKey;
        this.tokenAlgorithm = algorithm;
        init();
    }

    public EncryptedToken(String secretKey, String algorithm, Integer uid,Long sid, String phoneNumber, String clientId, ClientType clientType) {
        super(uid,Token.DEFAULTFRN,sid, phoneNumber, clientId, clientType);
        this.tokenKey = secretKey;
        this.tokenAlgorithm = algorithm;
        init();
    }

    private void init() {
        // 初始化密钥key
        try {
            DESKeySpec dks = new DESKeySpec(tokenKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(tokenAlgorithm);
            key = keyFactory.generateSecret(dks);
        } catch (Exception e) {
            throw new BaseSecurityException(e);
        }
    }

    public EncryptedToken(String secretKey, String algorithm, String token) {
        this.tokenKey = secretKey;
        this.tokenAlgorithm = algorithm;
        init();
        try {
            Cipher cipher = Cipher.getInstance(tokenAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainBytes = cipher.doFinal(new BASE64Decoder().decodeBuffer(token));
            String tokenPlainText = new String(plainBytes);
            StringTokenizer st = new StringTokenizer(tokenPlainText, "|");
            uid = Integer.parseInt(st.nextToken());
            frnId = Integer.parseInt(st.nextToken());
            sid = Long.parseLong(st.nextToken());
            phoneNumber = st.nextToken();
            clientId = st.nextToken();
            clientType = ClientType.parse(Integer.parseInt(st.nextToken()));
            long createTimeMS = Long.parseLong(st.nextToken());
            createdTime = createTimeMS;
            //每个token保存到当前的线程中
        } catch (Exception e) {
            throw new BaseSecurityException(e);
        }
    }

    public Integer getUid() {
        return uid;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String toCipherString() {
        try {
            Cipher cipher = Cipher.getInstance(tokenAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String tokenPlainText = uid + "|" +frnId+"|"+sid+"|"+ phoneNumber + "|" + clientId + "|" + clientType.getCode() + "|" + createdTime;
            byte[] cipherBytes = cipher.doFinal(tokenPlainText.getBytes());
            return URLEncoder.encode(new String(new BASE64Encoder().encode(cipherBytes)),"UTF-8");
        } catch (Exception e) {
            throw new BaseSecurityException(e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("uid :").append(uid).append(",").
                append("frnId :").append(frnId).append(",").
                append("sid :").append(sid).append(",").
                append("phoneNumber :").append(phoneNumber).append(",").
                append("clientId : ").append(clientId).append(",").
                append("clientType : ").append(clientType.name()).append(",").
                append("create Date :").append(createdTime);
        return sb.toString();
    }

    public static void main(String args[]) {
        Token token = new EncryptedToken("94a7cbbf8511a288d22d4cf8705d61d0", "DES", 111,1L, "13981719943", null, ClientType.WXAPP);
        System.out.println(token);
        String cipherToken = token.toCipherString();
        System.out.println(cipherToken);
        Token _token = null;
        try {
            _token = new EncryptedToken("94a7cbbf8511a288d22d4cf8705d61d0", "DES", URLDecoder.decode("e7SynJfrlzfsgNfLU1TK5Z9IsrraAz3kNUjr%2BNzob2heuR9xYwiSVQ%3D%3D","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(_token);
    }
}
