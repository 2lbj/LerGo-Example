package com.xxxxxx.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lergo.framework.annotation.LogTracker;
import com.xxxxxx.common.exception.BizEnumException;
import com.xxxxxx.entity.po.TgDemo;
import com.xxxxxx.service.TgDemoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xxxxxx.common.constants.BizErrorEnum.UNKNOWN_ERROR;


@RestController
@RequestMapping("demo/db")
@Slf4j
@Tag(name = "数据样例", description = "DB代码实现模板")
public class DemoPGController {

    @Resource
    TgDemoService tgDemoService;

    @GetMapping("list")
    @LogTracker
    public List<TgDemo> list() {
        return tgDemoService.list();
    }

    @GetMapping("page")
    public IPage<TgDemo> page(
            @Parameter(description = "页码", deprecated = true)
            @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "页长", deprecated = true)
            @RequestParam(defaultValue = "10") Integer size) {
        return tgDemoService.page(new Page<>(current, size));
    }

    @PostMapping("save")
    public TgDemo save(@RequestBody TgDemo demo) {
        if(tgDemoService.save(demo)) {
            return demo;
        }
        throw new BizEnumException(UNKNOWN_ERROR);
    }

    @PatchMapping("update")
    public TgDemo update(@RequestBody TgDemo demo) {
        if(tgDemoService.updateById(demo)) {
            return demo;
        }
        throw new BizEnumException(UNKNOWN_ERROR);
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

