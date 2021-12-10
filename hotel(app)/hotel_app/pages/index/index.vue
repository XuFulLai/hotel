<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			<text class="title">{{title}}</text>
		</view>
		<view>
			<p>账号</p>
			<input v-model="username" type="text" placeholder="请输入账号" />
			<view style="height: 20rpx;"></view>
			<p>密码</p>
			<input v-model="password" type="password" placeholder="请输入密码" />
		</view>
		<view style="margin-top: 12rpx;">
			<button @click="login">登录</button>
		</view>
	</view>
</template>

<script>
	import request from '../../common/request.js'
	import c from '../../common/const.js'
	export default {
		data() {
			return {
				title: 'Hotel',
				username: undefined,
				password: undefined
			}
		},
		onLoad() {

		},
		methods: {

			login() {
				let data = {
					username: this.username,
					password: this.password
				}
				request('/auth/login', data, "POST").then(res => {
					console.log(res)
					if (res.code === "200") {
						c.setToken(res.data.token)
						uni.redirectTo({
							url: "./scan/index" 
						})
					}
				})
			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
