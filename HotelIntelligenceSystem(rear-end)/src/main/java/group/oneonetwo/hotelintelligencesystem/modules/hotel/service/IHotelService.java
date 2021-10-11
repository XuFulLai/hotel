package group.oneonetwo.hotelintelligencesystem.modules.hotel.service;

import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelP0;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;

public interface IHotelService {
    HotelP0 selectOneById(String id);
    HotelP0 add(HotelVO hotelVO);
    HotelVO selectOneByIdReturnVO(String id);
    HotelP0 save(HotelVO hotelVO);
    Integer deleteById(String id);
}
