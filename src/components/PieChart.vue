<template>
  <div ref="echarts" style="width: 550px; height: 350px;"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  props: {
    title: String,
    percent: Number
  },
  data() {
    return {
    }
  },
  mounted() {
    // 监听 chartData 属性的变化
    this.$watch('percent', this.initChart, { immediate: true });
  },
  methods: {
    initChart() {
      console.log('percent',this.percent)
      // 基于this.$refs.echarts初始化echarts实例
      const chartDom = this.$refs.echarts
      const myChart = echarts.init(chartDom)

      // 图表配置项
      const option = {
        title: {
          text: this.title,
          left: 'center', // 设置标题居中
          textStyle: {
            fontSize: 18, // 标题字体大小
            fontWeight: 'bold' // 标题字体加粗
            // 其他标题样式设置
          }
        },
        series: [
          {
            type: 'gauge',
            progress: {
              show: true,
              width: 18
            },
            axisLine: {
              lineStyle: {
                width: 18
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              length: 15,
              lineStyle: {
                width: 2,
                color: '#999'
              }
            },
            axisLabel: {
              distance: 25,
              color: '#999',
              fontSize: 20
            },
            anchor: {
              show: true,
              showAbove: true,
              size: 25,
              itemStyle: {
                borderWidth: 10
              }
            },
            title: {
              show: false
            },
            detail: {
              valueAnimation: true,
              fontSize: 60,
              offsetCenter: [0, '70%']
            },
            data: [
              {
                value: this.percent
              }
            ]
          }
        ]
      }

      // 使用刚指定的配置项和数据显示图表
      myChart.setOption(option)
    }
  }
}
</script>

<style>
/* 在此处添加样式 */
</style>
