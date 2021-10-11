package group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("折扣实体")
public class DiscountsVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("折扣名称")
    private String name;

    @ApiModelProperty("折扣描述")
    private String  description;

    @ApiModelProperty("折扣类型")
    private String type;

    @ApiModelProperty("折扣幅度")
    private String discounts;

    @ApiModelProperty("生效条件类型(0:满足xx天,1:提前xx天,2:会员卡)")
    private Integer effectType;

    @ApiModelProperty("生效条件(天数)")
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

    @Override
    public String toString(){
        return "DiscountsVO{"+
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", discounts='" + discounts + '\'' +
                ", effectType=" + effectType +
                ", effectCondition=" + effectCondition  +
                '}';
    }
}
