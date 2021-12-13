import axios from "axios";

//创建axios实例
const instance = axios.create({
    baseURL:"http://127.0.0.1:8081",
    // baseURL:"http://8.130.179.210:8081",
    timeout:15000,
    //headers:{}
})

//请求拦截
instance.interceptors.request.use(
    function (config) {
        // console.log('请求拦截：',config)
        if (localStorage.getItem('Token')) {
            config.headers.Authorization = localStorage.getItem('Token');
        } else {
            console.log('token无法获取');
        }
        return config
    },
    function (err) {
        return Promise.reject(err)
    }
)

//响应拦截
instance.interceptors.response.use(
    function (res) {
        // console.log('响应拦截：',res);
        return res
    },
    function (err) {
        return Promise.reject(err)
    }
)

//封装get请求
export function get(url,params) {
    return instance.get(url,{
        params
    })
}

//封装post请求
export function post(url,data,config) {
    return instance.post(url,data,config)
}

export function reqFuc(data) {
    return instance.request({
        url: '/api/order/download', // 接口
        method: 'post',  //
        responseType: 'blob', // 注意:这里必须指定返回类型,否则打不开文件
        data:data
    })
}