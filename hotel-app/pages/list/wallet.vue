<template>

	<view class='container'>
		<!-- 密码弹窗 -->
		<!-- 对象样式 -->
		<u-popup :show="flag" @close="close" @open="open" mode="center" :closeable='false' :closeOnClickOverlay='false'
			round='5' overlayOpacity='0.7' :customStyle="{ marginBottom: '200rpx'}">

			<view class="back_icon">
				<!-- //返回按钮 -->
				<u-icon name='arrow-leftward' @click="backToIndex()" size="24"></u-icon>
			</view>



			<view class="pwd_container">
				<view class="wrap">
					<view class="wrap-header">
						<view class="wrap-header-item">请输入密码</view>
					</view>
					<view>
						<view class="input-wrap" style="font-size: 42rpx;">
							<input class="input1" type="number" v-for="(item, index) in input_len" @tap="onInput"
								:key="index" disabled :value="input_val.length >= index + 1 ? input_val[index] : ''"
								password="true" />
						</view>
						<!-- 实际监听 -->
						<input class="input2" v-if="isFocus" @blur="blurInput" @input="setInput" v-model="input_val"
							:maxlength="input_len" :focus="true" />
					</view>
					<view class="tip">
						<view class="tip_icon">
							<u-icon name='info-circle' size="16" color="#9b9ba5"></u-icon>
						</view>
						<text>首次设置密码6位数字密码</text>
					</view>
				</view>



			</view>
		</u-popup>

		<!-- 钱包详情 -->
		<view class="wallet">
			<view class="change">
				<text @click="openCpwd()" style="color: #898991;">更改密码</text>
				<u-popup :show="showCpwd" @close="closeCpwd" @open="openCpwd" round="10" mode="center">
					<view class="change_pwd">
						<u--input class="input" type='password' placeholderStyle='color:#aca5a5' placeholder='原密码'
							fontSize='40rpx' v-model="input_val"></u--input>
						<u--input class="input" type='password' placeholderStyle='color:#aca5a5' placeholder='新密码'
							fontSize='40rpx' v-model="newPwd" @confirm="login"></u--input>
					</view>
					<u-button type="primary" text="确认修改" size='large' @click="changePwd"></u-button>
				</u-popup>
			</view>
			<view class="wallet_icon">
				<u-icon name="rmb-circle-fill" color="#daba01" size="60"></u-icon>
			</view>

			<text>我的零钱</text>

			<view class="wallet_number">
				<u-icon name='rmb' color="#000000" size="22" :bold='true'></u-icon>
				<p v-show='rmb'>{{rmb}}.00</p>
				<p v-show='!rmb'>0.00</p>
			</view>

			<!-- 底部按钮 -->
			<view class="rmb_button">
				<view class="chongzhi">
					<u-button text="充值" type='success'></u-button>
				</view>
				<view class="tixian">
					<u-button text="提现"></u-button>
				</view>
			</view>
		</view>

		<!-- 密码错误提示 -->
		<u-modal :show="showNotify" @confirm="confirm" ref="uModal" :asyncClose="true" :title="notifyText"
			confirmText='重试'>
			<view class="slot-icon">
				<u-icon name='close-circle-fill' size='40' color="#ff0000"></u-icon>
			</view>
		</u-modal>

	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	export default {
		data() {
			return {
				notifyText: '',
				newPwd: '',
				showCpwd: false,
				input_len: 6, // 密码长度
				input_val: '', // 密码的值
				isFocus: false,
				flag: true, //密码框
				rmb: '',
				showNotify: false
			}
		},
		onLoad() {
			this.onInput()
		},
		methods: {

			async changePwd() {
				const pwd = parseInt(this.newPwd)
				if (isNaN(pwd)) {
					this.newPwd = ''
					this.notifyText = '密码必须为6位纯数字'
					this.showNotify = true
				} else {
					const token = db.get('token')
					const res = await this.$myRequest({
						url: '/api/wallet/editPwd',
						method: 'POST',
						header: {
							'Content-Type': 'application/x-www-form-urlencoded',
							'Authorization': token
						},
						data: {
							"oldPwd": this.input_val,
							"newPwd": this.newPwd
						}
					})
					if (res.data.code !== '200') {
						this.showNotify = true
						this.notifyText = '密码错误，请重新输入'
					} else {
						this.closeCpwd()
					}
					this.newPwd = ''
					this.input_val = ''
				}

			},
			openCpwd() {
				this.showCpwd = true
				this.input_val = ''
			},
			closeCpwd() {
				this.showCpwd = false
			},
			confirm() {
				this.showNotify = false
				this.input_val = ''
				this.onInput()
			},
			open() {
			},
			close() {
				this.flag = false
				// console.log('close');
			},
			onInput() {
				this.isFocus = true;
			},
			setInput(e) {
				this.input_val = e.detail.value;
				if (this.input_val.length == 6) {
					this.getWallet()
				}
			},
			blurInput() {
				this.isFocus = false;
			},
			// 取消输入密码
			backToIndex() {
				uni.navigateTo({
					url: '../index/index'
				})
			},
			async getWallet() {
				const token = db.get('token')
				const res = await this.$myRequest({
					url: '/api/wallet/get',
					method: 'POST',
					header: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'Authorization': token
					},
					data: {
						"pwd": this.input_val
					}
				})
				this.input_val = ''
				console.log(res.data.code);
				if (res.data.code != '200') {
					this.notifyText = '密码错误，请重新输入'
					this.showNotify = true
				} else {
					uni.showToast({
						icon: 'success'
					})
					this.rmb = res.data.data.balance
					this.close()
				}
			},

		}
	}
