package com.cc.kafka.message;

import lombok.Data;

/**
 * @author 86130
 */
@Data
public class Demo01Message {

    public static final String TOPIC = "DEMO_01";

    /**
     * 编号
     */
    private Integer id;

}