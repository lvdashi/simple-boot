package com.ljh.codegen;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.*;
import static com.ljh.codegen.TypeConverts.contains;
import static com.ljh.codegen.TypeConverts.containsAny;


/**
 * MYSQL 数据库字段类型转换
 */
public class MySqlTypeConvert implements ITypeConvert {
    public static final MySqlTypeConvert INSTANCE = new MySqlTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(containsAny("char", "text", "json", "enum").then(STRING))
            .test(contains("bigint").then(LONG))
            .test(containsAny("tinyint(1)", "bit").then(BOOLEAN))
            .test(contains("int").then(INTEGER))
            .test(contains("decimal").then(BIG_DECIMAL))
            .test(contains("clob").then(CLOB))
            .test(contains("blob").then(BLOB))
            .test(contains("binary").then(BYTE_ARRAY))
            .test(contains("float").then(FLOAT))
            .test(contains("double").then(DOUBLE))
            .test(containsAny("date", "time", "year").then(t -> toDateType(config, t)))
            .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    public static IColumnType toDateType(GlobalConfig config, String type) {
        switch (config.getDateType()) {
            case ONLY_DATE:
                return DbColumnType.DATE;
            case SQL_PACK:
                switch (type) {
                    case "date":
                    case "year":
                        return LOCAL_DATE;
                    case "time":
                    default:
                        return LOCAL_DATE_TIME;
                }
            case TIME_PACK:
                switch (type) {
                    case "date":
                        return DbColumnType.LOCAL_DATE;
                    case "time":
                        return DbColumnType.LOCAL_TIME;
                    case "year":
                        return DbColumnType.YEAR;
                    default:
                        return DbColumnType.LOCAL_DATE_TIME;
                }
        }
        return STRING;
    }

}
