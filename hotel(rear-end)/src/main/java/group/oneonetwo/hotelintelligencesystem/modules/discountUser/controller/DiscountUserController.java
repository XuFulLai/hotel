package group.oneonetwo.hotelintelligencesystem.modules.discountUser.controller;

import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 文
 * @description: 折扣-用户表
 * @date 2022/5/7 17:16
 */
@Api(tags="折扣-用户相关接口")
@RestController
@RequestMapping("api/discountsUser")
public class DiscountUserController {

    @Autowired
    IDiscountUserService discountUserService;

    @GetMapping("my")
    public Reply<List<DiscountUserVO>> getListByUid() {
        return Reply.success(discountUserService.getMyDiscount());
    }


}
