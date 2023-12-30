import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 存储节点状态
    nodeList: [{
      name: 'Welcome!',
      address: 'Yu-Bar',
      contactTime: 'Distribute',
      capacityUsage: 50,
      capacity: "Supervision",
      version: 'System',
      status: 1,
      memUsage: 0,
      mem: '',
      procNum: 0,
      cpuLoad: 0,
    }],
    // 令牌
    token: null,
    userId: null
  },
  getters: {
  },
  mutations: {
    updateNodeList(state, data) {
      state.nodeList = data;
    },
    setToken(state, token) {
      state.token = token;
    },
    setUserId(state, userId) {
      state.userId = userId;
    }
  },
  actions: {
  },
  modules: {
  }
})
