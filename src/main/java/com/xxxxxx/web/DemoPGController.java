package com.xxxxxx.web;


import com.lergo.framework.annotation.LogTracker;
import com.xxxxxx.entity.po.TgDemo;
import com.xxxxxx.mapper.TgDemoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("demo/db")
@Slf4j
@Tag(name = "数据样例", description = "DB代码实现模板")
public class DemoPGController {

    @Resource
    TgDemoMapper tgDemoMapper;


    @GetMapping("all")
    @LogTracker
    public List<TgDemo> all() {
        return tgDemoMapper.selectList(null);
    }

//    @GetMapping("list-mbp")
//    public IPage<TgDemo> listMbp() {
//        return tgDemoService.page(new Page(1,10));
//    }
//
//    @PostMapping("save")
//    public Boolean save(@RequestBody TgDemo demo) {
//        return tgDemoService.save(demo);
//    }
//
//    @PatchMapping("update")
//    public Boolean update(@RequestBody TgDemo demo) {
//        return tgDemoService.updateById(demo);
//    }
//
//    @DeleteMapping("delete")
//    public Boolean delete(@RequestParam Long id) {
//        return tgDemoService.removeById(id);
//    }


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

