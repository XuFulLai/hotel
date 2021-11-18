package group.oneonetwo.hotelintelligencesystem.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.user.dao.UserMapper;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 文
 * @description 用户服务层实现类
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserPO add(UserVO userVO){
        if (userVO == null) {
            throw new SavaException("插入用户失败：user实体为空");
        }
        userVO.setPassword(bCryptPasswordEncoder.encode(userVO.getPassword()));
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO,userPO);
        int insert = userMapper.insert(userPO);
        if (insert > 0) {
            return userPO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public UserVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501,"id参数为空");
        }
        UserPO userPO = userMapper.selectById(id);
        UserVO userVO = new UserVO();
        if (userPO != null) {
            BeanUtils.copyProperties(userPO,userVO);
        }
        return userVO;
    }

    @Override
    public UserPO save(UserVO userVO){
        if (userVO == null) {
            throw new CommonException(501,"user实体为空");
        }
        UserVO check = selectOneByIdReturnVO(userVO.getId());
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + userVO.getId() + "'的数据");
        }
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO,userPO);
        int save = userMapper.updateById(userPO);
        if (save > 0) {
            return userMapper.selectById(userPO.getId());
        }
        throw new SavaException("更改用户失败");
    }

    @Override
    public Integer deleteById(String id) {
        UserVO check = selectOneByIdReturnVO(id);
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + id + "'的数据");
        }
        int i = userMapper.deleteById(id);
        return i;
    }

    @Override
    public Reply<UserVO> selectOneById(String id) {
        UserVO userVO = selectOneByIdReturnVO(id);
        if (userVO == null) {
            return Reply.noData();
        }
        return Reply.success(userVO);
    }

    @Override
    public UserPO findByUsername(String s) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<UserPO>();
        wrapper.eq("username",s).eq("is_deleted",0);
        UserPO userPO = userMapper.selectOne(wrapper);
        return userPO;
    }

    @Override
    public Reply register(UserVO userVO) throws SavaException {
        userVO.setReviewStatus(0);
        userVO.setPassword(bCryptPasswordEncoder.encode(userVO.getPassword()));
        UserPO add = this.add(userVO);
        BeanUtils.copyProperties(add,userVO);
        return Reply.success(userVO);
    }

    @Override
    public Reply<UserVO> update(UserVO userVO) {
        if (!"".equals(userVO.getPassword()) && userVO.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userVO.setPassword(encoder.encode(userVO.getPassword()));
        } else {
            userVO.setPassword(null);
        }
        UserPO save = save(userVO);
        BeanUtils.copyProperties(save,userVO);
        return Reply.success(userVO);
    }

    @Override
    public UserVO addOneUser(UserVO userVO) {
        UserPO add = add(userVO);
        BeanUtils.copyProperties(add,userVO);
        return userVO;
    }

    @Override
    public Page<UserVO> getPage(UserVO userVO){
        QueryWrapper<UserPO> wrapper=new QueryWrapper<>();
        Page<UserPO> page=new Page<>(userVO.getPage().getPage(),userVO.getPage().getSize());
        Page<UserPO> poiPage=(Page<UserPO>) userMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage,UserVO.class);
    }

}
