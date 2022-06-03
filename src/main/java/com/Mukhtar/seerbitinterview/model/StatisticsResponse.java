package com.Mukhtar.seerbitinterview.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StatisticsResponse {

    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count = 0;

}
