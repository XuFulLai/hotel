package group.oneonetwo.hotelintelligencesystem.modules.discounts.service;

import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;

public interface IDiscountsService {
    DiscountsPO add(DiscountsVO discountsVO);
    DiscountsVO selectOneByIdReturnVO(String id);
    DiscountsPO save(DiscountsVO discountsVO);
    Integer deleteById(String id);
    DiscountsPO selectOneById(String id);

}

