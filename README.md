# Pepper Metrics Project  
## Architecture  
Pepper Metrics项目从核心概念上来说，基于Tom Wilkie的[RED](https://grafana.com/blog/2018/08/02/the-red-method-how-to-instrument-your-services/)理论，即对每个服务
（这里的服务特指进程中的某种调用，比如调用一次数据库查询）进行RED指标收集，并持久化到数据库，并通过dashboard进行展示，辅助进行性能趋势分析。  
更多介绍请点击：[Architecture](./docs/Architecture.md)
### Concept
![](http://oss.zrbcool.top/picgo/pepper-metrics-concept.png)
### Arch
![](http://oss.zrbcool.top/picgo/pepper-metrics-arch-2019-08-14.png)
> 各个组件说明
> - Profiler， 核心部分，用于启动定期调度任务，并通过ExtensionLoad加载所有的ScheduledRun扩展，按照指定周期发起调度。同时内部维护Stats的构造器Profiler.Builder
> - Scheduler， 虚拟概念，在Profiler作为一个定时任务存在
> - ExtensionLoader， 非常重要的组件，通过Java SPI机制加载插件，使项目的各个模块可以灵活插拔，也是项目架构的基石
> - ScheduledRun， 扩展点：pepper metrics core会定时调度，传递所有的Stats，实现插件可以使用Stats当中收集到的性能数据，目前已实现的为scheduled printer组件
> - Pepper Metrics X， 具体的集成，我们的目标是度量一切，目前计划实现的为：jedis，motan，dubbo，servlet，mybatis等最常用组件
## Getting started  
以Mybatis集成为例，更多其他请参考：[User Guide](./docs/User_guide.md#samples)  
- 增加maven依赖
```xml
<dependencies>
    <!-- pepper metrics dependencies -->
    <dependency>
        <groupId>com.pepper</groupId>
        <artifactId>pepper-metrics-mybatis</artifactId>
        <version>1.0.0</version>
    </dependency>
    <!-- pepper-metrics datasource use prometheus by default -->
    <dependency>
        <groupId>com.pepper</groupId>
        <artifactId>pepper-metrics-ds-prometheus</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```
- 配置Pepper Metrics定制的Mybatis插件使集成生效
```xml
<configuration>
    <typeAliases>
        ...
    </typeAliases>
    <!-- 加入如下配置 -->
    <plugins>
        <plugin interceptor="com.pepper.metrics.integration.mybatis.MybatisProfilerPlugin" />
    </plugins>
    <mappers>
        ...
    </mappers>
</configuration>
```
- 日志输出效果：
```bash
[perf:mybatis:20190814144344] - --------------------------------------------------------------------------------------------------------------------------------------------------------------
[perf:mybatis:20190814144344] - | Metrics                                                                       Max(ms) Concurrent     Error     Count   P90(ms)   P99(ms)  P999(ms)     Qps | 
[perf:mybatis:20190814144344] - | com.pepper.metrics.sample.mybatis.mapper.HotelMapper.selectByCityId               3.4          0         0      1950       0.7       1.7       3.3    32.5 | 
[perf:mybatis:20190814144344] - | sample.mybatis.mapper.CityMapper.selectCityById                                  58.7          0         0      1950       0.7       2.4      58.7    32.5 | 
[perf:mybatis:20190814144344] - --------------------------------------------------------------------------------------------------------------------------------------------------------------
```
- Prometheus指标输出效果（默认的实现，可以修改为其他数据库）
```bash
 ✗ curl localhost:9145/metrics
# HELP jedis_summary_seconds  
# TYPE jedis_summary_seconds summary
jedis_summary_seconds{method="set",namespace="somens",quantile="0.9",} 9.8304E-4
jedis_summary_seconds{method="set",namespace="somens",quantile="0.99",} 9.8304E-4
jedis_summary_seconds{method="set",namespace="somens",quantile="0.999",} 0.209682432
jedis_summary_seconds{method="set",namespace="somens",quantile="0.99999",} 0.209682432
jedis_summary_seconds_count{method="set",namespace="somens",} 440.0
jedis_summary_seconds_sum{method="set",namespace="somens",} 0.609
# HELP jedis_summary_seconds_max  
# TYPE jedis_summary_seconds_max gauge
jedis_summary_seconds_max{method="set",namespace="somens",} 0.208
# HELP jedis_concurrent_gauge  
# TYPE jedis_concurrent_gauge gauge
jedis_concurrent_gauge{method="set",namespace="somens",} 0.0
```
### Maven dependency
以Mybatis为例，更多其他请参考：[User Guide](./docs/User_guide.md#samples)  
```xml
<dependencies>
    <!-- pepper metrics dependencies -->
    <dependency>
        <groupId>com.pepper</groupId>
        <artifactId>pepper-metrics-mybatis</artifactId>
        <version>1.0.0</version>
    </dependency>
    <!-- pepper-metrics datasource use prometheus by default -->
    <dependency>
        <groupId>com.pepper</groupId>
        <artifactId>pepper-metrics-ds-prometheus</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```
### Next steps  
- [Pepper Metrics User Guide](./docs/User_guide.md)
- [Pepper Metrics Architecture](./docs/Architecture.md)
- [Pepper Metrics ROAD-MAP](./docs/Roadmap.md)
- [Pepper Metrics Development Guide](./docs/Dev_Guide.md)
- [Pepper Metrics Development Plan](./docs/Dev_plan.md)
## Building  
```bash
mvn clean package install
```
## Contact  
* Bugs: [Issues](https://github.com/zrbcool/pepper-metrics/issues/new?template=dubbo-issue-report-template.md)
* Dingtalk chat group [Link](https://qr.dingtalk.com/action/joingroup?code=v1,k1,U4KKXEbTFBpuMbQMIQNij2IYszit+yktsAJh/9NjLFM=&_dt_no_comment=1&origin=11):  
![](http://oss.zrbcool.top/picgo/pepper-metrics-dingtalk-qrcode.png) 
* Developer
    * [@zrbcool](https://github.com/zrbcool)
    * [@Lord-X](https://github.com/Lord-X)
