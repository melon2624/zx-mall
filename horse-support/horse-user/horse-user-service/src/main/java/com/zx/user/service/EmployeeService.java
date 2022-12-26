package com.zx.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.user.mapper.EmployeeMapper;
import com.zx.user.model.entity.Employee;
import org.springframework.stereotype.Service;

/**
 * (Employee)表服务实现类
 *
 * @author zhangxin
 * @date 2022/12/13 14:54
 */
@Service("employeeService")
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> {


    public Employee getEmplyeeByUserId(Long userId) {
        return super.lambdaQuery().eq(Employee::getUserId, userId).one();
    }
}
