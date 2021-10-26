package group.oneonetwo.hotelintelligencesystem.modules.hotel.service;

import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;

public interface IHotelService {
    HotelPO selectOneById(String id);
    HotelVO add(HotelVO hotelVO);
    HotelVO selectOneByIdReturnVO(String id);
    HotelVO save(HotelVO hotelVO);
    Integer deleteById(String id);
}
