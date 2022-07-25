<template>
	<view v-if="!confirmFlag" class="content">
		<button style="width: 30vw;margin-top: 100rpx;margin-bottom: 30rpx;" @click="scan">扫码登录</button>
		<button @click="showInfo">test</button>
		<p style="color:red;margin-top: 12rpx;">{{msg}}</p>
		<p>{{token}}</p>
	</view>
	<view v-else>
		<button style="background-color: #007AFF;color: #FFFFFF;width: 30vw;margin-top: 100rpx;margin-bottom: 30rpx;" @click="confirm">确认登录</button>
		<p style="color:red;margin-top: 12rpx;word-wrap: break-word;">{{msg}}</p>
		<view style="width: 30vw;"><button @click="showInfo">test</button></view>
		<p>{{token}}</p>
	</view>
</template>

<script>
	import request from '@/common/request.js'
	import c from '@/common/const.js'
	export default {
		data() {
			return {
				title: 'Hotel',
				msg: '',
				confirmFlag: false,
				code: undefined,
				token: undefined
			}
		},
		onLoad() {
			console.log(c.token)
			
		},
		methods: {
			showInfo() {
				console.log("c.token======",c.token)
				if(c.token) {
					this.msg = "已成功获取到Token"
				}else {
					this.msg = "Token is undefine"
				}
			},
			login() {
				let data = {
					username: this.username,
					password: this.password
				}
				request('/auth/login',data,"POST").then(res => {
					console.log(res)
					if(res.code === "200") {
						c.setToken(res.data.token)
						this.token = c.token
					}
				})
				console.log("login after===")
				
			},
			scan() {
				let that = this
				uni.scanCode({
				    onlyFromCamera: true,
				    success: function (res) {
				        console.log('条码类型：' + res.scanType);
				        console.log('条码内容：' + res.result);
						that.code = res.result
						let data = {
							type: "1",
							qrCode: res.result,
							token: c.token
						}
						let header = {
							Authorization: c.token
						}
						request('/auth/scan',data,"POST",header).then(res => {
							console.log(res)
							console.log("code",res.code)
							if(res.code === "200") {
								that.confirmFlag = true
							}else {
								that.msg = res.msg
							}
						})
				    }
				});
			},
			confirm() {
				let that = this
				let data = {
					type: "2",
					qrCode: that.code,
					token: c.token
				}
				let header = {
					Authorization: c.token
				}
				request('/auth/scan',data,"POST",header).then(res => {
					console.log(res)
					console.log("code",res.code)
					if(res.code === "200") {
						that.confirmFlag = false
					}else {
						that.msg = res.msg
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
