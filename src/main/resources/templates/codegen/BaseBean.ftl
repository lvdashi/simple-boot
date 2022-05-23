package ${srcPath}common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseBean implements Serializable {
    //@TableId(type = IdType.ASSIGN_UUID)
    //private String id;

}
