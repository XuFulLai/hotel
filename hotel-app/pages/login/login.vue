<template>
	<view class="container">
		
	<view class="logo">
		<image src="../../static/logo.png" mode=""></image>
	</view>
	<view class="hotel_name">
		<u--text size='60' text='绿帽子酒店'></u--text>
		<p>Welcome欢迎登录</p>
	</view>
	<view class="username" style="padding: 13px 20px;">
		<!-- <text >账号：</text> -->
		<u--input 
		type='text'
		clearable
		placeholder='账号'
		placeholderStyle='color:#aca5a5'
		fontSize='40rpx'
		v-model="username"
		border="bottom"
		></u--input>
	</view>
	<view class="password">
		<!-- <text >密码：</text> -->
		<u--input 
		class="input"
		type='password'
		placeholderStyle='color:#aca5a5'
		placeholder='密码'
		fontSize='40rpx'
		v-model="password"
		@confirm="login"
		></u--input>
	</view>
	<view class="register">
		<navigator url="./register" size='10px'>还没有账号？点击注册</navigator>
	</view>
	<view class="submit_button">
		<button 
		<u-button class="custom-style" text="登录" color='#41B883' @click="login"></u-button>
		</button>
	</view>

	
	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	export default {
		data() {
			return {
				username:"zyx",
				password:"123456",
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
			}
			
		}
	}
</script>

<style lang="scss">
	.custom-style {
			width: 322px;
			margin-top: 80rpx;
		}
		page{
			background-color:#FFFFFF;
		}
.container{
	display: flex;
	flex-direction: column;
	align-items: center;
	
}
.logo{
	width: 150rpx;
	height: 150rpx;
	margin-top: 150rpx;
	margin-bottom: 60rpx;
	image{
		width: 100%;
		height: 100%;
	}
}

.username{
	margin: 60rpx 0rpx 0rpx 0px;
	width: 620rpx;
}
.u-input{
	background-color: #e7e7e7;
	padding: 13px 20px!important ;
}
.password{
	width: 620rpx;
}

.register uni-navigator{
		margin-top:60rpx !important ;
		font-size: 30rpx;
		color: #41B883;
		align-self: flex-end;
}
</style>
