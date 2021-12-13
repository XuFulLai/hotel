<template>
    <div>
        <!-- 背景和导航栏 -->
        <div class="top-bg">
            <!--<img src="../assets/images/index-bg.png" >-->
            <div class="t-nav">
                <div class="nav-content">
                    <ul>
                        <li><a href="#" title="">首页</a></li>
                        <li><a href="#" title="">酒店中心</a></li>
                        <li><a href="#" title="">酒店管理</a></li>
                        <!---->
                        <button class="nav-bt">注册/登录</button>
                    </ul>
                </div>
            </div>
        </div>

        <div class="d-flex align-items-center justify-content-center">
            <div class="index-center">
                <!-- 房源搜索模块 -->
                <div class="room-center">
                    <div class="room-left">
                        <!-- <img src="../assets/images/room.png" alt=""> -->
                    </div>
                    <div class="room-right">
                        <div class="room-rh-content">
                            <h1>房源预定</h1>
                            <p>预定独一无二的房源与体验。</p>
                            <input type="text" class="inp1" placeholder="包图市包图区设计大厦">
                            <div class="inp2">
                                <select class="sel">
                                    <option>入住日期</option>
                                    <option>1999</option>

                                </select>

                                <select class="sel">
                                    <option>退房日期</option>
                                    <option>2</option>
                                </select>
                            </div>
                            <select class="sel2">
                                <option>入住人数</option>
                                <option>2</option>
                            </select>
                            <button>搜索</button>
                        </div>
                    </div>
                </div>

                <!-- 热门城市 -->
                <h1 class="city-h">热门城市攻略</h1>
                <p class="city-p">汇集热门城市攻略  尽在包图</p>

                <div class="city-search">
                    <div class="city-sel">
                        <p>城市</p>
                        <div class="city-inp">
                            <select class="sel">
                                <option>海外游</option>
                                <option>海内游</option>
                            </select>
                        </div>
                    </div>
                    <div class="city-sel">
                        <p>房间大小</p>
                        <div class="city-inp">
                            <select class="sel">
                                <option>中型房间</option>
                                <option>大型房间</option>
                                <option>小型房间</option>
                            </select>
                        </div>
                    </div>
                    <div class="city-sel">
                        <p>主题</p>
                        <div class="city-inp">
                            <select class="sel">
                                <option>温馨</option>
                                <option>激情</option>
                                <option>恬静</option>
                            </select>
                        </div>
                    </div>

                    <em style="margin: auto;">搜索</em>
                </div>
                <!-- 城市轮播图 -->
                <div class="city-hot">
                    <div class="b-left"><</div>

                    <div class="city-bg1">
                        <h1>清迈</h1>
                        <p>泰国最文艺小城</p>
                        <p>人均￥<i>154</i>/晚</p>
                    </div>
                    <div class="city-bg2">
                        <h1>清迈</h1>
                        <p>泰国最文艺小城</p>
                        <p>人均￥<i>154</i>/晚</p>
                        <button>立即预约</button>
                    </div>
                    <div class="city-bg3">
                        <h1>伦敦</h1>
                        <p>感受英伦风情</p>
                        <p>人均￥<i>354</i>/晚</p>
                    </div>

                    <div class="b-right">></div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "ly"
    }
</script>

