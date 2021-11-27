package group.oneonetwo.hotelintelligencesystem.modules.chart.controller;

import group.oneonetwo.hotelintelligencesystem.modules.chart.model.vo.ChartVO;
import group.oneonetwo.hotelintelligencesystem.modules.chart.service.IChartService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 文
 * @description 可视化图表控制层
 */
@Api("可视化图表相关接口")
@RestController
@RequestMapping("api/chart")
public class ChartController {

    @Autowired
    IChartService chartService;

    @ApiOperation("各房型订单数统计")
    @GetMapping("order/roomType")
    public Reply<List<ChartVO>> orderRoomTypeChart() {
        return Reply.success(chartService.orderRoomTypeChart());
    }

    @ApiOperation("订单来访地统计")
    @GetMapping("order/map")
    public Reply<List<ChartVO>> mapChart() {
        return Reply.success(chartService.mapChart());
    }

    @ApiOperation("订单来源统计")
    @GetMapping("order/way")
    public Reply<List<ChartVO>> wayChart() {
        return Reply.success(chartService.wayChart());
    }

    @ApiOperation("近一周订单数量统计")
    @GetMapping("order/orderNum/day/7")
    public Reply<List<ChartVO>> orderNumOf7Day() {
        return Reply.success(chartService.orderNumOf7Day());
    }

    @ApiOperation("近一年订单数统计")
    @GetMapping("order/orderNum/year/1")
    public Reply<List<ChartVO>> orderNumOf1Year() {
        return Reply.success(chartService.orderNumOf1Year());
    }

    @ApiOperation("近一周营业额统计")
    @GetMapping("order/income/day/7")
    public Reply<List<ChartVO>> incomeOf7Day() {
        return Reply.success(chartService.incomeOf7Day());
    }



}
