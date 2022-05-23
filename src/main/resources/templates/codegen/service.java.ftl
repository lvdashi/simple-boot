package ${package.Service};

import ${package.Mapper}.${entity}Mapper;
import com.ljh.jpa.BaseService;
import ${package.Entity}.${entity}DO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务类
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${entity}Service extends BaseService<${entity}Mapper, ${entity}DO> {

}

