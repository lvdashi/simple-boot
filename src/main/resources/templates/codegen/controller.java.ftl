package ${package.Entity?replace(".entity",".controller")};


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Service}.${entity}Service;
import ${package.Service?replace(".service",".rsp")}.${entity}RSP;
import ${package.Service?replace(".service",".req")}.Query${entity}REQ;
import ${package.Service?replace(".service",".req")}.Update${entity}REQ;
import ${package.Entity}.${entity}DO;
import ${package.Service?replace(".service",".req")}.Add${entity}REQ;
import com.ljh.api.ApiResponse;
import com.ljh.util.VoUtil;
import org.springframework.validation.annotation.Validated;
import com.ljh.entity.MobilePageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * ${table.comment!}
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@RestController
@Api(value = "${table.comment!}", tags = "${table.comment!}")
@RequestMapping("/${table.entityPath}")
public class ${entity}Controller {

    @Resource
    ${table.serviceName} ${table.entityPath}Service;

    @GetMapping("/list")
    @ApiOperation("查询${table.comment!} 列表")
    public ApiResponse list(@Valid Query${entity}REQ req) {
        List<${entity}DO> list = ${table.entityPath}Service.list(req.buildQueryWrapper());
        List<${entity}RSP> resultList = VoUtil.getInstance().listConvert(list, ${entity}RSP.class);
        return ApiResponse.ok(resultList);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询${table.comment!} 列表")
    public ApiResponse page(@Valid Query${entity}REQ req,  @Validated MobilePageReq mobilePageReq) {
        Page<${entity}DO> page = new Page<>(mobilePageReq.getPageNum(), mobilePageReq.getPageSize());
        page=bsAppService.page(page, req.buildQueryWrapper());
        return ApiResponse.ok(page);
    }

    @PostMapping("")
    @ApiOperation("添加${table.comment!}信息")
    public ApiResponse add(@RequestBody @Valid Add${entity}REQ req) {
        ${entity}DO entity =new ${entity}DO();
        BeanUtils.copyProperties(req,${entity}DO.class);
        boolean result = ${table.entityPath}Service.add(entity);
        if (result) {
            return ApiResponse.ok("添加${table.comment!}成功");
        }
        return ApiResponse.error("添加${table.comment!}失败");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新${table.comment!}表信息")
    public ApiResponse update(@PathVariable("id") Long id,
                                               @RequestBody @Valid Update${entity}REQ req) {
        //验证数据是否存在
        ${entity}DO entity = ${table.entityPath}Service.getById(id);
        BeanUtils.copyProperties(req, entity);
        boolean result = ${table.entityPath}Service.updateById(entity);
        if (result) {
            return ApiResponse.error("更新${table.comment!}成功");
        }
        return ApiResponse.error("更新${table.comment!}失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除${table.comment!}信息")
    public ApiResponse del(@PathVariable("id") Long id) {
        boolean result = ${table.entityPath}Service.removeById(id);
        if (result) {
        return ApiResponse.error("删除${table.comment!}成功");
        }
        return ApiResponse.error("删除${table.comment!}失败");
    }

    @GetMapping("/{id}")
    @ApiOperation("详情信息")
    public ApiResponse get(@PathVariable("id") Long id) {
        ${entity}DO entity = ${table.entityPath}Service.getById(id);
        ${entity}RSP resultVo = new ${entity}RSP();
            BeanUtils.copyProperties(entity, ${entity}RSP.class);
        return ApiResponse.ok(resultVo);
    }
}