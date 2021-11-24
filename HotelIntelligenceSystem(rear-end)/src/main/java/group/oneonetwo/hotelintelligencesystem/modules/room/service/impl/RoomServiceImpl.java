package group.oneonetwo.hotelintelligencesystem.modules.room.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.room.dao.RoomMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoomServiceImpl implements IRoomService {
    @Autowired
    RoomMapper roomMapper;

    @Override
    public RoomVO add(RoomVO roomVO) {
        if (roomVO == null) {
            throw new SavaException("插入用户失败,房间实体为空");

        }
        RoomPO roomPO = new RoomPO();
        BeanUtils.copyProperties(roomVO, roomPO);
        int insert = roomMapper.insert(roomPO);
        if (insert > 0) {
            return roomVO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public RoomVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501, "参数为空");
        }
        RoomPO roomPO = roomMapper.selectById(id);
        RoomVO roomVO = new RoomVO();
        if (roomVO != null) {
            BeanUtils.copyProperties(roomPO, roomVO);
        }
        return roomVO;
    }

    @Override
    public RoomPO save(RoomVO roomVO){
        if(roomVO==null){
            throw new CommonException(501,"房间实体为空");
        }
        RoomVO check=selectOneByIdReturnVO(roomVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为'"+roomVO.getId()+"'的数据");
        }
        RoomPO roomPO=new RoomPO();
        BeanUtils.copyProperties(roomVO,roomPO);
        int save=roomMapper.updateById(roomPO);
        if(save>0){
            return roomMapper.selectById(roomPO.getId());
        }
        throw new SavaException("更改房间失败");
    }
    @Override
    public Integer deleteById(String id){
        RoomVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为'"+id+"'的数据");
        }
        int i= roomMapper.deleteById(id);
        return i;
    }
    @Override
    public RoomPO selectOneById(String id){
        RoomPO roomPO=roomMapper.selectById(id);
        return roomPO;
    }

    @Override
    public RoomVO saveone(RoomVO roomVO){
        RoomPO save=save(roomVO);
        BeanUtils.copyProperties(save,roomVO);
        return roomVO;
    }

    @Override
    public Page<RoomVO> getPage(RoomVO roomVO){
        QueryWrapper<RoomPO> wrapper=new QueryWrapper<>();
        Page<RoomPO> page=new Page<>(roomVO.getPage().getPage(),roomVO.getPage().getSize());
        Page<RoomPO> poiPage=(Page<RoomPO>) roomMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage,RoomVO.class);
    }

    @Override
    public List<RoomVO> getAllList(RoomVO roomVO){
        return roomMapper.getAllList(roomVO);
    }

    @Override
    public List<RoomVO> getList(RoomVO roomVO){
        return roomMapper.getList(roomVO);
    }
}
