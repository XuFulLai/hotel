package group.oneonetwo.hotelintelligencesystem.modules.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.dao.WalletMapper;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.po.WalletPO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.vo.WalletVO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.service.WalletService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 文
* @description 针对表【wallet】的数据库操作Service实现
* @createDate 2022-04-18 14:38:01
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletMapper walletMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthUtils authUtils;

    @Override
    public WalletVO getWallet(String pwd) {
        String uid = authUtils.getUid();
        QueryWrapper<WalletPO> wrapper = new QueryWrapper<WalletPO>();
        wrapper.eq("u_id",uid);
        WalletPO walletPO = walletMapper.selectOne(wrapper);
        if (walletPO == null) {
            walletPO = createAccount(uid,pwd);
        }else {
            boolean matches = bCryptPasswordEncoder.matches(pwd, walletPO.getPassword());
            if (!matches) {
                throw new CommonException("账户密码错误");
            }
        }
        WalletVO walletVO = new WalletVO();
        BeanUtils.copyProperties(walletPO,walletVO);
        return walletVO;
    }

    @Override
    public Reply editPwd(String oldPwd, String newPwd) {
        String uid = authUtils.getUid();
        QueryWrapper<WalletPO> wrapper = new QueryWrapper<WalletPO>();
        wrapper.eq("u_id",uid);
        WalletPO walletPO = walletMapper.selectOne(wrapper);
        boolean matches = bCryptPasswordEncoder.matches(oldPwd, walletPO.getPassword());
        if (matches) {
            walletPO.setPassword(bCryptPasswordEncoder.encode(newPwd));
            int i = walletMapper.updateById(walletPO);
            if (i == 0) {
                throw new CommonException("密码更新异常");
            }
        }else {
            throw new CommonException("账户密码错误");
        }
        return Reply.success();
    }

    private WalletPO createAccount(String uid,String pwd) {
        WalletPO walletPO = new WalletPO();
        walletPO.setPassword(bCryptPasswordEncoder.encode(pwd));
        walletPO.setuId(uid);
        int insert = walletMapper.insert(walletPO);
        if (insert == 0) {
            throw new CommonException("创建账户失败");
        }
        walletPO.setBalance(0.00);
        return walletPO;

    }
}
