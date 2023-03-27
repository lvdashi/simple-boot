package com.ljh.codegen;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.ljh.entity.TableFileds;
import com.ljh.util.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.*;

import static com.baomidou.mybatisplus.generator.config.rules.DateType.TIME_PACK;

/**
 * 基于mybatis-plus的代码生成器
 */
@Slf4j
@Component
public class CodeGenerator {

    private CodeGenerator() {
    }

    public static CodeGenerator getInstance() {
        return new CodeGenerator();
    }


    private static String dbUrl;
    private static String dbUser;
    private static String dbPwd;
    private static String packagePath;

    private static TableFileds setTableFileds;

    @Value("${simple-boot.codeGen.dbUrl}")
    public void setDbUrl(String dbUrl) {
        CodeGenerator.dbUrl = dbUrl;
    }
    @Value("${simple-boot.codeGen.dbUser}")
    public void setDbUser(String dbUser) {
        CodeGenerator.dbUser = dbUser;
    }
    @Value("${simple-boot.codeGen.dbPwd}")
    public void setDbPwd(String dbPwd) {
        CodeGenerator.dbPwd = dbPwd;
    }
    @Value("${simple-boot.codeGen.packagePath}")
    public void setPackagePath(String packagePath) {
        CodeGenerator.packagePath = packagePath;
    }

    @Resource
    private TableFileds tableFileds;

    @PostConstruct
    public void init() {
        setTableFileds = this.tableFileds;
    }

        /**
         * 生成代码
         * @param moduleName 生成模块名称
         * @param tableName 生成的数据库对应表名
         * @param author 生成作者
         */
    public static void generate(String moduleName,String tableName,String author) throws IOException {
        log.info("生成代码 -> ["+moduleName+"] 表名：" + tableName+" 作者："+author);
        log.info("配置生成路径 -> "+packagePath);
        String codeOutPath="";
        if(SystemUtil.getOsInfo().isWindows()){
            codeOutPath="\\"+packagePath.replace(".","\\").toString()+"\\";
        }else{
            codeOutPath="/"+packagePath.replace(".","/").toString()+"/";
        }
        log.info("codeOutPath->"+codeOutPath);
        final String codeOutPathFinal=codeOutPath;

        List<String> genFileList = Arrays.asList("entity", "req", "rsp", "controller", "service", "mapper", "xml");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();


        String codePath="";
        if(SystemUtil.getOsInfo().isWindows()){
            codePath=System.getProperty("user.dir")+"\\src\\main\\java";
        }else {
            codePath=System.getProperty("user.dir")+"/src/main/java";
        }
        log.info("codePath->"+codePath);
        final String projectPath = codePath;
        final String xmlPath=codePath.replace("java","resources").toString()+"/mapper/";
        log.info("代码实际生成路径 -> "+projectPath + codeOutPathFinal);
        gc.setOutputDir(projectPath);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger 注解
        gc.setServiceName("%sService");
        gc.setDateType(TIME_PACK);
        mpg.setGlobalConfig(gc);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        //dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(dbUser);
        dsc.setPassword(dbPwd);
        dsc.setDbType(DbType.MYSQL);
        //dsc.setTypeConvert(MySqlTypeConvert.INSTANCE);
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(packagePath);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出



        //entity
        if(genFileList.contains("entity")){
            focList.add(new FileOutConfig("templates/codegen/entity.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {

                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/entity/" + tableInfo.getEntityName() + "DO" + StringPool.DOT_JAVA;
                }
            });
        }

        if(genFileList.contains("req")){
            //add
            focList.add(new FileOutConfig("templates/codegen/addEntityReq.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/req/Add" + tableInfo.getEntityName() + "REQ" + StringPool.DOT_JAVA;
                }
            });

