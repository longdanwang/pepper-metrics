package com.pepper.metrics.sample.core;

import com.pepper.metrics.core.Stats;
import com.pepper.metrics.core.extension.SpiMeta;
import com.pepper.metrics.extension.scheduled.AbstractPerfPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhangrongbincool@163.com
 * @date 19-8-14
 */
@SpiMeta(name = "coreSamplePrinter")
public class CoreSamplePrinter extends AbstractPerfPrinter {
    @Override
    public List<Stats> chooseStats(Set<Stats> statsSet) {
        List<Stats> statsList = new ArrayList<>();
        for (Stats stats : statsSet) {
            if ("custom".equalsIgnoreCase(stats.getName())) {
                statsList.add(stats);
            }
        }
        return statsList;
    }
}