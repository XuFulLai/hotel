<template>
  <div>
    <div class="main-content">
      <div id="order-box"></div>
      <div id="order-statistics"></div>
      <div id="map-box"></div>
      <div id="room-type-box"></div>
      <div id="contrast-box"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {get, post} from "../utils/request";
import chinaJson from "../assets/js/china"

echarts.registerMap('china', {
  geoJSON: chinaJson
})

export default {
  name: "MainContent",
  data() {
    return {}
  },
  mounted() {
    this.orderInit()
    this.orderStatisticsInit()
    this.mapInit()
    this.roomTypeInit()
    this.contrastInit()
  },
  methods: {
    //
    orderInit() {
      let order = document.getElementById('order-box')
      let orderChart = echarts.init(order)
      get('/api/chart/order/way')
          .then(res => {
            orderChart.setOption({
              title: {
                text: '订单来源统计',
                left: 'center'
              },
              tooltip: {
                trigger: 'item'
              },
              legend: {
                orient: 'vertical',
                left: '5%',
                top: '5%'
              },
              series: [
                {
                  type: 'pie',
                  radius: ['40%', '70%'],
                  avoidLabelOverlap: false,
                  label: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    label: {
                      show: true,
                      fontSize: '40',
                      fontWeight: 'bold'
                    }
                  },
                  labelLine: {
                    show: false
                  },
                  data: res.data.data
                }
              ]
            })
          })
          .catch(err => {
            console.log(err);
          })

    },

    //
    orderStatisticsInit() {
      let orderStatistics = document.getElementById('order-statistics')
      let orderStatisticsChart = echarts.init(orderStatistics)

      get('/api/chart/order/orderNum/year/1')
          .then(res => {
            let monthList = []
            let valList = []
            for (let i = 0; i < res.data.data.length; i++) {
              monthList.push(res.data.data[i].name)
              valList.push(res.data.data[i].value)
            }
            orderStatisticsChart.setOption({
              title: {
                text: '近一年订单统计',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{b} <br/> {c}单'
              },
              xAxis: {
                type: 'category',
                data: monthList
              },
              yAxis: {
                type: 'value'
              },
              series: [
                {
                  data: valList,
                  type: 'bar',
                  showBackground: true,
                  backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                  }
                }
              ]
            })
          })
          .catch(err => {
            console.log(err);
          })
    },

    //订单来访地统计
    mapInit() {
      let map = document.getElementById('map-box')
      let mapChart = echarts.init(map)

      get('/api/chart/order/map')
          .then(res => {
            if (res.data.code == 200) {
              let valArr = []
              for (let i = 0; i < res.data.data.length; i++) {
                valArr.push(res.data.data[i].value)
              }
              let maxNum = Math.max(...valArr)
              mapChart.setOption({
                // 进行相关配置
                title: {
                  text: '订单来访地统计',
                  left: 'center'
                },
                toolbox: {
                  show: true,
                  feature: {
                    mark: { show: true },
                    dataView: { show: true, readOnly: true },
                    restore: { show: true },
                    saveAsImage: { show: true }
                  }
                },
                tooltip: {
                  trigger: 'item',
                  showDelay: 0,
                  transitionDuration: 0.2,
                  formatter: function (params) {
                    return params.seriesName + '<br/>' + params.name + ': ' + params.value;

                  }
                },
                visualMap: {
                  left: 'left',
                  min: 0,
                  max: maxNum + 10,
                  inRange: {
                    color: [
                      '#ffffbf',
                      '#eaf7f8',
                      '#abd9e9',
                      '#74add1',
                      '#4575b4',
                      '#313695',
                      // '#fdae61',
                      // '#f46d43',
                      // '#d73027',
                      // '#a50026'
                    ]
                  },
                  text: ['High', 'Low'],
                  calculable: true
                },
                geo: {
                  // 这个是重点配置区
                  map: "china", // 表示中国地图
                  roam: true,
                  label: {
                    normal: {
                      show: true, // 是否显示对应地名
                      textStyle: {
                        color: "#000",
                      },
                    },
                  },
                  zoom: 1.2,
                  scaleLimit: {
                    min: 1,
                    max: 3.5,
                  },
                },
                series: [
                  {
                    type: "scatter",
                    coordinateSystem: "geo", // 对应上方配置
                  },
                  {
                    name: "订单数量", // 浮动框的标题
                    type: "map",
                    geoIndex: 0,
                    data: res.data.data
                  },
                ],
              });
            }
          })
          .catch(err => {

          })
    },

    //
    roomTypeInit() {
      let roomType = document.getElementById('room-type-box')
      let roomTypeChart = echarts.init(roomType)

      get('/api/chart/order/roomType')
          .then(res => {
            roomTypeChart.setOption({
              title: {
                text: '订单房间类型',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{b} <br/> {c} ({d}%)'
              },
              legend: {
                top: 'bottom',
              },
              series: [
                {
                  type: 'pie',
                  radius: [20, 120],
                  center: ['50%', '50%'],
                  roseType: 'area',
                  itemStyle: {
                    borderRadius: 8
                  },
                  data: res.data.data
                }
              ]
            })
          })
          .catch(err => {
            console.log(err);
          })


    },

    //订单统计-盈利统计
    contrastInit() {
      let incomeList = []
      let dateList = []
      let orderNumList = []

      // 实例化
      let contrast = document.getElementById('contrast-box')
      let contrastChart = echarts.init(contrast)

      function f1() {
        get('/api/chart/order/income/day/7')
            .then(res => {
              for (let i = 0; i < res.data.data.length; i++) {
                incomeList.push(res.data.data[i].value) //整理出符合的value值到新数组
                genFunction.next() //next
              }
            })
            .catch(err => {
              console.log(err);
            })

      }

      function f2() {
        get('/api/chart/order/orderNum/day/7')
            .then(res => {
              for (let i = 0; i < res.data.data.length; i++) {
                dateList.push(res.data.data[i].name)//整理后台数据到新数组
                orderNumList.push(res.data.data[i].value)//整理后台数据到新数组
              }

              // 渲染数据，创建可视化视图
              contrastChart.setOption({
                tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                    type: 'cross',
                    crossStyle: {
                      color: '#999'
                    }
                  }
                },
                legend: {
                  data: ['订单统计', '盈利统计']
                },
                xAxis: [
                  {
                    type: 'category',
                    data: dateList,
                    axisPointer: {
                      type: 'shadow'
                    }
                  }
                ],
                yAxis: [
                  {
                    type: 'value',
                    name: '订单统计',
                    nameTextStyle: {
                      color: 'skyblue',
                      fontSize: 16,
                      align: 'center'
                    },
                    nameGap: 30,
                    axisLabel: {
                      formatter: '{value} 单'
                    }
                  },
                  {
                    type: 'value',
                    name: '盈利统计',
                    nameTextStyle: {
                      color: 'skyblue',
                      fontSize: 16,
                      align: 'center'
                    },
                    nameGap: 30,
                    axisLabel: {
                      formatter: '{value} 元'
                    }
                  }
                ],
                series: [
                  {
                    name: '订单统计',
                    type: 'bar',
                    barWidth: 30,//柱图宽度
                    data: orderNumList
                  },
                  {
                    name: '盈利统计',
                    type: 'line',
                    yAxisIndex: 1,
                    data: incomeList
                  }
                ]
              })
              genFunction.next() //next
            })
            .catch(err => {
              console.log(err);
            })


      }

      // 生成器函数
      function* gen() {
        yield f1()
        yield f2()

      }

      let genFunction = gen()
      genFunction.next()


    }

  }
}
</script>

<style scoped>
.main-content {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.main-content > div {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 46%;
  height: 430px;
  margin: 2%;
  background: #FFFFFF;
  padding: 15px 0px;
}

#map-box {
  width: 100%;
}
</style>