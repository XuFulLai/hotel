<template>
    <div style="background-color: #ecf5ff;min-height: 100vh">

        <!-- 导航栏 -->
        <top-nav></top-nav>

        <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
            <img src="../assets/images/hotel-list-bg.png" alt="">
            <h3>酒店列表</h3>
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
                                <div v-html="item.otherPolicy"></div>
                                <button @click="hotelDetails(index,item.id)">选择此酒店</button>
                            </div>
                        </li>
                    </ul>
                </div>

                <div v-if="pageNum > 5" class="d-flex align-items-center justify-content-center mb-30">
                    <el-pagination
                            background
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
                title="预定房间"
                center
                :visible.sync="dialogVisible"
                width="570px">
            <div class="contont">
                <div class="d-flex align-items-center mb-15">
                    <p class="w-100 text-left">选择日期:</p>
                    <div class="block">
                        <el-date-picker
                                v-model="dateValue"
                                type="daterange"
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </div>
                </div>
                <div class="d-flex align-items-center mb-15">
                    <p class="w-100 text-left">选择房型:</p>
                    <div>
                        <el-radio v-for="(item,index) in roomTypeList" v-model="roomType" :label="item.id">
                            {{ item.roomTypeName }}
                        </el-radio>
                    </div>
                </div>
                <div class="d-flex align-items-center mb-15">
                    <p class="w-100 text-left">来自省份:</p>
                    <el-select v-model="provinceVal" placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirm">确 定</el-button>
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
        data() {
            return {
                provinceVal: '',
                options: [
                    {
                    value: '北京市',
                    label: '北京市'
                }, {
                    value: '上海市',
                    label: '上海市'
                }, {
                    value: '天津市',
                    label: '天津市'
                }, {
                    value: '重庆市',
                    label: '重庆市'
                }, {
                    value: '河北省',
                    label: '河北省'
                }, {
                    value: '山西省',
                    label: '山西省'
                }, {
                    value: '内蒙古自治区',
                    label: '内蒙古自治区'
                }, {
                    value: '辽宁省',
                    label: '辽宁省'
                }, {
                    value: '吉林省',
                    label: '吉林省'
                }, {
                    value: '黑龙江省',
                    label: '黑龙江省'
                }, {
                    value: '江苏省',
                    label: '江苏省'
                }, {
                    value: '浙江省',
                    label: '浙江省'
                }, {
                    value: '安徽省',
                    label: '安徽省'
                }, {
                    value: '福建省',
                    label: '福建省'
                }, {
                    value: '江西省',
                    label: '江西省'
                }, {
                    value: '山东省',
                    label: '山东省'
                }, {
                    value: '河南省',
                    label: '河南省'
                }, {
                    value: '湖北省',
                    label: '湖北省'
                }, {
                    value: '湖南省',
                    label: '湖南省'
                }, {
                    value: '广东省',
                    label: '广东省'
                }, {
                    value: '广西壮族自治区',
                    label: '广西壮族自治区'
                }, {
                    value: '海南省',
                    label: '海南省'
                }, {
                    value: '四川省',
                    label: '四川省'
                }, {
                    value: '贵州省',
                    label: '贵州省'
                }, {
                    value: '云南省',
                    label: '云南省'
                }, {
                    value: '西藏自治区',
                    label: '西藏自治区'
                }, {
                    value: '陕西省',
                    label: '陕西省'
                }, {
                    value: '甘肃省',
                    label: '甘肃省'
                }, {
                    value: '宁夏回族自治区',
                    label: '宁夏回族自治区'
                }, {
                    value: '青海省',
                    label: '青海省'
                }, {
                    value: '新疆维吾尔族自治区',
                    label: '新疆维吾尔族自治区'
                }, {
                    value: '台湾省',
                    label: '台湾省'
                }, {
                    value: '香港',
                    label: '香港'
                }, {
                    value: '澳门',
                    label: '澳门'
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
                post('/api/room/getRoomTypeList', data)
                    .then(res => {
                        console.log(res);
                        this.dialogVisible = true
                        this.roomTypeList = res.data.data
                    })
                    .catch(err => {
                        console.log(err);
                    })

                // this.$router.push({
                //     name: 'hotelDetails',
                //     params: {
                //         index: index,
                //         hotleId: id
                //     }
                // })
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
                                message: '预定成功！',
                                type: 'success'
                            });
                            this.dialogVisible = false
                          console.log("订单id：",res.data.data.id)
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