<style scoped>
    .t-nav{
             width: 100%;
             height: 98px;
             background: #fff;
             opacity: 0.7;
             z-index: 999;
             position: absolute;
             top: 0;
        }
    .nav-content{
    }
    .nav-content ul{
        display: flex;
        justify-content: right;
        line-height: 98px;
    }
    .nav-content li{
        font-size: 18px;
        font-family: 59;
        font-weight: 400;
        margin: 0 60px;   
    }
    .nav-bt{
        width: 123px;
        height: 34px;
        background: #28B535;
        border-radius: 16px;
        border: 0;
        color: #fff;
        margin:30px 60px;
    }
    .nav-content a{
        color: #A1A09E;
    }
    .nav-content a:hover{
        color: #323230
    }
    .top-bg img{
    width: 100%;
    background-size: cover;
}
    .index-center{
        width: 1000px;
        height: 1000px;
        /*margin: auto;*/
        position: relative;
    }
    .room-center{
        width: 1000px;
        height: 500px;
        background-color: #fff;
        box-shadow: 0px 5px 38px 0px rgba(67, 170, 253, 0.18);
        border-radius: 15px;
        position: relative;
        top: -100px;
        display: flex;
        justify-content: space-between;

    }
    .room-left{
        width: 50%;
        height: 100%;
        background: url(../assets/images/room.png);
        background-size: cover;
    }
    .room-left img{
        background-size: cover;
    }
    .room-right{
        width: 50%;
        height: 100%;
    }
    .room-rh-content{
        width: 400px;
        background-color: #fff;
        margin: 45px 54px;
    }
    .room-rh-content h1{
        height: 36px;
        font-size: 44px;
        font-weight: 400;
        color: #646464;
        line-height: 36px;
    }
    .room-rh-content p{
        height: 19px;
        font-size: 22px;
        font-family: 58;
        font-weight: 400;
        color: #646464;
        line-height: 36px;
        margin: 40px 0;
    }
    .inp1{
        height: 50px;
        width: 100%;
        color: #646464;
        font-size: 20px;
        font-family: 59;
        font-weight: 400;
        border: 1px solid #666666;
        border-radius: 4px; 
        /* padding-left: 10px; */
    }
    .inp2{
        display: flex;
        justify-content: space-between;
        margin: 30px 0;
    }
    .sel{
        width: 180px;
        height: 46px;
        border: 1px solid #DDDDDD;
        border-radius: 4px;
        color: #B6B6B6;
    }
    .sel2{
        height: 50px;
        width: 100%;
        border: 1px solid #DDDDDD;
        border-radius: 4px;
        color: #B6B6B6;
    }
    .room-rh-content button{
        width: 100%;
        height: 50px;
        background: linear-gradient(90deg, #40A5FD, #5ED8FD);
        border-radius: 24px;
        margin-top: 30px;
        font-size: 24px;
        font-weight: 400;
        color: #FFFFFF;
        border: 0;
        cursor: pointer;
    }
    .city-h{
        font-size: 45px;
        font-weight: 400;
        color: #666666;
        text-align: center;
    }
    .city-p{
        font-size: 16px;
        font-weight: 400;
        color: #666666;
        text-align: center;
        margin-top: 20px;
    }
    .city-search{
        width: 100%;
        height: 122px;
        background: #FFFFFF;
        box-shadow: 0px 5px 38px 0px rgba(16, 16, 16, 0.1);
        margin-top: 120px;
        display: flex;
        padding: 24px;
        padding-right: 0;
    }
    .city-search p{
        font-size: 24px;
        font-weight: 400;
        color: #B9B9B9;
        text-align: center;
    }
    .city-sel{
        width: 270px;
        height: 100%;
        border-right: 1px solid #ECECEC;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;

    }
    .city-inp{
        margin-bottom: 0;
        margin: auto;
    }
    .city-inp .sel{
        font-size: 34px;
        font-weight: 400;
        color: #656565;
        border: 0;
        text-align: center;
    }
    .city-hot{
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 60px;
        position: relative;
    }
    .city-bg1{
        background: url(../assets/images/city1.png);
        background-size: cover;
        width: 300px;
        height: 600px;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-end;
    }
    .city-bg2{
        background: url(../assets/images/city2.png);
        background-size: cover;
        width: 300px;
        height: 650px;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-end;
    }
    .city-bg3{
        background: url(../assets/images/city3.png);
        background-size: cover;
        width: 300px;
        height: 600px;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-end;
    }
    .city-hot h1{
        font-size: 35px;
        font-weight: bold;
        color: #FFFFFF;
    }
    .city-hot p{
        font-size: 13px;
        font-weight: 400;
        color: #FFFFFF;
        margin-bottom: 30px;
    }
    .city-hot i{
        font-size: 49px;
        font-weight: 400;
        color: #FFFFFF;
    }
    .city-bg2 button{
        width: 236px;
        height: 48px;
        background: linear-gradient(90deg, #3DA5FE, #5FDBFD);
        border-radius: 6px;
        font-size: 24px;
        font-weight: 400;
        color: #FFFFFF;
        margin-bottom: 30px;
        border: 0;
        cursor: pointer;
    }
    .b-left,.b-right{
        width: 66px;
        height: 66px;
        background-color: #ccc;
        border-radius: 50%;
        font-size: 50px;
        color: #fff;
        text-align: center;
        line-height: 66px;
    }
    .b-left{
       position: absolute;
       left: -150px;
    }
    .b-right{
       position: absolute;
       right: -150px;
    }
</style>