<template>
    <div>
        <!-- 导航栏 -->
        <div class="t-nav">
            <div class="nav-content">
                <ul>
                    <li><router-link to="/index">首页</router-link></li>
                    <li><router-link to="/hotelList">酒店列表</router-link></li>
                    <li><router-link to="/orderList">我的订单</router-link></li>
                    <li><router-link to="/login">注销</router-link></li>
                    <!--<li><button class="nav-bt">注册/登录</button></li>-->
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TopNav"
    }
</script>

<style scoped>
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

</style>