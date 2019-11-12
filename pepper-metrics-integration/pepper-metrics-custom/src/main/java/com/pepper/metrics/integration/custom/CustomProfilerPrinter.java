package com.pepper.metrics.integration.custom;

import com.pepper.metrics.core.Stats;
import com.pepper.metrics.core.extension.SpiMeta;
import com.pepper.metrics.extension.scheduled.AbstractPerfPrinter;
import com.pepper.metrics.extension.scheduled.PerfPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhangrongbincool@163.com
 * @version 19-8-12
 */
@SpiMeta(name = "customProfilerPrinter")
public class CustomProfilerPrinter extends AbstractPerfPrinter implements PerfPrinter {
    @Override
    public List<Stats> chooseStats(Set<Stats> statsSet) {
        List<Stats> statsList = new ArrayList<>();
        for (Stats stats : statsSet) {
            if ("custom".equalsIgnoreCase(stats.getType())) {
                statsList.add(stats);
            }
        }
        return statsList;
    }

    @Override
    public String setPrefix(Stats stats) {
        return "perf-custom";
    }
}
