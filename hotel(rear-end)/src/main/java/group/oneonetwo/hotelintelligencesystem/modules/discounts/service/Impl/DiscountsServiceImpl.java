package group.oneonetwo.hotelintelligencesystem.modules.discounts.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.enums.DiscountEnums;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;

import group.oneonetwo.hotelintelligencesystem.modules.discountUser.service.IDiscountUserService;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.dao.DiscountsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl.LogsService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DiscountsServiceImpl implements IDiscountsService {
    @Autowired
    DiscountsMapper discountsMapper;

    @Autowired
    LogsService logsService;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IDiscountUserService discountUserService;

    @Override
    public DiscountsVO add(DiscountsVO discountsVO) {
        if (discountsVO == null) {
            throw new SavaException(("插入用户失败,折扣实体为空"));
        }
        DiscountsPO discountsPO = new DiscountsPO();
        BeanUtils.copyProperties(discountsVO, discountsPO);
        discountsPO.setHotelId(authUtils.getUserHotelId());
        int insert = discountsMapper.insert(discountsPO);
        Gson gson = new Gson();
        logsService.createLog("【添加】折扣信息", gson.toJson(discountsVO), 1, 1);
        if (insert > 0) {
            return discountsVO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public DiscountsVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501, "参数为空");
        }
        DiscountsPO discountsPO = discountsMapper.selectById(id);
        DiscountsVO discountsVO = new DiscountsVO();
        if (discountsPO != null) {
            BeanUtils.copyProperties(discountsPO, discountsVO);
        }
        return discountsVO;
    }

    @Override
    public DiscountsPO save(DiscountsVO discountsVO) {
        if (discountsVO == null) {
            throw new CommonException(501, "折扣实体为空");
        }
        DiscountsVO check = selectOneByIdReturnVO(discountsVO.getId());
        if (check == null) {
            throw new CommonException(4004, "找不到id为'" + discountsVO.getId() + "'的数据");
        }
        DiscountsPO discountsPO = new DiscountsPO();
        BeanUtils.copyProperties(discountsVO, discountsPO);
        int save = discountsMapper.updateById(discountsPO);

        if (save > 0) {
            return discountsMapper.selectById(discountsPO.getId());
        }
        throw new SavaException("折扣修改失败");
    }

    @Override
    public Integer deleteById(String id) {
        DiscountsVO check = selectOneByIdReturnVO(id);
        if (check == null) {
            throw new CommonException(4004, "找不到id为'" + id + "'的数据");
        }
        Gson gson = new Gson();
        logsService.createLog("【删除】折扣信息", gson.toJson(check), 1, 1);
        int i = discountsMapper.deleteById(id);
        return i;
    }

    @Override
    public DiscountsPO selectOneById(String id) {
        DiscountsPO discountsPO = discountsMapper.selectById(id);
        return discountsPO;
    }

    @Override
    public DiscountsVO saveone(DiscountsVO discountsVO) {
        DiscountsVO before = selectOneByIdReturnVO(discountsVO.getId());
        DiscountsPO save = save(discountsVO);
        BeanUtils.copyProperties(save, discountsVO);
        Gson gson = new Gson();
        logsService.createLog("【修改】折扣信息", gson.toJson(before) + "@*@" + gson.toJson(save), 1, 1);
        return discountsVO;
    }

    @Override
    public Page<DiscountsVO> getPage(DiscountsVO discountsVO) {
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        if (!"admin".equals(authUtils.getRole())) {
            wrapper.eq("hotel_id", authUtils.getUserHotelId());
        }

        Page<DiscountsPO> page = new Page<>(discountsVO.getPage().getPage(), discountsVO.getPage().getSize());
        Page<DiscountsPO> poiPage = (Page<DiscountsPO>) discountsMapper.selectPage(page, wrapper);
        return ConvertUtils.transferPage(poiPage, DiscountsVO.class);
    }

    /**
     * 计算价格
     *
     * @param days     天数
     * @param price    房间价格
     * @param discount 折扣
     * @return int[0]为原价, int[1]为折后价
     */
    @Override
    public int[] countPay(Integer days, Integer price, String discount) {
        int[] pays = new int[2];
        pays[0] = days * price;
        //下面可写优惠政策


        pays[1] = pays[0];
        return pays;
    }

    @Override
    public List<DiscountsVO> getHotelDiscountListByHotelId(String id) {
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        wrapper.eq("hotel_id", id).ge("validity_time", new Date()).eq("type", DiscountEnums.TYPE_HOTEL.getCode());
        List<DiscountsPO> list = discountsMapper.selectList(wrapper);
        List<DiscountsVO> res = ConvertUtils.transferList(list, DiscountsVO.class);
        return res;
    }

    @Override
    public List<DiscountsVO> getPersonalDiscountListByHotelId(String id) {
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        wrapper.eq("hotel_id", id).ge("validity_time", new Date()).eq("type", DiscountEnums.TYPE_HOTEL.getCode());
        List<DiscountsPO> list = discountsMapper.selectList(wrapper);
        String uid = authUtils.getUid();
        Iterator<DiscountsPO> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (discountUserService.isGotThisCoupon(uid, iterator.next().getId())) {
                iterator.remove();
            }
        }
        List<DiscountsVO> res = ConvertUtils.transferList(list, DiscountsVO.class);
        return res;
    }

    @Override
    public boolean isLegal(String discountsId) {
        DiscountsPO discountsPO = selectOneById(discountsId);
        if (discountsPO == null) {
            return false;
        }
        if (new Date().after(discountsPO.getValidityTime())) {
            return false;
        }
        return true;
    }

    @Override
    public List<DiscountsVO> getCurrentCanUseMyPersonalDiscountList(DiscountsVO discountsVO) {
        if (WStringUtils.isBlank(discountsVO.getHotelId())) {
            throw new CommonException("酒店ID参数为空");
        }
        if (WStringUtils.isBlank(discountsVO.getBookDay())) {
            throw new CommonException("预订天数参数为空");
        }
        if (WStringUtils.isBlank(discountsVO.getOrderFee())) {
            throw new CommonException("订单价格参数为空");
        }
        //查出已有全部可用优惠券(全局优惠券和当前酒店优惠券)
        QueryWrapper<DiscountsPO> wrapper = new QueryWrapper<>();
        wrapper.ge("validity_time", new Date())
                .and(w1 -> w1.eq("type", DiscountEnums.TYPE_ALL.getCode())
                        .or(w2 -> w2.eq("type", DiscountEnums.TYPE_HOTEL.getCode()).eq("hotel_id", discountsVO.getHotelId()))
                );
        List<DiscountsPO> list = discountsMapper.selectList(wrapper);
        List<DiscountsVO> res = ConvertUtils.transferList(list, DiscountsVO.class);

        //查询互斥卷
        String exclusiveAllDiscount = null;
        Set<String> exclusiveSet = new HashSet<>();
        if (!WStringUtils.isBlank(discountsVO.getCurrentUse())) {
            String[] split = discountsVO.getCurrentUse().split(",");
            for (String i : split) {
                DiscountsPO discountsPO = selectOneById(i);
                if (DiscountEnums.EXCLUSIVE_TYPE_ALL.getCode().equals(discountsPO.getExclusiveType())) {
                    exclusiveAllDiscount = discountsPO.getId();
                    break;
                } else if (DiscountEnums.EXCLUSIVE_TYPE_NONE.getCode().equals(discountsPO.getExclusiveType())) {
                    continue;
                }else if (DiscountEnums.EXCLUSIVE_TYPE_RANGE.getCode().equals(discountsPO.getExclusiveType())) {
                    String[] exclusiveArr = discountsPO.getExclusiveRange().split(",");
                    for (String e: exclusiveArr) {
                        exclusiveSet.add(e);
                    }
                }
            }
        }


        Iterator<DiscountsVO> iterator = res.iterator();
        String uid = authUtils.getUid();
        final String HAVE_EXCLUSIVE = "当前使用的券中与该券互斥";
        final String CONDITION_NOT_MATCH = "当前订单不符合该券使用条件";
        while (iterator.hasNext()) {
            DiscountsVO next = iterator.next();
            //把使用过的和没领取的卷去除
            if (discountUserService.isUsed(uid, next.getId())) {
                iterator.remove();
                continue;
            }

            //标记可使用的卷
            //有唯一券的情况下
            if (!WStringUtils.isBlank(exclusiveAllDiscount)) {
                if (exclusiveAllDiscount.equals(next.getId())) {
                    next.setCanUse(true);
                }else {
                    next.setCanUse(false);
                    next.setCantUseReason(HAVE_EXCLUSIVE);
                }
            //有互斥券
            }else if (!exclusiveSet.isEmpty()) {
                if (exclusiveSet.contains(next.getId())) {
                    next.setCanUse(false);
                    next.setCantUseReason(HAVE_EXCLUSIVE);
                }else {
                    next.setCanUse(true);
                }
            }else {
                next.setCanUse(true);
            }

            //验证非无门槛券
            if (!DiscountEnums.EFFECT_TYPE_ALL.getCode().equals(next.getEffectType())) {
                if (DiscountEnums.EFFECT_TYPE_DAYS.getCode().equals(next.getEffectType()) && Integer.parseInt(discountsVO.getBookDay()) >= next.getEffectCondition()) {
                    next.setCanUse(true);
                } else if (DiscountEnums.EFFECT_TYPE_MONEY.getCode().equals(next.getEffectType()) && Double.parseDouble(discountsVO.getOrderFee()) >= next.getEffectCondition()) {
                    next.setCanUse(true);
                } else {
                    next.setCanUse(false);
                    next.setCantUseReason(CONDITION_NOT_MATCH);
                }
            }
        }

        return res;
    }


}
