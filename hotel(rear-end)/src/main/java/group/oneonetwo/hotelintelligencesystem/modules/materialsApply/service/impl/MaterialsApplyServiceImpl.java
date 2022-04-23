package group.oneonetwo.hotelintelligencesystem.modules.materialsApply.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.dao.MaterialsApplyMapper;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.po.MaterialsApplyPO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.model.vo.MaterialsApplyVO;
import group.oneonetwo.hotelintelligencesystem.modules.materialsApply.service.IMaterialsApplyService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

/**
* @author 文
* @description 针对表【materials_apply】的数据库操作Service实现
* @createDate 2022-04-22 20:48:03
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class MaterialsApplyServiceImpl implements IMaterialsApplyService {

    @Autowired
    MaterialsApplyMapper materialsApplyMapper;

    @Autowired
    AuthUtils authUtils;

    @Override
    public MaterialsApplyVO add(MaterialsApplyVO materialsApplyVO) {
        if(materialsApplyVO==null){
            throw new SavaException("插入检测信息为空:实体为空");
        }
        MaterialsApplyPO materialsApplyPO = new MaterialsApplyPO();
        BeanUtils.copyProperties(materialsApplyVO,materialsApplyPO);
        int insert=materialsApplyMapper.insert(materialsApplyPO);
        if(insert>0){
            BeanUtils.copyProperties(materialsApplyPO,materialsApplyVO);
            return materialsApplyVO;
        }
        throw new SavaException("插入检测信息失败");
    }

    @Override
    public MaterialsApplyVO apply(MaterialsApplyVO materialsApplyVO) {
        materialsApplyVO.setUid(authUtils.getUid());
        return add(materialsApplyVO);
    }

    @Override
    public Page<MaterialsApplyVO> getPage(MaterialsApplyVO materialsApplyVO) {
        QueryWrapper<MaterialsApplyPO> wrapper=new QueryWrapper<>();
        if(!"admin".equals(authUtils.getRole())){
            wrapper.eq("uid",authUtils.getUid());
        }
        Page<MaterialsApplyPO> page = new Page<>(materialsApplyVO.getPage().getPage(),materialsApplyVO.getPage().getSize());
        Page<MaterialsApplyPO> poiPage= (Page<MaterialsApplyPO>) materialsApplyMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage, MaterialsApplyVO.class);
    }

    @Override
    public MaterialsApplyVO selectOneByIdReturnVO(String id) {
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        MaterialsApplyPO materialsApplyPO = materialsApplyMapper.selectById(id);
        MaterialsApplyVO materialsApplyVO = new MaterialsApplyVO();
        if(materialsApplyPO!=null){
            BeanUtils.copyProperties(materialsApplyPO,materialsApplyVO);
        }


        return materialsApplyVO;
    }

    @Override
    public MaterialsApplyPO selectOneById(String id) {
        MaterialsApplyPO materialsApplyPO = materialsApplyMapper.selectById(id);
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        return materialsApplyPO;
    }

    @Override
    public Integer deleteById(String id) {
        MaterialsApplyVO materialsApplyVO = selectOneByIdReturnVO(id);
        if (materialsApplyVO==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i = materialsApplyMapper.deleteById(id);
        return i;
    }

    @Override
    public MaterialsApplyVO save(MaterialsApplyVO materialsApplyVO) {
        if(materialsApplyVO==null){
            throw new CommonException(501,"隔离人员信息实体为空");
        }
        MaterialsApplyVO materialsApplyVO1 = selectOneByIdReturnVO(materialsApplyVO.getId());
        if (materialsApplyVO1==null){
            throw  new CommonException(4004,"找不到id为"+materialsApplyVO.getId()+"的数据");
        }
        MaterialsApplyPO materialsApplyPO = new MaterialsApplyPO();
        BeanUtils.copyProperties(materialsApplyVO,materialsApplyPO);
        int i = materialsApplyMapper.updateById(materialsApplyPO);
        if(i>0){
            return materialsApplyVO;
        }
        throw new SavaException("更改物资信息失败");
    }


}
