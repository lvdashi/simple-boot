package com.ljh.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 生成代码表名前缀
 *
 * @author jinhuilv
 * @date 2023/03/29 00:18
 **/
@Data
@Component
@ConfigurationProperties(prefix = "simple-boot.code-gen.common")
public class TablePrefix {

    private List<String> commonPrefix;
}
