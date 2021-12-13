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
        <div class="inp font-14">
            <p class="m-0">确认密码：</p>
            <input v-model="againPassword" type="password" placeholder="请再次输入密码">
        </div>
        <div class="d-flex align-items-center justify-content-center">
            <button @click="register">注册</button>
        </div>

    </div>
</template>

<script>
    import {get,post} from "../utils/request";

    export default {
        name: "Register",
        data(){
            return {
                username: '',
                password: '',
                againPassword: '',
            }
        },
        mounted(){},
        methods: {
            register(){
                let data = {
                    username: this.username,
                    password: this.password
                }
                if (this.password != this.againPassword){
                    this.$message.error('两次密码输入不一致');
                } else {
                    post('/open/auth/register',data)
                        .then(res => {
                            console.log(res);
                            if (res.data.code == 200) {
                                this.$message({
                                    message: '注册成功！',
                                    type: 'success'
                                });
                            }
                        })
                        .catch(err => {
                            console.log(err);
                        })
                }
            }
        }
    }
</script>

<style scoped>
    .inp {
        display: flex;
        align-items: center;
        justify-content: space-between;
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
        margin-top: 20px;
        cursor: pointer;
    }
</style>