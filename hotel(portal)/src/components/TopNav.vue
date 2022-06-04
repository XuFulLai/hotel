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
            <i @click="drawer = true" class="el-icon-menu menu-icon"></i>
            <p class="app-menu-title font-26">{{ $t('nav.menu') }}</p>
            <el-drawer
                :visible.sync="drawer"
                direction="ltr"
                :size="drawerWidth"
                :with-header="false"
                :modal-append-to-body="false">
                <ul class="drawer-list">
                    <li><router-link to="/index">{{ $t('nav.index') }}</router-link></li>
                    <li><router-link to="/hotelList">{{ $t('nav.list') }}</router-link></li>
                    <li><router-link to="/collectionList">{{ $t('nav.collection') }}</router-link></li>
                    <li><router-link to="/orderList">{{ $t('nav.order') }}</router-link></li>
                    <li><router-link to="/recordList">{{ $t('nav.record') }}</router-link></li>
                    <li><router-link to="/wallet">{{ $t('nav.wallet') }}</router-link></li>
                    <li><router-link to="/login">{{ $t('nav.logOut') }}</router-link></li>
                    <li class="lang-link">
                        <el-link type="primary" @click="langSwitch('zh')">中</el-link>
                        <span class="ml-5 mr-5">/</span>
                        <el-link type="primary" @click="langSwitch('en')">En</el-link>
                    </li>                    
                </ul>                
            </el-drawer>            
        </div>
    </div>
</template>

<script>
    export default {
        name: "TopNav",
        data(){
            return {
                drawer: false,
                drawerWidth: 100
            }
        },
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
        font-size: 1.8rem;
        margin: 0 2.5rem;
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

    .nav-mask {
        display: none;
    }
    .menu-icon {
        display: none;
    }
    .drawer-list {
        display: none;
    }
    .app-menu-title {
        display: none;
    }

    /* 媒体查询 Start */
    @media screen and (max-width: 767.9px) { /* 页面测试无法显示767，实际是767.2px */
        .t-nav {
            display: flex;
            align-items: center;
            height: 45px;
            opacity: 1;
        }
        .nav-content {
            display: none;
        }
        .menu-icon {
            display: flex;
            font-size: 35px;
            margin-left: 1rem;
        }
        .drawer-list {
            display: flex;
            flex-direction: column;
            overflow: auto;
        }
        .drawer-list li {
            border-bottom: 1px solid #999;
        }
        .drawer-list li a {
            height: 50px;
            display: flex;
            align-items: center;
            justify-content: center;            
            color: #A1A09E;
            font-size: 14px;

        }
        .lang-link {
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .app-menu-title {
            display: inline-block;
        }
        /* .nav-content ul {
            width: 100%;
            flex-direction: column;
        }
        .nav-content ul li {
            margin: 0;
        }
        .nav-mask {
            display: block;
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 100px;
            overflow: auto;
            margin: 0;
            background-color: black;
            opacity: 0.5;
        } */

    }

    /* 媒体查询 End */
</style>