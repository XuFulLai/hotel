package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo.MaterialsApplyVO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.service.IMaterialsApplyService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import org.ehcache.spi.service.MaintainableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 文
 * @description: 物资申请控制层
 * @date 2022/4/22 21:29
 */
@Api(tags="物资申请相关接口")
@RestController
@RequestMapping("api/materialsApply")
public class MaterialsApplyController {

    @Autowired
    IMaterialsApplyService maintainableService;

    @PostMapping("apply")
    public Reply apply(@RequestBody MaterialsApplyVO materialsApplyVO) {
        return Reply.success(maintainableService.apply(materialsApplyVO));
    }

    @PostMapping("page")
    public Reply<Page<MaterialsApplyVO>> getPage(@RequestBody MaterialsApplyVO materialsApplyVO) {
        return Reply.success(maintainableService.getPage(materialsApplyVO));
    }

}
