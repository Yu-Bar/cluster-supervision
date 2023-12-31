import {http} from "@/service/request";

export const getNodeById = (id) => {
    return http({
        method:'GET',
        url: `/node/${id}`
    })
}

export const offlineNodeById = (id) => {
    return http({
        method:'PUT',
        url: `/node/${id}`
    })
}

export const deleteNodeById = (id) => {
    return http({
        method:'DELETE',
        url: `/node/${id}`
    })
}

export const changeNodeNameById = (id,name) => {
    return http({
        method:'PUT',
        url: `/node`,
        data: {
            id: id,
            name: name
        }
    })
}
