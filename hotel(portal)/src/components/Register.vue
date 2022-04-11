<template>
    <div class="mt-10">
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.account') }}:</p>
            <input v-model="username" type="text" :placeholder="$t('login.accountTips')">
        </div>
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.password') }}:</p>
            <input v-model="password" type="password" :placeholder="$t('login.passwordTips')">
        </div>
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.passwordAgain') }}:</p>
            <input v-model="againPassword" type="password" :placeholder="$t('login.passwordAgainTips')">
        </div>
        <div class="d-flex align-items-center justify-content-center">
            <button @click="register">{{  $t('login.register') }}</button>
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
                    this.$message.error(this.$t('login.passwordError'));
                } else {
                    post('/open/auth/register',data)
                        .then(res => {
                            console.log(res);
                            if (res.data.code == 200) {
                                this.$message({
                                    message: this.$t('login.success'),
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
        /* justify-content: space-between; */
        height: 50px;
        border-bottom: 1px solid #ccc;
    }
    .inp p{
        min-width: 70px;
        white-space: nowrap;
        margin-right: 10px;
    }

    .inp input {
        width: 260px;
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