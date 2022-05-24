<template>
	<view class="about">
		<view class="title">
			<H3></H3>
		</view>
		<view class="content">
			<u-skeleton
					rowsHeight='30'
				    rows="20"
				    title
					loading
					:rowsWidth='rowsWidth'
				></u-skeleton>
			<rich-text :nodes="message"></rich-text>
		</view>
	</view>
</template>

<script>
export default {
    data() {
        return {
			message:'...',
			rowsWidth:[380,400,350,360,200,355,580,380,400,350,360,200,355,580,380,400,350,360,200,355,580,380,400,350,360,200,355,580,]
		}
	},
	onLoad() {
		this.showModal()
	},
	methods:{
		showModal() {
			this.show = true;
		},
		confirm() {
				// 3秒后自动关闭
				this.show = false;
		}
    }
}
</script>
<style lang="scss">
	page{
		background-color: #FFFFFF;
	}
	
</style>