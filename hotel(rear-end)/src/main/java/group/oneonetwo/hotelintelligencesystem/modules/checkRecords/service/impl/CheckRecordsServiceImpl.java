package group.oneonetwo.hotelintelligencesystem.modules.checkRecords.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.dao.CheckRecordsMapper;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.po.CheckRecordsPO;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsExcelTemplate;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.model.vo.CheckRecordsVO;
import group.oneonetwo.hotelintelligencesystem.modules.checkRecords.service.ICheckRecordsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 文
* @description 针对表【check_records】的数据库操作Service实现
* @createDate 2022-04-19 14:07:07
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class CheckRecordsServiceImpl implements ICheckRecordsService {

    @Autowired
    CheckRecordsMapper checkRecordsMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    EmailUtils emailUtils;

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Override
    public int upload(List<CheckRecordsExcelTemplate> list) {
        List<CheckRecordsVO> data = new ArrayList<>();
        CheckRecordsVO checkRecordsVO = null;
        CheckRecordsExcelTemplate checkRecordsExcelTemplate = null;
        for (int i = 0; i < list.size(); i++) {
            checkRecordsVO = new CheckRecordsVO();
            checkRecordsExcelTemplate = list.get(i);
            checkRecordsVO.setId(IdWorker.getIdStr());
            checkRecordsVO.setuId(checkRecordsExcelTemplate.getUId());
            checkRecordsVO.setCheckType(checkRecordsExcelTemplate.getCheckType());
            checkRecordsVO.setCheckRes(Double.valueOf(checkRecordsExcelTemplate.getCheckRes()));
            try {
                checkRecordsVO.setCheckTime(sdf.parse(checkRecordsExcelTemplate.getCheckTime()));
            } catch (ParseException e) {
                throw new CommonException("导入文件时间格式出错");
            }
            checkRecordsVO.setCreateBy(authUtils.getUid());
            checkRecordsVO.setCreateTime(new Date());
            data.add(checkRecordsVO);
        }
        int i = batchInsert(data);
        foundAbnormal(data);
        return i;
    }

    @Override
    public int batchInsert(List<CheckRecordsVO> list) {
        List<CheckRecordsPO> data = ConvertUtils.transferList(list, CheckRecordsPO.class);
        if (data.size() == 0) {
            return 0;
        }
        return checkRecordsMapper.batchInsert(data);
    }

    @Override
    public int insertManually(CheckRecordsVO checkRecordsVO) {
        CheckRecordsPO checkRecordsPO = new CheckRecordsPO();
        BeanUtils.copyProperties(checkRecordsVO,checkRecordsPO);
        int insert = checkRecordsMapper.insert(checkRecordsPO);
        List<CheckRecordsVO> list = new ArrayList();
        list.add(checkRecordsVO);
        foundAbnormal(list);
        return insert;
    }

    @Override
    public CheckRecordsVO add(CheckRecordsVO checkRecordsVO) {
        if(checkRecordsVO==null){
            throw new SavaException("插入检测信息为空:实体为空");
        }
        CheckRecordsPO checkRecordsPO = new CheckRecordsPO();
        BeanUtils.copyProperties(checkRecordsVO,checkRecordsPO);
        int insert=checkRecordsMapper.insert(checkRecordsPO);
        if(insert>0){
            return checkRecordsVO;
        }
        throw new SavaException("插入检测信息失败");

    }

    @Override
    public int deleteById(String id) {
        CheckRecordsVO check = selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=checkRecordsMapper.deleteById(id);
        return i;

    }

    @Override
    public CheckRecordsVO save(CheckRecordsVO checkRecordsVO) {
        if (checkRecordsVO==null){
            throw new CommonException(501,"检测信息实体为空");
        }
        CheckRecordsVO check = selectOneByIdReturnVO(checkRecordsVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+checkRecordsVO.getId()+"的数据");
        }
        CheckRecordsPO checkRecordsPO = new CheckRecordsPO();
        BeanUtils.copyProperties(checkRecordsVO,checkRecordsPO);
        int save=checkRecordsMapper.updateById(checkRecordsPO);
        if(save>0){
            return checkRecordsVO;
        }
        throw new SavaException("更改隔离人员信息失败");

    }

    @Override
    public Page<CheckRecordsVO> getPage(CheckRecordsVO checkRecordsVO) {
        Page<CheckRecordsVO> page=new Page<>(checkRecordsVO.getPage().getPage(),checkRecordsVO.getPage().getSize());
        return checkRecordsMapper.getPage(page,checkRecordsVO);
    }

    @Override
    public CheckRecordsVO selectOneByIdReturnVO(String id) {
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        CheckRecordsPO checkRecordsPO = checkRecordsMapper.selectById(id);
        CheckRecordsVO checkRecordsVO = new CheckRecordsVO();
        if(checkRecordsPO!=null){
            BeanUtils.copyProperties(checkRecordsPO,checkRecordsVO);
        }
        return checkRecordsVO;

    }















    private void foundAbnormal(List<CheckRecordsVO> list) {
        for (CheckRecordsVO item: list) {
            if (item.getCheckType() == 0 && item.getCheckRes() > 37.3) {
                logger.info("id为{}的体温检测异常,检测结果为{}℃",item.getuId(),item.getCheckRes());
                //获取该人邮箱和房间信息进行业务处理
                //emailUtils.sendMail("15218066874@qq.com","1243626786@qq.com",null,"这是体温检测标题","id为"+item.getuId()+"的体温检测异常,检测结果为"+item.getCheckRes()+"℃");
            }else if (item.getCheckType() == 1 && item.getCheckRes() == 1.00) {
                logger.info("id为{}的核酸检测异常,检测结果为阳性",item.getuId());
                //获取该人邮箱和房间信息进行业务处理
                //emailUtils.sendMail(null,"1499602163@qq.com",null,"这是核酸检测标题","id为"+item.getuId()+"的核酸检测异常,检测结果为+");

            }
        }
    }

}
