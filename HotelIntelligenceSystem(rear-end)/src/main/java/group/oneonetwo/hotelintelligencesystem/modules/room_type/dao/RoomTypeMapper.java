package group.oneonetwo.hotelintelligencesystem.modules.room_type.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.po.BedTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("房间类型实体")
public interface RoomTypeMapper extends BaseMapper<RoomTypePO> {
}
