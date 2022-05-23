package ${package.Mapper};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${package.Entity}.${entity}DO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${table.comment!} Mapper 接口
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${entity}Mapper extends BaseMapper<${entity}DO> {

}