package ${package.Entity?replace(".entity",".rsp")};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ${table.comment!}
 * @author ${author}
 * @since ${date}
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "${entity}", description = "${entity}返回参数")
public class ${entity}RSP implements Serializable {

<#--    @ApiModelProperty(value = "id")-->
<#--    private Long id;-->

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
    @ApiModelProperty(value = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}

