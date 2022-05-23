package com.ljh.codegen;

import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class NameConvert implements INameConvert {


    /**
     * 执行实体名称转换
     *
     * @param tableInfo 表信息对象
     * @return
     */
    @Override
    public String entityNameConvert(TableInfo tableInfo) {
        String tableName = tableInfo.getName().replace("t_", "");
        String entityName = "";
        if (tableName.contains("_")) {
            String[] tn = tableName.split("_");
            if (tn.length > 1) {
                entityName = NamingStrategy.capitalFirst(NamingStrategy.underlineToCamel(tableName.substring(tableName.indexOf("_"))));
            }
        } else {
            entityName = NamingStrategy.capitalFirst(tableName);
        }
        tableInfo.setEntityName(entityName);
        return entityName;
    }

    /**
     * 执行属性名称转换
     *
     * @param field 表字段对象，如果属性表字段命名不一致注意 convert 属性的设置
     * @return
     */
    @Override
    public String propertyNameConvert(TableField field) {
        String fName = "";
        fName = NamingStrategy.underlineToCamel(field.getName());
        field.setColumnName(fName);
        return fName;
    }


}
