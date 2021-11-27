package group.oneonetwo.hotelintelligencesystem.modules.order.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Api(tags="订单相关接口")
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    IOrderService orderService;


    @PostMapping("add")
    @ApiOperation("添加订单")
    public  Reply<OrderVO> add(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.add(orderVO));
    }


    @GetMapping("delete/{id}")
    @ApiOperation("根据id删除订单")
    public Reply deleteById(@PathVariable("id") String id){
        return orderService.deleteById(id)>0?Reply.success() :Reply.failed();
    }


    @ApiOperation("根据id查找订单")
    @GetMapping("get/{id}")
    public  Reply<OrderVO> selectById(@PathVariable("id") String id){
        return Reply.success(orderService.selectOneByIdReturnVO(id));
    }


    @ApiOperation("更改订单")
    @PostMapping("modify")
    public Reply<OrderVO> modify(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.saveone(orderVO));
    }

    @ApiOperation("获取订单列表(分页)")
    @PostMapping("page")
    public Reply<Page<OrderVO>> getPage(@RequestBody OrderVO orderVO){
        return Reply.success(orderService.getPage(orderVO));
    }

    @ApiOperation("下载订单记录")
    @PostMapping("download")
    public void downloadOrders(@RequestBody OrderVO orderVO, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        long currentTimeMillis = System.currentTimeMillis();
        String exportFileName = "酒店订单_" + String.valueOf(currentTimeMillis);
        String fileName = URLEncoder.encode(exportFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), OrderVO.class).sheet("订单").doWrite(orderService.getAllList(orderVO));
    }


    @GetMapping("randomOrder")
    public void s() {
        orderService.randomOrder();
    }

}
