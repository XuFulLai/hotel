package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.IsolationInfoService;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.dao.IsolationInfoMapper;

import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 文
* @description 针对表【isolation_info】的数据库操作Service实现
* @createDate 2022-04-19 10:26:02
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class IsolationInfoServiceImpl implements IsolationInfoService{

    @Autowired
    IsolationInfoMapper isolationInfoMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IUserService userService;

    @Autowired
    IHotelService hotelService;


    @Autowired
    IRoomService roomService;

    @Override
    public IsolationInfoVO add(IsolationInfoVO isolationInfoVO) {
        if(isolationInfoVO==null){
            throw new SavaException("插入隔离人员信息失败:实体为空");
        }
        IsolationInfoPO isolationInfoPO = new IsolationInfoPO();
        BeanUtils.copyProperties(isolationInfoVO,isolationInfoPO);
        int insert=isolationInfoMapper.insert(isolationInfoPO);
        if(insert>0){
            return isolationInfoVO;
        }
        throw new SavaException("插入隔离人员信息失败");

    }

    @Override
    public IsolationInfoVO save(IsolationInfoVO isolationInfoVO) {
        if (isolationInfoVO==null){
            throw new CommonException(501,"隔离人员信息实体为空");
        }
        IsolationInfoVO check=selectOneByIdReturnVO(isolationInfoVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+isolationInfoVO.getId()+"的数据");
        }
        if (check.getStatus() == 1 || check.getStatus() == 2) {
            if (isolationInfoVO.getStatus() == 0) {
                throw new CommonException("请重新增加隔离人员数据!");
            }
        }else {
            if (isolationInfoVO.getStatus() != 0) {
                String roomId = check.getRoomId();
                roomService.leaveIsolationRoom(roomId);
            }
        }

        IsolationInfoPO isolationInfoPO = new IsolationInfoPO();
        BeanUtils.copyProperties(isolationInfoVO,isolationInfoPO);
        int save=isolationInfoMapper.updateById(isolationInfoPO);
        if(save>0){
            return isolationInfoVO;
        }
        throw new SavaException("更改隔离人员信息失败");

    }

    @Override
    public Integer deleteById(String id) {
        IsolationInfoVO check = selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=isolationInfoMapper.deleteById(id);
        return i;

    }

    @Override
    public IsolationInfoVO selectOneByIdReturnVO(String id) {
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        IsolationInfoPO isolationInfoPO = isolationInfoMapper.selectById(id);
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        if(isolationInfoPO!=null){
            BeanUtils.copyProperties(isolationInfoPO,isolationInfoVO);
        }
        return isolationInfoVO;
    }

    @Override
    public IsolationInfoPO selectOneById(String id) {
        IsolationInfoPO isolationInfoPO = isolationInfoMapper.selectById(id);
        return isolationInfoPO;
    }

    @Override
    public Page<IsolationInfoVO> getPage(IsolationInfoVO isolationInfoVO) {
        Page<IsolationInfoPO> page=new Page<>(isolationInfoVO.getPage().getPage(),isolationInfoVO.getPage().getSize());
        return isolationInfoMapper.getPage(page,isolationInfoVO);
    }

    @Override
    public List<IsolationInfoVO> getAllList(IsolationInfoVO isolationInfoVO) {
        String authority = authUtils.getRole();
        switch (authority) {
            case "admin":break;
            case "hotel_admin":
                UserVO userVO = userService.selectOneByIdReturnVO(authUtils.getUid());
                if (userVO == null) {
                    throw new CommonException(401,"无权限");
                }
                HotelVO hotelVO = new HotelVO();
                hotelVO.setDeptId(userVO.getDept());
                hotelVO = hotelService.selectOneByDeptId(userVO.getDept());
                if (hotelVO == null) {
                    throw new CommonException(500,"该用户未绑定酒店");
                }
                isolationInfoVO.setHotelId(hotelVO.getId());
                break;
            default:
                throw new CommonException(401,"无权限");
        }
        return isolationInfoMapper.getAllList(isolationInfoVO);
    }

    @Override
    public Page<IsolationInfoVO> getOwnPage(IsolationInfoVO isolationInfoVO) {
        String uid = authUtils.getUid();
        isolationInfoVO.setuId(uid);
        return getPage(isolationInfoVO);
    }



    @Override
    public IsolationInfoPO selectOneByRoomId(String roomId) {
        QueryWrapper<IsolationInfoPO> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId);
        IsolationInfoPO isolationInfoPO = isolationInfoMapper.selectOne(wrapper);
        return isolationInfoPO;
    }


    @Override
    public void distribution(IsolationInfoVO isolationInfoVO) {
        RoomVO roomVO = roomService.isolationCheckIn(isolationInfoVO.getHotelId(), isolationInfoVO.getRoomType(), null);
        if(!WStringUtils.isBlank(isolationInfoVO.getUserName())) {
            UserPO username = userService.findByUsername(isolationInfoVO.getUserName());
            isolationInfoVO.setuId(username.getId());
        }
        isolationInfoVO.setStatus(0);
        isolationInfoVO.setRoomId(roomVO.getId());
        isolationInfoVO.setRoomName(roomVO.getName());
        add(isolationInfoVO);
    }

    @Override
    public IsolationInfoVO selectByRoomIdAndStaus(String roomId) {
        QueryWrapper<IsolationInfoPO> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId);
        wrapper.eq("status",0);
        List<IsolationInfoPO> isolationInfoPOS = isolationInfoMapper.selectList(wrapper);
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        BeanUtils.copyProperties(isolationInfoPOS.get(0),isolationInfoVO);
        return isolationInfoVO;
    }


}