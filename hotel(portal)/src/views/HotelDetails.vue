<template>
  <div class="big-body">
    <!-- 导航栏 -->
    <top-nav></top-nav>
    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="">
      <!--      <h3>{{ $t('hotelList.title') }}</h3>-->
    </div>
    <div class="hotel-list-main">


      <div class="big-box">
<!--        <el-button @click="confirmOrder"></el-button>-->
        <!--        <p>酒店id：{{ hotelDetails.hotleId }}</p>-->
        <div class="hotel-title">
          {{ hotelDetails.name }}
        </div>

        <div class="hotel-detail">
          <div class="detail-left">
            <div class="detail-detail">
              <div class="flex flex-row" v-if="hotelDetails.badge || hotelDetails.allowIsolation">
                <div style="background: #F56C6C" class="badge" v-if="hotelDetails.allowIsolation">
                  隔离酒店
                </div>
                <div v-if="hotelDetails.badge" class="badge" v-for="i in hotelDetails.badge.split(',')">
                  {{ i }}
                </div>
              </div>
              <div class="flex flex-row flex-n-wrap">
                <div class="flex-column address-detail">
                  <p>{{ hotelDetails.province + " " + hotelDetails.city }}</p>
                  <p>{{ hotelDetails.address }}</p>
                </div>
                <div class="flex justify-content-end align-items-center" style="width: 50%">
                  <i :class="isCollection ? 'el-icon-star-on' : 'el-icon-star-off'" class="location"
                     @click="collection()"></i>
                  <i class="el-icon-map-location location" @click="toMap()"></i>
                </div>
              </div>
              <el-divider content-position="left">酒店介绍</el-divider>
              <div class="detail-introduce" v-html="hotelDetails.introduce"></div>
            </div>

            <div class="detail-block" v-if="hotelDiscounts != null && hotelDiscounts.length !== 0">
              <h3 class="sub-title">
                酒店优惠
                <span class="en">discount</span>
              </h3>

              <div class="detail-content flex flex-row flex-wrap" style="margin: 0;padding: 0">
                <div class="d-flex flex-row flex-wrap">
                  <div @click="gotCoupon(i.id)" class="discounts-box" v-for="i in hotelDiscounts">
                    <div class="discounts-title flex flex-row justify-content-between" :class="[i.isGot?'active':'', isGot?'active':'']">
                      <p style="margin-left: 6px;font-size: 14px;font-weight: 700">{{ i.name }}</p>
                      <el-tooltip placement="right" style="margin: 4px">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 8px;margin-left: 10px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div style="border-top: 1px dotted #999;"></div>
                    <div class="discounts-body flex flex-column" :class="[i.isGot?'active':'', isGot?'active':'']">
                      <div class="discounts-body-top flex flex-row align-items-end">
                        <div class="discounts-body-price">
                          {{ i.discountsType == 0 ? '￥' + i.discounts : i.discounts * 10 + '折' }}
                        </div>
                        <div class="discounts-body-condition" v-if="i.effectType == 0">
                          {{ '[满' + i.effectCondition + '天可用]' }}
                        </div>
                        <div class="discounts-body-condition" v-if="i.effectType == 1">
                          {{ '[满' + i.effectCondition + '元可用]' }}
                        </div>
                        <div class="discounts-body-condition" v-if="i.effectType == 2">[无门槛使用]</div>
                      </div>
                      <div class="discounts-body-bottom">
                        有效期到 {{ dateTimeFormat(i.validityTime) }}
                      </div>

                    </div>
                  </div>                  
                
                </div>
                <div class="d-flex align-items-center">
                  <el-button type="success">展开按钮</el-button>
                </div>
              </div>
            </div>

            <div class="detail-block">
              <h3 class="sub-title">
                酒店图片
                <span class="en">picture</span>
              </h3>

              <div class="detail-content" style="margin: 0;padding: 0">
                <el-image
                    style="width: 100%; height: 100%"
                    :src="hotelDetails.cover"
                    fit="fit"></el-image>
              </div>
            </div>

            <div class="detail-block">
              <h3 class="sub-title">
                设施服务
                <span class="en">AMENITIES</span>
              </h3>

              <div class="detail-content">
                <div v-html="hotelDetails.facilities"></div>
              </div>
            </div>


            <div class="detail-block">
              <h3 class="sub-title">
                其他政策
                <span class="en">NOTICE</span>
              </h3>

              <div class="detail-content">
                <div v-html="hotelDetails.otherPolicy"></div>
              </div>
            </div>
          </div>
          <div class="detail-right">
            <div class="detail-right-price" id="detailRight"
                 :style="isFixed ? 'position:fixed;top:90px;width: 440px;' : ''">
              <div class="book-box">

                <el-switch
                    style="display: block;margin: 20px 10px;"
                    v-model="switchType"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    active-text="正常预定"
                    inactive-text="自申报">
                </el-switch>

                <!-- 正常预定模块 -->
                <div v-show="switchType == true" class="natural">
                  <span class="price-sum">
                    <span>
                      <span class="unit">¥</span>
                      <span class="zg-price">
                        <strong>
                          <span v-if="!bookDay || !totalFee">{{ minFee + '~' + maxFee }}</span>
                          <span v-else>{{ totalFee }}</span>
                        </strong>
                      </span>
                    </span>
                    <!--                  <small class="text-weak">/晚</small>-->
                  </span>
                  <div class="book-date flex flex-row align-items-center">
                    <el-date-picker
                        v-model="dateValue"
                        type="daterange"
                        value-format="yyyy-MM-dd"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                    <p v-if="bookDay" style="margin: 2px">共{{ bookDay }}晚</p>
                  </div>
                  <div class="book-room">
                    <el-select v-model="currentRoomType" :placeholder="$t('hotelList.selectRoom')">
                      <el-option
                          v-for="item in roomTypeList"
                          :label="item.name"
                          :value="item.id">
                        <span style="float: left">{{ item.name }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">￥{{ item.fee }}</span>
                      </el-option>
                    </el-select>
                    <div class="room-detail" v-if="currentRoomType">
                      <i class="el-icon-files"> 床位数:{{ roomTypeMap[currentRoomType].bed }}</i><br>
                      <i class="el-icon-user"> 最大容纳:{{ roomTypeMap[currentRoomType].maxLoad }}</i><br>
                      <i class="el-icon-picture-outline"> 是否有窗:{{
                          roomTypeMap[currentRoomType].haveWindow == 1 ? "是" : "否"
                        }}</i><br>
                      <i class="el-icon-fork-spoon"> 是否含早餐:{{
                          roomTypeMap[currentRoomType].haveBreakfast == 1 ? "是" : "否"
                        }}</i><br>
                      <i class="el-icon-magic-stick"> 是否有网络:{{
                          roomTypeMap[currentRoomType].network == 1 ? "是" : "否"
                        }}</i>
                    </div>
                    <div class="mt-10">
                      <el-select v-model="provinceVal" :placeholder="$t('hotelList.province')">
                        <el-option
                            v-for="item in options"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                      </el-select>
                    </div>
                    <div>
                      <el-button @click="confirmOrderHandle" type="primary" style="width: 100%;margin: 10px 0">预订</el-button>
                    </div>
                  </div>
                </div>

                <!-- 自申报 -->
                <div v-show="switchType == false" class="abnormal">

                  <span v-show="situation == 2 || situation == 3" class="price-sum mb-10">
                    <span>
                      <span class="unit">¥</span>
                      <span class="zg-price">
                        <strong>

                          <!-- <span v-if="!bookDay || !totalFee">{{ minFee + '~' + maxFee }}</span> -->
                          <span>{{ isolateFee }}</span>
                        </strong>
                      </span>
                    </span>
                  </span>

                  <div class="block mb-10">
                    <el-date-picker
                        v-model="date"
                        type="date"
                        placeholder="入住时间"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                  </div>

                  <!-- 申报类型 -->
                  <el-select class="mb-10" v-model="situation" placeholder="申报类型">
                    <el-option
                        v-for="item in situationOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                  </el-select>

                  <!-- 选择房型 -->
                  <el-select v-show="situation == 2 || situation == 3" class="mb-10" v-model="isolateRoomType"
                             :placeholder="$t('hotelList.selectRoom')">
                    <el-option
                        v-for="item in isolateRoomTypeList"
                        :label="item.name"
                        :value="item.id">
                      <span style="float: left">{{ item.name }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">￥{{ item.isolationFee }}</span>
                    </el-option>
                  </el-select>

                  <el-input class="mb-10" v-model="userName" placeholder="姓名"></el-input>

                  <el-input class="mb-10" v-model="userId" placeholder="身份证"></el-input>

                  <el-input class="mb-10" v-model="phoneNum" placeholder="手机号"></el-input>

                  <el-input class="mb-10" v-model="userEmail" placeholder="邮箱"></el-input>

                  <!-- 陪同人员 start -->
                  <!-- <div class="mb-10">
                    <el-switch                    
                      active-text="是否需要陪同人员"
                      v-model="accompany"
                      active-color="#13ce66"
                      inactive-color="#ff4949">
                    </el-switch>

                    <el-tooltip placement="right">
                      <div slot="content">根据规定，<br/>14岁以下儿童，<br/>岁以上老人可申请陪同</div>        
                      <el-button style="border-radius: 50%;padding: 5px 8px;margin-left: 10px;">?</el-button>
                    </el-tooltip>                    
      
                  </div>               

                  <el-input v-show="accompany == true" class="mb-10" v-model="accompanyName" placeholder="陪同人员姓名"></el-input>

                  <el-input v-show="accompany == true" class="mb-10" v-model="accompanyId" placeholder="陪同人员身份证"></el-input> -->
                  <!-- 陪同人员 end -->

                  <div>
                    <el-cascader
                        class="mb-10"
                        size="large"
                        :options="addressData"
                        :v-model="area"
                        @change="getAddress"
                    ></el-cascader>
                  </div>

                  <p class="mb-10 font-16"><i class="el-icon-warning-outline mr-5"></i>自行申报需提前48小时申报。</p>

                  <el-button class="w-percent-100" @click="submit" type="primary">提交申报</el-button>


                </div>

              </div>
            </div>
          </div>
        </div>

      </div>
      <Footer></Footer>

    </div>
    <el-dialog
        title="确认订单"
        :visible.sync="confirmOrderVisible"
        width="460px"
        >
      <div class="order-box flex flex-column">
        <div class="order-hotel-detail-box flex flex-row">
          <el-image
              style="width: 150px; height: 100px"
              :src="hotelDetails.cover"
              fit="fit"></el-image>
          <div class="order-hotel-detail-detail flex flex-column">
            <p class="mb-5" style="font-weight: 700;font-size: 16px">{{ hotelDetails.name }}</p>
            <p class="mt-5 mb-5">{{ roomTypeMap[currentRoomType] ? roomTypeMap[currentRoomType].name : '' }}</p>
            <p class="mb-5">{{ dateValue[0] +  ' -- ' + dateValue[1] + ', 共' + bookDay + '晚' }}</p>
            <p>{{ '来自: ' + provinceVal }}</p>
          </div>
        </div>

        <div class="mt-5 mb-5" style="border-top: 1px dashed #999;width: 100%"></div>

        <div v-if="confirmOrderData.hotelDiscountList.length != 0 || confirmOrderData.personalDiscountList.length != 0" class="order-choose-discount flex flex-column">
          <el-collapse>
            <el-collapse-item v-if="confirmOrderData.hotelDiscountList.length != 0" title="酒店优惠券" name="1">
              <div class="choose-hotel-discount">
                <div class="choose-discount-box flex flex-row" v-for="i in confirmOrderData.hotelDiscountList" @click="addUseDiscount(i.id)">
                  <div :class="confirmOrderData.useDiscountMap[i.id].use ? 'choose-discount-chosen' : ''" class="choose-discount-left flex flex-row justify-content-between align-items-end">
                    <div class="choose-discount-name">
                      {{ i.name }}
                      <el-tooltip placement="right" style="margin: 4px 4px 4px 0px;">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 7px;margin-left: 0px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div class="choose-discount-date">
                      有效期至 {{ dateTimeFormat(i.validityTime) }}
                    </div>
                  </div>
                  <div style="border-left: 1px dashed #999;height: 100%"></div>
                  <div :class="confirmOrderData.useDiscountMap[i].use ? 'choose-discount-chosen' : ''" class="choose-discount-right flex align-items-center justify-content-center">
                    {{ i.discountsType == 0 ?  i.discounts +'元' : i.discounts * 10 + '折' }}
                  </div>
                </div>
              </div>
            </el-collapse-item>

            <el-collapse-item v-if="confirmOrderData.personalDiscountList.length != 0" title="个人优惠券" name="1">
              <div class="choose-hotel-discount">
                <div class="choose-discount-box flex flex-row cursor" v-for="i in confirmOrderData.personalDiscountList" @click="addUseDiscount(i)">
                  <div :class="confirmOrderData.useDiscountMap.has(i.id) ? 'choose-discount-chosen' : ''" class="choose-discount-left flex flex-row justify-content-between align-items-end">
                    <div class="choose-discount-name">
                      {{ i.name }}
                      <el-tooltip placement="right" style="margin: 4px 4px 4px 0px;">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 7px;margin-left: 0px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div class="choose-discount-date">
                      有效期至 {{ dateTimeFormat(i.validityTime) }}
                    </div>
                  </div>
                  <div style="border-left: 1px dashed #999;height: 100%"></div>
                  <div :class="confirmOrderData.useDiscountMap.has(i.id) ? 'choose-discount-chosen' : ''" class="choose-discount-right flex align-items-center justify-content-center">
                    {{ i.discountsType == 0 ?  i.discounts +'元' : i.discounts * 10 + '折' }}
                  </div>
                </div>
              </div>
            </el-collapse-item>

          </el-collapse>

          <div class="mt-5 mb-5" style="border-top: 1px dashed #999;width: 100%"></div>


        </div>
        <div class="order-count">
          <div class="order-count-item">
            <div>房间单价</div>
            <div>￥{{ this.confirmOrderData.totalFee / bookDay }}</div>
          </div>
          <div class="order-count-item">
            <div>订单总价</div>
            <div>￥{{ this.confirmOrderData.totalFee }}</div>
          </div>
          <div class="order-count-item" v-for="i in discountVisibleList">
            <div>{{ i.name }}</div>
            <div>-￥{{ i.price }}</div>
          </div>
          <div class="order-count-item" style="font-size: 18px;color: black">
            <div>预计实付款</div>
            <div style="color: #ff4d6a;">￥{{ this.confirmOrderData.totalFee - this.confirmOrderData.discountFee }}</div>
          </div>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="confirmOrderVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
        title="请输入钱包密码"
        :visible.sync="payVisible"
        width="460px"
        center
    >
      <div class="flex flex-column pay-box align-items-center">
        <div>当前支付金额</div>
        <div class="pay-box-price"><span style="font-size: 26px">￥</span>{{ payForm.lastPay }}</div>
        <el-input placeholder="请输入密码" v-model="payForm.pwd" show-password></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="pay">确 定</el-button>
      </span>

    </el-dialog>


  </div>
</template>

<script>

import TopNav from '../components/TopNav'
import Footer from '../components/Footer.vue';
import {formDataPost, get, post} from "../utils/request";
import {dateTimeFormat} from "../utils/format";
import {CodeToText, provinceAndCityData} from 'element-china-area-data'

export default {
  name: "HotelDetails",
  components: {
    TopNav,
    Footer
  },
  data() {
    return {
      form: {},
      accompany: false,
      date: '',
      accompanyName: '',
      accompanyId: '',
      userName: '',
      userId: '',
      phoneNum: '',
      userEmail: '',
      addressData: provinceAndCityData,
      area: [],
      situation: '',
      isGot: false,
      situationOptions: [{
        value: 0,
        label: '密接'
      }, {
        value: 1,
        label: '应隔尽隔人员'
      }, {
        value: 2,
        label: '入境人员'
      }, {
        value: 3,
        label: '中高风险地区人员'
      }],
      payVisible: false,
      confirmOrderVisible: false,
      payForm: {
        orderId: undefined,
        lastPay: undefined,
        walletPwd: undefined
      },
      confirmOrderData: {
        totalFee: undefined,
        discountFee: undefined,
        hotelDiscountList: [],
        personalDiscountList:[],
        useDiscountMap: {},
      },
      discountVisibleList:[],
      switchType: true,
      hotelId: '',
      isCollection: false,
      hotelDetails: '',
      roomTypeList: [],
      isolateRoomTypeList: [],
      hotelDiscounts: [],
      isolateRoomType: '',
      isolateFee: '',
      roomTypeMap: {},
      currentRoomType: '',
      dateValue: '',
      bookDay: 0,
      maxFee: 0,
      minFee: 0,
      totalFee: 0,
      provinceVal: '',
      options: [
        {
          value: this.$t('hotelList.beijing'),
          label: this.$t('hotelList.beijing')
        }, {
          value: this.$t('hotelList.shanghai'),
          label: this.$t('hotelList.shanghai')
        }, {
          value: this.$t('hotelList.tianjin'),
          label: this.$t('hotelList.tianjin')
        }, {
          value: this.$t('hotelList.chongqing'),
          label: this.$t('hotelList.chongqing')
        }, {
          value: this.$t('hotelList.hebei'),
          label: this.$t('hotelList.hebei')
        }, {
          value: this.$t('hotelList.shanxi'),
          label: this.$t('hotelList.shanxi')
        }, {
          value: this.$t('hotelList.neimenggu'),
          label: this.$t('hotelList.neimenggu')
        }, {
          value: this.$t('hotelList.liaoning'),
          label: this.$t('hotelList.liaoning')
        }, {
          value: this.$t('hotelList.jilin'),
          label: this.$t('hotelList.jilin')
        }, {
          value: this.$t('hotelList.heilongjiang'),
          label: this.$t('hotelList.heilongjiang')
        }, {
          value: this.$t('hotelList.jiangsu'),
          label: this.$t('hotelList.jiangsu')
        }, {
          value: this.$t('hotelList.zhejiang'),
          label: this.$t('hotelList.zhejiang')
        }, {
          value: this.$t('hotelList.anhui'),
          label: this.$t('hotelList.anhui')
        }, {
          value: this.$t('hotelList.fujian'),
          label: this.$t('hotelList.fujian')
        }, {
          value: this.$t('hotelList.jiangxi'),
          label: this.$t('hotelList.jiangxi')
        }, {
          value: this.$t('hotelList.shandong'),
          label: this.$t('hotelList.shandong')
        }, {
          value: this.$t('hotelList.henan'),
          label: this.$t('hotelList.henan')
        }, {
          value: this.$t('hotelList.hubei'),
          label: this.$t('hotelList.hubei')
        }, {
          value: this.$t('hotelList.hunan'),
          label: this.$t('hotelList.hunan')
        }, {
          value: this.$t('hotelList.guangdong'),
          label: this.$t('hotelList.guangdong')
        }, {
          value: this.$t('hotelList.guangxi'),
          label: this.$t('hotelList.guangxi')
        }, {
          value: this.$t('hotelList.hainan'),
          label: this.$t('hotelList.hainan')
        }, {
          value: this.$t('hotelList.sichuan'),
          label: this.$t('hotelList.sichuan')
        }, {
          value: this.$t('hotelList.guizhou'),
          label: this.$t('hotelList.guizhou')
        }, {
          value: this.$t('hotelList.yunnan'),
          label: this.$t('hotelList.yunnan')
        }, {
          value: this.$t('hotelList.xizang'),
          label: this.$t('hotelList.xizang')
        }, {
          value: this.$t('hotelList.shanxi'),
          label: this.$t('hotelList.shanxi')
        }, {
          value: this.$t('hotelList.gansu'),
          label: this.$t('hotelList.gansu')
        }, {
          value: this.$t('hotelList.ningxia'),
          label: this.$t('hotelList.ningxia')
        }, {
          value: this.$t('hotelList.qinghai'),
          label: this.$t('hotelList.qinghai')
        }, {
          value: this.$t('hotelList.xinjiang'),
          label: this.$t('hotelList.xinjiang')
        }, {
          value: this.$t('hotelList.taiwan'),
          label: this.$t('hotelList.taiwan')
        }, {
          value: this.$t('hotelList.xianggang'),
          label: this.$t('hotelList.xianggang')
        }, {
          value: this.$t('hotelList.aomen'),
          label: this.$t('hotelList.aomen')
        }],
      isFixed: false,
      offsetTop: 0,
      pickerOptions: {
        disabledDate(v) {
          return v.getTime() < new Date().getTime() - 86400000;
        }
      }

    }
  },
  watch: {
    "dateValue"(val, oldVal) {//普通的watch监听
      // console.log("a: "+val, oldVal);
      this.bookDay = 0
      this.totalFee = 0
      if (val) {
        this.updateFee()
      }
    },
    "currentRoomType"(val, oldVal) {//普通的watch监听
      // console.log("a: "+val, oldVal);
      this.bookDay = 0
      this.totalFee = 0
      if (val) {
        this.updateFee()
      }
    },
    // "date"(val, oldVal) {
    //   this.isolateFee = 0
    //   if (val) {
    //     this.isolateFeeF()
    //   }
    // },
    "isolateRoomType"(val, oldVal) {
      console.log(val);
      console.log(oldVal);
      if (val) {
        this.isolateFeeF()
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      //获取对象相对于版面或由 offsetTop 属性指定的父坐标的计算顶端位置
      this.offsetTop = document.querySelector('#detailRight').offsetTop;
    })
    window.addEventListener("scroll", this.handleScroll, true);
    this.hotelId = this.$route.params.hotelId
    this.getHotelDetails()
    this.getRoomType();
    this.getIsolateRoomTypeList()
    this.getCollectionStatus()
    this.getHotelDiscountList()
  },
  methods: {
    updateDiscountVisible() {
      this.discountVisibleList = []
      this.confirmOrderData.discountFee = 0
      this.getCurrentCanUseDiscount()
      for (let val of this.confirmOrderData.useDiscountMap.values()) {
        let map = {
          name: val.name + '[优惠券]',
          price: 0
        }
        if (val.discountsType == 0) {
          this.confirmOrderData.discountFee += val.discounts - 0
          map.price = val.discounts
        }else if (val.discountsType == 1) {
          this.confirmOrderData.discountFee += this.confirmOrderData.totalFee*(1-val.discounts) - 0;
          map.price = this.confirmOrderData.totalFee*(1-val.discounts)
        }
        console.log(this.confirmOrderData.discountFee)
        this.discountVisibleList.push(map)
      }
    },
    confirmOrderHandle() {
      this.confirmOrderData.discountFee = 0
      this.confirmOrderData.totalFee = this.roomTypeMap[this.currentRoomType].fee * this.bookDay
      this.confirmOrderData.useDiscountMap = new Map();
      this.discountVisibleList = []
      this.getCurrentCanUseDiscount()
    },

    map2String(map) {
      let str = ""
      let size = map.size
      if (size != 0) {
        let currentNum = 1;
        for (let key of map.keys()) {
          str += key
          if (currentNum != size) {
            str += ','
          }
          currentNum++
        }
      }
      return str
    },
    getCurrentCanUseDiscount() {
      let currentUse = this.map2String(this.confirmOrderData.useDiscountMap)

      let data = {
        hotelId: this.hotelId,
        orderFee: this.confirmOrderData.totalFee,
        bookDay: this.bookDay,
        currentUse: currentUse
      }
      post("/api/discounts/list/personal/got/",data).then(res => {
        if (res.data.code == 200) {
          this.confirmOrderData.personalDiscountList = res.data.data
          // for (var index in this.confirmOrderData.personalDiscountList) {
          //   let map = {
          //     obj: this.confirmOrderData.personalDiscountList[index],
          //     use: false
          //   }
          //   this.confirmOrderData.useDiscountMap[this.confirmOrderData.personalDiscountList[index].id] = map;
          // }
          // console.log("map",this.confirmOrderData.useDiscountMap)
          this.confirmOrderVisible = true
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },

    gotCoupon(id) {
      let data = {
        discountsId: id
      }
      formDataPost("/api/discountsUser/gotCoupon",data).then(res => {
        if (res.data.code == 200) {
          this.$message({
            message: this.$t('common.success'),
            type: 'success'
          });
          this.isGot = true
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },

    dateTimeFormat(val) {
      return dateTimeFormat(val)
    },

    getHotelDiscountList() {
      get("/api/discounts/list/personal/" + this.hotelId).then(res => {
        console.log(res.data);
        if (res.data.code == 200) {
          this.hotelDiscounts = res.data.data
        }
      })
    },

    getCollectionStatus() {
      get('/api/collection/isCollection/' + this.hotelId).then(res => {
        if (res.data.code == 200) {
          this.isCollection = res.data.data
        }
      })
    },

    // 获取酒店隔离房间类型函数
    getIsolateRoomTypeList() {
      const data = {
        isIsolation: 1
      }
      get(`api/roomType/currentRoomTypeList/${this.hotelId}`, data)
          .then(res => {
            console.log(res);
            console.log(res.data.data);
            this.isolateRoomTypeList = res.data.data

          })
          .catch(err => {
            console.error(err);
          })
    },
    // 提交审核按钮
    submit() {
      const data = {
        checkInTime: this.date,
        checkOutTime: new Date(Date.parse(this.date) + 1209600000), // 14天之后的时间
        type: this.situation,
        name: this.userName,
        idCard: this.userId,
        phone: this.phoneNum,
        email: this.userEmail,
        province: this.area[0],
        city: this.area[1],
        hotelId: this.hotelId,
        roomType: this.isolateRoomType

      }
      console.log(data);
      post('/api/review/check', data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: this.$t('common.success'),
                type: 'success'
              });
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(err => {
            console.error(err);
          })

    },
    getAddress(value) { //value是长度为2的装有被选择省、市代码的数组;CodeToText是个对象，键名为代码，键值为省和城市
      this.area = [];
      for (let i = 0; i < value.length; i++) {
        let code = value[i];
        this.area.push(CodeToText[code]);
      }
      this.form.province = this.area[0]
      this.form.city = this.area[1]
      console.log(this.area); //["河北省","唐山市"]
    },
    updateFee() {
      this.countTime()
      this.totalFee = this.bookDay * this.roomTypeMap[this.currentRoomType].fee
    },
    isolateFeeF() {
      // this.countTime()
      this.isolateFee = 14 * this.roomTypeMap[this.isolateRoomType].isolationFee
    },
    countTime() {
      let estimatedCheckIn = new Date(this.dateValue[0])
      let estimatedCheckOut = new Date(this.dateValue[1])
      this.bookDay = parseInt(Math.abs(estimatedCheckIn - estimatedCheckOut) / 1000 / 60 / 60 / 24)
      // console.log("dateDiff-----------",this.bookDay)
    },

    handleScroll() {
      let scrollTop =
          document.documentElement.scrollTop || document.body.scrollTop;
      this.isFixed = scrollTop > this.offsetTop * 1.15 ? true : false;
      // console.log(this.offsetTop)
      // console.log(this.isFixed)
      // console.log(scrollTop);
    },

    addUseDiscount(val) {
      console.log("this.confirmOrderData.useDiscountMap.has(val.id)",this.confirmOrderData.useDiscountMap.has(val.id))
      if (this.confirmOrderData.useDiscountMap.has(val.id)) {
        this.confirmOrderData.useDiscountMap.delete(val.id)
      }else {
        this.confirmOrderData.useDiscountMap.set(val.id,val)
      }
      this.updateDiscountVisible()
      console.log(this.confirmOrderData.useDiscountMap)

    },

    collection() {
      let data = {
        id: this.hotelId
      }
      formDataPost("/api/collection/collection", data).then(res => {
        if (res.data.code == 200) {
          this.isCollection = !this.isCollection
          this.$message({
            message: res.data.data,
            type: 'success'
          });
        } else {

        }
      })
    },

    toMap() {
      let that = this;
      window.open("https://ditu.amap.com/search?query=" + that.hotelDetails.address)
    },

    getRoomType() {
      //this.hotelId = this.$route.params.hotelId
      get('/api/roomType/currentRoomTypeList/' + this.hotelId)
          .then(res => {
            console.log(res);
            //this.dialogVisible = true
            this.roomTypeList = res.data.data
            var map = {};
            let min = 0;
            let max = 0;
            for (var index in this.roomTypeList) {
              let fee = this.roomTypeList[index].fee;
              if (fee < min) {
                min = fee
              } else if (fee > max) {
                max = fee
              }
              map[this.roomTypeList[index].id] = this.roomTypeList[index];
            }
            this.roomTypeMap = map
            this.maxFee = max
            this.minFee = min
            console.log(this.maxFee)
          })
          .catch(err => {
            console.log(err);
          })
    },
    pay() {
      let data = {
        orderId: this.payForm.orderId,
        walletPwd: this.payForm.walletPwd
      }
      formDataPost("/api/order/pay",data).then(res => {
        if (res.data.code == 200) {
          this.$message({
            message: this.$t('hotelList.success'),
            type: 'success'
          });
          this.payVisible = false
          this.confirmOrderVisible = false
          this.payForm = {
            orderId: undefined,
            lastPay: undefined,
            walletPwd: undefined
          }
        } else {
          this.$message({
            message: res.data.msg,
            type: 'warning',
            duration: 4000
          });
        }

      })
    },

    confirm() {
      let data = {
        customerId: localStorage.getItem('userId'),
        hotelId: this.hotelId,
        roomType: this.currentRoomType,
        estimatedCheckIn: this.dateValue[0],
        estimatedCheckOut: this.dateValue[1],
        province: this.provinceVal,
        discount: this.map2String(this.confirmOrderData.useDiscountMap)
      }
      // console.log("data=========", data)
      post('/api/order/create', data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: this.$t('hotelList.success'),
                type: 'success'
              });
              this.payForm.orderId = res.data.data.id
              this.payForm.lastPay = res.data.data.lastPay
              this.payVisible = true
              this.confirmOrderVisible = false

              //初始化表单
              this.currentRoomType = undefined
              this.dateValue = ''
              this.provinceVal = ''
              console.log("订单id：", res.data.data.id)
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
                duration: 4000
              });
            }

          })
          .catch(err => {
            console.log(err);
          })

    },

    getHotelDetails() {
      //let id = this.$route.params.hotelId
      let that = this
      // console.log(id)
      get("/api/hotel/get/" + this.hotelId).then(res => {
        that.hotelDetails = res.data.data;
        console.log(that.hotelDetails)
      })
    }
  }
}


</script>

<style scoped>

/* 定义滚动条样式 */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
  border-radius: 10px;
  background-color: #eeeeee;
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 0px rgb(245, 245, 245);
  border-radius: 10px;
  background-color: #eeeeee;
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 0px rgba(240, 240, 240, .5);
  background-color: #b2b2b2;
}


