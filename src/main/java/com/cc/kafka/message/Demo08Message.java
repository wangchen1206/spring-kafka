package com.cc.kafka.message;

import lombok.Data;

@Data
public class Demo08Message {

    public static final String TOPIC = "DEMO_08";

    /**
     * 编号
     */
    private Integer id;


}