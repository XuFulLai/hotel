package group.oneonetwo.hotelintelligencesystem.modules.wallet.controller;

import group.oneonetwo.hotelintelligencesystem.modules.wallet.model.vo.WalletVO;
import group.oneonetwo.hotelintelligencesystem.modules.wallet.service.WalletService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 文
 * @description: 钱包控制层
 * @date 2022/4/18 14:53
 */
@Api(tags = "钱包类型相关接口")
@RestController
@RequestMapping("api/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @ApiOperation("查询钱包")
    @PostMapping("get")
    public Reply<WalletVO> getWallet(String pwd) {
        return Reply.success(walletService.getWallet(pwd));
    }

    @ApiOperation("修改密码")
    @PostMapping("editPwd")
    public Reply editPwd(String oldPwd,String newPwd) {
        return walletService.editPwd(oldPwd,newPwd);
    }

}
