package group.oneonetwo.hotelintelligencesystem.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 文
 * @description 对象自动填充操作类（对创建人、创建时间、更新人、更新时间作填充）
 */
@Component
public class ObjAutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String s = authentication.getName();
//        System.out.println("authentication.getAuthorities()=====================" + s);
//        this.setFieldValByName("createBy", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
