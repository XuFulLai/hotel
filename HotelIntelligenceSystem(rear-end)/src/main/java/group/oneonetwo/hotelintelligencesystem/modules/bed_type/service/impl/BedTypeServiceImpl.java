package group.oneonetwo.hotelintelligencesystem.modules.bed_type.service.impl;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.dao.BedTypeMapper;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.po.BedTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.model.vo.BedTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.bed_type.service.IBedTypeService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BedTypeServiceImpl implements IBedTypeService {
    @Autowired
    BedTypeMapper bedTypeMapper;

    @Override
    public BedTypeVO add(BedTypeVO bedTypeVO){
        if(bedTypeVO ==null){
            throw new SavaException("插入用户失败,床的类型实体为空");
        }
        BedTypePO bedTypePO=new BedTypePO();
        BeanUtils.copyProperties(bedTypeVO,bedTypePO);
        int insert=bedTypeMapper.insert(bedTypePO);
        if(insert>0){
            return  bedTypeVO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public BedTypeVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        BedTypePO bedTypePO=bedTypeMapper.selectById(id);
        BedTypeVO bedTypeVO=new BedTypeVO();
        if(bedTypePO!=null){
            BeanUtils.copyProperties(bedTypePO,bedTypeVO);
        }
        return bedTypeVO;
    }
    @Override
    public BedTypePO save(BedTypeVO bedTypeVO){
        if(bedTypeVO==null){
            throw new CommonException(501,"床的类型实体为空");
        }
        BedTypeVO check=selectOneByIdReturnVO(bedTypeVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为"+bedTypeVO.getId()+"的数据");
        }
        BedTypePO bedTypePO=new BedTypePO();
        BeanUtils.copyProperties(bedTypeVO,bedTypePO);
        int save=bedTypeMapper.updateById(bedTypePO);
        if(save>0){
            return bedTypeMapper.selectById(bedTypePO.getId());
        }
        throw new SavaException("更改床的类型失败");
    }

    @Override
    public Integer deleteById(String id){
        BedTypeVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+id+"的数据");
        }
        int i=bedTypeMapper.deleteById(id);
        return i;
    }

    @Override
    public BedTypePO selectOneById(String id){
        BedTypePO bedTypePO=bedTypeMapper.selectById(id);
        return  bedTypePO;
    }

    @Override
    public BedTypeVO saveone(BedTypeVO bedTypeVO){
        BedTypePO save=save(bedTypeVO);
        BeanUtils.copyProperties(save,bedTypeVO);
        return bedTypeVO;
    }

}
