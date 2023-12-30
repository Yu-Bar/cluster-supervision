import axios from 'axios';
import store from '@/store';

const BASE_URL = 'http://localhost:9090';

// 创建一个 Axios 实例
const instance = axios.create({
    baseURL: BASE_URL
});

// 添加请求拦截器
instance.interceptors.request.use(
    function(config) {
        let token = '';
        // 在请求头中添加 authentication 变量
        if (config.url !== '/login' || config.url !== '/register' ) {
            // 从 Vuex store 中获取 token
            token = store.state.token;
        }
        config.headers['authentication'] = token;
        return config;
    },
    function(error) {
        return Promise.reject(error);
    }
);

// 添加响应拦截器
instance.interceptors.response.use(
    function(response) {
        // 在登录成功后将返回的 token 存储到 Vuex
        // console.log('response',response)
        if (response.config.url === '/user/login' || response.config.url === '/user/sign') {
            if(response.data.code == 1){
                const data = response.data.data
                // 存储 token 到 Vuex
                store.commit('setToken', data.token);
                store.commit('setUserId', data.id);
            }
        }
        return response;
    },
    function(error) {
        return Promise.reject(error);
    }
);

// http 方法，使用上面配置好的 Axios 实例
export const http = async (options) => {
    return instance({
        method: options.method,
        url: options.url,
        params: options.params,
        data: options.data
    }).then(function(response){
        return response.data;
    }).catch(function(reason){
        console.error('Error fetching data:', reason.message);
    });
};


