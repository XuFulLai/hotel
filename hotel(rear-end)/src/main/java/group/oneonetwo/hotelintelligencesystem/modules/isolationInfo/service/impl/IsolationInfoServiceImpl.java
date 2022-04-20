package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.IsolationInfoService;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.dao.IsolationInfoMapper;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.po.OrderPO;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
