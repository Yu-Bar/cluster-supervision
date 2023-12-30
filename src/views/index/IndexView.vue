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
    }
  },
  mounted () {
    // 初始化
    this.init()
  },
  methods: {

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
