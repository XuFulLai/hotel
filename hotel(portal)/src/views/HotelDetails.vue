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

        <div ref="hotelDetail" class="hotel-detail">
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

            <div class="detail-block">
              <h3 class="sub-title">
                酒店评价
                <span class="en">Evaluation</span>
              </h3>

              <div class="detail-content" style="margin: 0;padding: 0">
                <div class="comment-box flex flex-column">
                  <div class="comment-total-box">
                    <div class="comment-total-score flex flex-row align-items-end">
                      <div class="comment-total-score-ball flex justify-content-center align-items-center">
                        {{ hotelAvgScore }}
                      </div>
                      <div class="comment-total-score-star">
                        <el-rate
                            v-model="hotelAvgScore"
                            :colors="colors"
                            disabled
                            show-text>
                        </el-rate>
                      </div>
                    </div>
                    <p class="font-18 color-6">共 {{ hotelCommentsTotal }} 条评论</p>
                  </div>
                  <el-divider content-position="left">用户评价</el-divider>
                
                  <div ref="commentUserBox" v-for="i in hotelCommentsList.slice(0, 1)" class="comment-user-box flex flex-row">
                    <div class="comment-user-box-left">
                      <el-avatar :size="circleUrlSize" :src="circleUrl"></el-avatar>
                    </div>
                    <div class="comment-user-box-right flex flex-column">
                      <div class="comment-user-nickname-date flex flex-row justify-content-between">
                        <div class="comment-user-nickname">{{ i.createBy}}</div>
                        <div class="comment-user-date">发布于 {{ i.createTime | dateTimeFormat('ymd') }}</div>
                      </div>
                      <div class="comment-user-roomType">{{ i.roomType }}</div>
                      <div class="comment-user-score">
                        <el-rate
                            v-model="i.score"
                            :colors="colors"
                            disabled
                            show-score>
                        </el-rate>
                      </div>
                      <div class="comment-user-content">
                        {{ i.content }}
                      </div>
                      <div class="comment-user-pic">
                        <el-image
                            style="width: 10rem; height: 10rem"
                            :src="url"
                            :preview-src-list="srcList">
                        </el-image>
                      </div>
                    </div>
                  </div>


                <transition name="comment">
                  <div class="comment-list-box" :style="{'--commentHeight':commentHeight,}" v-show="comment">
                    <div v-for="i in hotelCommentsList.slice(1,hotelCommentsList.length)" class="comment-user-box flex flex-row">
                      <div class="comment-user-box-left">
                        <el-avatar :size="circleUrlSize" :src="circleUrl"></el-avatar>
                      </div>
                      <div class="comment-user-box-right flex flex-column">
                        <div class="comment-user-nickname-date flex flex-row justify-content-between">
                          <div class="comment-user-nickname">{{ i.createBy}}</div>
                          <div class="comment-user-date">发布于 {{ i.createTime | dateTimeFormat('ymd') }}</div>
                        </div>
                        <div class="comment-user-roomType">{{ i.roomType }}</div>
                        <div class="comment-user-score">
                          <el-rate
                              v-model="i.score"
                              :colors="colors"
                              disabled
                              show-score>
                          </el-rate>
                        </div>
                        <div class="comment-user-content">
                          {{ i.content }}
                        </div>
                        <div class="comment-user-pic">
                          <el-image
                              style="width: 10rem; height: 10rem"
                              :src="url"
                              :preview-src-list="srcList">
                          </el-image>
                        </div>
                      </div>
                    </div>

                    <!-- 分页器 -->
                    <el-pagination
                      class="d-flex align-items-center justify-content-center"
                      :small="smallPagination"
                      background
                      :page-size="5"
                      :pager-count="5"
                      @current-change="handleCurrentChange"
                      @prev-click="prevPage"
                      @next-click="nextPage"
                      layout="prev, pager, next"
                      :total="hotelCommentsTotal"
                    >
                    </el-pagination>

                  </div>

                </transition>

                <div style="margin: 1rem auto" class="d-flex align-items-center">
                  <!-- <el-button v-if="hotelCommentsList.length > 1" @click="comment = !comment" type="primary">{{ comment ? '收起':'展开' }}</el-button> -->
                  <el-button v-if="hotelCommentsList.length > 1" @click="commentBtn" type="primary">{{ comment ? '收起':'展开' }}</el-button>
                </div>                  



                </div>
              </div>
            </div>

            <div class="detail-block" v-if="hotelDiscounts != null && hotelDiscounts.length !== 0">
              <h3 class="sub-title">
                酒店优惠
                <span class="en">discount</span>
              </h3>

              <div class="detail-content flex flex-row flex-wrap" style="margin: 0;padding: 0">
                
                <div ref="couponBoxHeight" class="w-percent-100 d-flex flex-wrap">
                  <div ref="couponHeight" @click="gotCoupon(i.id)" class="discounts-box" v-for="i in hotelDiscounts.slice(0, 3)">
                    <div class="discounts-title flex flex-row justify-content-between" :class="[i.isGot?'active':'', isGot?'active':'']">
                      <p style="margin-left: 0.6rem;font-size: 1.4rem;font-weight: 700">{{ i.name }}</p>
                      <el-tooltip placement="right" style="margin: 4px">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 8px;margin-left: 10px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div style="border-top: 1px dotted #999;"></div>
                    <div class="discounts-body flex flex-column" :class="[i.isGot?'active':'', isGot?'active':'']">
                      <div class="discounts-body-top flex flex-row align-items-center">
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
                        有效期到 {{ i.validityTime | dateTimeFormat }}
                      </div>
                    </div>
                  </div>
                </div>
                <transition name="draw">
                  <div :style="{'--couponHeight':couponHeight,}" class="coupon-box d-flex flex-row flex-wrap" v-show="coupon">
                    <div @click="gotCoupon(i.id)" class="discounts-box" v-for="i in hotelDiscounts.slice(3,hotelDiscounts.length)">
                      <div class="discounts-title flex flex-row justify-content-between" :class="[i.isGot?'active':'', isGot?'active':'']">
                        <p style="margin-left: 0.6rem;font-size: 1.4rem;font-weight: 700">{{ i.name }}</p>
                        <el-tooltip placement="right" style="margin: 4px">
                          <div slot="content">{{ i.description }}</div>
                          <el-button
                              style="border-radius: 50%;padding: 2px 8px;margin-left: 10px;font-weight: 800;color: #999">!
                          </el-button>
                        </el-tooltip>
                      </div>
                      <div style="border-top: 1px dotted #999;"></div>
                      <div class="discounts-body flex flex-column" :class="[i.isGot?'active':'', isGot?'active':'']">
                        <div class="discounts-body-top flex flex-row align-items-center">
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
                          有效期到 {{ i.validityTime | dateTimeFormat }}
                        </div>
                      </div>
                    </div>                                         
                  </div>
                </transition>

                <div class="d-flex align-items-center expand-collapse cursor" @click="coupon = !coupon">
                  <div style="margin: 0.8rem auto" v-if="hotelDiscounts.length > 3" >
                    <i :class="coupon ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"/>&nbsp;{{ coupon ? '收起':'展开' }}
                  </div>                  
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
                 :style="isFixed ? `position:fixed;top:90px;width:${dynamicWidth}px` : ''">
              <div class="book-box">

                <el-switch
                    style="display: block;margin: 2rem 1rem;"
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
                    <p v-if="bookDay" style="margin: 2px;white-space: nowrap;">共{{ bookDay }}晚</p>
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
                      <el-button @click="confirmOrderHandle" type="primary" style="width: 100%;margin: 1rem 0">预订</el-button>
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


          <div class="app-booking-btn">
            <el-button style="box-shadow: 0px 0px 40px -10px #000;" class="w-percent-100" @click="appBooking = true" type="primary">预订</el-button>
          </div>

          <van-popup v-model="appBooking" position="bottom" :style="{ height: '75%',boxSizing: 'border-box' }">
            
            <el-switch      
              style="display: block;padding: 16px;"
              v-model="appSwitchType"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="正常预定"
              inactive-text="自申报">
            </el-switch>

            <!-- app 预定 -->
            <div v-show="appSwitchType" class="app-booking-main">
              <!-- 表单主体 -->
              <div class="app-booking-form">

                <!-- 选择开始时间 Start -->
                <van-field
                  v-model="appDateStart"
                  label="开始时间"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showAppDateStart = true" />

                <van-popup v-model="showAppDateStart" position="bottom">
                  <van-datetime-picker
                    v-model="startCurrentDate"
                    :min-date="minDate"
                    @cancel="startCancelDate"
                    @confirm="startConfirmDate"
                    type="date"
                  />
                </van-popup>
                <!-- 选择开始时间 End -->

                <!-- 选择结束时间 Start -->
                <van-field
                  v-model="appDateEnd"
                  label="结束时间"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showAppDateEnd = true" />

                <van-popup v-model="showAppDateEnd" position="bottom">
                  <van-datetime-picker
                    v-model="endCurrentDate"
                    :min-date="minDate"
                    @cancel="endCancelDate"
                    @confirm="endConfirmDate"
                    type="date"
                  />
                </van-popup>
                <!-- 选择结束时间 End -->

                <!-- 选择房型 Start -->
                <van-field
                  v-model="appCurrentRoomType"
                  label="选择房型"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showRoomType = true" />

                <van-popup v-model="showRoomType" position="bottom">
                  <van-picker
                    show-toolbar
                    :columns="roomTypeList"
                    value-key="name"
                    @confirm="roomTypeConfirm"
                    @cancel="roomTypeCancel"
                  />
                </van-popup>
                <!-- 选择房型 End -->

                <!-- 选择省份 Start -->
                <van-field
                  v-model="appCurrentProvince"
                  label="选择省份"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showProvince = true" />

                <van-popup v-model="showProvince" position="bottom">
                  <van-picker
                    show-toolbar
                    :columns="options"
                    value-key="label"
                    @confirm="provinceConfirm"
                    @cancel="provinceCancel"
                  />
                </van-popup>
                <!-- 选择省份 End -->


              </div>
              
              <!-- 按钮 -->
              <div class="mb-15 text-center">
                <el-button style="width:60%" @click="confirmOrderHandle" type="primary">确 定</el-button>                
              </div>

            </div>

            <!-- app 自申报 -->
            <div v-show="appSwitchType == false" class="app-booking-main">
              <!-- 表单主体 -->
              <div class="app-booking-form">

                <van-field v-model="userName" label="姓名" />
                <van-field v-model="userId" label="身份证" />
                <van-field v-model="phoneNum" type="tel" label="手机号" />               
                <van-field v-model="userEmail" label="邮箱" />

                <!-- 入住时间 Start -->
                <van-field
                  v-model="appDate"
                  label="入住时间"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showAppDate = true" />

                <van-popup v-model="showAppDate" position="bottom">
                  <van-datetime-picker
                    v-model="currentDate"
                    :min-date="minDate"
                    @cancel="cancelDate"
                    @confirm="confirmDate"
                    type="date"
                  />
                </van-popup>
                <!-- 入住时间 End -->


                <!-- 申报类型 Start -->
                <van-field
                  
                  v-model="appSituation"
                  label="选择类型:"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showSituation = true" />

                <van-popup v-model="showSituation" position="bottom">
                  <van-picker
                    show-toolbar
                    :columns="situationOptions"
                    value-key="label"
                    @confirm="situationConfirm"
                    @cancel="situationCancel"
                  />
                </van-popup>
                <!-- 申报类型 End -->

                <!-- 选择房型 Start -->
                <van-field
                  v-show="situation == 2 || situation == 3"
                  v-model="appCurrentRoomType1"
                  label="选择房型"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showAppRoomType = true" />

                <van-popup v-model="showAppRoomType" position="bottom">
                  <van-picker
                    show-toolbar
                    :columns="isolateRoomTypeList"
                    value-key="name"
                    @confirm="appRoomTypeConfirm"
                    @cancel="appRoomTypeCancel"
                  />
                </van-popup>
                <!-- 选择房型 End -->                

                <!-- 选择省份 Start -->
                <van-field
                  v-model="appCurrentProvince1"
                  label="选择省份"
                  :placeholder="'请选择'"
                  input-align="right"
                  readonly
                  right-icon="arrow"
                  @click="showProvince1 = true" />

                <van-popup v-model="showProvince1" position="bottom">
                  <van-picker
                    show-toolbar
                    :columns="addressData"
                    value-key="label"
                    @confirm="provinceConfirm1"
                    @cancel="provinceCancel1"
                  />
                </van-popup>
                <!-- 选择省份 End -->                


              </div>
              
              <!-- 按钮 -->
              <div class="mb-15 text-center">
                <el-button style="width:60%" type="primary" @click="submit">确 定</el-button>                
              </div>

            </div>            


          </van-popup>

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
                      有效期至 {{ i.validityTime | dateTimeFormat}}
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
                      有效期至 {{ i.validityTime | dateTimeFormat }}
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
import {CodeToText, provinceAndCityData} from 'element-china-area-data'
import { Popup, DatetimePicker, Picker, Field  } from 'vant';
import 'vant/lib/index.css';

