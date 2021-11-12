package group.oneonetwo.hotelintelligencesystem.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.dao.OrderMapper;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public OrderVO add(OrderVO orderVO){
        if(orderVO==null){
            throw new SavaException("插入订单失败:订单实体为空");
        }
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        int insert=orderMapper.insert(orderPO);
        if(insert>0){
            return orderVO;
        }
        throw new SavaException("插入订单失败");
    }

    @Override
    public OrderVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        OrderPO orderPO=orderMapper.selectById(id);
        OrderVO orderVO=new OrderVO();
        if(orderVO!=null){
            BeanUtils.copyProperties(orderPO,orderVO);
        }
        return orderVO;
    }

    @Override
    public OrderPO save(OrderVO orderVO){
        if (orderVO==null){
            throw new CommonException(501,"订单实体为空");
        }
        OrderVO check=selectOneByIdReturnVO(orderVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+orderVO.getId()+"的数据");
        }
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        int save=orderMapper.updateById(orderPO);
        if(save>0){
            return orderPO;
        }
        throw new SavaException("更改订单失败");
    }
    @Override
    public Integer deleteById(String id){
        OrderVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=orderMapper.deleteById(id);
        return i;
   }

    @Override
    public OrderPO selectOneById(String id){
        OrderPO orderPO=orderMapper.selectById(id);
        return orderPO;
    }

    @Override
    public OrderVO saveone(OrderVO orderVO){
        OrderPO save=save(orderVO);
        BeanUtils.copyProperties(save,orderVO);
        return orderVO;
    }

    @Override
    public Page<OrderVO> getPage(OrderVO orderVO){
        QueryWrapper<OrderPO> wrapper=new QueryWrapper<>();
        Page<OrderPO> page=new Page<>(orderVO.getPage().getPage(),orderVO.getPage().getSize());
        Page<OrderPO> poiPage=(Page<OrderPO>) orderMapper.selectPage(page,wrapper);
        return ConvertUtil.transferPage(poiPage,OrderVO.class);
    }
}
