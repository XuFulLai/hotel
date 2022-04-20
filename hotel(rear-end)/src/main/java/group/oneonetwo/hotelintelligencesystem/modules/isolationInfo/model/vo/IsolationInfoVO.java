package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo;




import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableLogic;
import group.oneonetwo.hotelintelligencesystem.tools.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liustart
 * @create 2022-04-19 10:56
 */
@ApiModel("隔离人员实体")
public class IsolationInfoVO  implements Serializable {
    /**
     * 隔离人员id
     */
    @ApiModelProperty("隔离人员id")
    private String id;

    /**
     * 隔离人员姓名
     */
    @ApiModelProperty("隔离人员姓名")
    private String name;

    /**
     * 身份证
     */
    @ApiModelProperty("身份证")
    private String idCard;

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    @ApiModelProperty("隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)")
    private Integer type;

    /**
     * 隔离人员电话
     */
    @ApiModelProperty("隔离人员电话")
    private String phone;

    /**
     * 隔离隔离人员邮箱
     */
    @ApiModelProperty("隔离隔离人员邮箱")
    private String email;

    /**
     * 隔离酒店id
     */
    @ApiModelProperty("隔离酒店id")
    private String hotelId;

    /**
     * 隔离酒店房间类型
     */
    @ApiModelProperty("隔离酒店房间类型")
    private String roomType;

    /**
     * 隔离支付
     */
    @ApiModelProperty("隔离支付")
    private String pay;

    /**
     * 入住时间
     */
    @ApiModelProperty("入住时间")
    private Date checkInTime;

    /**
     * 离开时间
     */
    @ApiModelProperty("退房时间")
    private Date checkOutTime;

    /**
     * 房间id
     */

    @ApiModelProperty("人员状态(0:正在隔离;1,解除隔离;2,阳性入院)")
    private  Integer status;
    @ApiModelProperty("房间id")
    private String roomId;

    /**
     * 房间名称
     */
    @ApiModelProperty("房间名称")
    private String roomName;

    /**
     * 省份
     */
    @ApiModelProperty("省份")
    private String province;

    /**
     * 城市
     */
    @ApiModelProperty("城市")
    private String city;

    /**
     * 是否删除
     */



    @ApiModelProperty("起始时间")
    private String beginTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 隔离人员id
     *
     *
     *
     */

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    /**
     * 隔离人员id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 隔离人员姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 隔离人员姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        return "IsolationInfoVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", type=" + type +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", pay='" + pay + '\'' +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", status=" + status +
                ", roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", page=" + page +
                '}';
    }
}
