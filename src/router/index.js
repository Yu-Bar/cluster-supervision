import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'index',
    component:  () => import('../views/mainview/DashBoardView.vue')  //对应的vue组件
  },
  {
    path: '/dashboard',  //地址hash
    name: 'dashboard',
    component:  () => import('../views/mainview/DashBoardView.vue')  //对应的vue组件
  },
  {
    path: '/command',  //地址hash
    name: 'command',
    component:  () => import('../views/mainview/CommandView.vue')  //对应的vue组件
  },
]

const router = new VueRouter({
  routes
})

export default router
