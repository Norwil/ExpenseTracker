package com.Norw.ExpenseTracker.services.stats;

import com.Norw.ExpenseTracker.dto.GraphDTO;
import com.Norw.ExpenseTracker.dto.StatsDTO;

public interface StatsService {

    GraphDTO getChartData();

    StatsDTO getStats();

}
