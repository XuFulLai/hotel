<template>
    <div style="background-color: #ecf5ff;min-height: 100vh">

        <!-- 导航栏 -->
        <top-nav></top-nav>

        <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
            <img src="../assets/images/hotel-list-bg.png" alt="">
            <h3>{{ $t('hotelList.title') }}</h3>
        </div>

        <div class="hotel-list-main">

            <div>
                <!--  酒店列表-->
                <div class="hotel-list-content">
                    <ul class="hotel-list">
                        <li v-for="(item,index) in hotelList">
                            <div class="li-l">
                                <img :src=item.cover alt="">
                            </div>
                            <div class="li-r">
                                <h4>{{ item.name }}</h4>
                                <h6>{{ item.address }}</h6>
                                <div v-html="item.introduce"></div>
                                <button @click="hotelDetails(index,item.id)">{{ $t('hotelList.confirm') }}</button>
                            </div>
                        </li>
                    </ul>
                </div>

                <div v-if="pageNum > 5" class="d-flex align-items-center justify-content-center mb-30">
                    <el-pagination
                            background
                            :page-size="5"
                            @current-change="handleCurrentChange"
                            @prev-click="prevPage"
                            @next-click="nextPage"
                            layout="prev, pager, next"
                            :total="pageNum">
                    </el-pagination>
                </div>
            </div>

            <!-- 页脚 -->
            <Footer></Footer>

        </div>


        <!-- 弹出框 -->
        <el-dialog
                :title="$t('hotelList.dialogTitle')"
                center
                :visible.sync="dialogVisible"
                width="570px">
            <div class="contont">
                <div class="d-flex align-items-center mb-15">
                    <p class="w-100 text-left">{{ $t('hotelList.selectDate') }}</p>
                    <div class="block">
                        <el-date-picker
                                v-model="dateValue"
                                type="daterange"
                                value-format="yyyy-MM-dd"
                                range-separator="-"
                                :start-placeholder="$t('hotelList.startDate')"
                                :end-placeholder="$t('hotelList.endDate')"
                                :picker-options="pickerOptions">
                        </el-date-picker>
                    </div>
                </div>
                <div class="d-flex align-items-center mb-15">
                    <p style="white-space: nowrap;min-width: 100px!important;" class="w-100 text-left">{{ $t('hotelList.selectRoom') }}</p>
                    <div>
                        <el-radio class="room-type-radio" v-for="(item,index) in roomTypeList" v-model="roomType" :label="item.id">
                            {{ item.roomTypeName | roomNameFormat }}
                        </el-radio>
                    </div>
                </div>
                <div class="d-flex align-items-center mb-15">
                    <p class="w-100 text-left">{{ $t('hotelList.province') }}</p>
                    <el-select v-model="provinceVal" :placeholder="$t('hotelList.select')">
                        <el-option
                                v-for="item in options"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">{{ $t('hotelList.dialogCancel') }}</el-button>
            <el-button type="primary" @click="confirm">{{ $t('hotelList.dialogConfirm') }}</el-button>
        </span>
        </el-dialog>

    </div>
</template>

