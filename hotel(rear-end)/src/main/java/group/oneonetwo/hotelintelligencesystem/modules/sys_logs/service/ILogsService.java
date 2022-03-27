package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service;

import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.po.BedTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.vo.BedTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.po.LogsPO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.vo.LogsVO;

/**
 * @author liustart
 * @create 2022-03-27 8:56
 */
public interface ILogsService {

    LogsPO selectOneById(String id);

    LogsVO add(LogsVO logsVO);

    LogsVO selectOneByIdReturnVO(String id);

    LogsPO save(LogsVO logsVO);

    Integer deleteById(String id);

    LogsVO saveone(LogsVO logsVO);
}
