package group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.impl;

import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.mapper.DiscountUserMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.model.vo.DiscountUserVO;
import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 文
* @description 针对表【discount_user】的数据库操作Service实现
* @createDate 2022-05-07 16:41:44
*/
@Service
public class IDiscountUserServiceImpl implements IDiscountUserService {

    @Autowired
    DiscountUserMapper discountUserMapper;

    @Autowired
    AuthUtils authUtils;

    @Override
    public List<DiscountUserVO> getMyDiscount() {
        return discountUserMapper.getMyDiscount(authUtils.getUid(),new Date());
    }
}
