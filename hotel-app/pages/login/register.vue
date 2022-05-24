<template>
	<view class="container">
		
		<view class="title">
			<h1>注册</h1>
		</view>
		<view class="username" style="padding: 13px 20px;">
			<u--input 
			type='text'
			clearable
			placeholder='账号'
			placeholderStyle='color:#aca5a5 border:3px solid #545454'
			fontSize='40rpx'
			v-model="username"
			></u--input>
		</view>
		<view class="nickname" style="padding: 13px 20px;">
			<u--input 
			type='text'
			clearable
			placeholder='昵称'
			placeholderStyle='color:#aca5a5'
			fontSize='40rpx'
			v-model="nickname"
			></u--input>
		</view>
		<view class="password">
			<u--input 
			class="input"
			type='password'
			placeholderStyle='color:#aca5a5'
			placeholder='密码'
			fontSize='40rpx'
			v-model="password"
			></u--input>
		</view>
		<view class="password">
			<u--input 
			class="input"
			type='password'
			placeholderStyle='color:#aca5a5'
			placeholder='确认密码'
			fontSize='40rpx'
			v-model="cpassword"
			></u--input>
		</view>
		<view class="submit_button">
			<u-button class="custom-style" text="注册" color='#41B883' @click="register"></u-button>
		</view>
	
	
	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	export default {
		data() {
			return {
				nickname:'',
				username:'',
				password:'',
				cpassword:'',
			}
		},
		methods: {
			async login(){
				 let userData = {
					"username":this.username,
					"password":this.password,
				}
				const res = await this.$myRequest({
					url:'/auth/login',	
					method:'POST',
					data:userData
				})
				// res.data.data.userInfo.heads = 'https://images-new-cdn.123rf.com.cn/450wm/zdeneksasek/zdeneksasek1808/zdeneksasek180800088.jpg'
				// 吧user Info和token存放到localStorage中
				db.save('userInfo',res.data.data.userInfo)
				db.save('token',res.data.data.token)
				console.log(res.data.data)
					uni.redirectTo({
						url:'../index/index'
					})
			},
			async register(){
				console.log(this.password.length)
				var re =  /^[0-9a-zA-Z]*$/g;
				if(this.username.length == 0){
					uni.showToast({
						icon:'error',
						title:"用户名不能为空！"
					})
				}else if(this.password != this.cpassword){
					uni.showToast({
						icon:'error',
						title:"两次输入的密码不一致！"
					})
				}else if(this.password.length < 6 || this.password.length >12){
					uni.showToast({
						icon:'error',
						title:"密码需在6到12位之间！"
					})
				}else if(!re.test(this.password)){
					uni.showToast({
						icon:'error',
						title:"密码必须由数字和字母组成！"
					})
				}else{
					let registerData = {
						username:this.username,
						password:this.password,
						nickname:this.nickname || ''
					}
					const res = await this.$myRequest({
						url:'/open/auth/register',
						method:'POST',
						data:registerData
					})
					if(res.data.code == '200'){
						console.log(res);
						uni.showToast({
							title:'ok',
						})
					this.login()
					}else{
						uni.showToast({
							title:res.data.msg,
						})
					}
				}
			}
		}
	}
</script>

<style lang="scss">
.custom-style {
		width: 322px;
		margin-top: 80rpx;
		color: 'green';
	}
.container{
	display: flex;
	flex-direction: column;
	align-items: center;
}
.username{
	margin: 60rpx 0rpx 0rpx 0px;
	width: 620rpx;
	
}
.nickname{
	margin: 20rpx 0rpx 0rpx 0px;
	width: 620rpx;
	
}
.u-input{
	padding: 13px 20px!important;
	background-color: #FFFFFF;
}
.password{
	width: 620rpx;
	margin-top: 50rpx;
	margin-bottom: 30rpx;
}

.title{
	align-self: flex-start;
	margin: 150rpx 60rpx 60rpx 80rpx;
	h1{
		font-size: 80rpx;
		color: #41B883;
	}
}
</style>
