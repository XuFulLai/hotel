package group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName("discounts")
public class DiscountsPO implements Serializable {
    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("type")
    private String type;

    @TableField("discounts")
    private String discounts;

    @TableField("effect_type")
    private Integer effectType;

    @TableField("effect_condition")
    private Integer effectCondition;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public Integer getEffectType() {
        return effectType;
    }

    public void setEffectType(Integer effectType) {
        this.effectType = effectType;
    }

    public Integer getEffectCondition() {
        return effectCondition;
    }

    public void setEffectCondition(Integer effectCondition) {
        this.effectCondition = effectCondition;
    }
}
