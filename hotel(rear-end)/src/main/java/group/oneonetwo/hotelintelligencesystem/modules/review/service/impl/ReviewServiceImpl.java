package group.oneonetwo.hotelintelligencesystem.modules.review.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.Review;
import group.oneonetwo.hotelintelligencesystem.modules.review.service.ReviewService;
import group.oneonetwo.hotelintelligencesystem.modules.review.dao.ReviewMapper;
import org.springframework.stereotype.Service;

/**
* @author 文
* @description 针对表【review】的数据库操作Service实现
* @createDate 2022-04-19 10:40:37
*/
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
implements ReviewService{

}
