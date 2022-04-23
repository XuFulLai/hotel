package group.oneonetwo.hotelintelligencesystem.modules.chart.service.impl;

import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.modules.chart.dao.ChartMapper;
import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;
import group.oneonetwo.hotelintelligencesystem.modules.chart.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 文
 */
@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    ChartMapper chartMapper;

    @Autowired
    AuthUtils authUtils;

    @Override
    public List<ChartVO> orderRoomTypeChart() {
        return chartMapper.orderRoomTypeChart(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> mapChart() {
        return chartMapper.mapChart(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> wayChart() {
        return chartMapper.wayChart(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> orderNumOf7Day() {
        return chartMapper.orderNumOf7Day(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> orderNumOf1Year() {
        return chartMapper.orderNumOf1Year(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> incomeOf7Day() {
        return chartMapper.incomeOf7Day(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> wayOnIsolationOf7Day(Integer way) {
        String hotelId = null;
        if (!"admin".equals(authUtils.getRole())) {
            hotelId = authUtils.getUserHotelId();
        }
        return chartMapper.wayOnIsolationOf7Day(way,hotelId);
    }

    @Override
    public List<ChartVO> isolationIncome() {
        return chartMapper.isolationIncome(authUtils.getUserHotelId());
    }

    @Override
    public List<ChartVO> isolationCheckIn() {
        return chartMapper.isolationCheckIn();
    }
}
