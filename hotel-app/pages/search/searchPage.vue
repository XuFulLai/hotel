<template>
	<view class="container">

		<view class="search_box">
			<!-- <view class="position_icon">
				<u-icon name="map" size='30px' color='#6062663'></u-icon>
			</view> -->
			<u-search searchIcon='map' bgColor="#FFFFFF" @clickIcon='getLocaltion(),postLocaltion()' placeholder="输入酒店名"
				v-model="keyword" shape="square" height="48px" :animation='true' :actionStyle="{color: '#7f7f7f'}"
				@custom="allHotel()" :label="showPosition"	></u-search>
		</view>

		<view class="fliter">
			<view class="fliter_position">
				<u-collapse @change="change1" @close="close" @open="open" align='center'>
					<u-collapse-item :title="p" name="Docs guide">

						<text class="position" v-for="(item,index) of position[0]" :key='index'
							@click="selectPosition(item),allHotel()">{{item.name}}</text>
					</u-collapse-item>
				</u-collapse>
			</view>
			<view class="fliter_distance">
				<u-collapse @change="change2" @close="close" @open="open" align='center'>
					<u-collapse-item :title="d" name="Docs guide">
						<text class="distance" v-for="(item,index) of distance" :key='index'
							@click="selectDistance(item),postLocaltion()">{{item}}</text>
					</u-collapse-item>
				</u-collapse>
			</view>
		</view>

		<view class="hotel_list" v-for="(item,index) of list" :key='index' @click="toDetail(item.id)">
			<view class="header">
				<text>{{item.name}}</text>
			</view>
			<u-line color="#969696"></u-line>
			<view class="order_body">
				<view class="bed_image">
					<image :src="item.cover" mode=""></image>
				</view>
				<view class="order_info">
					地点：{{item.address}}<br>
					省份：{{item.province}}
					设施
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import db from '../../util/localStorage.js'

	export default {
		components: {},
		name: "searchPop",
		data() {
			return {
				d: '周边距离',
				p: '所在地点',
				show: false,
				showPosition:'',
				distance: ['2km','5km', '20km', '50km', '100km'],
				position: [
					[{
						name: "全国"
					}, {
						name: "广东省"
					}, {
						name: "湖南省"
					}, {
						name: "福建省"
					}, {
						name: "上海市"
					}, {
						name: "北京市"
					}, {
						name: "江苏省"
					}]
				],
				filterList: [],
				keyword: '',
				list: [],
				longitude: '',
				latitude: ''
			};
		},
		onLoad() {
			this.allHotel()
		},
		methods: {
			toDetail(id) {
				uni.navigateTo({
					url: '../details/details?id=' + id,
				});
			},
			selectPosition(item) {
				this.p = item.name
			},
			selectDistance(item) {
				this.d = item

			},
			open(e) {

			},

			close(e) {
				// console.log('close', e)
			},
			change1(e) {
				this.d = '周边距离'
			},
			change2(e) {
				this.p = '所在地点'
			},
			async postLocaltion() {
				const token = db.get('token')
				const long = db.get('longitude')
				const lati = db.get('latitude')
				let d = this.d
				if(this.d == '周边距离'){
					d = '500'
				}else{
					d = d.toString().substring(0, this.d.length - 2) +'00'
				}
				const res = await this.$myRequest({
					url: '/api/hotel/page',
					method: 'POST',
					data: {
						"longitude": long,
						"latitude": lati,
						"distance": d ,
						"page": {
							"page": 1,
							"size": 999
						},
					}
				})
				console.log(res)
				this.list = res.data.data.records
				console.log(this.list);
				if(this.list.length){
					if(this.list[0].province){
				this.showPosition = this.list[0].province;
				}else{
					this.showPosition = ''
				}
				}
				
			},

			getLocaltion() {
				uni.getLocation({
					type: 'wgs84',
					geocode:true,
					success(res) {
						console.log('当前位置的经度：' + res.longitude);
						console.log('当前位置的纬度：' + res.latitude);
						db.save('longitude', res.longitude)
						db.save('latitude', res.latitude)
						// this.longitude = res.longitude;
						// this.latitude = res.longitude;
					},
					fail(err) {
						console.log("失败了");
					}
				});
			},
			// 酒店筛选
			async allHotel() {
				// const keyword = this.keyword

				let p = this.p
				if (p === '全国' || this.p === '所在地点') {
					p = ''
				}
				let res = await this.$myRequest({
					url: '/api/hotel/page',
					method: 'POST',
					data: {
						"distance": this.d.toString().substring(0,this.d.length-2)+'00' || '',
						"province": p || '',
						"name": this.keyword || '',
						"page": {
							"page": this.page,
							"size": 999
						},
					}
				})
				console.log(res.data.data.records);
				this.list = [...res.data.data.records]
				if (res.data.data.records.length < 6) {
					this.flag = true
				}
				console.log(this.list);
			},
		}
	}
</script>

<style lang="scss">
	.u-cell__body {
		background-color: #FFFFFF !important;
	}

	.fliter {
		width: 100%;
		margin-top: 30rpx;
		display: flex;
		background-color: #FFFFFF;

		.fliter_distance {
			width: 50%;
			height: 100%rpx;
			z-index: 99;

			.distance {
				line-height: 60rpx;
				font-size: 18px;
				background-color: #FFFFFF;
			}
		}

		.fliter_position {
			width: 50%;
			height: 100%rpx;
			z-index: 99;

			.position {
				width: 100%;
				margin: 0px;
				line-height: 60rpx;
				font-size: 18px;
				background-color: #FFFFFF;
			}
		}
	}

	page {
		background-color: #f1f1f1;
	}

	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin: 0rpx 0px 0px 0rpx;
	}

	.search_box {
		width: 100%;
		height: 176rpx;
		display: flex;
		align-items: center;
		box-shadow: 0 0 10px #303133;
	}

	.hotel_list {
		margin: 30rpx 0rpx 0px 0rpx;
		width: 680rpx;
		height: 300rpx;
		background-color: #FFFFFF;
		border-radius: 15rpx;
		border: #cfc0cc 0.5px solid;

		.header {
			display: flex;
			justify-content: space-between;
			padding: 30rpx;
		}
	}

	.order_body {
		display: flex;
		padding: 20rpx;

		.bed_image {
			width: 150rpx;
			height: 150rpx;
			background-color: #007AFF;
			margin-right: 40rpx;

			image {
				width: 170rpx;
				height: 155rpx;
			}
		}

		.order_info {
			line-height: 45rpx;
		}
	}
</style>
