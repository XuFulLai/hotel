package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptP0;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MenuDeptMapper extends BaseMapper<MenuDeptP0> {
}
