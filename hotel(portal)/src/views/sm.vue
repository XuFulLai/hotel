<template>
    <div>
        <div></div>
        <!-- 背景和导航栏 -->
              
        <div class="top-bg">

            <div class="t-nav">
                               
                <div class="nav-content">
                                       
                    <ul>
                                               
                        <li><a href=" " title="">首页</a></li>
                                               
                        <li><a href="#" title="">酒店中心</a></li>
                                               
                        <li><a href="#" title="">酒店管理</a></li>
                                               
                        <button class="nav-bt">注册/登录</button>
                                           
                    </ul>
                                   
                </div>
                         
            </div>
            <div class="t-v">
                <h2>房间和利率</h2>
                <p>这里是文字内容 这里是文字内容
                    这里是文字内容 这里是文字内容
                    这里是文字内容 </p>
            </div>
                 
        </div>
        <div class="content">
            <div class="content-imag">
            </div>
            <div class="content-v">
                <h1>这里是文字标题</h1>
                <p>起价为每天320美元</p>
                <p class="in">这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容</p>
                <div class="more"><p><a>了解详情</a></p></div>
                <button>按钮</button>

            </div>
        </div>
        <div class="content">
            <div class="content-v">
                <h1>这里是文字标题</h1>
                <p>起价为每天320美元</p>
                <p class="in">这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容</p>
                <div class="more"><p><a>了解详情</a></p></div>
            </div>
            <div class="content-imag">
            </div>
        </div>
        <div class="content">
            <div class="content-imag"></div>
            <div class="content-v">
                <h1>这里是文字标题</h1>
                <p>起价为每天320美元</p>
                <p class="in">这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容</p>
                <div class="more"><p><a>了解详情</a></p></div>
            </div>
        </div>
        <div class="content">
            <div class="content-v">
                <h1>这里是文字标题</h1>
                <p>起价为每天320美元</p>
                <p class="in">这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容
                    这里是文字内容这里是文字内容这里是文字内容这里
                    是文字内容这里是文字内容这里是文字内容这里是文
                    字内容这里是文字内容这里是文字内容这里是文字内
                    容这里是文字内容这里是文字内容这里是文字内容</p>
                <div class="more"><p><a>了解详情</a></p></div>
            </div>
            <div class="content-imag">
            </div>
            <button>按钮</button>
        </div>
    </div>

</template>

<script>
    export default {
        name: "sm"
    }
</script>

<style scoped>
    .t-nav {
        width: 100%;
        opacity: 0.7;
        top: 0;
    }

    .top-bg .t-v {
        height: 160px;
        background: #40A5FD;
        text-align: center;
    }

    .top-bg .t-v h2 {
        color: #FFFFFF;
        line-height: 40px;
        letter-spacing: 5px;
        font-size: 24px;
        padding-top: 50px;
    }

    .top-bg .t-v p {
        color: #FFFFFF;
    }

    .nav-content {
        /* display: flex;
        justify-content: right;
        line-height: 98px; */
    }

    .nav-content ul {
        display: flex;
        justify-content: right;
        line-height: 98px;

    }

    .nav-content li {
        /* float: left;  */
        font-size: 18px;
        font-family: 59;
        font-weight: 400;
        margin: 0 60px;
    }

    .nav-bt {
        width: 123px;
        height: 34px;
        background: #28B535;
        border-radius: 16px;
        border: 0;
        color: #fff;
        margin: 30px 60px;
    }

    .nav-content a {
        color: #A1A09E;
    }

    .nav-content a:hover {
        color: #323230;
    }

    .content {
        display: flex;
    }

    .content-v {
        margin-top: 160px;
        margin-left: 160px;
        height: 400px;
    }

    .content-imag {
        text-align: center;
        margin-top: 100px;
        margin-left: 160px;
        width: 470px;
        height: 520px;
        background: #999999;
    }

    .content .content-v h1 {
        font-size: 36px;
        font-family: 59;
        font-weight: 400;
        color: #333333;
        line-height: 40px;
        letter-spacing: 10px;
    }

    .content .content-v p {
        height: 13px;
        font-size: 14px;
        font-family: 59;
        font-weight: 400;
        color: #333333;
        line-height: 25px;
    }

    .content .content-v .in {
        width: 461px;
        height: 85px;
        margin-top: 40px;
        font-size: 15px;
        font-family: 59;
        font-weight: 400;
        color: #898989;
        line-height: 24x;
    }

    .content .content-v .more {
        width: 118px;
        height: 38px;
        background: #E1BD85;
        margin-top: 150px;
    }

    .content .content-v .more p {
        height: 10px;
        font-size: 12px;
        font-family: 59;
        font-weight: 400;
        color: #FFFFFF;
        line-height: 38px;
        text-align: center;
    }


</style>
