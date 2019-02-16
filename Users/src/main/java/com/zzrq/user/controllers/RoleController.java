package com.zzrq.Role.controllers;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.user.dto.SysRole;
import com.zzrq.user.service.IRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 查询角色
     * @param sysRole
     * @return
     */
    @ApiOperation(value = "查询角色", notes = "查询角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "role", value = "角色dto", required = true, dataType = "Role"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("query")
    public ResponseData query(SysRole sysRole, int pageNum, int pageSize) {
        List<SysRole> sysRoles = this.roleService.query(sysRole,pageNum,pageSize);
        return new ResponseData(sysRoles);
    }

    /**
     * 添加角色
     * @param sysRoles
     * @return
     */
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "roles", value = "角色dto", required = true, dataType = "List<Role>"),})
    @PostMapping("/add")
    public ResponseData add(@Valid List<SysRole> sysRoles) {
        Boolean boo = this.roleService.add(sysRoles);
        return new ResponseData(boo);
    }

    /**
     * 修改角色
     * @param sysRoles
     * @return
     */
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "roles", value = "角色dto", required = true, dataType = "List<Role>"),})
    @PutMapping("/change")
    public ResponseData change(@Valid List<SysRole> sysRoles) {
        Boolean boo = this.roleService.change(sysRoles);
        return new ResponseData(boo);
    }

    /**
     * 删除角色
     * @param sysRoles
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "Roles", value = "角色dto", required = true, dataType = "List<Role>"),})
    @DeleteMapping("/delete")
    public ResponseData delete(@Valid List<SysRole> sysRoles) {
        Boolean boo = this.roleService.delete(sysRoles);
        return new ResponseData(boo);
    }
}
