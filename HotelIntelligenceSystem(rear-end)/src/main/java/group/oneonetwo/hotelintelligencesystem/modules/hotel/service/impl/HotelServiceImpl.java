package group.oneonetwo.hotelintelligencesystem.modules.hotel.service.impl;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.dao.HotelMapper;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po.HotelPO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class HotelServiceImpl implements IHotelService {
    @Autowired
    HotelMapper hotelMapper;

    @Override
    public HotelVO add(HotelVO hotelVO){
        if(hotelVO==null){
            throw new SavaException("插入用户失败:酒店实体为空");
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
    public HotelPO selectOneById(String id){
        HotelPO hotelPO =hotelMapper.selectById(id);
        return hotelPO;
    }

}
