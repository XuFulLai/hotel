<template>
	<view class="container">

		<view class="header">
			<view class="account_profile">
				<view class="head_icon">
					<u-icon name='camera' size=" 40px" @click="changeProfile()" color="#3e3e3e"></u-icon>
				</view>
				<view class="head_image">
					<image
						:src="heads"
						mode="aspectFill"></image>
				</view>
			</view>

			<view class="nickname">
				<h1>你好，我叫</h1>
				<view class="name_icon">
					<h2>{{userInfo.nickname}}</h2>
					<u-icon name='edit-pen-fill' size="20px" @click="open"></u-icon>
				</view>
			</view>
		</view>

		<!-- 修改昵称弹窗 -->
		<view class="nickname">
			<u-popup :show="show" @close="close" @open="open" round="10" mode="center">
				<view class="change_nickname">
					<view class="input_nickname">
						<u--input type='text' clearable placeholder='输入新的名字' placeholderStyle='color:#aca5a5'
							fontSize='40rpx' v-model="nickname" class="input"></u--input>
						<view class="buttonBox">
							<u-button class="custom-style" text="修改" color='#41B883' @click="changeNickname"></u-button>
						</view>
					</view>
				</view>
			</u-popup>
		</view>
		<view class="tags" v-if="userInfo.deptName">
			<u-tag :text="userInfo.deptName" shape="circle"></u-tag>
		</view>

		<view class="content">
			<view class="message_title">
				<text>留言版：</text>
			</view>
			<view class="message_info">
				<textarea placeholder-style="color:#959595" placeholder="这位用户什么都不想说" v-model="message" focus />
				<!-- <u-textarea v-model="message" placeholder="这位用户什么都不想说" count ></u-textarea> -->
			</view>
		</view>


	</view>
</template>

<script>
	import db from '../../util/localStorage.js'

	export default {
		data() {
			return {
				message: '',
				nickname: '',
				userInfo: {},
				heads: 'https://tse4-mm.cn.bing.net/th/id/OIP-C.MWvXkQEwHy26_UZsj8M6MwHaHa?pid=ImgDet&rs=1',
				uploadHeads: '',
				show: false,
			}
		},
		onLoad() {
			this.message = db.get('msg')
			this.userInfo = db.get('userInfo')
			if (this.userInfo.heads) {
				this.heads = 'http://' + this.userInfo.heads
			}
			this.nickname = this.userInfo.nickname
		},
		methods: {
			// 上传图片
			uploadImg(res) {
				this.uploadHeads = JSON.stringify(res.tempFilePaths);
				const token = db.get('token')
				uni.uploadFile({
					header: {
						'Authorization': token
					},
					url: 'http://106.52.219.171:8100/upload/img', //post请求的地址
					filePath: this.uploadHeads,
					name: 'img',
					formData: {
						'key': 'heads'
					},
					success: (uploadFileRes) => {
						//这里要注意，uploadFileRes.data是个String类型，要转对象的话需要JSON.parse一下
						const obj = JSON.parse(uploadFileRes.data);
						console.log(obj);
						this.setPorfile(obj)
					}
				})
			},
			// 选择图片
			changeProfile() {
				uni.chooseImage({
								count: 1, //默认9
								sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
								sourceType: ['album'], //从相册选择
								loop: true,
								success: res => {
									console.log(res);
									
									var tempFilePaths = res.tempFilePaths;
									const token = db.get('token')
									console.log(tempFilePaths);
									console.log(tempFilePaths[0]);
									uni.uploadFile({
										header:{
											'Authorization':token
										},
										url: 'http://106.52.219.171:8100/upload/img',
										filePath: tempFilePaths[0],
										name: 'img',
										formData:{
											key:'head'
										},
										success: uploadFileRes => {
											this.setPorfile(JSON.parse(uploadFileRes.data))
											console.log('上传图片', JSON.parse(uploadFileRes.data));
										},
										fail(err) {
											console.log(err);
										}
									});
								}
							});

			},
			// 更改头像
			async setPorfile(obj) {
				console.log(obj);
				const res = await this.$myRequest({
					url: '/api/user/modify',
					method: 'POST',
					data: {
						"id": this.userInfo.id,
						"heads": obj.data
					}
				})
				this.heads= 'http://' + res.data.data.heads
					db.remove('userInfo')
					db.save('userInfo',res.data.data)
			},
			// 更改昵称
			async changeNickname() {
				const res = await this.$myRequest({
					url: '/api/user/modify',
					method: 'POST',
					data: {
						"id": this.userInfo.id,
						"nickname": this.nickname
					}
				})
				db.save('userInfo', res.data.data)
				this.userInfo = db.get('userInfo')
				this.nickname = this.userInfo.nickname
				this.close()
			},
			open() {
				this.show = true
			},
			close() {
				this.show = false
			}

		},
		watch: {
			'message': (newVal) => {
				db.save('msg', newVal)
			}
		}
	}
</script>
<style lang="scss">
	.message_info {
		textarea {
			background-color: #FFFFFF;
			color: #6d6d6d;
			border-radius: 5px;
			padding: 15px;
			width: 620rpx;
		}
	}

	.content {
		padding: 0px 20px 0px 20px;

		.message_title {
			margin-bottom: 8px;
			font-size: 18px;
			font-style: inherit;
			color: #6d6d6d;
		}
	}

	.tags {
		display: flex;
		margin: 0px 40px 20px 20px;
		flex-direction: row-reverse;

	}

	.account_profile {
		width: 200rpx;
		margin-right: 50rpx;
		display: flex;
		align-items: center;
		background-color: #FFFFFF;

		.head_image {
			width: 180rpx;
			height: 180rpx;
			border-radius: 50%;

			image {
				position: absolute;
				width: 200rpx;
				height: 200rpx;
				right: 50rpx;
				opacity: 0.7;
			}
		}

		.head_icon {
			margin-left: 65rpx;
			width: 35px;
			height: 35px;
			z-index: 99;
		}

	}

	.header {
		width: 100%;
		height: 200rpx;
		display: flex;
		align-items: center;
		margin: 100rpx 0px 0px 0px;
		flex-direction: row-reverse;
		justify-content: space-between;
	}

	.nickname {
		// line-height: 20px;
		display: flex;
		flex-direction: column;
		margin: 10px 0px 10px 10px;

		h1 {
			margin-bottom: 15px;
		}

		.name_icon {
			display: flex;

			.u-icon {
				margin-left: 10rpx;
			}
		}
	}

	.change_nickname {
		display: flex;
		width: 700rpx;
		height: 400rpx;
		justify-content: center;
		align-items: center;

		.input_nickname {
			width: 550rpx;
			display: flex;

			.input {
				border: #6d6d6d 3rpx solid;
			}
		}

		.custom-style {
			width: 100rpx;
			margin-top: 5rpx;
			margin-left: 30rpx;
			height: 80rpx;
		}

	}
</style>
