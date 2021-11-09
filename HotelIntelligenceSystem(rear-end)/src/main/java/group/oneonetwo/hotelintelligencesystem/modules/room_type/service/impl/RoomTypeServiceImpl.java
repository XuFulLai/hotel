package group.oneonetwo.hotelintelligencesystem.modules.room_type.service.impl;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.room.dao.RoomMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.dao.RoomTypeMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoomTypeServiceImpl implements IRoomTypeServeice {
    @Autowired
    RoomTypeMapper roomTypeMapper;

    @Override
    public RoomTypeVO add(RoomTypeVO roomTypeVO){
        if(roomTypeVO==null){
            throw new SavaException("插入用户失败,房的类型实体为空");
        }
        RoomTypePO roomTypePO=new RoomTypePO();
        BeanUtils.copyProperties(roomTypeVO,roomTypePO);
        int insert=roomTypeMapper.insert(roomTypePO);
        if(insert>0){
            return roomTypeVO;
        }
        throw  new SavaException("插入用户失败");
    }
    public RoomTypeVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数不能为空");
        }
        RoomTypePO roomTypePO=roomTypeMapper.selectById(id);
        RoomTypeVO roomTypeVO=new RoomTypeVO();
        if(roomTypePO!=null){
            BeanUtils.copyProperties(roomTypePO,roomTypeVO);
        }
        return roomTypeVO;
    }
    @Override
    public Integer deleteById(String id){
        RoomTypeVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=roomTypeMapper.deleteById(id);
        return i;
    }
    @Override
    public RoomTypePO save(RoomTypeVO roomTypeVO){
        if(roomTypeVO==null){
            throw new CommonException(501,"房间的类型实体为空");
        }
        RoomTypeVO check=selectOneByIdReturnVO(roomTypeVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为"+roomTypeVO.getId()+"的数据");
        }
        RoomTypePO roomTypePO=new RoomTypePO();
        BeanUtils.copyProperties(roomTypeVO,roomTypePO);
        int save=roomTypeMapper.updateById(roomTypePO);
        if(save>0){
            return roomTypeMapper.selectById(roomTypePO.getId());
        }
        throw new SavaException("更改房间的类型失败");
    }
    @Override
    public RoomTypePO selectOneById(String id){
        RoomTypePO roomTypePO=roomTypeMapper.selectById(id);
        return roomTypePO;
    }

    @Override
    public RoomTypeVO saveone(RoomTypeVO roomTypeVO){
        RoomTypePO save=save(roomTypeVO);
        BeanUtils.copyProperties(save,roomTypeVO);
        return roomTypeVO;
    }
}
