package com.xxxxxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxxxx.entity.po.TgDemo;
import com.xxxxxx.mapper.TgDemoMapper;
import org.springframework.stereotype.Service;

@Service
public class TgDemoService extends ServiceImpl<TgDemoMapper, TgDemo> implements IService<TgDemo> {
}
