package group.oneonetwo.hotelintelligencesystem.modules.orderComment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.po.OrderCommentPO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文
* @description 针对表【order_comment】的数据库操作Mapper
* @createDate 2022-05-18 14:19:29
* @Entity group.oneonetwo.hotelintelligencesystem.modules.orderComment.model.OrderComment
*/
@Mapper
public interface OrderCommentMapper extends BaseMapper<OrderCommentPO> {


    String getAvgScore(String hotelId);
}
