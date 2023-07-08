package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.constants.Message;
import com.tainika.qlnt.qlnt.constants.Status;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class MessageResultService<T> {
    private String action;
    private String content;
    private String responseMessage;
    private Integer status;
    private T item;

    public MessageResultService(String action, T item) {
        this.action = action;
        this.content = item.toString();
        this.item = item;
    }

    public MessageResultService(String action, String content, T item) {
        this.action = action;
        this.content = content;
        this.item = item;
    }

    public MessageResultService(String action, String content, String responseMessage, Integer status, T item) {
        this.action = action;
        this.content = content;
        this.responseMessage = responseMessage;
        this.status = status;
        this.item = item;
    }

    public MessageResultService<T> withSuccessResponse() {
        this.responseMessage = String.format(Message.LOG.ACTION_SUCCESS.getName(), action, content);
        this.status = Status.COMMON.SUCCESS;
        log.info(responseMessage);
        return this;
    }

    public MessageResultService<T> withFailureResponse() {
        this.responseMessage = String.format(Message.LOG.ACTION_FAIL.getName(), action, content);
        this.status = Status.COMMON.FAILURE;
        log.info(responseMessage);
        return this;
    }

    public MessageResultService<T> withErrorResponse() {
        this.responseMessage = String.format(Message.LOG.ACTION_ERROR.getName(), action, content);
        this.status = Status.COMMON.ERROR;
        log.error(responseMessage);
        return this;
    }
}
