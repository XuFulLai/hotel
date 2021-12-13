<template>
    <div class="mt-10">
        <div class="inp font-14">
            <p class="m-0">账号：</p>
            <input v-model="username" type="text" placeholder="请输入用户名">
        </div>
        <div class="inp font-14">
            <p class="m-0">密码：</p>
            <input v-model="password" type="password" placeholder="请输入密码">
        </div>
        <div class="d-flex align-items-center justify-content-center">
            <button @click="login">登录</button>
        </div>

    </div>
</template>

<script>
    import {get,post} from "../utils/request";

    export default {
        name: "SignIn",
        data(){
            return {
                username: 'admin',
                password: '123456',
            }
        },
        mounted(){

        },
        methods: {
            //登录
            login() {
                let userData = {
                    username: this.username,
                    password: this.password,
                }
                post('/auth/login', userData)
                    .then(res => {
                        console.log('登录信息:',res);
                        //判断返回的数据是否存在token和用户id
                        if (res.data.data.token && res.data.data.userInfo.id ) {
                            // 将token及用户id存放到Vuex中
                            this.$store.commit('SAVE_TOKEN',res.data.data.token)
                            this.$store.commit('SAVE_USER_ID',res.data.data.userInfo.id)
                            //路由跳转
                            this.$router.push({
                                name: 'index',
                            })

                        } else {
                            console.log('token或用户id不存在，登录失败');
                        }
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }
        }
    }
</script>

<style scoped>
    .inp {
        display: flex;
        align-items: center;
        height: 50px;
        border-bottom: 1px solid #ccc;
    }
    .inp p{
        min-width: 70px;
        white-space: nowrap;
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
        margin-top: 71px;
        cursor: pointer;
    }
</style>