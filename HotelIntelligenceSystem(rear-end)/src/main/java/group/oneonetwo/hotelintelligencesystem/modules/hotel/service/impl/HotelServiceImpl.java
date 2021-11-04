package group.oneonetwo.hotelintelligencesystem.modules.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.dao.HotelMapper;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class HotelServiceImpl implements IHotelService {
    @Autowired
    HotelMapper hotelMapper;

    @Autowired
    IUserService userService;

    @Override
    public HotelVO add(HotelVO hotelVO){
        if(hotelVO==null){
            throw new SavaException("插入用户失败:酒店实体为空");
        }
        if(!"".equals(hotelVO.getDeptId())&&hotelVO.getDeptId()!=null){
            QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
            wrapper.eq("dept_id",hotelVO.getDeptId());
            List<HotelPO> hotelPOS = hotelMapper.selectList(wrapper);

            if(!hotelPOS.isEmpty()){
                throw new SavaException("该部门已被绑定,绑定的酒店为: "+hotelPOS.get(0).getName());
            }
        }

        HotelPO hotelP0=new HotelPO();
        BeanUtils.copyProperties(hotelVO,hotelP0);
        int insert=hotelMapper.insert(hotelP0);
        if(insert>0){
            return hotelVO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public HotelVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        HotelPO hotelP0=hotelMapper.selectById(id);
        HotelVO hotelVO=new HotelVO();
        if(hotelVO!=null){
            BeanUtils.copyProperties(hotelP0,hotelVO);
        }
        return hotelVO;
    }

    @Override
    public HotelVO save(HotelVO hotelVO){
        if(hotelVO==null){
            throw new CommonException(501,"hotel实体为空");
        }
        if(!"".equals(hotelVO.getDeptId())&&hotelVO.getDeptId()!=null){
            QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
            wrapper.eq("dept_id",hotelVO.getDeptId());
            List<HotelPO> hotelPOS = hotelMapper.selectList(wrapper);

            if(!hotelPOS.isEmpty()){
                throw new SavaException("该部门已被绑定,绑定的酒店为: "+hotelPOS.get(0).getName());
            }
        }
        HotelVO check=selectOneByIdReturnVO(hotelVO.getId());
        if (check==null){
            throw new CommonException(4004,"找不到id为'"+hotelVO.getId()+"'的数据");
        }
        HotelPO hotelPO =new HotelPO();
        BeanUtils.copyProperties(hotelVO, hotelPO);
        int save=hotelMapper.updateById(hotelPO);
        if(save>0){
            return hotelVO;
        }
        throw  new SavaException("更改酒店失败");
    }
    @Override
    public  Integer deleteById(String id){
        HotelVO check=selectOneByIdReturnVO(id);
        if(check==null){
            throw new CommonException(4004,"找不到id为'"+id+"'的数据");

        }
        int i= hotelMapper.deleteById(id);
        return i;
    }

    @Override
    public Page<HotelVO> getPage(HotelVO hotelVO) {
        // 构建查询条件
        QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
        if (!"".equals(hotelVO.getName()) && hotelVO.getName() != null) {
            wrapper.like("name",hotelVO.getName());
        }
        Page<HotelPO> page = new Page<>(hotelVO.getPage().getPage(), hotelVO.getPage().getSize());
        Page<HotelPO> poiPage = (Page<HotelPO>) hotelMapper.selectPage(page, wrapper);
        return ConvertUtil.transferPage(poiPage,HotelVO.class);
    }

    @Override
    public HotelVO myHotel() {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        UserVO userVO = userService.selectOneByIdReturnVO(uid);
        if (userVO == null) {
            throw new CommonException("暂无信息,请通知管理员绑定酒店");
        }
        QueryWrapper<HotelPO> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id",userVO.getDept());
        HotelPO hotelPO = hotelMapper.selectOne(wrapper);
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelPO,hotelVO);
        return hotelVO;
    }

    @Override
    public HotelPO selectOneById(String id){
        HotelPO hotelPO =hotelMapper.selectById(id);
        return hotelPO;
    }


}
