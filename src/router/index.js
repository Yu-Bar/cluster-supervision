import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/dashboard' //重定向
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
  {
    path: '/detail/:id',  //地址hash
    name: 'detail',
    component:  () => import('../views/mainview/NodeDetailView.vue')  //对应的vue组件
  },
]

const router = new VueRouter({
  routes
})

export default router
