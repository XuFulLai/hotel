package group.oneonetwo.hotelintelligencesystem.modules.review.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.IsolationInfoService;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.po.ReviewPO;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.vo.ReviewVO;
import group.oneonetwo.hotelintelligencesystem.modules.review.service.ReviewService;
import group.oneonetwo.hotelintelligencesystem.modules.review.dao.ReviewMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.service.WalletService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 文
* @description 针对表【review】的数据库操作Service实现
* @createDate 2022-04-19 10:40:37
*/
@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    IRoomService roomService;

    @Autowired
    IRoomTypeServeice roomTypeServeice;

    @Autowired
    IsolationInfoService isolationInfoService;

    @Autowired
    ReviewService reviewService;


    @Autowired
    WalletService walletService;

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IUserService userService;

    @Override
    public ReviewPO selectOneById(String id) {
        ReviewPO reviewPO = reviewMapper.selectById(id);
        return reviewPO;
    }

    @Override
    public ReviewVO add(ReviewVO reviewVO) {
        if(reviewVO==null){
            throw new SavaException("插入隔离人员信息失败:实体为空");
        }
        ReviewPO reviewPO = new ReviewPO();
        BeanUtils.copyProperties(reviewVO,reviewPO);
        int insert=reviewMapper.insert(reviewPO);
        if(insert>0){
            return reviewVO;
        }
        throw new SavaException("增加审批信息失败");
    }

    @Override
    public ReviewVO selectOneByIdReturnVO(String id) {
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        ReviewPO reviewPO = reviewMapper.selectById(id);
        ReviewVO reviewVO = new ReviewVO();
        if(reviewPO!=null){
            BeanUtils.copyProperties(reviewPO,reviewVO);
        }
        return reviewVO;
    }

    @Override
    public ReviewVO save(ReviewVO reviewVO) {
        if (reviewVO==null){
            throw new CommonException(501,"隔离人员信息实体为空");
        }
        ReviewVO check = selectOneByIdReturnVO(reviewVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+reviewVO.getId()+"的数据");
        }
        ReviewPO reviewPO = new ReviewPO();
        BeanUtils.copyProperties(reviewVO,reviewPO);
        int save=reviewMapper.updateById(reviewPO);
        if(save>0){
            return reviewVO;
        }
        throw new SavaException("更改审批信息失败");
    }

    @Override
    public Integer deleteById(String id) {
        ReviewVO check = selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=reviewMapper.deleteById(id);
        return i;

    }

    @Override
    public Page<ReviewVO> getPage(ReviewVO reviewVO) {
        Page<ReviewPO> page=new Page<>(reviewVO.getPage().getPage(),reviewVO.getPage().getSize());
        return reviewMapper.getPage(page,reviewVO);

    }
    //id
    @Override
    public void getCheck(ReviewVO reviewVO) {
        reviewVO.setuId(authUtils.getUid());
        if(reviewVO.getType()==0 || reviewVO.getType()==1){
            add(reviewVO);
        }else {

            if(userService.selectOneById(reviewVO.getuId())==null){
                throw new CommonException(501,"账户未注册");
            }
            RoomTypePO roomTypePO = roomTypeServeice.selectOneById(reviewVO.getRoomType());
            Integer isolationFee = roomTypePO.getIsolationFee();
            WalletPO walletPO = walletService.getWalletPOByUid(reviewVO.getuId());

            double balances=0;
            balances=walletPO.getBalance()-isolationFee*14;
            if(balances<=0.0){
                throw new CommonException(502,"账户余额不足");
            }
            //这里貌似查了个更新
            walletPO.setBalance(balances);
            walletService.save(walletPO);
            reviewVO.setTotalFee((int)isolationFee*14);
            add(reviewVO);
        }

    }

    //admin  id  review_status
    @Override
    public void getReviews(ReviewVO reviewVO) {
        if(reviewVO.getReviewStatus()==2){
            if(reviewVO.getRemark()==null || reviewVO.getRemark()=="") {
                throw new CommonException(501, "拒绝的理由不能为空");
            }
            //这里有个如果拒绝后有个打回钱的流程
            ReviewPO reviewPO = selectOneById(reviewVO.getId());
            if(reviewPO.getType()==2 || reviewPO.getType()==3){
                RoomTypePO roomTypePO = roomTypeServeice.selectOneById(reviewPO.getRoomType());
                WalletPO walletPO = walletService.getWalletPOByUid(reviewPO.getuId());
                walletPO.setBalance(walletPO.getBalance()+roomTypePO.getIsolationFee());
                walletService.save(walletPO);
            }
            reviewPO.setRemark(reviewVO.getRemark());
            reviewPO.setReviewStatus(2);
            ReviewVO reviewVO1 = new ReviewVO();
            BeanUtils.copyProperties(reviewPO,reviewVO1);
            reviewService.save(reviewVO1);
            return;
        }

        ReviewPO reviewPO = selectOneById(reviewVO.getId());


        RoomVO roomVO = roomService.isolationCheckIn(reviewVO.getHotelId(), reviewVO.getRoomType(), null);
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        isolationInfoVO.setName(reviewPO.getName());
        isolationInfoVO.setuId(reviewPO.getuId());
        isolationInfoVO.setIdCard(reviewPO.getIdCard());
        isolationInfoVO.setType(reviewPO.getType());
        isolationInfoVO.setPhone(reviewPO.getPhone());
        isolationInfoVO.setEmail(reviewPO.getEmail());
        isolationInfoVO.setHotelId(reviewPO.getHotelId());
        isolationInfoVO.setRoomType(roomVO.getType());
        isolationInfoVO.setPay(reviewPO.getTotalFee());
        isolationInfoVO.setCheckInTime(reviewPO.getCheckInTime());
        isolationInfoVO.setCheckOutTime(reviewPO.getCheckOutTime());
        isolationInfoVO.setRoomId(roomVO.getId());
        isolationInfoVO.setRoomName(reviewPO.getName());
        isolationInfoVO.setProvince(reviewPO.getProvince());
        isolationInfoVO.setCity(reviewPO.getCity());
        isolationInfoVO.setStatus(0);
        isolationInfoService.add(isolationInfoVO);
        ReviewVO reviewVO1 = new ReviewVO();
        reviewPO.setReviewStatus(1);
        BeanUtils.copyProperties(reviewPO,reviewVO1);
        reviewService.save(reviewVO1);

    }

    @Override
    public ReviewPO selectByUID(String id) {
        QueryWrapper<ReviewPO> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",id);
        List<ReviewPO> reviewPOS = reviewMapper.selectList(wrapper);
        return reviewPOS.get(0);
    }

    @Override
    public Page<ReviewVO> my(ReviewVO reviewVO) {
        String uid = authUtils.getUid();
        reviewVO.setuId(uid);
        Page<ReviewVO> page = this.getPage(reviewVO);
        return page;
    }


}
