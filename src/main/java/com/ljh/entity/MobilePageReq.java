package com.ljh.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * pda分页参数
 *
 * @author jinhuilv
 * @date 2022/04/18 09:45
 **/
@Data
@Valid
public class MobilePageReq {
    @Range(min=1,message = "页码错误")
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码")
    private Integer pageNum;

    @Range(min=5, max=100,message = "每页数量超过限制")
    @NotNull(message = "每页数量不能为空")
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
}