.hotel-list-main {
  /*height: calc(100vh - 220px);*/
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.big-body {
  background-color: #ecf5ff;
}

.hotel-list-bg {
  width: 100%;
  height: 220px;
}

.hotel-list-bg img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 220px;
}

.big-box {
  margin: 0 auto;
  /*border: 1px solid red;*/
  width: 1200px;

}

.hotel-title {
  font-size: 30px;
  color: #252626;
  font-family: inherit;
  font-weight: 600;
  line-height: 1.1;
  margin: 30px 0;
}

.hotel-detail {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  /*border: 1px solid blue;*/
}

.detail-left {
  left: 0;
  flex: 1;
  max-width: 60%;
  overflow: hidden;
  /*border: 1px solid blueviolet;*/
}

/*@media (min-width: 1000px)*/
.detail-right {
  /*position:fixed;*/
  /*right: 0;*/
  /*top:100%;*/
  width: 440px;
  /*border: 1px solid saddlebrown;*/
}

.detail-detail {
  line-height: 1.1;
  position: relative;
  background-color: #fff;
  overflow: hidden;
  padding: 10px;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.sub-title {
  display: block;
  font-size: 24px;
  margin-block-start: 1.6em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  font-weight: bold;
  position: relative;
  /*margin-top: 10px;*/
}

h3.sub-title .en {
  position: absolute;
  top: 0;
  font-size: 10px;
  color: #898a8a;
  font-weight: 500;
  font-style: italic;
  margin-left: 4px;
  text-transform: uppercase;
}

.detail-content {
  display: flex;
  /* justify-content: space-between; */
  align-items: center;
  padding: 12px 10px;
  line-height: 2;
  /*position: relative;*/
  background-color: #fff;
  overflow: hidden;
  overflow-x: hidden;
  overflow-y: hidden;
  margin-bottom: 30px;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);
}

