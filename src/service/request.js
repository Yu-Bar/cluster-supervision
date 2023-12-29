import axios from 'axios';

const BASE_URL = 'http://localhost:9090';

export const http = async (options) => {
    return axios({
        method: options.method,
        url:`${BASE_URL}${options.url}`,
        params: options.params
    }).then(function(response){
        //成功响应时执行的回调 value.data可以获取到服务器响应内容
        return response.data
    }).catch(function(reason){
        //有异常时执行的回调  reason.response.data可以获取到响应的内容  reason.message / reason.stack 可以查看错误的信息
        console.error('Error fetching data:', reason.message);
    });
};

