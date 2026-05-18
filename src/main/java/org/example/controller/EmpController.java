package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name,
//                       Integer gender,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate end){
//        log.info("查询参数：page={},pageSize={},name={},gender={},begin={},end={}",page,pageSize,name,gender,begin,end);
//        PageResult pageResult = empService.page(page,pageSize,name,gender,begin,end);
//        return Result.success(pageResult);
//    }

    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("查询参数：{}",empQueryParam);
        PageResult pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("保存员工：{}",emp);
        empService.save(emp);
        return Result.success();
    }

//    @DeleteMapping
//    public Result delete(Integer[] ids){
//        log.info("删除员工：{}", Arrays.toString(ids));
//        // empService.delete(ids);
//        return Result.success();
//    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工：{}", ids);
        empService.delete((ids));
        return Result.success();
    }

    /**
     * 查询回显
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        log.info("获取员工信息：{}", id);
        Emp emp = empService.get(id);
        return Result.success(emp);
    }

    /**
     * 更新员工
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息：{}", emp);
        empService.update(emp);
        return Result.success();
    }

}
