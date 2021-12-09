package group.oneonetwo.hotelintelligencesystem.modules.hotel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HotelMapper extends BaseMapper<HotelPO> {
}
