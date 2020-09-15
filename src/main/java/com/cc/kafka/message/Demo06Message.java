package com.cc.kafka.message;

import lombok.Data;

@Data
public class Demo06Message {

    public static final String TOPIC = "DEMO_06";

    /**
     * 编号
     */
    private Integer id;


}