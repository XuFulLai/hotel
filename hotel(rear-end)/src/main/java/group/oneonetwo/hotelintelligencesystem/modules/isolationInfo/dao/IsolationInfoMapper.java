package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author 文
* @description 针对表【isolation_info】的数据库操作Mapper
* @createDate 2022-04-19 10:26:02
* @Entity group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO
*/
@Repository
@Mapper
public interface IsolationInfoMapper extends BaseMapper<IsolationInfoPO> {
    public Page<IsolationInfoVO> getPage(@Param("page") Page page, @Param("vo")IsolationInfoVO vo);


}
