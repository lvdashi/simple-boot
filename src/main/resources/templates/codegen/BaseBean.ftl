package ${srcPath}common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseBean implements Serializable {
    //@TableId(type = IdType.ASSIGN_UUID)
    //private String id;

    //@ApiModelProperty("创建人")
    //private Integer createBy;

    //@ApiModelProperty("更新人")
    //private Integer updateBy;

    //@ApiModelProperty(value = "创建时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    //private LocalDateTime createTime;

    //@ApiModelProperty("更新时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    //private LocalDateTime updateTime;


    //@ApiModelProperty(value = "删除时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@TableLogic //逻辑删除
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    //private LocalDateTime deleteTime;

    /**
    * 插入前更新共用字段
    * @author fuxin
    * @since 0.0.1
    * @date 2021/10/11 13:59
    */
    /*public void preInsert(){
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now;
        this.updateTime = now;
        if(UserContext.getUserId() != null){
            this.createBy = Integer.parseInt(UserContext.getUserId());
            this.updateBy = Integer.parseInt(UserContext.getUserId());
        }
    }*/

    /**
    * 更新前更新共用字段
    * @author fuxin
    * @since 0.0.1
    * @date 2021/10/11 14:00
    */
    /*public void preUpdate(){
        LocalDateTime now = LocalDateTime.now();
        this.updateTime = now;
        if(UserContext.getUserId() != null){
            this.updateBy = Integer.parseInt(UserContext.getUserId());
        }
    }*/
}
