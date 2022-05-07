package group.oneonetwo.hotelintelligencesystem.modules.discountUser.service;


import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserVO;

import java.util.List;

/**
* @author 文
* @description 针对表【discount_user】的数据库操作Service
* @createDate 2022-05-07 16:41:44
*/
public interface IDiscountUserService {

    List<DiscountUserVO> getMyDiscount();
}
