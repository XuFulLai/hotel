<template>
	<view class="container">
		<view class="badge">
			<u-notice-bar v-show="hotel.badge" :text="hotelBadge" mode="closable"></u-notice-bar>
		</view>
		<view class="image">
			<!-- <image
				src="https://images-new-cdn.123rf.com.cn/450wm/naturestudio/naturestudio2103/naturestudio210300005.jpg"
				mode=""></image> -->
			<image :src="hotel.cover" mode=""></image>
		</view>
		<view class="header">
			<view class="position">
				<h2>{{hotel.name}}</h2>
				<rich-text :nodes="hotel.address"></rich-text>
			</view>
			<!-- 收藏展示 -->
			<view class="follow">

				<follow :size="heartSzie" :detailId='hotelId'></follow>

			</view>
		</view>

		<!-- 酒店介绍 -->
		<view class="introduce" v-show="hotel.introduce">
			<text>酒店简介</text>
			<rich-text :nodes='hotel.introduce'></rich-text>
		</view>

		<!-- 酒店设施 -->
		<view class="facilities introduce" v-show="hotel.facilities">
			<text>酒店设施</text>
			<rich-text :nodes='hotel.facilities'></rich-text>
		</view>

		<!-- 时间选择 -->
		<view class="selet_time">
			<view class="start_time" @click="show = true">
				<u--input placeholder='2022' :disabled='true' :value='start_time'>
					<u-icon name='calendar' slot="prefix" size="22px"></u-icon>
				</u--input>
			</view>
			<view class="date_icon">
				<u-icon name="arrow-rightward" @click="show = true"></u-icon>
			</view>
			<view class="end_time" @click="show = true">
				<u--input placeholder='2022' :disabled='true' :value='end_time'>
					<u-icon name='calendar' slot="prefix" size='22px'></u-icon>
				</u--input>
			</view>
			<u-calendar startText="住店" endText="离店" confirmDisabledText="请选择离店日期" :formatter="formatter" :show="show"
				mode="range" :defaultDate="day" @confirm="confirm" ref="calendar" :closeOnClickOverlay='true'
				@close="close">
			</u-calendar>
		</view>

		<!-- 床型 -->
		<view class="beds">
			<view class="bed" v-for="(item,index) of room" :key='index'>
				<view class="bed_image">
					<image src="https://images-new-cdn.123rf.com.cn/450wm/dit26978/dit269781708/dit26978170800228.jpg"
						mode="scaleToFill" @click="preview()">
					</image>
					<!-- {{item.cover}} -->
				</view>
				<view class="bed_info">
					<h4>{{item.name}}</h4>
					<view class="room_info">
						<text v-show="item.network">有WiFi </text>
						<text>|最多容纳：{{item.maxLoad}}</text>
						<text v-show="item.haveWindow">|有窗</text>
					</view>
				</view>
				<!-- 订房button -->
				<view class="order_button_view">
					<u-button class="order_button" text="预定" color='#58b1e1' @click="showOrder(index)"></u-button>
				</view>
				<!-- <u-line color="#ff557f"></u-line> -->
			</view>
		</view>

		<!-- 其他须知 -->
		<view class="introduce" v-show="hotel.facilities">
			<text>其他须知</text>
			<rich-text :nodes='hotel.otherPolicy'></rich-text>
		</view>
		<view class="divider">
			<u-divider v-show="bottom" text="到底了" lineColor="#7d7d7d" textColor="#7d7d7d"></u-divider>
		</view>

		<!-- 底部弹窗 -->
		<u-popup :show="orderShow" @close="orderClose" mode="bottom" :closeable='true'>
			<view class="order_hotel_name">
				<h3>{{hotel.name}}</h3>
			</view>
			<view class="order_pop">
				<view class="order_header">
					<view class="time">
						{{start_time}}---{{end_time}}
						<view>
							共:{{selectDay}}晚
						</view>
					</view>
					<view class="hotel_info">
						<view class="room_name">
							{{selectRoom.name}}
						</view>
						<view class="room_info">
							<text v-show="selectRoom.network">有WiFi</text>
							<text>|最多容纳:{{selectRoom.maxLoad}}</text>
							<text v-show="selectRoom.haveWindow">|有窗</text>
						</view>
					</view>
				</view>

				<view class="custom_info">
					<input class="uni-input" placeholder="输入姓名" />
					<input class="uni-input" type="number" placeholder="联系方式" />
				</view>
			</view>
			<u-line color="#4d4d4d" margin="15px"></u-line>
			<view class="about">
				<h6>退款规则</h6><br>
				预订成功后15分钟内可免费取消<br>
				<br>
				<h6>预订说明</h6><br>
				订单需等酒店或供应商确认后才生效，订单确认结果以美团短信 或邮件通知为准;
				请在订单生效后再至酒店前台办理入住。 预订服务由美团酒店平台提供，
				住宿服务由住宿服务提供者（酒 店/民宿）提供。请您在提交订单前仔细阅读 <navigator url="../list/about">《绿帽子酒店预订条 款》。</navigator>
				</p>

			</view>
			<view class="order_bottom">
				￥:<h2>{{selectRoom.fee*selectDay}}</h2>
				<u-button class="send_order_button" text="提交订单" type="primary" @click="createOrder"></u-button>
			</view>
		</u-popup>

		<!-- 支付弹窗 -->
		<u-popup :show="flag" @close="close1" @open="open1" mode="center" :closeable='true' :closeOnClickOverlay='false'
			round='5' :customStyle="{ marginBottom: '200rpx'}" :safeAreaInsetTop='true'>
			<view class="wrap">
				<view class="wrap-header">
					<view class="wrap-header-item">输入支付密码</view>
				</view>
				<view class="last_pay">
					<p>实付金额：</p>
					<h6>￥</h6>{{lastPay}}
				</view>
				<!-- 选择支付方式 -->
				<!-- <view class="pay_way">
					<u-collapse @change="change1" @close="close" @open="open" align='center'>
						<u-collapse-item title="我的钱包" name="Docs guide">
							<text>我的钱包</text>
						</u-collapse-item>
					</u-collapse>
				</view> -->
				<view>
					<view class="input-wrap" style="font-size: 42rpx;">
						<!-- 页面显示  如果想要加密形式 给此input加上 password="true" 属性-->
						<input class="input1" type="number" v-for="(item, index) in input_len" @tap="onInput"
							password="true" :key="index" disabled
							:value="input_val.length >= index + 1 ? input_val[index] : ''" />
					</view>
					<!-- 实际监听 -->
					<input class="input2" v-if="isFocus" @blur="blurInput" @input="setInput" v-model="input_val"
						:maxlength="input_len" :focus="true" />
				</view>
			</view>
		</u-popup>

		<u-modal :show="showNotify" @confirm="enterConfirm" ref="uModal" :asyncClose="true" title="密码错误,请重新输入"
			confirmText='重试'>
			<view class="slot-icon">
				<u-icon name='close-circle-fill' size='40' color="#ff0000"></u-icon>
			</view>
		</u-modal>
	</view>