</script>

<style lang="scss">
	.change {
		position: absolute;
		top: 5px;
		right: 5px;

		.change_pwd {
			width: 700rpx;
			height: 350rpx;
		}
	}

	.u-input {
		margin-top: 50rpx;
		background-color: #e7e7e7;
		padding: 13px 20px !important;
	}

	.container {
		height: 1400rpx;
	}

	.rmb_button {
		height: 200rpx;
		margin-top: 500rpx;
	}

	.chongzhi,
	.tixian {
		width: 320rpx;
		margin: 15rpx;
	}

	.slot-icon {
		margin-top: 20rpx;
	}

	.tip {
		position: absolute;
		bottom: 100rpx;
		left: 25%;
		align-items: center;
		justify-content: center;
		display: flex;
		font-size: 14px;
		height: 60rpx;

		text {
			color: #9b9ba5;
		}

		.tip_icon {
			height: 40rpx;
			width: 40rpx;
			margin-top: 10 rpx;
			margin-right: 5rpx;
		}
	}

	.wrap {
		padding: 0 30rpx;
	}

	.wrap-header {
		padding: 50rpx 0;

		.wrap-header-item {
			font-size: 42rpx;
			color: #898991;
			// margin-bottom: 17rpx;
		}
	}

	.input-wrap {
		display: flex;
		justify-content: space-between;
		align-items: center;

		.input1 {
			width: 80rpx;
			height: 80rpx;
			border-bottom: 1px solid #9b9ba5;
			justify-content: center;
			text-align: center;
		}
	}

	.input2 {
		height: 0;
		width: 0;
	}

	.badge {
		width: 100%;
		position: absolute;
		z-index: 99;
	}

	.u-divider {
		position: relative;
		bottom: 30rpx;
	}

	.back_icon {
		width: 50rpx;
		height: 50rpx;
		margin: 20rpx;
	}

	.wallet {
		height: 500px;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 100rpx;
		padding: 100rpx 40rpx 0px 40rpx;

		.wallet_icon {
			margin-bottom: 60rpx;
		}

		text {
			color: #000000;
			font-weight: bold;
		}

		.wallet_number {
			margin-top: 40rpx;
			display: flex;
			align-items: flex-start;

			p {
				font-size: 35px;
				font-weight: bold;
			}
		}
	}

	.pwd_container {
		width: 600rpx;
		height: 400rpx;
	}

	.pwdInput {
		position: absolute;
		top: 40%;
		left: 25%;
	}
</style>
