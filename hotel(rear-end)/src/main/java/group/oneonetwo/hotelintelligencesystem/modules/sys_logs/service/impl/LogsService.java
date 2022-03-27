package group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.impl;

import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.dao.LogsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.po.LogsPO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.model.vo.LogsVO;
import group.oneonetwo.hotelintelligencesystem.modules.sys_logs.service.ILogsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liustart
 * @create 2022-03-27 8:56
 */

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class LogsService implements ILogsService {
    @Autowired
    LogsMapper logsMapper;

    @Autowired
    AuthUtils authUtils;

    @Override
    public LogsPO selectOneById(String id) {
        LogsPO logsPO = logsMapper.selectById(id);
        return logsPO;
    }

    @Override
    public LogsVO add(LogsVO logsVO) {
        if(logsVO==null){
            throw  new SavaException("插入失败,日志实体为空");
        }
        logsVO.setHotelId(authUtils.getUserHotelId());
        LogsPO logsPO = new LogsPO();
        BeanUtils.copyProperties(logsVO,logsPO);
        int insert = logsMapper.insert(logsPO);
        if(insert>0){
            return logsVO;
        }
        throw  new SavaException("插入日志失败");
    }

    @Override
    public LogsVO selectOneByIdReturnVO(String id) {

        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        LogsPO logsPO = logsMapper.selectById(id);
        LogsVO logsVO = new LogsVO();
        if(logsPO!=null){
            BeanUtils.copyProperties(logsPO,logsVO);
        }
        return logsVO;
    }

    @Override
    public LogsPO save(LogsVO logsVO) {
        if (logsVO !=null){
            throw  new CommonException(501,"传入的参数为空");
        }
        LogsVO check = selectOneByIdReturnVO(logsVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为"+logsVO.getId()+"的数据");
        }
        LogsPO logsPO = new LogsPO();
        BeanUtils.copyProperties(logsVO,logsPO);
        int i = logsMapper.updateById(logsPO);
        if(i>0){
            return logsMapper.selectById(logsPO.getId());
        }
        throw new SavaException("更改日志失败");
    }

    @Override
    public Integer deleteById(String id) {
        LogsVO logsVO = selectOneByIdReturnVO(id);
        if(logsVO==null){
            throw new CommonException(4004,"找不到id为:"+id+"的数据");
        }
        int i = logsMapper.deleteById(id);
        return i;
    }

    @Override
    public LogsVO saveone(LogsVO logsVO) {
        LogsPO save = save(logsVO);
        BeanUtils.copyProperties(save,logsVO);
        return logsVO;
    }
}
