<template>

    <div class="bg">
        <img src="../assets/images/bg.jpg" alt="">
        <div class="login-center">

            <div class="login-l">
                <img src="../assets/images/login-img.png" alt="">
            </div>

            <div class="login-r">
                <h3>{{  $t('login.title') }}</h3>
                <p class="mt-5">{{  $t('login.text') }}</p>

                <ul class="login-list d-flex align-items-center">
                    <li @click="skip('signIn')" :class="status==0?'active':''">{{  $t('login.login') }}</li>
                    <li @click="skip('register')" :class="status==1?'active':''">{{  $t('login.register') }}</li>
                </ul>

                <router-view></router-view>
            </div>

            <div class="lang-switch">
                <!-- <el-link type="primary" @click="$i18n.locale = 'zh'">中</el-link>
                <span>/</span>
                <el-link type="primary" @click="$i18n.locale = 'en'">English</el-link> -->
                <el-link type="primary" @click="langSwitch('zh')">中</el-link>
                <span>/</span>
                <el-link type="primary" @click="langSwitch('en')">English</el-link>
                <!-- <a href="#" @click="$i18n.locale = 'zh'">中</a>
                <span>/</span>
                <a href="#" @click="$i18n.locale = 'en'">English</a> -->
            </div>

        </div>
    </div>

</template>

<script>
    import {get, post} from "../utils/request";
    import SignIn from "../components/SignIn"
    import Register from "../components/Register"

    export default {
        name: "login",
        components: {
            SignIn,
            Register
        },
        data() {
            return {
                status: 0,
                username: '',
                password: '',
            }
        },
        mounted(){
            //页面挂载完毕清除token和用户id
            localStorage.removeItem('Token')
            localStorage.removeItem('userId')
        },
        methods: {
            //路由
            skip(val){
                if (val == 'signIn') {
                    this.status = 0
                } else if (val == 'register') {
                    this.status = 1
                }
                this.$router.push({
                    name: val,
                })
            },
            langSwitch(value){
                localStorage.setItem('lang',value)
                this.$i18n.locale = value
                this.$router.go(0)

            }

        }
    }
</script>

<style scoped>
    * {
        padding: 0;
        margin: 0;
    }

    .bg {
        width: 100vw;
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .bg img {
        width: 100%;
        height: 100%;
        opacity: 0.6;
        position: absolute;
    }

    .login-center {
        width: 700px;
        height: 300px;
        position: relative;
        background-color: #fff;
        box-shadow: 0px 0px 40px -10px #000;
        border-radius: 20px;
        padding: 60px 50px;
        display: flex;
        z-index: 9;
        justify-content: space-between;
    }

    .login-l img {
        width: 300px;
        height: 300px;
        opacity: 1;
    }

    .login-l, .login-r {
        width: 100%;
    }
    .login-r {
        min-width: 370px;
    }

    .login-r h3 {
        font-size: 25px;
        font-weight: normal;
        margin-top: 10px;
    }

    .login-r p {
        color: #ccc;
    }

    .inp {
        display: flex;
        align-items: center;
        height: 50px;
        border-bottom: 1px solid #ccc;
    }

    .inp input {
        width: 300px;
        height: 30px;
        outline: none;
        border: none;
        color: #ccc;
        font-size: 16px;
    }

    .login-r button {
        width: 300px;
        height: 35px;
        background-color: #4f93fe;
        color: #fff;
        border-radius: 50px;
        border: none;
        margin-top: 50px;
        cursor: pointer;
    }

    .login-list {}
    .login-list li{
        padding: 8px;
        font-size: 18px;
        border-bottom: 4px solid transparent;
        cursor: pointer;
    }
    .login-list li.active{
        border-bottom-color: #009688;
    }
    .login-list li a {
        color: #333333;
    }

    .lang-switch {
        position: absolute;
        right: 20px;
        top: 15px;
        display: flex;
        align-items: center;
    }
    .lang-switch span {
        margin: 0 2px;
    }
</style>