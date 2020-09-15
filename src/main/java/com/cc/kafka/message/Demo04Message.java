package com.cc.kafka.message;

import lombok.Data;
/**
 * Description
 *
 * @author wangchen
 * @createDate 2020/9/15
 **/
@Data
public class Demo04Message {

    public static final String TOPIC = "DEMO_04";

    /**
     * 编号
     */
    private Integer id;


}