package com.cc.kafka.message;

import lombok.Data;

/**
 * @author cc
 */
@Data
public class Demo02Message {

    public static final String TOPIC = "DEMO_02";

    /**
     * 编号
     */
    private Integer id;


}