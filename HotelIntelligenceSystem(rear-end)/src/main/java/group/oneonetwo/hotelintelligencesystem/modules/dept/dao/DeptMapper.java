package group.oneonetwo.hotelintelligencesystem.modules.dept.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 文
 * @description dept数据操作层
 */
@Mapper
@Repository
public interface DeptMapper extends BaseMapper<DeptPO> {
}
