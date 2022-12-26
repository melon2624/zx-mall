package com.zx.framework.demo.service.convert;

import com.zx.framework.demo.entity.Student;
import com.zx.framework.demo.entity.StudentVO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-20T11:26:34+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class StudentConvertImpl implements StudentConvert {

    @Override
    public StudentVO toVo(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentVO studentVO = new StudentVO();

        studentVO.setName( student.getName() );
        studentVO.setSex( student.getSex() );

        return studentVO;
    }
}
