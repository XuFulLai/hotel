<template>
    <div class="box">
        <el-form ref="form" :model="form" label-width="120px">
            <el-form-item v-show="false">
                <el-input v-model="form.id"></el-input>
            </el-form-item>
            <el-form-item label="酒店名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="酒店详细地址">
                <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="停车场">
                <el-input v-model="form.parkingLot"></el-input>
            </el-form-item>
            <el-form-item label="修改酒店封面">
                <div class="d-flex align-items-center mb-15">
                    <el-image style="width: 100px; height: 100px"
                              :src="form.cover"
                              fit="fit">
                    </el-image>
                    <el-upload
                            ref="upload"
                            action="#"
                            class="avatar-uploader ml-10"
                            accept="image/png,image/gif,image/jpg,image/jpeg"
                            :show-file-list="false"
                            :on-change="onChangeFile"
                            :before-upload="beforeAvatarUpload"
                            :http-request="uploadImg">
                        <el-button
                                type="success"
                                plain
                                round
                                size="mini">
                            选择图片
                        </el-button>
                    </el-upload>
                </div>
            </el-form-item>
            <el-form-item label="其他政策">
                <div class="Tinymce_box">
                    <tinymce v-model="form.otherPolicy" :height="500"/>
                    <div v-if="form.otherPolicy" class="editor-content">
                        <h3>预览效果：</h3>
                        <div v-html="form.otherPolicy"/>
                    </div>
                </div>
            </el-form-item>


            <el-form-item>
                <el-button type="primary" @click="saveInfo">保存</el-button>
                <el-button @click="getHotelInfo">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {get, post} from "../utils/request";
    import Tinymce from "./Tinymce"

    export default {
        name: "MyHotel",
        components: {
            Tinymce
        },
        data() {
            return {
                form: {
                    cover: '',
                    otherPolicy: '',
                    id: '',
                    name: '',
                    address: '',
                    parkingLot: '',
                },
                a: ''
            }
        },
        created() {
            this.getHotelInfo()
        },
        mounted() {
        },
        methods: {
            getHotelInfo() {
                get("/api/hotel/myHotel").then(res => {
                    console.log(res);
                    this.form = res.data.data
                    // this.form.cover = 'http://' + res.data.cover
                    // this.form.cover = res.data.cover
                }).catch(err => {
                    this.$notify.error({
                        title: '错误',
                        message: err
                    });
                })
            },
            saveInfo() {
                post("/api/hotel/modify", this.form).then(res => {
                    this.$notify({
                        title: '信息',
                        message: '修改成功'
                    })
                })
            },

            //图片改变调用函数
            onChangeFile(file) {
                this.form.cover = URL.createObjectURL(file.raw);

            },
            //图片上传之前的回调函数
            beforeAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 5MB!');
                }
                return isLt2M;
            },
            //图片上传函数
            uploadImg(params) {
                console.log(params);
                const config = {
                    headers: {'Content-Type': 'multipart/form-data'},
                }
                const file = params.file

                //formdata
                const fd = new FormData();//通过form数据格式来传
                fd.append("img", file); //传文件
                fd.append("key", "hotel_cover");

                post("/upload/img", fd, config)
                    .then(res => {
                        console.log(res);
                        this.form.cover = 'http://' + res.data.data
                    })
                    .catch(err => {
                        console.log(err);
                    })
            },
        }
    }
</script>

<style scoped>

    .box {
        width: calc(100vw + 235px);
        /*width: 100vw;*/
        display: flex;
        flex-direction: column;
        padding: 25px;
        background: #FFFFFF;
        box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
    }

    .Tinymce_box {
        display: flex;
    }

    .editor-content {
        margin-left: 30px;
        flex-grow: 1;
        border: 2px dashed #f1f1f1;
        padding: 0 20px;
    }

    h3 {
        color: #808080;
    }
</style>