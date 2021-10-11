package group.oneonetwo.hotelintelligencesystem.modules.discounts.service.Impl;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.dao.DiscountsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DiscountsServiceImpl implements IDiscountsService {
    @Autowired
    DiscountsMapper discountsMapper;

    @Override
    public DiscountsPO add(DiscountsVO discountsVO){
        if (discountsVO==null){
            throw  new SavaException(("插入用户失败,折扣实体为空"));
        }
        DiscountsPO discountsPO=new DiscountsPO();
        BeanUtils.copyProperties(discountsVO,discountsPO);
        int insert=discountsMapper.insert(discountsPO);
        if(insert>0){
            return discountsPO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public DiscountsVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501,"参数为空");
        }
        DiscountsPO discountsPO=discountsMapper.selectById(id);
        DiscountsVO discountsVO=new DiscountsVO();
        if(discountsPO!=null){
            BeanUtils.copyProperties(discountsPO,discountsVO);
        }
        return discountsVO;
    }

    @Override
    public DiscountsPO save(DiscountsVO discountsVO){
        if(discountsVO==null){
            throw new CommonException(501,"折扣实体为空");
        }
        DiscountsVO check=selectOneByIdReturnVO(discountsVO.getId());
        if(check==null){
            throw  new CommonException(4004,"找不到id为'"+discountsVO.getId()+"'的数据");
        }
        DiscountsPO discountsPO=new DiscountsPO();
        BeanUtils.copyProperties(discountsVO,discountsPO);
        int save =discountsMapper.updateById(discountsPO);

        if(save>0){
            return discountsMapper.selectById(discountsPO.getId());
        }
        throw new SavaException("折扣修改失败");
    }

    @Override
    public  Integer deleteById(String id){
        DiscountsVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为'"+id+"'的数据");
        }
        int i=discountsMapper.deleteById(id);
        return i;
    }

    @Override
    public  DiscountsPO selectOneById(String id){
        DiscountsPO discountsPO=discountsMapper.selectById(id);
        return discountsPO;
    }
}