</template>

<script>
	import follow from '../../components/follow/follow.vue'
	import db from '@/util/localStorage.js'

	const d = new Date()
	const year = d.getFullYear()
	let month = d.getMonth() + 1
	month = month < 10 ? `0${month}` : month
	const date = d.getDate()

	export default {
		components: {
			follow,
		},
		data() {
			return {
				image: "https://images-new-cdn.123rf.com.cn/450wm/dit26978/dit269781708/dit26978170800228.jpg",
				showNotify: false,
				input_len: 6, // 密码长度
				input_val: '', // 密码的值
				isFocus: false,
				day: [`${year}-${month}-${date}`, `${year}-${month}-${date + 1}`],
				bottom: false,
				heartSzie: 30,
				selectDay: 1,
				start_time: `${year}-${month}-${date}`,
				end_time: `${year}-${month}-${date + 1}`,
				room: {},
				selectRoom: {},
				hotel: {},
				show: false,
				orderShow: false,
				hotelId: '',
				flag: false, // 支付弹窗
				hotelBadge: '',
				orderId: '',
				lastPay: '',
			}
		},
		onReachBottom() {
			setTimeout(() => {
				this.bottom = true;
			}, 500)
		},
		// 使用id重新发起请求,获取酒店具体数据
		onLoad(option) {
			this.getDetail(option)
			this.hotelId = option.id
			// this.confirm()
			// this.getFollow(option)
		},

		methods: {
			enterConfirm() {
				this.showNotify = false
				this.input_val = ''
				this.onInput()
			},
			preview(url) {
				let imgsArray = [];
				imgsArray[0] = this.image;
				uni.previewImage({
					current: 0,
					urls: imgsArray
				});
			},
			enterConfirm() {
				this.showNotify = false
			},
			open1() {
				this.flag = true;
			},
			close1() {
				this.flag = false
				this.input_val = ''
			},
			onInput() {
				this.isFocus = true;
			},
			setInput(e) {
				this.input_val = e.detail.value;
				if (this.input_val.length == this.input_len) {
					this.payOrder(this.input_val)
				}
			},
			blurInput() {
				this.isFocus = false;
			},
			async getDetail(option) {
				const res = await this.$myRequest({
					url: '/api/hotel/get/' + option.id,
				})
				this.hotel = res.data.data
				this.hotelBadge = this.hotel.badge;
				db.save('hotelId', this.hotel.id)
				console.log(this.hotel);
				const room = await this.$myRequest({
					url: '/api/roomType/currentRoomTypeList/' + option.id,
				})
				this.room = room.data.data
				// console.log(this.room);

			},
			// 提交订单
			async createOrder(index) {
				const userInfo = db.get('userInfo');
				const res = await this.$myRequest({
					url: '/api/order/create',
					method: 'POST',
					// 酒店id，房型id，优惠券id，天数
					data: {
						"hotelId": this.hotel.id,
						// "id":this.id,
						"customerId": userInfo.id,
						"roomType": this.selectRoom.id,
						"estimatedCheckIn": this.start_time,
						"estimatedCheckOut": this.end_time,
						"province": this.hotel.province,
						"discount": 'null'
					}

				})
				if (res.data.code === '200') {
					console.log(res);
					this.orderClose()
					this.flag = true;
					this.onInput()
					this.orderId = res.data.data.id
					this.lastPay = res.data.data.lastPay
				}
			},
			// 支付订单
			async payOrder(pwd) {
				const res = await this.$myRequest({
					url: '/api/order/payOrder',
					method: 'POST',
					header: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'Authorization': db.get('token')
					},
					data: {
						"walletPwd": pwd,
						"orderId": this.orderId,
					}

				})
				if (res.data.code === '200') {
					console.log(res);
					this.flag = false;
					this.orderClose()
					uni.showToast({
						title: '支付成功',
						icon: 'success'
					})
				} else {
					this.showNotify = true
				}
				this.input_val = ''
			},
			// 显示订单详情
			showOrder(index) {
				if (this.selectDay) {
					this.orderShow = true;
					this.index = index;
					this.selectRoom = this.room[index];
				} else {
					uni.showToast({
						icon: 'warnning',
						title: "请先选择时间！"
					})
				}
			},
			orderClose() {
				this.orderShow = false;
			},
			// 房间类型预定
			close() {
				this.show = false;
			},
			confirm(e) {
				this.start_time = e[0],
					console.log(e);
				this.end_time = e[e.length - 1],
					this.selectDay = e.length - 1
				this.close()
			},
			// 日历的日期文字自定义
			formatter(day) {
				const d = new Date()
				let month = d.getMonth() + 1
				const date = d.getDate()
				if (day.month == month && day.day == date + 3) {
					// day.bottomInfo = '有优惠'
					// day.dot = true
				}
				return day
			}

		}
	}