<script>
    import {get, post} from "../utils/request";
    import TopNav from '../components/TopNav'
    import Footer from '../components/Footer.vue';

    export default {
        name: "HotelList",
        components: {
            TopNav,
            Footer
        },
        watch: {
            dialogVisible(newVal,oldVal){
                console.log(newVal);
                console.log(oldVal);
                if (newVal == false && oldVal == true) {
                    this.dateValue = []
                    this.roomType = ''
                    this.provinceVal = ''
                }
            }
        },
        filters: {
            roomNameFormat(val){
                const lang = localStorage.getItem('lang')
                if (lang == 'zh') {
                    return val
                } else if(lang == 'en') {
                    if (val == '单人房') {
                        return val = 'Single-bed room'
                    } else if(val == '豪华大床房') {
                        return val = 'Deluxe single Room'
                    } else if(val == '双人房') {
                        return val = 'Double room'
                    } else if(val == '总统套房') {
                        return val = 'Presidential suite'
                    } else if(val == '经济房') {
                        return val = 'Economy Room'
                    } else {
                        return val
                    }
                }
            }
        },
        data() {
            return {
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
                hotelList: [],
                startTime: '',
                endTime: '',
                hotelId: '',
                pageNum: 0,
                dateValue: [],
                dialogVisible: false,
                roomType: '',
                roomTypeId: '',
                roomTypeList: [],
                bedType: undefined,
                form: {
                    name: undefined,
                    maxLoad: undefined,
                    bed: undefined,
                    haveWindow: undefined,
                    network: undefined,
                    haveBreakfast: undefined,
                    sort: undefined,
                    fee: undefined,
                    bedType: undefined,
                },
                pickerOptions: {
                    disabledDate(v){
                        return v.getTime() < new Date().getTime() - 86400000;
                    }
                }
            }
        },
        // watch: {
        //     date(val) {
        //         if (val) {
        //             if (val.length > 0) {
        //                 let startDate = new Date(val[0]);
        //                 let endDate = new Date(val[1]);
        //                 // 1、两个日期不允许跨年
        //                 let sameYear = startDate.getFullYear() == endDate.getFullYear() ? true : false;
        //                 if (sameYear) {
        //                     this.form.fStopStartDate = val[0];
        //                     this.form.fStopEndDate = val[1];
        //                     // 2、计算选择日期范围的天数
        //                     let stopDays = parseInt(Math.abs(endDate - startDate) / (24 * 3600 * 1000));
        //                     this.form.fStopDays = stopDays + 1;
        //                 } else {
        //                     this.date = [];//清空日期
        //                     this.$notify({
        //                         title: "日期不允许跨年",
        //                         type: "warning",
        //                         duration: 2500
        //                     });
        //                 }
        //             }
        //         } else {
        //             this.form.fStopStartDate = null;
        //             this.form.fStopEndDate = null;
        //         }
        //     }
        // },
        mounted() {
            this.getHotelList()
        },
        methods: {
            //获取酒店列表
            getHotelList() {
                let hotelInfo = ''
                if (this.$route.params.hotelInfo) {
                    hotelInfo = JSON.parse(this.$route.params.hotelInfo)
                }
                console.log(hotelInfo);
                let data = {
                    page: {
                        page: 1,
                        size: 5
                    },
                }
                if (hotelInfo) {
                    this.hotelList = hotelInfo
                } else {
                    this.hotelListRequest(data)
                }

            },
            //获取酒店列表请求
            hotelListRequest(data) {
                post('/api/hotel/page', data)
                    .then(res => {
                        console.log(res);
                        this.hotelList = res.data.data.records
                        this.pageNum = res.data.data.total
                    })
                    .catch(err => {
                        console.log(err);
                    })
            },

            //选择页码
            handleCurrentChange(num) {
                let data = {
                    page: {
                        page: num,
                        size: 5
                    }
                }
                this.hotelListRequest(data)
                window.scrollTo(0, 0)
            },

            //上一页
            prevPage(num) {
                let data = {
                    page: {
                        page: num,
                        size: 5
                    }
                }
                this.hotelListRequest(data)
                window.scrollTo(0, 0)
            },

            //下一页
            nextPage(num) {
                let data = {
                    page: {
                        page: num,
                        size: 5
                    }
                }
                this.hotelListRequest(data)
                window.scrollTo(0, 0)
            },


            hotelDetails(index, id) {
                console.log(index);
                console.log(id);
                this.hotelId = id
                let data = {
                    hotelId: id
                }
                // post('/api/room/getRoomTypeList', data)
                //     .then(res => {
                //         console.log(res);
                //         this.dialogVisible = true
                //         this.roomTypeList = res.data.data
                //     })
                //     .catch(err => {
                //         console.log(err);
                //     })

                this.$router.push({
                    name: 'hotelDetails',
                    params: {
                        index: index,
                        hotelId: id
                    }
                })
            },

            confirm() {
                let data = {
                    customerId: localStorage.getItem('userId'),
                    hotelId: this.hotelId,
                    roomType: this.roomType,
                    estimatedCheckIn: this.dateValue[0],
                    estimatedCheckOut: this.dateValue[1],
                    province: this.provinceVal,

                }
                post('/api/order/create',data)
                    .then(res => {
                        console.log(res);
                        if (res.data.code == 200) {
                            this.$message({
                                message: this.$t('hotelList.success'),
                                type: 'success'
                            });
                            this.dialogVisible = false
                          console.log("订单id：",res.data.data.id)
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
        }
    }
</script>

<style scoped>
    .room-type-radio .el-radio__label {
        display: inline-block;
        width: 85px;
    }
    .hotel-list-main {
        height: calc(100vh - 220px);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .hotel-list-main>div:first-child {
        background-color: #ecf5ff;
    }

    .t-nav {
        width: 100%;
        height: 68px;
        background: #fff;
        opacity: 0.8;
        z-index: 999;
        position: fixed;
        top: 0;
        left: 0;
    }

    .nav-content {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        height: 100%;
    }

    .nav-content ul {
        display: flex;
        align-items: center;
        height: 100%;
    }

    .nav-content li {
        font-size: 18px;
        margin: 0 30px;
    }

    .nav-bt {
        width: 123px;
        height: 34px;
        line-height: 34px;
        background: #28B535;
        border-radius: 16px;
        border: 0;
        color: #fff;
    }

    .nav-content a {
        color: #A1A09E;
    }

    .nav-content a:hover {
        color: #323230
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

    .hotel-list-bg h3 {
        margin-bottom: 25px;
        font-size: 36px;
        font-weight: 400;
        color: #FFFFFF;
    }

    .hotel-list-content {
        display: flex;
        justify-content: center;
        padding-top: 30px;
    }

    .hotel-list {

    }

    .hotel-list li {
        display: flex;
        margin-bottom: 30px;
    }

    .hotel-list li .li-l {
        width: 370px;
        height: 423px;
        margin: 0px 30px;
    }

    .hotel-list li .li-l img {
        width: 100%;
        height: 100%;
    }

    .hotel-list li .li-r {
        width: 360px;
        margin: 0px 30px;
        position: relative;
    }

    .hotel-list li .li-r h4 {
        font-size: 36px;
        margin-bottom: 15px;
        font-weight: 400;
    }

    .hotel-list li .li-r h6 {
        font-size: 14px;
        margin-bottom: 28px;
        font-weight: 400;
    }

    .hotel-list li .li-r div {
        font-size: 15px;
        color: #898989;
        margin-bottom: 20px;
        overflow: auto;
        max-height: 206px;
    }

    .hotel-list li .li-r button {
        position: absolute;
        left: 0;
        bottom: 0;
        width: 118px;
        height: 38px;
        line-height: 38px;
        background: #E1BD85;
        font-size: 12px;
        color: #FFFFFF;
        border-radius: 5px;
        border: none;
    }

    .hotel-list li:nth-child(even) .li-l {
        order: 2;
    }
    .mb-30 {
        margin-bottom: 30px;
    }


</style>