import {http} from "@/service/request";

export const loginService = (username,password) => {
    return http({
        method:'POST',
        url: `/user/login`,
        data: {
            username: username,
            password: password
        }
    })
}

export const signService = (username,password) => {
    return http({
        method:'POST',
        url: `/user/sign`,
        data: {
            username: username,
            password: password
        }
    })
}
