<template>
	<view class="container">

		<view class="follow_list" v-for="(item,index) of wishList" :key='index'>
			<view class="header">
				<text>{{item.name}}</text>
				
				<!-- <follow></follow> -->
				
			</view>
			<u-line color="#969696"></u-line>
			<view class="follow_body">
				<view class="bed_image">
					<image :src="item.cover">
				</view>
				<view class="follow_info">
					酒店位置：{{item.province}} {{item.city}}<br>	
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	import follow from '../../components/follow/follow.vue'
	
	export default {
		components:{follow},
		data() {
			return {
				wishList:{}
			}
		},
		onLoad(){
			this.getFollow()
		},
		methods: {
			async getFollow(){
				const res = await this.$myRequest({
					url: '/api/collection/myCollectionList',
					method:'POST',
					data:{
						  "page": 1,
						  "size": 10
					}
				})
				this.wishList = res.data.data.records
				console.log(res.data.data.records);
			},
		}
	}
</script>

<style lang="scss">
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin: 60rpx 0px 0px 0rpx;
	}

	.follow_list {
		width: 700rpx;
		height: 300rpx;
		background-color: #FFFFFF;
		border-radius: 15rpx;
		border: #cfc0cc 0.5px solid;
		margin-bottom: 30rpx;

		.header {
			display: flex;
			justify-content: space-between;
			padding: 30rpx;
		}
	}
	
	.follow_body{
		display: flex;
		padding:20rpx;
		.bed_image{
			width: 150rpx;
			height: 150rpx;
			background-color: #007AFF;
			margin-right: 40rpx;
			image {
				width: 170rpx;
				height: 155rpx;
			}
		}
		.follow_info{
			line-height: 45rpx;
		}
	}
</style>
