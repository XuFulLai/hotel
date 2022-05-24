<template>
	<view class="container">

		<!-- 状态选择器 -->
		<view class="subsection">
			<u-subsection :list="statusList" :current="curNow" @change="sectionChange" fontSize='20'></u-subsection>
		</view>

		<!-- 列表为空的显示 -->
		<view class="empty" v-show="!orderList.length">
			<u-empty mode="order" icon="http://cdn.uviewui.com/uview/empty/order.png">
			</u-empty>
		</view>


		<view class="order_list" v-for="(item,index) of orderList" :key='index'>
			<view class="header">
				<text>{{item.hotelName}}</text>
				<view class="order_status">
					{{item.currentStatus}}
				</view>
			</view>
			<u-line color="#969696"></u-line>
			<view class="order_body">
				<view class="order_info">
					<p>订单号：{{item.id}}</p>
					<p>预定时间：{{item.estimatedCheckIn | formatDate}}至{{item.estimatedCheckOut | formatDate}}</p>
					<p>房间类型：{{item.roomTypeName}}</p>
					<view class="inOutTime" v-if="item.checkOutTime">
						<p>入住时间：{{item.checkInTime | format}}</p>
						<p>退房时间：{{item.checkOutTime | format}}</p>
					</view>
					<view class="inOutTime" v-if="!item.checkOutTime">
						<p>入住时间：暂无数据</p>
						<p>退房时间：暂无数据</p>
					</view>
				</view>
				<view class="price">
					<p>￥{{item.lastPay}}</p>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {

				statusList: [{
						name: '全部',
						status: ''
					}, {
						name: '未支付',
						status: '0'
					},
					{
						name: '已支付',
						status: '1'
					},
				],
				curNow: '',
				orderList: [],
			}
		},
		onLoad() {
			this.getOrder()
		},
		methods: {
			async getOrder(status) {
				const userInfo = uni.getStorageSync('userInfo');
				const res = await this.$myRequest({
					url: '/api/order/my',
					method: 'POST',
					data: {
						"id": userInfo.id,
						"page": {
							"page": 1,
							"size": 999
						},
						"status": status || ''
					}
				})
				this.orderList = res.data.data.records
				console.log(this.orderList);
			},
			sectionChange(index) {
				this.curNow = index;
				this.getOrder(this.statusList[index].status)
			}
		},

	}
</script>

<style lang="scss">
	.subsection {
		width: 100%;
		margin: 0px 15rpx 20rpx 15rpx;

	}

	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin: 60rpx 0px 0px 0rpx;
	}

	.order_list {
		line-height: 22px;
		margin: 25rpx 25rpx 15rpx 25rpx;
		width: 700rpx;
		height: 350rpx;
		background-color: #FFFFFF;
		border-radius: 15rpx;
		border: #cfc0cc 0.5px solid;

		.header {
			font-weight: bold;
			display: flex;
			justify-content: space-between;
			padding: 30rpx;

			.order_status {
				color: #5a5a5a;
			}
		}
	}

	.order_body {
		font-size: 14px;
		display: flex;
		padding: 20rpx;
		height: 220rpx;
		justify-content: space-between;

		.price {
			width: 100rpx;
			position: relative;
			right: 25rpx;
			// bottom: 20rpx;
			align-self: flex-end;
			p{
				font-size: 18px;
			}
		}


	}
</style>
