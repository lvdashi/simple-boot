package ${package.Entity?replace(".entity",".req")};

import java.time.LocalDateTime;
import com.ljh.jpa.BaseQueryReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import lombok.Data;
import ${package.Entity}.${entity}DO;

/**
 * ${table.comment!}
 * @author ${author}
 * @since ${date}
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "${entity}查询参数", description = "${table.comment!}")
public class Query${entity}REQ extends BaseQueryReq<${entity}DO> {

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