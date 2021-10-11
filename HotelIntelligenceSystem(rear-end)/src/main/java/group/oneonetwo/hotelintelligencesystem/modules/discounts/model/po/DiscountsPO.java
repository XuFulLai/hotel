package group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

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
