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
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        if (uid == null) {
            uid = "0";
        }
        this.setFieldValByName("createBy", uid, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        if (uid == null) {
            uid = "0";
        }
        this.setFieldValByName("updateBy", uid, metaObject);
    }
}
