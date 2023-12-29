<template>

  <el-descriptions class="margin-top" title="节点信息" :column="3" border>
    <template slot="extra">
      <el-button type="primary" size="small">操作</el-button>
    </template>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        节点 ID
      </template>
      {{nodeInfo.id}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        节点名称
      </template>
      {{nodeInfo.name}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-location-outline"></i>
        Socket 地址
      </template>
      {{nodeInfo.address}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        节点状态
      </template>
      <el-tag size="small">
        <span v-if="nodeInfo.status == 0">连接断开</span>
        <span v-else-if="nodeInfo.status == 1" >连接正常</span>
        <span v-else-if="nodeInfo.status == 2">重连中</span>
        <span v-else>主动下线</span>
      </el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-office-building"></i>
        版本号
      </template>
      {{nodeInfo.version}}
    </el-descriptions-item>
  </el-descriptions>
</template>

<script>

import {getNodeById} from "@/service/NodeApi";

export default {
  data () {
    return {
      nodeInfo: ''
    };
  },
  methods: {
    async getNodeInfo() {
      const res = await getNodeById(this.$route.params.id)
      // console.log(res)
      if (res.code == 1) {
        this.nodeInfo = res.data
      }
    }
  },
  mounted() {
    this.getNodeInfo();
  }
}
</script>
