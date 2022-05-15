<template>
    <div>
        <!-- 导航栏 -->
        <div class="t-nav">
            <div class="nav-content">
                <ul>
                    <li><router-link to="/index">{{ $t('nav.index') }}</router-link></li>
                    <li><router-link to="/hotelList">{{ $t('nav.list') }}</router-link></li>
                    <li><router-link to="/collectionList">{{ $t('nav.collection') }}</router-link></li>
                    <li><router-link to="/orderList">{{ $t('nav.order') }}</router-link></li>
                    <li><router-link to="/recordList">{{ $t('nav.record') }}</router-link></li>
                    <li><router-link to="/wallet">{{ $t('nav.wallet') }}</router-link></li>
                    <li><router-link to="/login">{{ $t('nav.logOut') }}</router-link></li>
                    <li>
                        <el-link type="primary" @click="langSwitch('zh')">中</el-link>
                        <span>/</span>
                        <el-link type="primary" @click="langSwitch('en')">En</el-link>
                    </li>                    
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TopNav",
        methods: {
            langSwitch(value){
                localStorage.setItem('lang',value)
                this.$i18n.locale = value
                this.$router.go(0)

            }            
        }
    }
</script>

<style scoped>
    .t-nav {
        width: 100%;
        height: 55px;
        background: #fff;
        opacity: 0.9;
        z-index: 999;
        position: fixed;
        top: 0;
        left: 0;
        box-shadow: 0 0 3px;
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
        height: 100%;
        display: flex;
        align-items: center;
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
        color: #323230;
    }
    .nav-content span{
        margin: 2px;
        color: #A1A09E;
    }

</style>