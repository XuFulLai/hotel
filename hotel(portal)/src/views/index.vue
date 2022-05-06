<template>
    <div>

        <!-- 导航栏 -->
        <top-nav></top-nav>

        <div class="carousel-box">
            <el-carousel trigger="click" height="450px">
                <el-carousel-item>
                    <img style="width: 100%;height: 100%;" src="../assets/images/index-bg-1.png" >
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width: 100%;height: 100%;" src="../assets/images/index-bg-5.png" >
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width: 100%;height: 100%;" src="../assets/images/index-bg-2.png" >
                </el-carousel-item>
            </el-carousel>
        </div>

        <div class="index-main">

            <div class="reserve-box d-flex justify-content-center">

                <!-- 房源搜索模块 -->
                <div class="room-center">
                    <div class="room-left">
                        <img src="../assets/images/room.png" alt="">
                    </div>
                    <div class="room-right">
                        <div class="room-rh-content d-flex flex-column justify-content-between">
                            <div>
                                <h1>{{ $t('index.title') }}</h1>
                                <p>{{ $t('index.content') }}</p>
                                <el-input v-model="hotelName" :placeholder="$t('index.tips')"></el-input>
                            </div>
                            <button @click="search">{{ $t('index.search') }}</button>
                        </div>
                    </div>
                </div>

            </div>

            <!-- 页脚 -->
            <Footer></Footer>

        </div>






    </div>
</template>

<script>
    import {get,post} from "../utils/request";
    import TopNav from '../components/TopNav'
    import Footer from '../components/Footer'

    export default {
        name: "index",
        components: {
            TopNav,
            Footer
        },
        data() {
            return {
                hotelName: ''
            }
        },
        mounted(){},
        methods: {
            search(){
                let data = {
                    name: this.hotelName,
                    page: {
                        page: 1,
                        size: 5
                    },
                }
                post('/api/hotel/page',data)
                    .then( res => {
                        console.log(res);
                        console.log(typeof res.data.data.records)
                        if (res.data.code == 200) {
                            this.$router.push({
                                name: 'hotelList',
                                params: {
                                    hotelInfo: JSON.stringify(res.data.data.records)
                                }
                            })
                        }

                    })
                    .catch( err => {

                    })
            }
        }
    }
</script>

<style scoped>

    .index-main {
        height: calc(100vh - 450px);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .reserve-box {
        margin-top: -135px;
        margin-bottom: 80px;
    }
    .room-center{
        width: 800px;
        height: 430px;
        background-color: #fff;
        box-shadow: 0px 5px 38px 0px rgba(67, 170, 253, 0.18);
        border-radius: 15px;
        display: flex;
        z-index: 99;
    }
    .room-left{
        width: 50%;
        height: 100%;
    }
    .room-left img {
        width: 100%;
        height: 100%;
    }
    .room-right{
        width: 50%;
        height: 100%;
    }
    .room-rh-content{
        width: 100%;
        height: 100%;
        background-color: #fff;
        border-top-right-radius: 15px;
        border-bottom-right-radius: 15px;
        padding: 50px 40px;
        box-sizing: border-box;
    }
    .room-rh-content h1{
        font-size: 44px;
        font-weight: 400;
        color: #646464;
    }
    .room-rh-content p{
        font-size: 22px;
        color: #646464;
        margin: 50px 0;
    }
    .room-rh-content button{
        width: 100%;
        height: 50px;
        background: linear-gradient(90deg, #40A5FD, #5ED8FD);
        border-radius: 24px;
        margin-top: 30px;
        font-size: 24px;
        color: #FFFFFF;
        border: 0;
    }

</style>