package group.oneonetwo.hotelintelligencesystem.modules.room.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.components.websocket.WebSocketServer;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.order.service.IOrderService;
import group.oneonetwo.hotelintelligencesystem.modules.room.dao.RoomMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.CheckInVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.DetailVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.TimeUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoomServiceImpl implements IRoomService {

    Logger logger = LoggerFactory.getLogger(Object.class);

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IOrderService orderService;

    @Autowired
    IRoomTypeServeice roomTypeServeice;

    @Autowired
    IDiscountsService discountsService;

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
        RoomPO thisRoom = selectOneById(roomPO.getId());
        BeanUtils.copyProperties(thisRoom,roomVO);
        if(save>0){
            if (!WStringUtils.isBlank(thisRoom.getId()) && !WStringUtils.isBlank(thisRoom.getHotelId())) {
                sendUpdateInfo(roomVO);
            }
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
        roomVO.setHotelId(authUtils.getUserHotelId());
        return roomMapper.getAllList(roomVO);
    }

    @Override
    public DetailVO getDetail(String id){
        return roomMapper.getDetail(id);
    }

    @Override
    public  List<RoomVO> getRoomTypeList( RoomVO roomVO){
        return roomMapper.getRoomTypeList(roomVO);
    }

    /**
     * 入住
     * @param checkInVO id,customerId(可能没),orderId(可能没),province
     * @return
     */
    @Override
    public OrderVO checkIn(CheckInVO checkInVO) {

        Date now = new Date();
        // 订单id不为空时,则是网订单
        if (!WStringUtils.isBlank(checkInVO.getOrderId())) {
            //判断是否到入住时间
            OrderVO thisOrder = orderService.selectOneByIdReturnVO(checkInVO.getOrderId());
            if (!now.after(thisOrder.getEstimatedCheckIn())) {
                throw new CommonException("订单未到预定入住时间");
            }
        }

        int[] pays = new int[2];
        RoomVO thisRoom = selectOneByIdReturnVO(checkInVO.getId());
        //当该房间是被预定且订单id不同时,重新分配房间
        if (thisRoom.getStatus() == 2 && (WStringUtils.isBlank(checkInVO.getOrderId()) || thisRoom.getOrderId().equals(checkInVO.getOrderId()))) {
            assignRoom(thisRoom);
        }
        RoomTypeVO roomTypeVO = roomTypeServeice.selectOneByIdReturnVO(thisRoom.getType());
        OrderVO updateOrder = new OrderVO();
        OrderVO orderVO;
        RoomVO updateRoom = new RoomVO();
        //房间状态设置为入住
        updateRoom.setStatus(1);
        updateRoom.setId(checkInVO.getId());

        //更新订单表
        updateOrder.setCheckInTime(now);
        updateOrder.setProvince(checkInVO.getProvince());
        if (WStringUtils.isBlank(checkInVO.getOrderId())) {
            updateOrder.setEstimatedCheckIn(TimeUtils.setSplitTime(now));
        } else {
            orderVO = orderService.selectOneByIdReturnVO(checkInVO.getOrderId());
            updateOrder.setEstimatedCheckIn(orderVO.getEstimatedCheckIn());
        }
        updateOrder.setEstimatedCheckOut(TimeUtils.setSplitTime(checkInVO.getEstimatedCheckOut()));
        updateOrder.setDays(TimeUtils.daysBetween(updateOrder.getEstimatedCheckIn(),updateOrder.getEstimatedCheckOut(),"ceil"));
        pays = discountsService.countPay(TimeUtils.daysBetween(updateOrder.getEstimatedCheckIn(),updateOrder.getEstimatedCheckOut(),"ceil"),roomTypeVO.getFee());
        updateOrder.setPay(String.valueOf(pays[0]));
        updateOrder.setLastPay(String.valueOf(pays[1]));

        //把房间信息写入订单表
        updateOrder.setRoomType(thisRoom.getType());
        updateOrder.setRoomName(thisRoom.getName());
        updateOrder.setHotelId(thisRoom.getHotelId());

        //若客户id不为空,则写入订单表
        if (!WStringUtils.isBlank(checkInVO.getCustomerId())) {
            updateOrder.setCustomerId(checkInVO.getCustomerId());
        }

        OrderPO orderSave;
        if (!WStringUtils.isBlank(checkInVO.getOrderId())) {
            updateOrder.setId(checkInVO.getOrderId());
            orderSave = orderService.save(updateOrder);
        } else {
            orderSave = orderService.add(updateOrder);
        }

        //绑定订单到房间
        updateRoom.setOrderId(orderSave.getId());

        RoomPO roomSave = save(updateRoom);

        //转换并返回
        OrderVO res = new OrderVO();
        BeanUtils.copyProperties(orderSave,res);
        return res;
    }

    /**
     * 退房
     * @param id
     * @return
     */
    @Override
    public String checkOut(String id) {
        Date now = new Date();
        RoomPO thisRoom = selectOneById(id);
        OrderVO thisOrder = orderService.selectOneByIdReturnVO(thisRoom.getOrderId());
        Integer btTime = 0;
        int[] pays = new int[2];
        int extraFee = 0;
        RoomTypeVO roomTypeVO = roomTypeServeice.selectOneByIdReturnVO(thisRoom.getType());
        //计算基本房费
        pays[0] = Integer.parseInt(thisOrder.getPay());
        pays[1] = Integer.parseInt(thisOrder.getLastPay());
        OrderVO updateOrder = new OrderVO();

        //超时的情况
        if (now.after(thisOrder.getEstimatedCheckOut())) {
            //超时
            btTime = TimeUtils.daysBetween(TimeUtils.setSplitTime(thisOrder.getEstimatedCheckOut()), now, "ceil");
            pays[0] += btTime * roomTypeVO.getFee();
            pays[1] += btTime * roomTypeVO.getFee();
            extraFee = btTime * roomTypeVO.getFee();
            updateOrder.setDays(btTime);

        }
        //更新订单信息
        updateOrder.setCheckOutTime(now);
        updateOrder.setId(thisOrder.getId());
        //获取房间单价,计算价格
        updateOrder.setPay(String.valueOf(pays[0]));
        updateOrder.setLastPay(String.valueOf(pays[1]));

        orderService.save(updateOrder);

        Integer integer = unlockRoom(thisRoom.getId());

        if (extraFee == 0) {
            return "订单完成!";
        } else {
            return "须补缴" + extraFee + "元!";
        }
    }

    public Integer unlockRoom(String id) {
        Integer integer = roomMapper.unlockRoom(id);
        if (integer > 0) {
            sendUpdateInfo(selectOneByIdReturnVO(id));
        }
        return integer;
    }

    private void sendUpdateInfo(RoomVO vo) {
        List<String> hotelAllUser = authUtils.getHotelAllUser(vo.getHotelId());
        Iterator<String> allUserIter = hotelAllUser.iterator();
        RoomVO roomVO = new RoomVO();
        roomVO.setId(vo.getId());
        List<RoomVO> allList = getAllList(roomVO);
        Gson gson = new Gson();
        String hotelInfo = gson.toJson(allList.get(0));
        while(allUserIter.hasNext()) {
            try {
                WebSocketServer.sendInfo(hotelInfo, allUserIter.next());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

    }

    @Override
    public void cancelRoom(RoomVO roomVO) {
        QueryWrapper<RoomPO> wrapper = new QueryWrapper<RoomPO>();
        wrapper.eq("order_id",roomVO.getOrderId()).eq("status",2);
        List<RoomPO> roomPOS = roomMapper.selectList(wrapper);
        unlockRoom(roomPOS.get(0).getId());
    }



    @Override
    public void assignRoom(RoomVO roomVO) {

        //解锁原来锁定的房间
        if (!WStringUtils.isBlank(roomVO.getOrderId())) {
            cancelRoom(roomVO);
        }

        QueryWrapper<RoomPO> wrapper = new QueryWrapper<RoomPO>();
        wrapper.eq("type",roomVO.getType()).eq("status",0);
        List<RoomPO> roomPOS = roomMapper.selectList(wrapper);
        if (roomPOS.size() > 1) {
            RoomVO vo = new RoomVO();
            vo.setId(roomPOS.get(0).getId());
            vo.setStatus(2);
            vo.setOrderId(roomVO.getOrderId());
            save(vo);
        } else {
            throw new CommonException("已没同类型房间,请选择其他类型房间进行入住");
        }
    }


}
