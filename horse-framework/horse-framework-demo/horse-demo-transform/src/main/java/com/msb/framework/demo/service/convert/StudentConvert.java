package com.zx.framework.demo.service.convert;

import com.zx.framework.demo.entity.Student;
import com.zx.framework.demo.entity.StudentVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentConvert {

    StudentVO toVo(Student student);


}
