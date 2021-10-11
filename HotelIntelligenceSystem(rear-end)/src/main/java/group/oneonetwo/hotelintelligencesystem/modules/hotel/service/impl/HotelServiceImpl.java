package group.oneonetwo.hotelintelligencesystem.modules.hotel.service.impl;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.dept.dao.DeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.dao.HotelMapper;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelP0;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.authentication.AnonymousAuthenticationWebFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class HotelServiceImpl implements IHotelService {
    @Autowired
    HotelMapper hotelMapper;

    @Override
    public HotelP0 add(HotelVO hotelVO){
        if(hotelVO==null){
            throw new SavaException("插入用户失败:酒店实体为空");
        }
        HotelP0 hotelP0=new HotelP0();
        BeanUtils.copyProperties(hotelVO,hotelP0);
        int insert=hotelMapper.insert(hotelP0);
        if(insert>0){
            return hotelP0;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public HotelVO selectOneByIdReturnVO(String id){
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        HotelP0 hotelP0=hotelMapper.selectById(id);
        HotelVO hotelVO=new HotelVO();
        if(hotelVO!=null){
            BeanUtils.copyProperties(hotelP0,hotelVO);
        }
        return hotelVO;
    }

    @Override
    public HotelP0 save(HotelVO hotelVO){
        if(hotelVO==null){
            throw new CommonException(501,"hotel实体为空");
        }
        HotelVO check=selectOneByIdReturnVO(hotelVO.getId());
        if (check==null){
            throw new CommonException(4004,"找不到id为'"+hotelVO.getId()+"'的数据");
        }
        HotelP0 hotelP0=new HotelP0();
        BeanUtils.copyProperties(hotelVO,hotelP0);
        int save=hotelMapper.updateById(hotelP0);
        if(save>0){
            return hotelMapper.selectById(hotelP0.getId());
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
    public HotelP0 selectOneById(String id){
        HotelP0 hotelP0=hotelMapper.selectById(id);
        return hotelP0;
    }

}
