package com.smart.helper;



public class Message {
    public String content;
    public String type;
    public Message(String content, String type) {
        this.content = content;
        this.type = type;
    }
    public String getMessage() {
        return content;
    }
    public void setMessage(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
