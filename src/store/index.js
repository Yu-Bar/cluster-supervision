import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    nodeList: [{
      name: 'Welcome!',
      address: 'Yu-Bar',
      contactTime: 'Distribute',
      capacityUsage: 520,
      capacity: "Supervision",
      version: 'System',
      status: 1,
      memUsage: 0,
      mem: '',
      procNum: 0,
      cpuLoad: 0,
    }]// 存储节点状态
  },
  getters: {
  },
  mutations: {
    updateNodeList(state, data) {
      state.nodeList = data;
    },
  },
  actions: {
  },
  modules: {
  }
})
