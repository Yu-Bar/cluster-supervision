<template>
  <el-container>
    <el-header>分布式集群监管系统</el-header>
    <el-container>
      <el-aside width="200px">
        <el-row>
          <el-col>
            <el-menu>
              <el-menu-item index="2">
                <i class="el-icon-menu"></i>
                <router-link to="/dashboard" class="router-link">控制面板</router-link>
              </el-menu-item>
              <el-menu-item index="4">
                <i class="el-icon-s-comment"></i>
                <router-link to="/command" class="router-link">执行命令</router-link>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </el-aside>
      <el-main><router-view></router-view></el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      path: 'ws://localhost:9090/ws/123',
      socket: ''
    }
  },
  mounted () {
    // 初始化
    this.init()
  },
  methods: {
    init: function () {
      if(typeof(WebSocket) === "undefined"){
        alert("您的浏览器不支持socket")
      }else{
        // 实例化socket
        this.socket = new WebSocket(this.path)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error
        // 监听socket消息
        this.socket.onmessage = this.getMessage
      }
    },
    open: function () {
      console.log("socket连接成功")
    },
    error: function () {
      console.log("连接错误")
    },
    getMessage: function (msg) {
      console.log(msg.data)
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
  destroyed () {
    // 销毁监听
    this.socket.onclose = this.close
  }
}
</script>

<style>
.el-header, .el-footer {
  background-color: #2976dc;
  color: #f6f6f6;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
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
