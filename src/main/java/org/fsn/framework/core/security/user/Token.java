package org.fsn.framework.core.security.user;

import java.io.Serializable;
import java.util.Date;


public class Token implements Serializable{
    private static final long serialVersionUID = -6602465878134234541L;

    public static int DEFAULTFRN =0;

    public enum ClientType{
        UNKNOWN(0),MOBILE(1),BROWSER(2),WX(3),WXAPP(4);

        protected int code;
        ClientType(int code){
            this.code = code;
        }
        public int getCode(){
            return code;
        }
        public static ClientType parse(int typeCode){
            ClientType _clientType = UNKNOWN;
            switch (typeCode){
                case 0:_clientType = UNKNOWN;break;
                case 1:_clientType = MOBILE;break;
                case 2:_clientType = BROWSER;break;
                case 3:_clientType = WX;break;
                case 4:_clientType = WXAPP;break;
            }
            return _clientType;
        }
    }

    protected Integer uid;
    protected Long ulid;
    protected Integer frnId;//加盟商
    protected Long sid;// 店铺id
    protected String phoneNumber;
    protected String clientId; //手机唯一编码
    protected ClientType clientType;
    protected long createdTime;
    protected String cipherString;


    public Token(){

    }

    public Token(Integer uid, Integer frnId,String phoneNumber, String clientId, ClientType clientType){
        this.uid = uid;
        this.ulid = 0L;
        this.frnId = frnId;
        this.sid = 0L;
        this.phoneNumber = phoneNumber;
        this.clientId = clientId;
        this.clientType = clientType;
        createdTime = new Date().getTime();
    }



    public Token(Integer uid, Integer frnId,Long sid,String phoneNumber, String clientId, ClientType clientType){
        this.uid = uid;
        this.ulid = 0L;
        this.frnId = frnId;
        this.sid = sid;
        this.phoneNumber = phoneNumber;
        this.clientId = clientId;
        this.clientType = clientType;
        createdTime = new Date().getTime();
    }

    public Token(Long ulid, Integer frnId,Long sid,String phoneNumber, String clientId, ClientType clientType){
        this.uid = 0;
        this.ulid = ulid;
        this.frnId = frnId;
        this.sid = sid;
        this.phoneNumber = phoneNumber;
        this.clientId = clientId;
        this.clientType = clientType;
        createdTime = new Date().getTime();
    }


    public String toCipherString(){return cipherString;};

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("uid : ").append(uid).append(",").append("frnId : ").append(frnId).append(",").append("sid : ").append(sid).append(",").
        append("phoneNumber : ").append(phoneNumber).append(",").
        append("clientId : ").append(clientId).append(",").
        append("clientType : ").append(clientType.name()).append(",").
        append("create Date :").append(createdTime);
        return sb.toString();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Integer getFrnId() {
        return frnId;
    }

    public void setFrnId(Integer frnId) {
        this.frnId = frnId;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getUlid() {
        return ulid;
    }

    public void setUlid(Long ulid) {
        this.ulid = ulid;
    }
}
