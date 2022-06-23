<template>
	<view class="container">

		<!-- 右侧弹窗 -->
		<view class="account_pop">
			<u-popup :show="show1" @close="close1" @open="open" mode="right">
				<account-pop :userInfo='userInfo' :heads='heads' v-show='account'></account-pop>
			</u-popup>
		</view>

		<!-- 轮播图上头像 -->
		<view class="account">
			<image :src="heads" mode="aspectFill" @click="show1 = true"></image>
		</view>

		<!-- 轮播图 -->
		<view class="lunBo">
			<u-swiper height="243" :list="lunBo" keyName="image_src" indicator indicatorMode="line" circular></u-swiper>
		</view>

		<!-- 搜索框 -->
		<search :list='list'></search>

		<!-- 地区筛选 -->
		<view class="index_position_select">
			<scroll-view class='position_scroll' enhanced="true" show-scrollbar="false"  scroll-x>
				<view class="scroll_item" v-for="(item,index) in position" :key="index"
					:class="index == active?'active':''" @click="getIndex(index)">
					{{item.name}}
				</view>
			</scroll-view>


			<!-- 瀑布流 -->
			<view class="content">
				<custom-waterfalls-flow ref="waterfallsFlowRef" :value="list" @imageClick='toDetail'>
					<template v-slot:default="item">
						<!-- <view class="follow">
								<follow :id='item.id'></follow>
							</view> -->
						<view class="cnt">
							<view class="title">{{item.name}}</view>
							<view class="province">{{item.province}}</view>
						</view>
					</template>
				</custom-waterfalls-flow>
				<view class="divider">
					<u-divider text="到底了" lineColor="#7d7d7d" textColor="#7d7d7d" v-show='flag'></u-divider>
				</view>
			</view>
		</view>




	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	import search from "../../components/search/search.vue"
	import accountPop from '../../components/accountPop/accountPop.vue'
	import follow from '../../components/follow/follow.vue'

	export default {
		components: {
			accountPop,
			search,
			follow
		},
		data() {
			return {
				account: false,
				active: 0,
				flag: false,
				page: 1,
				userInfo: {},
				heads: 'https://tse4-mm.cn.bing.net/th/id/OIP-C.MWvXkQEwHy26_UZsj8M6MwHaHa?pid=ImgDet&rs=1',
				show1: false,
				list: [],
				selectPosition: '',
				position: [{
					name: "全国"
				}, {
					name: "广东"
				}, {
					name: "湖南"
				}, {
					name: "福建"
				}, {
					name: "上海"
				}, {
					name: "北京"
				}, {
					name: "江苏"
				}],
				lunBo: [{
						"image_src": "https://tse1-mm.cn.bing.net/th/id/R-C.55d2130535fb04becfdda1def44474c9?rik=WhiuTF8%2bPLDmhg&riu=http%3a%2f%2fimg2.ixinwei.com%2f201810%2f781.jpg&ehk=sHexkCyIgCm5AY9MPgOc2vePjVprfk8KrqzzlGs8uSg%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1",
					}, {
						"image_src": "https://images-new-cdn.123rf.com.cn/450wm/alenkasm/alenkasm1904/alenkasm190400055.jpg"
					},
					{
						"image_src": "https://z1.muscache.cn/pictures/carson/carson-1650779625489-8e52cf67/original/db64cb34-c15b-4cc4-80a5-9a1c39fd9518.jpg"
					},
					{
						"image_src": "https://img95.699pic.com/photo/40016/5752.jpg_wh860.jpg"
					}
				],
			}
		},
		// 监听地区选择切换
		watch: {
			'active': function(newVal) {
				if (newVal === 0) {
					this.selectPosition = ''
				} else if (this.position[newVal].name === '北京' || this.position[newVal].name === '上海') {
					this.selectPosition = this.position[newVal].name + '市';
				} else {
					this.selectPosition = this.position[newVal].name + '省';
				}

				this.list = []
				this.page = 1
				this.getGoods()
			}
		},
		onShow() {
			this.userInfo = db.get('userInfo')
			this.heads  = 'http://' +this.userInfo.heads
		},
		onLoad() {
			const user = db.get('userInfo')
			this.userInfo = user
			this.getGoods();
			// this.getLunBo();
			if (this.userInfo.heads) {
				this.heads = 'http://' + this.userInfo.heads
			}
			console.log(this.heads);
		},
		// 下拉刷新
		onPullDownRefresh() {
			this.page = 1;
			this.list = [];
			this.getGoods();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		// 触底加载更多
		onReachBottom() {
			console.log("触底了")
				++this.page
			if (!this.flag) {

				setTimeout(() => {
					this.getGoods()
				}, 1000)
			}
		},
		methods: {
			getIndex(index) {
				this.active = index;
			},
			// 跳到酒店详情页
			toDetail(item) {
				uni.navigateTo({
					url: '../details/details?id=' + item.id,
				});
			},
			open() {
				console.log('open');
				this.account = true;
			},
			close1() {
				this.show1 = false;
				console.log('close');
			},
			// 获取首页轮播图的数据
			// getLunBo() {
			// 	uni.request({
			// 		url: "https://api-hmugo-web.itheima.net/api/public/v1/home/swiperdata",
			// 		success: (res) => {
			// 			if (res.data.meta.status !== 200) {
			// 				return uni.showToast({
			// 					title: "没有拿到数据",
			// 				})
			// 			}
			// 			this.lunBo = [...this.lunBo, ...res.data.message]
			// 		}
			// 	})
			// },
			// 获取酒店列表信息
			
			async getGoods() {
					const res = await this.$myRequest({
					url: '/api/hotel/page',
					method: 'POST',
					data: {
						"province": this.selectPosition,
						"page": {
							"page": this.page,
							"size": 10
						},
					}
				})
				// console.log(res);
				// console.log(res.statusCode);
				// if(res.statusCode !== '200'){
				// 	uni.redirectTo({
				// 		url:'../login/login'
				// 	})
				// }
				
				this.list = [...this.list, ...res.data.data.records]
				for (let item of res.data.data.records) {

						// item.cover = 'https://pic32.photophoto.cn/20140719/0033034049244968_b.jpg'
					if (!item.cover) {
						item.cover = 'http://r3ac7i7fr.hn-bkt.clouddn.com/hotel_cover_1639463752314'
					}
					if (item.name.length > 12){
						item.name = item.name.substring(0,14) + '...'
					}
				}
				if (res.data.data.records.length < 10) {
					this.flag = true
				}

				console.log(this.list);
			},
			// 重定向回到登录页面
			// toLogin(){
			// 	const 
			// },
		}

	}
</script>
<style lang="scss">

	.follow {
		position: absolute;
		// top:0px;
		margin-top: -50rpx;
		margin-left: 280rpx;
	}

	.cnt {
		background-color: #e1e1e1;
		border: solid #e3f0f2;
		padding: 10rpx;
		height: 35px;
		border-radius: 0px 0px 20rpx 20rpx;

		.title {
			font-size: 12px;
			margin-top: 4rpx;
			font-weight: bolder;
			color: #303133;
		}

		.province {
			font-size: 10px;
			// margin-top: 10rpx;
			color: #303133;
		}
	}

	.content {
		margin-top: 40rpx;
		// padding: 0px 20px 0px 20px;
	}

	.container {
		height: 100%;
	}

	.position_scroll {
		// width: 100%;
		// height: 100%;
		::-webkit-scrollbar {
		   width: 0;
		   height: 0;
		   color: transparent;
		}
		white-space: nowrap;

		// font-weight: bolder;
		.scroll_item {
			font-weight: bold;
			display: inline-block;
			width: 130rpx;
			border-radius: 6rpx;
			border: 2rpx solid #D8D8D8;
			text-align: center;
			margin-right: 20rpx;
			padding: 22rpx 0px 22rpx 0px;
		}
	}

	.active {
		background-color: #008489;
		border-color: #008489;
		color: #FFFFFF;
	}

	.u-divider {
		position: relative;
		bottom: 30rpx;
	}

	.account {
		position: absolute;
		right: 0rpx;
		top: 0rpx;
		height: 80px;
		width: 160rpx;
		z-index: 1;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.account>image {
		width: 28px;
		height: 28px;
		border-radius: 50%;
		object-fit: cover;
		object-position: center;
		border: 6rpx solid #FFFFFF;
	}

	.index_position_select {
		margin: 40rpx;

	}
</style>
