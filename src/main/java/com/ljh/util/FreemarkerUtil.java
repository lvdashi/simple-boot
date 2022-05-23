package com.ljh.util;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

/**
 * FreemarkerUtil工具类
 */
public class FreemarkerUtil {

    /**
     * 根据模板，利用提供的数据，生成文件
     *
     * @param sourceFile 模板文件，带路径
     * @param data       数据
     * @param aimFile    最终生成的文件，带路径
     * @throws IOException
     * @throws TemplateException
     */
    public static void execute(String sourceFile, Map<String, Object> data, String aimFile) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);//创建Freemarker配置实例
        cfg.setClassForTemplateLoading(FreemarkerUtil.class, StringPool.SLASH);
        cfg.setDefaultEncoding("UTF-8");

        Template t1 = cfg.getTemplate(sourceFile);
        Writer out = new FileWriter(new File(aimFile));
        t1.process(data, out);
        out.flush();
        out.close();
    }


}
