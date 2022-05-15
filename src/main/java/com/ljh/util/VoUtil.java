package com.ljh.util;

import cn.hutool.core.util.PageUtil;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.ljh.entity.MobilePage;
import com.ljh.entity.MobilePageReq;


import java.util.ArrayList;
import java.util.List;

/**
 * vo工具
 * @author jinhuilv
 * @date 2022/04/18 11:14
 **/
public class VoUtil<T> {

    /**
     * 查询结果构造成移动端分页数据
     * @param req
     * @param count
     * @param list
     * @param t
     * @return
     */
    public MobilePage pageConvert(MobilePageReq req, int count, List list, Class<T> t){
        List resultList=new ArrayList<>();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        list.forEach(item->{
            T vo=mapper.map(item,t);
            resultList.add(vo);
        });
        return new MobilePage(PageUtil.totalPage(count, req.getPageSize()),req.getPageNum(),resultList);
    }
    /**
     * 查询结果构造成移动端分页数据
     * @param req
     * @param count
     * @param list
     * @param t
     * @return
     */
    public MobilePage pageConvert(MobilePageReq req, Long count, List list, Class<T> t){
        List resultList=new ArrayList<>();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        list.forEach(item->{
            T vo=mapper.map(item,t);
            resultList.add(vo);
        });
        return new MobilePage(PageUtil.totalPage(Integer.parseInt(count.toString()), req.getPageSize()),req.getPageNum(),resultList);
    }

    /**
     * 实体List转VO list
     * @param list
     * @param t
     * @return
     */
    public List<T> listConvert(List list,Class<T> t){
        List resultList=new ArrayList<>();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        list.forEach(item->{
            T vo=mapper.map(item,t);
            resultList.add(vo);
        });
        return resultList;
    }

}
