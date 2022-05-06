package group.oneonetwo.hotelintelligencesystem.modules.discounts.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;

import group.oneonetwo.hotelintelligencesystem.modules.discounts.dao.DiscountsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
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

    @Autowired
    LogsService logsService;

    @Autowired
    AuthUtils authUtils;

    @Override
    public DiscountsVO add(DiscountsVO discountsVO){
        if (discountsVO==null){
            throw  new SavaException(("插入用户失败,折扣实体为空"));
        }
        DiscountsPO discountsPO=new DiscountsPO();
        discountsPO.setHotel_Id(authUtils.getUserHotelId());
        BeanUtils.copyProperties(discountsVO,discountsPO);
        int insert=discountsMapper.insert(discountsPO);
        Gson gson = new Gson();
        logsService.createLog("【添加】折扣信息",gson.toJson(discountsVO),1,1);
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
        Gson gson = new Gson();
        logsService.createLog("【删除】折扣信息",gson.toJson(check),1,1);
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
        DiscountsVO before = selectOneByIdReturnVO(discountsVO.getId());
        DiscountsPO save=save(discountsVO);
        BeanUtils.copyProperties(save,discountsVO);
        Gson gson = new Gson();
        logsService.createLog("【修改】折扣信息",gson.toJson(before) + "@*@" + gson.toJson(save),1,1);
        return discountsVO;
    }

    @Override
    public Page<DiscountsVO> getPage(DiscountsVO discountsVO){
        QueryWrapper<DiscountsPO> wrapper=new QueryWrapper<>();
        if(!"admin".equals(authUtils.getRole())){
            wrapper.eq("hotel_id",authUtils.getUserHotelId());
        }

        Page<DiscountsPO> page = new Page<>(discountsVO.getPage().getPage(),discountsVO.getPage().getSize());
        Page<DiscountsPO> poiPage=(Page<DiscountsPO>) discountsMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage,DiscountsVO.class);
    }

    /**
     * 计算价格
     * @return int[0]为原价,int[1]为折后价
     */
    @Override
    public int[] countPay(Integer days, Integer price){
        int[] pays = new int[2];
        pays[0] = days * price;
        //下面可写优惠政策


        pays[1] = pays[0];
        return pays;
    }

}