            //query
            focList.add(new FileOutConfig("templates/codegen/queryEntityPageReq.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/req/Query" + tableInfo.getEntityName() + "REQ" + StringPool.DOT_JAVA;
                }
            });

            //update
            focList.add(new FileOutConfig("templates/codegen/updateEntityReq.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/req/Update" + tableInfo.getEntityName() + "REQ" + StringPool.DOT_JAVA;
                }
            });
        }

        if(genFileList.contains("rsp")){
            //rsp
            focList.add(new FileOutConfig("templates/codegen/entityRsp.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/rsp/" + tableInfo.getEntityName() + "RSP" + StringPool.DOT_JAVA;
                }
            });
        }

        if(genFileList.contains("controller")){
            //controller
            focList.add(new FileOutConfig("templates/codegen/controller.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
                }
            });
        }

        if(genFileList.contains("service")){
            //service
            focList.add(new FileOutConfig("templates/codegen/service.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
                }
            });
        }

        if(genFileList.contains("mapper")){
            //mapper
            focList.add(new FileOutConfig("templates/codegen/mapper.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + codeOutPathFinal + pc.getModuleName() + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
                }
            });
        }

        if(genFileList.contains("xml")){
            //mapper.xml
            focList.add(new FileOutConfig("templates/codegen/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return xmlPath + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
        }

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略：下划线连转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //strategy.setNameConvert(new NameConvert());
        // 表字段生成策略：下划线连转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.example.demo.common.entity.BaseBean");
        // 是否为lombok模型; 需要lombok依赖
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setChainModel(true);
        // 公共父类
        //strategy.setSuperControllerClass("com.example.demo.entity.jpa.base.BaseController");
        strategy.setSuperServiceClass(" com.ljh.jpa.BaseService");
        //写于父类中的公共字段
//        for (int i = 0; i < setTableFileds.getCommonFields().size(); i++) {
//            System.out.println("===>"+setTableFileds.getCommonFields().get(i));
//        }

        //strategy.setSuperEntityColumns("id", "create_by", "create_time", "update_by", "update_time", "delete_time");
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        //设置表名前缀 例： bs_ sys_
        strategy.setTablePrefix("");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new MyFreemarkerTemplateEngine());
        mpg.execute();

        generateBaseBean();
        deleteUnUseDir(projectPath + codeOutPathFinal,pc.getModuleName());
        writeBaseBen(projectPath + codeOutPathFinal,pc.getModuleName());
    }

    /**
     * 生成BaseBean
     */
    public static void generateBaseBean(){
        String rootPath="";
        String rootPack="";
        String[] packageUrl=packagePath.split("\\.");
        for (int i = 0; i < packageUrl.length-1; i++) {
            if(i==packageUrl.length-1){
                rootPath+=packageUrl[i];
                rootPack+=packageUrl[i];
            }else {
                rootPath+=packageUrl[i]+"\\";
                rootPack+=packageUrl[i]+".";
            }
        }
        String baseBeanFile=System.getProperty("user.dir")+"\\src\\main\\java\\"+rootPath+"common\\entity\\BaseBean.java";
        if(!SystemUtil.getOsInfo().isWindows()){
            baseBeanFile=baseBeanFile.replaceAll("\\\\","/");
        }
        String mobanFile="/templates/codegen/BaseBean.ftl";

        if(FileUtil.exist(baseBeanFile)==false){//不存在,则创建
            FileUtil.touch(baseBeanFile);

            Map<String, Object> map = new HashMap<>();
            map.put("srcPath", rootPack);
            try {
                FreemarkerUtil.execute(mobanFile, map, baseBeanFile);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成配置文件示例
     */
    public static void generateConfigDemoFile(){
        String configFile=System.getProperty("user.dir")+"\\src\\main\\resources\\application-demo.yml";
        if(!SystemUtil.getOsInfo().isWindows()){
            configFile=configFile.replaceAll("\\\\","/");
        }
        String mobanFile="/templates/codegen/config.ftl";

        if(FileUtil.exist(configFile)==false){//不存在,则创建
            FileUtil.touch(configFile);

            Map<String, Object> map = new HashMap<>();
            try {
                FreemarkerUtil.execute(mobanFile, map, configFile);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除用不着的代码生成目录
     * @param srcPath
     * @param moduleName
     */
    public static void deleteUnUseDir(String srcPath, String moduleName){
        String xmlPath=srcPath+"/"+moduleName+"/mapper/xml";
        String implPath=srcPath+"/"+moduleName+"/service/impl";
        FileUtil.del(new File(xmlPath));
        FileUtil.del(new File(implPath));
    }

    /**
     * 自己写入bean导包
     * @param srcPath
     * @param moduleName
     */
    public static void writeBaseBen(String srcPath, String moduleName) {
        String entityPath=srcPath+"/"+moduleName+"/entity/";
        File[] files=FileUtil.ls(entityPath);
        Arrays.stream(files).forEach(file->{
            try {
                String realEntityPath=file.getPath();
                File entityFile=new File(realEntityPath);
                FileReader fileReader=new FileReader(entityFile);
                String oldFile=fileReader.readString();
                String baseBeanPath=packagePath.replaceAll("modules","common.entity").toString();
                String newFile =oldFile.replaceAll("#BaseBean#","import "+baseBeanPath+".BaseBean;").toString();
                FileUtil.del(entityFile);

                BufferedWriter bw=new BufferedWriter(new FileWriter(realEntityPath,true));
                bw.write(newFile);
                bw.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}