package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po.MaterialsApplyPO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文
* @description 针对表【materials_apply】的数据库操作Mapper
* @createDate 2022-04-22 20:48:03
* @Entity group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po.MaterialsApply
*/
@Mapper
public interface MaterialsApplyMapper extends BaseMapper<MaterialsApplyPO> {


}
