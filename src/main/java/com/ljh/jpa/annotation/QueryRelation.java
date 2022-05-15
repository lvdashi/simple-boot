package com.ljh.jpa.annotation;

public enum QueryRelation {

    EQ(),
    LIKE(),
    /**
     * LIKE '值%'
     */
    LIKE_RIGHT(),
    LT(),
    GT(),
    IN(),
    LE(),
    GE(),
    BETWEEN_BEGIN(),
    BETWEEN_END(),
    ;
}
