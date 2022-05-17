import axios from "axios";

//创建axios实例
const instance = axios.create({
    // baseURL:"http://106.52.219.171:8100",
     baseURL:"http://127.0.0.1:8105",
    // timeout:15000, 请求超时设置
})

//请求拦截
instance.interceptors.request.use(
    function (config) {
        if (localStorage.getItem('Token')) {
            config.headers.Authorization = localStorage.getItem('Token');
        } else if(/auth/.test(config.url)) { // 通过正则匹配请求url是否有auth，为true则不需要携带token
            return config
        } else{ // 请求为携带token，且不是非权限接口，则提示无法获取token
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

//post请求,Content-Type为application/x-www-form-urlencoded
export function formDataPost(url,data) {
    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
        transformRequest : [function (data) {
            let ret = ''
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret
        }]
    }
    return instance.post(url,data,config)
}

// export function postOnForm(url,data,config) {
//     instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
//     instance.defaults.transformRequest = [function (data) {
//         let ret = ''
//         for (let it in data) {
//             ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
//         }
//         return ret
//     }]
//     return instance.post(url,data,config)
// }