export default {
  name: "HotelDetails",
  components: {
    TopNav,
    Footer,
    [Popup.name]: Popup,
    [DatetimePicker.name]: DatetimePicker,
    [Picker.name]: Picker,
    [Field.name]: Field,
  },
  data() {
    return {
      showAppRoomType: false,
      appCurrentRoomType1: '',
      appSituation: '',
      showSituation: false,
      appDate: '',
      showAppDate: false,
      currentDate: new Date(),
      appCurrentProvince: '',
      appCurrentProvince1: '',
      showProvince: false,
      showProvince1: false,
      showRoomType: false,
      appDateEnd: '',
      showAppDateEnd: false,
      endCurrentDate: new Date(),

      appDateStart: '',
      showAppDateStart: false,
      startCurrentDate: new Date(),   
      
      minDate: new Date(),

      appSwitchType: true,
      appBooking: false,
      smallPagination: false,
      circleUrlSize: 50,
      hotelCommentsList: [],
      hotelCommentsTotal: 0,
      couponHeight: '',
      coupon: false,
      commentHeight: '',
      comment: false,
      dynamicWidth: '',
      colors: ['#99A9BF', '#F7BA2A', '#ff7300'],
      hotelAvgScore: 0,
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
      appCurrentRoomType: '',
      currentRoomType: '',
      dateValue: [],
      bookDay: 0,
      maxFee: 0,
      minFee: 0,
      totalFee: 0,
      provinceVal: '',
      pageNum: 0,
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
    const that = this
    if (window.document.body.clientWidth < 768) {
      this.circleUrlSize = 40
      this.smallPagination = true
    } else {
      this.smallPagination = false
      this.circleUrlSize = 50
    }
    window.onresize = () => {
      return (() => {
          if (window.document.body.clientWidth < 768) {
            this.circleUrlSize = 40
          } else {
            this.smallPagination = false
            this.circleUrlSize = 50
          }
          that.dynamicWidth = ( that.$refs.hotelDetail.offsetWidth ) * 0.35 - 0 
      })()
    }    
    this.$nextTick(() => {
      this.dynamicWidth = this.$refs.hotelDetail.offsetWidth - 17
      this.dynamicWidth = this.dynamicWidth * 0.35 - 0
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
    this.getHotelAvgScore()
    this.getCommentsList()
  },
  methods: {
    // 评论展开按钮
    commentBtn() {
      this.comment = !this.comment
      this.commentHeight = this.$refs.commentUserBox[0].clientHeight * (this.hotelCommentsList.length - 1) + 55 + 'px'
    },
    // 获取酒店评价
    getCommentsList() {
      const data = {
          page: {
              page: 1,
              size: 5
              
          },
      }
      this.commentsListRequest(data)
    },
    //选择页码
    handleCurrentChange(num) {
      const data = {
        page: {
          page: num,
          size: 5
        }
      }
      this.commentsListRequest(data)
    },

    //上一页
    prevPage(num) {
      const data = {
        page: {
          page: num,
          size: 5
        }
      }
      this.commentsListRequest(data)
    },

    //下一页
    nextPage(num) {
      const data = {
        page: {
          page: num,
          size: 5
        }
      }
      this.commentsListRequest(data)
    },    

    commentsListRequest(data) {
      post('/api/orderComment/page',data)
        .then( res => {
          if (res.data.code == 200) {
            console.log('酒店评论数据：',res);
            this.hotelCommentsList = res.data.data.records
            this.hotelCommentsTotal = res.data.data.total
            if (this.$refs.commentUserBox[0].clientHeight) {              
              this.commentHeight = this.$refs.commentUserBox[0].clientHeight * (this.hotelCommentsList.length - 1) + 35 + 'px'          
            }
          }
        })
        .catch( err => {
          console.error(err);
        })      

    },

    getHotelAvgScore() {
      get(`/api/orderComment/score/${this.hotelId}`).then(res => {
        if (res.data.code == 200) {
          this.hotelAvgScore = res.data.data - 0
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
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
      console.log('id:',id);
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

    getHotelDiscountList() {
      get("/api/discounts/list/personal/" + this.hotelId).then(res => {
        console.log(res.data);
        if (res.data.code == 200) {
          this.hotelDiscounts = res.data.data
        }
      })
    },
    // 优惠券展开点击事件
    couponClick(){
      this.coupon = !this.coupon
      if (window.document.body.clientWidth < 768) {
        this.couponHeight = (this.hotelDiscounts.length - 3) * (this.$refs.couponHeight[0].clientHeight + 24) + 'px'
      } else {
        this.couponHeight = Math.ceil((this.hotelDiscounts.length - 3) / 3) * this.$refs.couponBoxHeight.clientHeight + 'px'
      }
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
      let leaveDate
      if (this.date) {
        leaveDate = new Date(Date.parse(this.date) + 1209600000) // 14天之后的时间
      } else {
        leaveDate = new Date(Date.parse(this.appDate) + 1209600000) // 14天之后的时间
      }
      const data = {
        checkInTime: this.date || new Date(this.appDate),
        checkOutTime: leaveDate,
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
      console.log('data:',data);
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
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      // console.log(scrollTop);
      // console.log(this.offsetTop * 1.15);
      // this.isFixed = scrollTop > this.offsetTop * 1.15 ? true : false;
      this.isFixed = scrollTop >= 200 ? true : false;
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
            this.roomTypeColumns=[{ values: Object.keys(this.roomTypeList) }, { values: cities['name'] }]
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
      formDataPost("/api/order/payOrder",data).then(res => {
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
    },

    // 移动端选择开始日期取消函数
    startCancelDate() {
      this.showAppDateStart = false
    },

    // 移动端选择开始日期确定函数
    startConfirmDate(value) {
      this.appDateStart = this.formatter(value)
      this.dateValue[0] = this.formatter(value)
      this.startCancelDate()
    },

    // 移动端选择结束日期取消函数
    endCancelDate() {
      this.showAppDateEnd = false
    },

    // 移动端选择结束日期确定函数
    endConfirmDate(value) {
      this.appDateEnd = this.formatter(value)
      this.dateValue[1] = this.formatter(value)
      this.endCancelDate()
    },    

    // 移动端选择房间取消函数
    roomTypeCancel() {
      this.showRoomType = false
    },

    // 移动端选择房间确定函数
    roomTypeConfirm(value, index) {
      console.log(value);
      this.appCurrentRoomType = value.name
      // this.currentRoomType = value.id
      this.roomTypeCancel()
    },

    // 移动端选择省份取消函数
    provinceCancel() {
      this.showProvince = false
    },

    // 移动端选择省份确定函数
    provinceConfirm(value, index) {
      console.log(value);
      this.appCurrentProvince = value.label
      this.provinceVal = value.value
      this.provinceCancel()
    },    

    // 移动端选择入住时间取消函数
    cancelDate() {
      this.showAppDate = false
    },

    // 移动端选择入住时间确定函数
    confirmDate(value, index) {
      console.log(value);
      this.appDate = this.formatter(value)
      this.cancelDate()
    },

    // 移动端选择省份取消函数
    provinceCancel1() {
      this.showProvince1 = false
    },

    // 移动端选择省份确定函数
    provinceConfirm1(value, index) {
      console.log(value);
      this.area[0] = value[0]
      this.area[1] = value[1]
      this.appCurrentProvince1 = value[0] + ' / ' + value[1]
      this.provinceCancel1()
    },      

    // 移动端选择申报类型取消函数
    situationCancel() {
      this.showSituation = false
    },

    // 移动端选择申报类型确定函数
    situationConfirm(value, index) {
      console.log(value);
      this.appSituation = value.label
      this.situation = value.value
      this.situationCancel()
    },    

    // 移动端自申报选择房间取消函数
    appRoomTypeCancel() {
      this.showAppRoomType = false
    },    

    // 移动端自申报选择房间确定函数
    appRoomTypeConfirm(value) {
      console.log(value);
      this.appCurrentRoomType1 = value.name
      this.isolateRoomType = value.id
      this.appRoomTypeCancel()
    },

    // 格式化函数
    formatter(value){
      const dateTime = new Date(value) // Date实例
      const YYYY = dateTime.getFullYear() // 获取当前年份
      const MM = dateTime.getMonth() + 1 // 获取当前月份
      const DD = dateTime.getDate() // 获取当前天数

      // 返回格式化之后的当前时间
      return `${YYYY}-${MM}-${DD}`
    
    }

  }
}


</script>

<style scoped>
.app-booking-btn {
  display: none;
}
.discounts-body-condition {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
/* 动画 Start */
.coupon-box {
  height: var(--couponHeight)
}
.draw-enter-active, .draw-leave-active {
    transition: all 0.6s ease;
}
.draw-enter, .draw-leave-to /* .fade-leave-active below version 2.1.8 */ {
  height: 0;
}
.expand-collapse {
  width: 100%;
  border-top: 1px solid rgba(206, 203, 203, 0.5);
  color: #aaaaaa;
  font-size: 1.2rem;
  font-weight: 600;
  background: #fff;
}
.comment-list-box {
  height: var(--commentHeight)
}
.comment-enter-active, .comment-leave-active {
    transition: all 0.6s ease;
}
.comment-enter, .comment-leave-to /* .fade-leave-active below version 2.1.8 */ {
  height: 0;
}
/* 动画 End */

.expand-collapse {
  width: 100%;
  border-top: 1px solid rgba(206, 203, 203, 0.5);
  color: #aaaaaa;
  font-size: 1.2rem;
  font-weight: 600;
  background: #fff;
}

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
  /* margin: 0 auto; */
  /*border: 1px solid red;*/
  /* width: 1200px; */
  /* width: 120rem; */
  padding: 0 4rem;

}

.hotel-title {
  /* font-size: 30px; */
  font-size: 3rem;
  color: #252626;
  font-family: inherit;
  font-weight: 600;
  line-height: 1.1;
  /* margin: 30px 0; */
  margin: 3rem 0;
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
  max-width: 62%;
  overflow: hidden;
  /*border: 1px solid blueviolet;*/
}

/*@media (min-width: 1000px)*/
.detail-right {
  /*position:fixed;*/
  /*right: 0;*/
  /*top:100%;*/
  /* width: 440px; */
  width: 35%;
  min-width: 252px;
  /* max-width: 44rem; */
  /*border: 1px solid saddlebrown;*/
}

.detail-detail {
  line-height: 1.1;
  position: relative;
  background-color: #fff;
  overflow: hidden;
  /* padding: 10px; */
  padding: 1rem;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.sub-title {
  display: block;
  /* font-size: 24px; */
  font-size: 2.4rem;
  /* margin-block-start: 1.6em;
  margin-block-end: 1em; */
  margin-block-start: 1.6rem;
  margin-block-end: 1rem;  
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
  align-items: center;
  /* padding: 12px 10px; */
  padding: 1.2rem 1rem;
  font-size: 1.8rem;
  line-height: 2;
  /*position: relative;*/
  background-color: #fff;
  overflow: hidden;
  overflow-x: hidden;
  overflow-y: hidden;
  margin-bottom: 3rem;
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
  /* padding-top: 8px;
  padding-bottom: 12px; */
  width: 100%;
  /* padding: 8px; */
  padding: 0.8rem;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.book-date {
  margin: 1rem;
}

.book-room {
  margin: 1rem;
}

.room-detail {
  font-size: 14px;
  line-height: 20px;
  margin: 8px 0;
}

.detail-introduce {
  /* line-height: 26px;
  margin: 0px 6px 8px 6px;
  font-size: 14px; */
  line-height: 2.8rem;
  margin: 0 0.6rem 0.8rem 0.6rem;
  font-size: 1.6rem;  
}



.location {
  color: #0af;
  /* margin: 6px 12px;
  font-size: 40px; */
  margin: 0.6rem 1.2rem;
  font-size: 4rem;  
  cursor: pointer;
}

.address-detail {
  width: 50%;
  /* font-size: 16px;
  line-height: 30px;
  padding: 6px; */
  font-size: 1.6rem;
  line-height: 3rem;
  padding: 0.6rem;  
}

.badge {
  /* margin: 2px 6px;
  padding: 6px 8px; */
  margin: 0.2rem 0.6rem;
  padding: 0.6rem 0.8rem;  
  border-radius: 6px;
  background: #4f93fe;
  font-size: 1.6rem;
  color: #fff !important;
}

.abnormal {
  padding: 10px;
}

.discounts-box {
  /*border: 1px solid red;*/
  /* width: 220px;
  height: 110px;
  margin: 10px; */
  /* width: 22rem;
  height: 11rem; */
  width: 31.3%;
  height: 110px;
  margin: 0.8rem 1%;
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
  margin: 1rem 0.4rem;
}

.discounts-body-price {
  color: #ff4d6a;
  /* font-size: 22px; */
  font-size: 2.2rem;
  margin-right: 0.5rem;
  font-weight: 800;
  line-height: 30px;
}

.discounts-body-bottom {
  color: #999;
  font-size: 1.4rem;
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

.comment-box {
  /* margin: 16px; */
  padding: 1.6rem;
  width: 100%;
  box-sizing: border-box;
}

.comment-total-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.comment-total-score-ball {
  border-radius: 50%;
  background: #00aaff;
  width: 50px;
  height: 50px;
  /* font-size: 20px;
  margin-right: 6px; */
  /* width: 5rem;
  height: 5rem; */
  font-size: 2rem;
  margin-right: 0.6rem;  
  font-weight: 800;
  color: #fff;
}

.comment-user-box-right {
  margin-left: 10px;
  width: 100%;
}

.comment-user-date {
  color: #999;
}

.comment-user-roomType {
  color: #999;
}

/* 媒体查询 Start */
@media screen and (max-width: 767.9px) { /* 页面测试无法显示767，实际是767.2px */
  .hotel-list-bg {
    height: 120px;
  }
  .hotel-list-bg img {
    height: 120px;
  }
  .big-box {
    padding: 0 2rem;
  }
  .detail-left {
    max-width: 100%;
  }
  .comment-total-score-ball {
    width: 40px;
    height: 40px;
  }
  .detail-right {
    display: none;
  }
  .discounts-box {
    width: 100%;
    margin: 2rem 5rem;
  }
  .comment-user-nickname {
    width: calc(100% - 100px);
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .comment-user-box-right {
    width: calc(100% - 50px);
  }    
  .app-booking-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    left: 0;
    bottom: 30px;
    width: 100%;
    padding: 0 5rem;
    box-sizing: border-box;
  }
  .app-booking-main {
    height: calc(100% - 52px);
    display: flex;
    flex-direction: column;
    justify-content: space-between;    
  }
}

@media screen and (min-width: 768px) and (max-width: 992px) {
  .big-box {
    padding: 0 2rem;
  }
  .detail-introduce {
    line-height: 3.2rem; 
  }
  .discounts-body-bottom {
    margin-top: 0;
    margin-left: 0;
  }  
  .discounts-box {
    width: 31.5%;
    margin: 0.5rem  
  }
}

@media screen and (min-width: 992px) and (max-width: 1440px) {
  .big-box {
    padding: 0 3rem;
  }

}
@media screen and (min-width: 1441px) {
  .discounts-box {
    height: 11rem;
  }

}
/* 媒体查询 End */

</style>