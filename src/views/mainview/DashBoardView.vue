<template>
  <el-table
      :data="$store.state.nodeList"
      stripe
      height="550"
      style="width: 100%">
    <el-table-column
        prop="name"
        label="节点名称"
        width="180">
      <template v-slot="{ row }">
        <div class="table-item-wrapper">
          <router-link :to="/detail/+row.id" class="router-link">{{ row.name }}</router-link>
          <span class="icon-image">
            <img v-if="row.status == 0" src="@/static/img/wrong.png" alt="连接断开">
            <img v-else-if="row.status == 1" src="@/static/img/ok.png" alt="已连接">
            <img class="el-icon-loading" v-else-if="row.status == 2" src="@/static/img/retry.png" alt="重连中...">
            <img v-else src="@/static/img/offline.png" alt="主动下线">
          </span>
        </div>
      </template>
    </el-table-column>

    <el-table-column
        prop="address"
        label="IP地址"
        width="180"
        align="center">
    </el-table-column>
    <el-table-column
        label="上次活跃时间"
        align="center">
      <template v-slot="{ row }">
        <span>{{ timeDiff(row.contactTime) }} S</span>
      </template>
    </el-table-column>
    <el-table-column prop="capacity" label="容量"  width="400" align="center">
      <template v-slot="{ row }">
        <div class="table-item-wrapper">
          <el-progress
              class="progress-bar"
              :text-inside="true"
              :stroke-width="24"
              :percentage="row.capacityUsage"
              status="success"
          ></el-progress>
          <span>{{ row.capacity }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column
        prop="version"
        label="版本"
        align="center">
    </el-table-column>
    <el-table-column
        label="操作"
        align="center">
      <template v-slot="{ row }">
        <el-button v-if="row.status == 1 || row.status == 2" type="info" icon="el-icon-bottom" circle @click="offlineNode(row)"></el-button>
        <el-button v-else type="danger" icon="el-icon-delete" circle @click="deleteNode(row)"></el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {deleteNodeById, offlineNodeById} from "@/service/NodeApi";

export default {
  data() {
    return {
      path: 'ws://localhost:9090/ws/',
      socket: ''
    }
  },
  computed: {
    timeDiff() {
      return (contactTime) => {
        const currentTime = new Date();
        const updateTime = new Date(contactTime);
        const difference = Math.floor((currentTime - updateTime) / 1000); // 计算时间差（单位：秒）
        return difference;
      }
    },
  },
  methods: {
    offlineNode(node) {
      this.$confirm(`确认要下线机器？${node.name}`, '下线节点', {
        distinguishCancelAndClose: true,
        confirmButtonText: '下线',
        cancelButtonText: '取消'
      }).then(async () => {
          const res = await offlineNodeById(node.id);
          if(res.code == 1){
            node.status = 3
            this.$message({
              type: 'success',
              message: '成功下线节点'
            })
          }
        }).catch(action => {
        console.log(action)
      });
    },
    async deleteNode(node) {
      this.$confirm(`确认要删除机器？${node.name}`, '删除节点', {
        distinguishCancelAndClose: true,
        confirmButtonText: '删除',
        cancelButtonText: '取消'
      }).then(async () => {
        const res = await deleteNodeById(node.id);
        if(res.code == 1){
          this.$message({
            type: 'success',
            message: '成功删除节点'
          })
        }
      }).catch(action => {
        console.log(action)
      });
    },
    initWebSocket: function () {
      console.log('初始化websocket')
      if(typeof(WebSocket) === "undefined"){
        alert("您的浏览器不支持socket")
      }else{
        if(this.$store.state.token){
          // 实例化socket
          this.socket = new WebSocket(this.path + this.$store.state.userId)
          // 监听socket连接
          this.socket.onopen = this.open
          // 监听socket错误信息
          this.socket.onerror = this.error
          // 监听socket消息
          this.socket.onmessage = this.getMessage
        }
      }
    },
    open: function () {
      console.log("socket连接成功")
    },
    error: function () {
      console.log("连接错误")
    },
    getMessage: function (msg) {
      // console.log(msg.data)
      this.$store.commit('updateNodeList',JSON.parse(msg.data))
    },
    // 发送消息给被连接的服务端
    send: function (params) {
      this.socket.send(params)
    },
    close: function () {
      console.log("socket已经关闭")
    }
  },
  beforeRouteEnter(to, from, next) {
    // 在组件被路由导航到之前执行的逻辑
    // 这个函数没有访问组件实例的 this，所以无法直接调用组件方法
    // 如果你需要访问组件实例，可以通过回调的方式实现
    next(vm => {
      // 在组件实例创建之后，可以访问组件的方法和属性
      if(vm.socket == '' || vm.socket == null){
        vm.initWebSocket(); // 调用组件的 init 方法
      }
    });
  }
}
</script>

<style>
.table-item-wrapper {
  display: flex;
  align-items: center;
}

.progress-bar {
  flex: 1;
}



.icon-image > img{
  margin-top: 10px;
  margin-left: 10px;
  width: 20px;
}

/* 覆盖默认的 router-link 样式 */
.router-link {
  color: inherit; /* 使用父级颜色 */
  text-decoration: none; /* 去掉下划线 */
}

.router-link:hover {
  text-decoration: underline; /* 鼠标悬停时显示下划线 */
}

</style>
