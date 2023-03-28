package com.ljh.codegen;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.ljh.entity.TablePrefix;

import java.util.List;

/**
 * 公共表名前缀处理
 *
 * @author jinhuilv
 * @date 2023/03/29 00:21
 **/
public class CommonPrefixHandler {
    /**
     * 处理公共表名前缀
     * @param tablePrefix
     * @param strategy
     * @return
     */
    public static StrategyConfig hander(TablePrefix tablePrefix, StrategyConfig strategy){
        List<String> items=tablePrefix.getCommonPrefix();
        switch (items.size()){
            case 1:
                strategy.setTablePrefix(items.get(0));
                break;
            case 2:
                strategy.setTablePrefix(items.get(0),items.get(1));
                break;
            case 3:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2));
                break;
            case 4:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3));
                break;
            case 5:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4));
                break;
            case 6:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5));
                break;
            case 7:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6));
                break;
            case 8:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6),items.get(7));
                break;
            case 9:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6),items.get(7),items.get(8));
                break;
            case 10:
                strategy.setTablePrefix(items.get(0),items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6),items.get(7),items.get(8),items.get(9));
                break;
            default:
        }
        return strategy;
    }
}
