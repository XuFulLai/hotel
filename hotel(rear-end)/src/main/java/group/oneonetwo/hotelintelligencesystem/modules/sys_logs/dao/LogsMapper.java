package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.po.LogsPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author liustart
 * @create 2022-03-27 9:07
 */

@Mapper
@Repository
public interface LogsMapper extends BaseMapper<LogsPO> {
}
