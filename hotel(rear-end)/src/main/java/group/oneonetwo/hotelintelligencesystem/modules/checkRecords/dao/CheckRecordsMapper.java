package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.po.CheckRecordsPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 文
* @description 针对表【check_records】的数据库操作Mapper
* @createDate 2022-04-19 14:07:07
* @Entity group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.po.CheckRecords
*/
@Mapper
public interface CheckRecordsMapper extends BaseMapper<CheckRecordsPO> {


    int batchInsert(List<CheckRecordsPO> list);
}
