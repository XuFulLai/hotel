package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo.MaterialsApplyVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;

/**
* @author 文
* @description 针对表【materials_apply】的数据库操作Service
* @createDate 2022-04-22 20:48:03
*/
public interface IMaterialsApplyService {

    MaterialsApplyVO add(MaterialsApplyVO materialsApplyVO);

    MaterialsApplyVO apply(MaterialsApplyVO materialsApplyVO);

    Page<MaterialsApplyVO> getPage(MaterialsApplyVO materialsApplyVO);
}
