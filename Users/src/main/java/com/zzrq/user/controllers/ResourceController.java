package com.zzrq.user.controllers;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.user.dto.SysResource;
import com.zzrq.user.service.IResourceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private IResourceService resourceService;

    /**
     * 查询资源
     * @param resource
     * @return
     */
    @ApiOperation(value = "查询资源", notes = "查询资源")
    @ApiImplicitParams({@ApiImplicitParam(name = "resource", value = "资源dto", required = true, dataType = "SysResource"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("query")
    public ResponseData query(SysResource resource, int pageNum, int pageSize) {
        List<SysResource> SysResources = this.resourceService.query(resource,pageNum,pageSize);
        return new ResponseData(SysResources);
    }

    /**
     * 添加资源
     * @param resources
     * @return
     */
    @ApiOperation(value = "添加资源", notes = "添加资源")
    @ApiImplicitParams({@ApiImplicitParam(name = "resources", value = "资源dto", required = true, dataType = "List<SysResource>"),})
    @PostMapping("/add")
    public ResponseData add(@Valid List<SysResource> resources) {
        Boolean boo = this.resourceService.add(resources);
        return new ResponseData(boo);
    }

    /**
     * 修改资源
     * @param resources
     * @return
     */
    @ApiOperation(value = "修改资源", notes = "修改资源")
    @ApiImplicitParams({@ApiImplicitParam(name = "resources", value = "资源dto", required = true, dataType = "List<SysResource>"),})
    @PutMapping("/change")
    public ResponseData change(@Valid List<SysResource> resources) {
        Boolean boo = this.resourceService.change(resources);
        return new ResponseData(boo);
    }

    /**
     * 删除资源
     * @param resources
     * @return
     */
    @ApiOperation(value = "删除资源", notes = "删除资源")
    @ApiImplicitParams({@ApiImplicitParam(name = "resources", value = "资源dto", required = true, dataType = "List<SysResource>"),})
    @DeleteMapping("/delete")
    public ResponseData delete(@Valid List<SysResource> resources) {
        Boolean boo = this.resourceService.delete(resources);
        return new ResponseData(boo);
    }
}
