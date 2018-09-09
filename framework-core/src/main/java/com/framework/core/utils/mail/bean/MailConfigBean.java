package com.framework.core.utils.mail.bean;

public class MailConfigBean {
    private String userName;
    private String password;
    private String smtp;
    
    
    public MailConfigBean(String userName, String password, String smtp) {
        super();
        this.userName = userName;
        this.password = password;
        this.smtp = smtp;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSmtp() {
        return smtp;
    }
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }
    
    
}
