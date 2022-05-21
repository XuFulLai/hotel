package group.oneonetwo.hotelintelligencesystem.modules.chart.service;

import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;

import java.util.List;

/**
 * @author 文
 */
public interface IChartService {


    List<ChartVO> orderRoomTypeChart();

    List<ChartVO> mapChart();

    List<ChartVO> wayChart();

    List<ChartVO> orderNumOf7Day();

    List<ChartVO> orderNumOf1Year();

    List<ChartVO> incomeOf7Day();

    List<ChartVO> wayOnIsolationOf7Day(Integer way);

    List<ChartVO> isolationIncome();

    List<ChartVO> isolationCheckIn();
}
