package com.ljh.jpa.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RangeCondition {

    private String colName;
    private Object begin;
    private Object end;
}
