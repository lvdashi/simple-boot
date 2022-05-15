package com.ljh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 移动端分页对象
 *
 * @author jinhuilv
 * @date 2022/04/15 17:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MobilePage<T> {
    /**
     * 总页数
     */
    private Integer allPage;
    /**
     * 当前页码
     */
    private Integer nowPage;
    /**
     * 当前页数据
     */
    private List<T> list;

}