.detail-right-price {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.text-weak {
  color: #585a5a;
  font-size: 12px;
}

.price-sum {
  color: #ff4d6a;
  font-size: 32px;
  margin: 10px;
}

.unit {
  font-size: 16px;
}

.book-box {
  line-height: 1.1;
  position: relative;
  background-color: #fff;
  overflow: hidden;
  padding-top: 8px;
  padding-bottom: 12px;
  width: 100%;
  padding: 8px;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.book-date {
  margin: 10px;
}

.book-room {
  margin: 10px;
}

.room-detail {
  font-size: 14px;
  line-height: 20px;
  margin: 8px 0;
}

.detail-introduce {
  line-height: 26px;
  margin: 0px 6px 8px 6px;
  font-size: 14px;
}

.location {
  color: #0af;
  font-size: 40px;
  cursor: pointer;
  margin: 6px 12px;
}

.address-detail {
  width: 50%;
  font-size: 16px;
  line-height: 30px;
  padding: 6px;
}

.badge {
  margin: 2px 6px;
  border-radius: 6px;
  background: #4f93fe;
  padding: 6px 8px;
  color: #fff !important;
}

.abnormal {
  padding: 10px;
}

.discounts-box {
  /*border: 1px solid red;*/
  width: 220px;
  height: 110px;
  margin: 10px;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);
  cursor: pointer;

}

/* .discounts-box.active {
  cursor: no-drop;
} */
.discounts-box .discounts-title.active {
  background: rgb(210 211 213);
  cursor: no-drop;
}
.discounts-box .discounts-title button {
  cursor: no-drop;
}
.discounts-box .discounts-body.active {
  background: rgb(210 211 213);
  cursor: no-drop;
}


.discounts-title {

  width: 100%;
  height: 29%;
  background: rgb(217, 236, 255);
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 6px;
}

.discounts-body {
  width: 100%;
  height: 70%;
  background: rgb(217, 236, 255);
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}

.discounts-body-top {
  margin: 10px 4px;
}

.discounts-body-price {
  color: #ff4d6a;
  font-size: 26px;
  font-weight: 800;
  line-height: 30px;
}

.discounts-body-bottom {
  color: #999;
  margin-top: -6px;
  margin-left: 6px;
}

.order-hotel-detail-detail {
  margin: 0px 6px;
}

.choose-hotel-discount {
  width: 100%;
  /* max-height: 110px;
  overflow-y: scroll;
  overflow-x: hidden; */
}

.choose-discount-box {
  margin: 6px;
  width: 96%;
  height: 40px;
  background: #fff;
  /*border: 1px solid red;*/
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.choose-discount-left {
  width: 79%;
  height: 100%;
  background: rgb(217, 236, 255);
  border-top-right-radius: 6px;
  border-bottom-right-radius: 6px;
}

.choose-discount-right {
  width: 21%;
  height: 100%;
  background: rgb(217, 236, 255);
  border-top-left-radius: 6px;
  border-bottom-left-radius: 6px;
  font-size: 20px;
  font-weight: 700;
  line-height: 44px;
  color: #ff4d6a;
}

.choose-discount-chosen {
  background: #d9d9d9;
  text-decoration: line-through;
}

.choose-discount-name {
  font-size: 16px;
  font-weight: 700;
  margin: auto 0;
  margin-left: 8px;
}

.choose-discount-date {
  color: #999;
  font-size: 10px;
  margin-right: 8px;
}

.order-count-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 6px;
  color: #999;
}

.pay-box {
  width: 100%;
  height: 200px;
  margin-bottom: -50px;
}

.pay-box-price {
  font-size: 56px;
  font-weight: 700;
  color: #ff4d6a;
  width: 100%;
  text-align: center;
  margin: 10px 0 30px 0;
}


</style>