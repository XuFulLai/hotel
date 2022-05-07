package group.oneonetwo.hotelintelligencesystem.modules.discounts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;


import java.util.List;

public interface IDiscountsService {
    DiscountsVO add(DiscountsVO discountsVO);
    DiscountsVO selectOneByIdReturnVO(String id);
    DiscountsPO save(DiscountsVO discountsVO);
    Integer deleteById(String id);
    DiscountsPO selectOneById(String id);
    DiscountsVO saveone (DiscountsVO discountsVO);
    Page<DiscountsVO> getPage(DiscountsVO discountsVO);

    int[] countPay(Integer days, Integer price,String discount);

    List<DiscountsVO> getListByHotelId(String id);
}

