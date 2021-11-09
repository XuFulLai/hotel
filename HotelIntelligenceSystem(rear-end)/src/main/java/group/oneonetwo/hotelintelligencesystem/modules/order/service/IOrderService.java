package group.oneonetwo.hotelintelligencesystem.modules.order.service;

import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;


public interface IOrderService {
    OrderPO selectOneById(String id);
    OrderVO add(OrderVO orderVO);
    OrderVO selectOneByIdReturnVO(String id);
    OrderPO save(OrderVO orderVO);
    Integer deleteById(String id);
    OrderVO saveone(OrderVO orderVO);
}
