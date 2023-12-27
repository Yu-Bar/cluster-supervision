<template>
  <el-table
      :data="tableData"
      stripe
      style="width: 100%">
    <el-table-column
        prop="name"
        label="节点名称"
        width="180">
      <template v-slot="{ row }">
        <div class="table-item-wrapper">
          <span>{{ row.name }}</span>
          <span class="icon-image">
            <img v-if="row.status == 0" src="@/static/img/wrong.png" alt="重连中...">
            <img v-else-if="row.status == 1" src="@/static/img/ok.png" alt="已连接">
            <img v-else src="@/static/img/retry.png" alt="连接断开">
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
        prop="contactTime"
        label="上次活跃时间"
        align="center">
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
        capacityUsage: '10',
        capacity: "100G",
        version: '1.01',
        status: 0
      }]
    }
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

</style>
