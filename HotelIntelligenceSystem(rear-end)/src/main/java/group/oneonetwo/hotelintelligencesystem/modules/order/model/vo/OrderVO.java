package group.oneonetwo.hotelintelligencesystem.modules.order.model.vo;

import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("订单实体")
public class OrderVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("客户id")
    private String customerId;

    @ApiModelProperty("酒店id")
    private String hotelId;

    @ApiModelProperty("房间类型")
    private String roomType;

    @ApiModelProperty("天数")
    private Integer days;

    @ApiModelProperty("折扣类型")
    private String discount;

    @ApiModelProperty("原价")
    private  String pay;

    @ApiModelProperty("实付价格")
    private String lastPay;

    @ApiModelProperty("状态:0为未支付,1为已支付,2为已关闭")
    private String status;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getLastPay() {
        return lastPay;
    }

    public void setLastPay(String lastPay) {
        this.lastPay = lastPay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    @Override
    public String toString() {
        return "OrderVO{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", days=" + days +
                ", discount='" + discount + '\'' +
                ", pay='" + pay + '\'' +
                ", lastPay='" + lastPay + '\'' +
                ", status='" + status + '\'' +
                ", province='" + province + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
