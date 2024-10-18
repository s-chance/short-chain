package org.entropy.shortchain.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.entropy.shortchain.entity.po.ClickStats;
import org.entropy.shortchain.mapper.ClickStatsMapper;
import org.springframework.stereotype.Service;

@Service
public class ClickStatsService extends ServiceImpl<ClickStatsMapper, ClickStats> {
}
