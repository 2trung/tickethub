package org.tickethub.controller.model.vo;

import lombok.Data;

@Data
public class ResultMessage<T> {

    /**
     * Cờ thành công
     */
    private boolean success;

    /**
     * Thông báo
     */
    private String message;

    /**
     * Mã trả về
     */
    private Integer code;

    /**
     * Dấu thời gian
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * Đối tượng kết quả
     */
    private T result;
}
