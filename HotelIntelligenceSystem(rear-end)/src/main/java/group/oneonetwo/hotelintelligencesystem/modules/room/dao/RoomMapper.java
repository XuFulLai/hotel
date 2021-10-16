package group.oneonetwo.hotelintelligencesystem.modules.room.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoomMapper extends BaseMapper<RoomPO> {

}
