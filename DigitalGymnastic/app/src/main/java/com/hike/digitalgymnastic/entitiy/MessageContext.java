package com.hike.digitalgymnastic.entitiy;

import java.util.List;

public class MessageContext {
    private int total;// 消息总数
    private List<Message> messageList;// 消息列表

    public void setTotal(int total) {
        this.total = total;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public int getTotal() {
        return total;
    }

    public List<Message> getMessageList() {
        return messageList;
    }


}
