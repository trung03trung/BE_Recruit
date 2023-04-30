package com.itsol.recruit.dto.respone;

import lombok.Data;

import java.util.List;
@Data
public class LineChartDataResponse {
    List<Integer> month;
    List<Integer> numberRecruit;
    List<Integer> numberSuccessJob;
}
