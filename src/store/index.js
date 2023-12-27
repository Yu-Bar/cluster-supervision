import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    nodeList: null, // 存储节点状态
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
