package com.zzrq.user.controllers;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.user.dto.SysResource;
import com.zzrq.user.dto.SysRole;
import com.zzrq.user.dto.SysRoleResource;
import com.zzrq.user.service.IRoleResourceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role/resource")
public class RoleResourceController {
    
    @Autowired
    private IRoleResourceService roleResourceService;

    /**
     * 查询角色资源
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询角色资源", notes = "根据角色ID查询资源")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("queryResources")
    public ResponseData queryResources(@RequestParam("roleId") Long roleId, int pageNum, int pageSize) {
        List<SysResource> sysResources = this.roleResourceService.queryResources(roleId,pageNum,pageSize);
        return new ResponseData(sysResources);
    }

    /**
     * 查询所有角色资源
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询所有角色资源", notes = "根据角色ID查询资源")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("queryResources")
    public ResponseData queryAllResources(@RequestParam("roleId") Long roleId, int pageNum, int pageSize) {
        List<SysResource> sysResources = this.roleResourceService.queryAllResources(roleId,pageNum,pageSize);
        return new ResponseData(sysResources);
    }

    /**
     * 查询资源角色
     * @param resourceId
     * @return
     */
    @ApiOperation(value = "查询资源角色", notes = "根据资源ID查询角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "resourceId", value = "资源ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("queryRoles")
    public ResponseData queryRoles(@RequestParam("resourceId") Long resourceId, int pageNum, int pageSize) {
        List<SysRole> roles = this.roleResourceService.queryRoles(resourceId,pageNum,pageSize);
        return new ResponseData(roles);
    }

    /**
     * 添加关系
     * @param SysRoleResources
     * @return
     */
    @ApiOperation(value = "添加关系", notes = "根据角色ID和资源ID添加关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "userRoles", value = "角色ID和资源ID集合", required = true, dataType = "List<UserRole>"),})
    @PostMapping("/add")
    public ResponseData add(List<SysRoleResource> SysRoleResources) {
        Boolean boo = this.roleResourceService.add(SysRoleResources);
        return new ResponseData(boo);
    }

    /**
     * 改变关系
     * @param SysRoleResources
     * @return
     */
    @ApiOperation(value = "改变关系", notes = "根据UserRole的ID删除关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "userRoles", value = "UserRole的ID集合", required = true, dataType = "List<UserRole>"),})
    @DeleteMapping("/change")
    public ResponseData change(List<SysRoleResource> SysRoleResources) {
        Boolean boo = this.roleResourceService.change(SysRoleResources);
        return new ResponseData(boo);
    }

    /**
     * 删除关系
     * @param SysRoleResources
     * @return
     */
    @ApiOperation(value = "删除关系", notes = "根据UserRole的ID删除关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "userRoles", value = "UserRole的ID集合", required = true, dataType = "List<UserRole>"),})
    @DeleteMapping("/delete")
    public ResponseData delete(List<SysRoleResource> SysRoleResources) {
        Boolean boo = this.roleResourceService.delete(SysRoleResources);
        return new ResponseData(boo);
    }
}
