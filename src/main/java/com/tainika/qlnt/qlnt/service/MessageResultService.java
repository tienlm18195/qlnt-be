package com.tainika.qlnt.qlnt.service;

import com.google.common.base.Strings;
import com.tainika.qlnt.qlnt.constants.Message;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.tainika.qlnt.qlnt.constants.Status.COMMON.*;

@Slf4j
@Data
@Builder
public class MessageResultService<T> {
    private String action;
    private String content;
    private String responseMessage;
    private Integer status;
    private T item;

    public MessageResultService(String action) {
        this.action = action;
    }

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
        this.status = SUCCESS;
        log.info(responseMessage);
        return this;
    }

    public MessageResultService<T> withFailureResponse() {
        this.responseMessage = String.format(Message.LOG.ACTION_FAIL.getName(), action, content);
        this.status = FAILURE;
        log.info(responseMessage);
        return this;
    }

    public MessageResultService<T> withErrorResponse() {
        this.responseMessage = String.format(Message.LOG.ACTION_ERROR.getName(), action, content);
        this.status = ERROR;
        log.error(responseMessage);
        return this;
    }

    public Exception throwRuntimeException() throws RuntimeException {
        throw new RuntimeException(Strings.nullToEmpty(responseMessage));
    }

    public Exception throwIllegalAccessException() throws IllegalAccessException {
        throw new IllegalAccessException(Strings.nullToEmpty(responseMessage));
    }
}
