simple-boot:
  auth:
    jwtTokenSecret: xx
    jwtTokenOffset: 1800
    refreshTokenOffset: 3600
  swagger:
    title: demo 文档
    version: v1.0
    controller:
      path: com.xx.xx.controller
  druid:
    loginUser: admin
    password: 123456
    reSet: false
  codeGen:
    dbUrl: jdbc:mysql://xx.xx.xx.xx:xx/xx?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    dbUser: xx
    dbPwd: xx
    packagePath: com.xx.xx.modules
    tablePrefix: bs_,sys_
server:
  port: 8080


logging:
  level:
    root: info

spring:
  #mvc:
    # swagger3.0兼容高版本springboot
    #pathmatch:
      #matching-strategy: ant_path_matcher
  datasource:
    username: xx
    password: xx
    url: jdbc:mysql://xx.xx.xx.xx:xx/xx?useUnicode=true&characterEncoding=utf-8&useSSL=false
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    # 下面为连接池的补充设置，应用到上面所有数据源中
    # 初始化大小，最小，最大
    initial-size: 5
    min-idle: 5
    max-active: 20
    # 配置获取连接等待超时的时间
    max-wait: 60000
    # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    time-between-eviction-runs-millis: 60000
    # 配置一个连接在池中最小生存的时间，单位是毫秒
    min-evictable-idle-time-millis: 300000
    validation-query: SELECT 1 FROM DUAL
    test-while-idle: true
    test-on-borrow: false
    test-on-return: false
    # 打开PSCache，并且指定每个连接上PSCache的大小
    pool-prepared-statements: true
    #   配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    max-pool-prepared-statement-per-connection-size: 20
    filters: stat,wall
    use-global-data-source-stat: true
    # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
    connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

mybatis-plus:
  configuration:
    # 开启下划线转驼峰
    map-underscore-to-camel-case: true
    # 指定默认枚举类型的类型转换器
    default-enum-type-handler: com.baomidou.mybatisplus.extension.handlers.MybatisEnumTypeHandler
    # 配置sql日志打印
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    # 开启/关闭 banner 打印
    banner: true
    db-config:
      # 逻辑删除（软删除）
      logic-delete-value: '1'
      logic-not-delete-value: '0'
  # mapper路径位置
  mapper-locations: classpath*:mapper/*Mapper.xml
  type-aliases-package: "com.xx.xx.**.bean"


