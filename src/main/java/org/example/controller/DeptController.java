package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DeptController {

    @Autowired
    private DeptService deptService;
    
    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping("/depts")
    public Result list() {
       List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    @DeleteMapping("/depts")
    public Result delete(@RequestParam(value = "id",required = false) Integer deptId){ // required = false 表示参数可以不传
        log.info("删除的部门是：{}", deptId);
        deptService.deleteById(deptId);
        return Result.success();
    }

//    @DeleteMapping("/depts")
//    public Result delete( Integer id){
//        System.out.println("删除的部门是："+ id);
//        deptService.deleteById(id);
//        return Result.success();
//    }

    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        log.info("添加的部门是：{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据id显示查询部门（查询回显示）
     */

    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id){
        Dept dept = deptService.findById(id);
        return Result.success(dept);
    }

    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        log.info("修改的部门是：{}", dept);
        deptService.update(dept);
        return Result.success();
    }

}
