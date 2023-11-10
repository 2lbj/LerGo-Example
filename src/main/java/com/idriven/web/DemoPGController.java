package com.idriven.web;


import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.operator.Equal;
import cn.zhxu.bs.operator.StartWith;
import cn.zhxu.bs.util.MapUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idriven.common.annotation.ApiToken;
import com.idriven.entity.po.TgDemo;
import com.idriven.service.TgDemoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("demo/db")
@Slf4j
@Tag(name = "样例", description = "代码实现模板")
public class DemoPGController {

    @Resource
    TgDemoService tgDemoService;


    @GetMapping("all")
    public List<TgDemo> all() {
        return tgDemoService.list();
    }

    @GetMapping("all-token")
    @ApiToken
    public List<TgDemo> allToken() {
        return tgDemoService.list();
    }

    @GetMapping("list-mbp")
    public IPage<TgDemo> listMbp() {
        return tgDemoService.page(new Page(1,10));
    }

    @PostMapping("save")
    public Boolean save(@RequestBody TgDemo demo) {
        return tgDemoService.save(demo);
    }

    @PatchMapping("update")
    public Boolean update(@RequestBody TgDemo demo) {
        return tgDemoService.updateById(demo);
    }

    @DeleteMapping("delete")
    public Boolean delete(@RequestParam Long id) {
        return tgDemoService.removeById(id);
    }


//    @Resource
//    private BeanSearcher searcher;
//    @GetMapping("list-bean-searcher")
//    public SearchResult<TgDemo> listBeanSearcher() {
//
//        return searcher.search(TgDemo.class, MapUtils.builder()
//                .field(TgDemo::getName, "张").op(StartWith.class)  // 条件：name 以 "张" 开头
//                .orderBy(TgDemo::getUpdateTime).asc()             // 排序：age，从小到大
//                .page(0, 15)                             // 分页：第 0 页, 每页 15 条
//                .build());
//    }

}