</script>

<style lang="scss">
	.custom_info {
		margin-top: 25rpx;
		width: 700rpx;

		input {
			margin-top: 15rpx;
			border-radius: 15px;
			background-color: #aaaaaa;
			padding: 25rpx;
		}
	}

	.wrap {
		padding: 30rpx;
		height: 400rpx;
		width: 650rpx;

		.last_pay {
			margin-right: 140rpx;
			align-items: flex-end;
			justify-content: center;
			display: flex;
			margin-top: -40px;
			font-size: 40px;
			font-weight: 800;

			p {
				font-size: 14px;
				font-weight: 600;
				color: #9b9ba5;
			}
		}

		// padding: 0 30rpx;
	}

	.wrap-header {
		padding: 90rpx 0;

		.wrap-header-item {
			font-size: 42rpx;
			color: #6d6d6d;
			font-weight: 600;
			margin-bottom: 0rpx;
		}
	}

	.input-wrap {
		display: flex;
		justify-content: space-between;
		align-items: center;
		// width: 500rpx;
		padding: 0px 20rpx 0px 20rpx;

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

	.bed_info {
		width: 380rpx;
		margin-left: 5rpx;
		margin-top: 20rpx;
		line-height: 60rpx;
	}

	.header {
		background-color: #FFFFFF;
		margin: 25rpx 25rpx 15rpx 25rpx;
		padding: 25rpx;
		display: flex;
		line-height: 30px;

		h2 {
			width: 580rpx;
		}

		.follow {
			width: 30px;
			height: 30px;
			margin-top: 25rpx;
			margin-left: 40rpx;
		}
	}

	.about {
		// height: 30rpx;
		padding-left: 60rpx;
		padding-right: 60rpx;
		margin-bottom: 180rpx;
		font-size: 12rpx;
		color: #484848;

		navigator {
			display: inline;
			color: #5AC725;
		}

		h6 {
			line-height: 0rpx;
		}
	}

	.order_bottom {
		display: flex;
		background-color: #aaaaaa;
		position: fixed;
		bottom: 0px;
		width: 100%;
		height: 120rpx;
		align-items: center;
		border-radius: 30rpx 30rpx 0px 0rpx;
		color: #E45656;

		.send_order_button {
			width: 150rpx;
			margin-right: 30rpx;
			border-radius: 20rpx;
		}
	}

	.order_hotel_name {
		margin: 30rpx 0px 0px 30rpx;
	}

	.order_pop {
		height: 650rpx;
		width: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;

		.order_header {
			margin-top: 50rpx;
			line-height: 60rpx;
			width: 650rpx;
			height: 200rpx;
			border-radius: 20px;
			background-color: #0484d3;
			padding: 30rpx;
			color: #FFFFFF;

			.order_info {
				font-size: 22px;
			}
		}

		.time {
			display: flex;
			justify-content: space-between;
		}
	}

	.introduce {
		margin: 25rpx 25rpx 15rpx 25rpx;
		background-color: #FFFFFF;
		padding: 25rpx;

		rich-text {
			line-height: 25px;
			letter-spacing: 1px;
		}

		text {
			font-weight: bold;
			font-size: 20px;

		}
	}

	.beds {
		margin: 0rpx 25rpx 10rpx 25rpx;
		display: flex;
		flex-direction: column;
		// margin: 20rpx 30rpx 0rpx 30rpx;
		height: 100%;

		// background-color: #E6EDFA;
		.bed {
			width: 680rpx;
			background-color: #FFFFFF;
			border-radius: 20px;
			height: 180rpx;
			height: 180rpx;
			display: flex;
			padding: 6px;
			margin-top: 20rpx;
			// align-items: center;
		}

		.bed_image {
			align-self: center;
			margin-right: 5px;
			border-radius: 20px;

			image {
				border: #2f2f2f 2rpx solid;
				border-radius: 20px;
				width: 180rpx;
				height: 160rpx;
			}
		}
	}

	.container {
		display: flex;
		flex-flow: column;
	}

	page {
		background-color: #e6edfa;
	}

	.title {
		color: $u-primary;
		text-align: center;
		padding: 20rpx 0 0 0;
	}

	.image {
		image {
			width: 100%;
		}
	}

	.selet_time {
		background-color: #FFFFFF;
		height: 100%;
		display: flex;
		align-items: center;
		margin: 15rpx 25rpx 0rpx 25rpx;
		border: 2rpx solid #ebebeb;

		.date_icon {
			margin: 0px 20px;
		}

		.start_time {
			margin-left: 10rpx;
			margin-top: 10rpx;
			width: 290rpx;
			height: 80rpx;
		}

		.end_time {
			margin-top: 10rpx;
			width: 290rpx;
			height: 80rpx;
		}
	}

	.order_button_view {
		align-self: center;
		width: 100rpx;
		height: 30rpx;
		// margin-right: 100rpx;
	}

	.room_info {
		border-radius: 30px;
		width: 400rpx;
	}

	.order_button {
		right: 0px;
		bottom: 30rpx
	}
</style>
