package com.cc.kafka.message;

import lombok.Data;

@Data
public class Demo07Message {

    public static final String TOPIC = "DEMO_07";

    /**
     * 编号
     */
    private Integer id;


}