package com.zx.user.api;

import com.zx.user.api.vo.EmployeeDO;

import java.util.List;

public interface EmployeeDubboService {

    EmployeeDO getEmployeeByUserId(Long userId);

    List<EmployeeDO> listEmployeeByUserId(List<Long> userId);

    List<EmployeeDO> listEmployeeByRoleId(Long roleId);
}
