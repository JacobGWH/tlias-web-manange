package org.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImp implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 1. 原始方式 实现分页查询
     */
//    @Override
//    public PageResult page(Integer page, Integer pageSize) {
//        //1. 获取总记录数
//        Long total = empMapper.count();
//
//        //2. 获取返回的记录
//        List<Emp> rows = empMapper.list((page - 1) * pageSize, pageSize);
//
//        //3. 封装PageResult对象并返回
//        return new PageResult(total,rows);
//    }

    /**
     * 2. 使用PageHelper
     */
    @Override
    public PageResult page(EmpQueryParam empQueryParam) {

        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize()); //将分页参数、每页记录数写入ThreadLocal中

        List<Emp> list = empMapper.list(empQueryParam);

        // 验证分页只对紧跟在其后的第一条SQL语句进行分页处理
//        List<Emp> list1 = empMapper.list();
//        System.out.println(list1);

        Page<Emp> p = (Page<Emp>) list;
        System.out.println(p); // 打印分页对象

        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    @Transactional
    public void save(Emp emp) {

        try {
            //1. 保存员工信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);




            //2. 保存员工经历信息
            if (!CollectionUtils.isEmpty(emp.getExprList())) {
                // 利用主键返回，遍历补全员工ID（即empId字段数据）
                emp.getExprList().forEach(expr -> {
                    expr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(emp.getExprList());
            }
            //3. 记录成功日志
            empLogService.insertLog(new EmpLog(null, LocalDateTime.now(), "保存员工信息成功: " + objectToJson(emp)));
        } catch (Exception e) {
            //4. 记录失败日志
            empLogService.insertLog(new EmpLog(null, LocalDateTime.now(), "保存员工信息失败: " + objectToJson(emp) + ", 错误: " + e.getMessage()));
            throw e;
        }

    }

    /**
     * 将对象转换为JSON字符串
     */
    private String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Integer> ids) {
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
    }
}
