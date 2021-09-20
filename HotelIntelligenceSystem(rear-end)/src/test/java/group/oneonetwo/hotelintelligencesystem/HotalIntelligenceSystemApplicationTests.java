package group.oneonetwo.hotelintelligencesystem;

import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HotalIntelligenceSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void listConvertTest() {
        List<UserPO> userPOS = new ArrayList<>();
        List<UserVO> userVOS = new ArrayList<>();
        UserPO userPO = new UserPO();
        userPO.setId("123");
        userPO.setDept("456");
        userPOS.add(userPO);

        List<UserVO> userVOS1 = ConvertUtil.transforList(userPOS, UserVO.class);
        System.out.println(userVOS1);

    }

}
