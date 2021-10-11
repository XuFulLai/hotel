package group.oneonetwo.hotelintelligencesystem.modules.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.po.MenuPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<MenuPO> {

}
