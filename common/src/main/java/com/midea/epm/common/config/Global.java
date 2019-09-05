package com.midea.epm.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 全局配置对象
 * <br/><strong>不可在其他使用@Configuration注解的类使用</strong
 */
@Configuration
public class Global implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(Global.class);

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        logger.warn("开始加载 environment");
        this.env=environment;
    }

    /*********** 分享签名盐常量 *************/
    public static String SHARE_SECRET;
    /**
     * 检测 env 是否为空，只有env不为空才可使用getConfig
     * @return true: env为null <br/> false: env已实例化
     */
    public static boolean isEnvNull(){
        return null==env;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        return env.getProperty(key);
    }


    /** 分页属性 页码*/
    public static final String PAGE_NO = "pageNo";
    /** 分页属性 页面大小*/
    public static final String PAGE_SIZE = "pageSize";
    /** 分页属性 排序*/
    public static final String ORDER_BY="orderBy";

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";
}
