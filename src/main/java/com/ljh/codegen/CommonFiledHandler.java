package com.ljh.codegen;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.ljh.entity.TableFileds;

import java.util.List;

/**
 * 表公共字段处理
 *
 * @author jinhuilv
 * @date 2023/03/28 23:38
 **/
public class CommonFiledHandler {
    /**
     * 处理公共字段
     * @param setTableFileds
     * @param strategy
     * @return
     */
    public static StrategyConfig hander(TableFileds setTableFileds, StrategyConfig strategy){
        List<String> items=setTableFileds.getCommonFields();
        switch (items.size()){
            case 1:
                strategy.setSuperEntityColumns(items.get(0));
                break;
            case 2:
                strategy.setSuperEntityColumns(items.get(0),items.get(1));
                break;
            case 3:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2));
                break;
            case 4:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3));
                break;
            case 5:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4));
                break;
            case 6:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5));
                break;
            case 7:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6));
                break;
            case 8:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6),items.get(7));
                break;
            case 9:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6),items.get(7),items.get(8));
                break;
            case 10:
                strategy.setSuperEntityColumns(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6),items.get(7),items.get(8),items.get(9));
                break;
            default:
        }
        return strategy;
    }
}
