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
          <span>{{ row.name }}</span>
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
        <el-button v-if="row.status == 1 || row.status == 2" type="info" icon="el-icon-bottom" circle></el-button>
        <el-button v-else type="danger" icon="el-icon-delete" circle></el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  data() {
    return {
      tableData: [{
        name: '节点1',
        address: '192.168.1.11:9866',
        contactTime: '2s',
        capacityUsage: 10,
        capacity: "100G",
        version: '1.01',
        status: 0
      },
        {
          name: '节点2',
          address: '192.168.1.11:9866',
          contactTime: '2s',
          capacityUsage: 30,
          capacity: "10G",
          version: '1.01',
          status: 1
        }, {
          name: '节点3',
          address: '192.168.1.11:9866',
          contactTime: '2s',
          capacityUsage: 50,
          capacity: "20G",
          version: '1.01',
          status: 2
        }]
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

</style>
