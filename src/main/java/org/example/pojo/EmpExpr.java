package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * 工作经历
 */
@Data
public class EmpExpr {
    private Integer id; //ID
    private Integer empId; //员工ID
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin; //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end; //结束时间
    private String company; //公司名称
    private String job; //职位

    // 前端传入的日期数组 [begin, end]
    private String[] exprDate;
}
