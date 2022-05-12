package group.oneonetwo.hotelintelligencesystem.enums;

/**
 * @author 文
 * @description: 折扣相关枚举类
 * @date 2022-05-07 16:26:55
 */
public enum DiscountEnums {
    //type
    TYPE_HOTEL(0),
    TYPE_PERSONAL_HOTEL(1),
    TYPE_PERSONAL_ALL(2),
    //effect_type
    EFFECT_TYPE_DAYS(0),
    EFFECT_TYPE_MONEY(1),
    EFFECT_TYPE_ALL(2),
    //discounts_type
    DISCOUNTS_TYPE_REDUCTION(0),
    DISCOUNTS_TYPE_DISCOUNT(0)
    ;

    private Integer code;

    DiscountEnums(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
