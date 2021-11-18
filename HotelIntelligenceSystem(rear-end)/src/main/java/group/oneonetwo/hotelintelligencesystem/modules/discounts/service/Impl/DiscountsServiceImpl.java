package group.oneonetwo.hotelintelligencesystem.modules.discounts.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;

import group.oneonetwo.hotelintelligencesystem.modules.discounts.dao.DiscountsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
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
    public DiscountsVO add(DiscountsVO discountsVO){
        if (discountsVO==null){
            throw  new SavaException(("插入用户失败,折扣实体为空"));
        }
        DiscountsPO discountsPO=new DiscountsPO();
        BeanUtils.copyProperties(discountsVO,discountsPO);
        int insert=discountsMapper.insert(discountsPO);
        if(insert>0){
            return discountsVO;
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

    @Override
    public DiscountsVO saveone (DiscountsVO discountsVO){
        DiscountsPO save=save(discountsVO);
        BeanUtils.copyProperties(save,discountsVO);
        return discountsVO;
    }

    @Override
    public Page<DiscountsVO> getPage(DiscountsVO discountsVO){
        QueryWrapper<DiscountsPO> wrapper=new QueryWrapper<>();
        Page<DiscountsPO> page = new Page<>(discountsVO.getPage().getPage(),discountsVO.getPage().getSize());
        Page<DiscountsPO> poiPage=(Page<DiscountsPO>) discountsMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage,DiscountsVO.class);
    }

}
