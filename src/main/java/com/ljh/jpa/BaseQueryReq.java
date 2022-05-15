package com.ljh.jpa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljh.jpa.annotation.QueryCondition;
import com.ljh.jpa.annotation.QueryRelation;
import com.ljh.jpa.pojo.RangeCondition;
import com.ljh.jpa.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.ljh.jpa.annotation.QueryRelation.EQ;

/**
 * 基础查询类，提供自动包装queryWrapper
 * @date 2021/7/6 00:41
 * @since 0.3.3
 */
@Slf4j
public abstract class BaseQueryReq<T> {

    public QueryWrapper<T> buildQueryWrapper() {
        QueryWrapper<T> qw = new QueryWrapper<>();
        Class clazz = this.getClass();
        Object _this = this;
        Field[] fields = clazz.getDeclaredFields();
        Map<String, RangeCondition> rangeConditionMap = new HashMap<>();
        Arrays.stream(fields)
                .forEach(f -> {
                    String colName = null;
                    String defaultValue = null;
                    QueryRelation relation = EQ;
                    String groupId = null;

                    QueryCondition[] qc = f.getAnnotationsByType(QueryCondition.class);

                    if (qc != null && qc.length > 0 && qc[0] != null) {
                        colName = qc[0].colName();
                        relation = qc[0].relation();
                        defaultValue = qc[0].defaultValue();
                        groupId = qc[0].group();
                    }
                    if (StringUtils.isEmpty(colName)) {
                        colName = ReflectUtils.humpToLine(f.getName());
                    }
                    try {
                        Method getter = clazz.getMethod(ReflectUtils.getter(f.getName()));
                        Object val = getter.invoke(_this);

                        if (val == null
                                || "".equals(val)
                                || (val instanceof Collection && ((Collection) val).size() == 0)
                                || (val instanceof String[] && ((String[]) val).length == 0)
                        ) {
                            if (StringUtils.isNotEmpty(defaultValue)) {
                                val = defaultValue;
                            } else {
                                return;
                            }
                        }

                        switch (relation) {
                            case EQ:
                                qw.eq(colName, val);
                                break;
                            case GT:
                                qw.gt(colName, val);
                                break;
                            case LT:
                                qw.lt(colName, val);
                                break;
                            case LIKE:
                                qw.like(colName, val);
                                break;
                            case LIKE_RIGHT:
                                qw.likeRight(colName, val);
                                break;
                            case IN:
                                qw.in(colName, (Object[]) val);
                                break;
                            case GE:
                                qw.ge(colName, val);
                                break;
                            case LE:
                                qw.le(colName, val);
                                break;
                            case BETWEEN_BEGIN: {
                                if (StringUtils.isBlank(groupId)) {
                                    break;
                                }
                                RangeCondition range = rangeConditionMap.get(groupId);
                                if (range == null) {
                                    range = new RangeCondition()
                                            .setColName(colName)
                                            .setBegin(val);
                                    rangeConditionMap.put(groupId, range);
                                } else {
                                    range.setBegin(val);
                                }
                                break;
                            }
                            case BETWEEN_END: {
                                if (StringUtils.isBlank(groupId)) {
                                    break;
                                }
                                RangeCondition range = rangeConditionMap.get(groupId);
                                if (range == null) {
                                    range = new RangeCondition()
                                            .setColName(colName)
                                            .setEnd(val);
                                    rangeConditionMap.put(groupId, range);
                                } else {
                                    range.setEnd(val);
                                }
                                break;
                            }

                        }

                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        log.error("自动组装查询条件失败", e);
                    }
                });
        if (rangeConditionMap.size() > 0) {
            rangeConditionMap.values()
                    .forEach(range -> {
                        if (StringUtils.isBlank(range.getColName())
                                || range.getBegin() == null
                                || range.getEnd() == null) {
                            return;
                        }
                        qw.between(range.getColName(), range.getBegin(), range.getEnd());
                    });
        }
        customQueryWrapper(qw);
        return qw;
    }

    protected void customQueryWrapper(QueryWrapper<T> qw) {

    }


}
