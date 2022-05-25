# simple-boot 推荐springboot版本：2.5.6  
#### 注意：高版本可能会导致swagger自动装配失败（可添加如下配置解决）

    spring:
      mvc:
        # swagger3.0兼容高版本springboot
        pathmatch:
          matching-strategy: ant_path_matcher
## 🌐 简单配置，快速初始化springboot接口项目


#### 配置yml文件：

    simple-boot:
      #权限验证
      auth:
        jwtTokenSecret: JYVc5fEh7oiu
        #access_token有效期
        jwtTokenOffset: 1800
        #refresh_token有效期
        refreshTokenOffset: 3600
      #swigger配置  
      swagger:
        title: xxx 接口
        version: v1.0
        controller:
          path: com.xxx.controller
      druid:
        #管理账号
        loginUser: admin
        #管理密码
        password: 123456
        #是否可以重置数据
        reSet: false
      #代码生成配置  
      codeGen:
        dbUrl: jdbc:mysql://-服务器IP-:-端口-/-改为数据库名-?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
        dbUser: 用户
        dbPwd: 密码
        #生成文件夹
        packagePath: com.xxx.xxx.modules
        #表前缀
        tablePrefix: bs_,sys_

#### 接口放开权限验证(两种方式)：
- 接口路径包含/open
- @TokenValidatorAnnotaion(required=false)

----------------------------------项目使用----------------------------------------

#### 🔖 添加仓库：

    <repositories>
        <repository>
            <id>maven-repository-main</id>
            <url>https://raw.github.com/lvdashi/simple-boot/pom/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
    
#### 🔖 添加引用：

    <dependency>
            <groupId>com.ljh</groupId>
            <artifactId>simple-boot</artifactId>
            <version>1.2</version>
        </dependency>
