package com.ljh.jpa;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
public class BaseEntity implements Serializable {

//    @TableId(type = IdType.ASSIGN_UUID)
//    private String id;

//    @TableId(type = IdType.AUTO)
//    private Long id;
//
//    @ApiModelProperty("创建人")
//    private String createBy;
//
//    @ApiModelProperty("创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    private LocalDateTime createTime;
//
//    @ApiModelProperty("更新人")
//    private String updateBy;
//
//    @ApiModelProperty("更新时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    private LocalDateTime updateTime;
//
//    /**
//     * 删除标志（0代表存在 2代表删除）
//     */
//    @TableLogic
//    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
//    private String delFlag;



}
