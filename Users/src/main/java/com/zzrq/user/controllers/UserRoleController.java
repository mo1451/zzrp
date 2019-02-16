package com.zzrq.user.controllers;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.user.dto.SysRole;
import com.zzrq.user.dto.SysUser;
import com.zzrq.user.dto.SysUserRole;
import com.zzrq.user.service.IUserRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/role")
public class UserRoleController {
    
    @Autowired
    private IUserRoleService userRoleService;
    
    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询用户角色", notes = "根据用户ID查询角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("queryRoles")
    public ResponseData queryRoles(@RequestParam("userId") Long userId, int pageNum, int pageSize) {
        List<SysRole> sysRoles = this.userRoleService.queryRoles(userId,pageNum,pageSize);
        return new ResponseData(sysRoles);
    }

    /**
     * 查询角色用户
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询角色用户", notes = "根据角色ID查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("queryUsers")
    public ResponseData queryUsers(@RequestParam("roleId") Long roleId, int pageNum, int pageSize) {
        List<SysUser> roles = this.userRoleService.queryUsers(roleId,pageNum,pageSize);
        return new ResponseData(roles);
    }

    /**
     * 添加关系
     * @param sysUserRoles
     * @return
     */
    @ApiOperation(value = "添加关系", notes = "根据用户ID和角色ID添加关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "userRoles", value = "用户ID和角色ID集合", required = true, dataType = "List<UserRole>"),})
    @PostMapping("/add")
    public ResponseData add(List<SysUserRole> sysUserRoles) {
        Boolean boo = this.userRoleService.add(sysUserRoles);
        return new ResponseData(boo);
    }

    /**
     * 删除关系
     * @param sysUserRoles
     * @return
     */
    @ApiOperation(value = "删除关系", notes = "根据UserRole的ID删除关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "userRoles", value = "UserRole的ID集合", required = true, dataType = "List<UserRole>"),})
    @DeleteMapping("/delete")
    public ResponseData delete(List<SysUserRole> sysUserRoles) {
        Boolean boo = this.userRoleService.delete(sysUserRoles);
        return new ResponseData(boo);
    }
}
