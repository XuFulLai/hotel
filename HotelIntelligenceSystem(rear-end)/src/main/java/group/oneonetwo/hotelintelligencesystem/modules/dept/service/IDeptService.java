package group.oneonetwo.hotelintelligencesystem.modules.dept.service;

import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;

/**
 * @author 文
 * @description 部门服务层接口
 */
public interface IDeptService {

    public DeptPO selectOneById(String id);

    DeptPO add(DeptVO deptVO);

    DeptVO selectOneByIdReturnVO(String id);

    DeptPO save(DeptVO deptVO);

    Integer deleteById(String id);

}
