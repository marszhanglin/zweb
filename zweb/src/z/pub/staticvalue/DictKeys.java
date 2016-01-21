package z.pub.staticvalue;


/**
 * Title: DictKeys.java 
 * @Description:存放常量数据字典
 * @author Mars zhang
 * @created 2014年10月6日 下午2:00:24
 */
public abstract class DictKeys {
 
    /**
     * URL缓存Key
     */
    public static final String CACHE_NAME_PAGE = "SimplePageCachingFilter";
   
    /**
     * session key
     */
    public static final String SESSION_INFO = "sessionInfo";

    /**
     * 系统缓存，主要是权限和数据字典等
     */
    public static final String CACHE_NAME_SYSTEM = "system";

    /**
     * 开发模式
     */
    public static final String CONFIG_DEVMODE = "config.devMode";

    /**
     * 是否重新构建Lucene索引（构建索引实在是慢）
     */
    public static final String CONFIG_LUCENEINDEX = "config.luceneIndex";

    /**
     * 加密
     */
    public static final String CONFIG_SECURITYKEY_KEY = "config.securityKey";

    /**
     * 密码最大错误次数
     */
    public static final String CONFIG_PASSERRORCOUNT_KEY = "config.passErrorCount";
   
    /**
     * IDEA加密秘钥
     */
    public static final String CONFIG_SECURITYKEY = "config.securityKey";

    /**
     * 密码错误最大次数后间隔登陆时间（小时）
     */
    public static final String CONFIG_PASSERRORHOUR_KEY = "config.passErrorHour";

    /**
     * #文件上传大小限制 50 * 1024 * 1024 = 50M
     */
    public static final String config_maxPostSize_key = "config.maxPostSize";

    /**
     * # cookie 值的时间
     */
    public static final String CONFIG_MAXAGE_KEY = "config.maxAge";
    
    /**===============================微信相关配置start===============================**/
    /**
     * 微信公众号地址
     */
    public static final String WEIXIN_URL = "weixin_url";
    /**
     * 微信token
     */
    public static final String WEIXIN_TOKEN = "weixin_token";
    /**
     * 微信appid
     */
    public static final String WEIXIN_APPID = "weixin_appid";
    /**
     * 微信appSecret
     */
    public static final String WEIXIN_APPSECRET = "weixin_appsecret";
    
    /**
     * 微信js端配置所需参数 key
     */
    public static final String WEIXINJS_CONFIG_PARAMS = "weixinjs_config_params";
    
    /**===============================微信相关配置end===============================**/
   
    
    /**===============================数据库相关配置======================= */
    /** 数据库类型 */
    public static  String db_type_key = "type";
    /**postgresql*/
    public static  String db_type_postgresql = "postgresql";
    /**mysql*/
    public static  String db_type_mysql = "mysql";
    /**oracle*/
    public static  String db_type_oracle = "oracle";

    /** 数据源别名 **/
    public static  String db_connection_alias = "alias";
    /**驱动类*/
    public static  String db_connection_driverClass = "driverClass";
    /**jdbcUrl*/
    public static  String db_connection_jdbcUrl = "jdbcUrl";
    /**用户名*/
    public static  String db_connection_userName = "userName";
    /**密码*/
    public static  String db_connection_passWord = "passWord";
    
    /**数据库连接ip*/
    public static  String db_connection_ip = "db_ip";
    /**数据库端口*/
    public static  String db_connection_port = "db_port";
    /**数据库连接实例名*/
    public static  String db_connection_dbName = "db_name";

    /**初始化连接数*/
    public static  String db_initialSize = "db.initialSize";
    /**初始化连接数*/
    public static  String db_minIdle = "db.minIdle";
    /**初始化最大连接数*/
    public static  String db_maxActive = "db.maxActive";

    /** ================================分页参数初始化值==========================*/
    /** 第几页*/
    public static final int DEFAULT_PAGENUMBER = 1;// 第几页
    /**每页显示几多*/
    public static final int DEFAULT_PAGESIZE = 20;// 每页显示几多

   
    
    
    /**=================================用户登录状态码=============================*/
    /**
     * 用户不存在
     */
    public static final int LOGIN_INFO_0 = 0;// 用户不存在
    /**
     * 停用账户
     */
    public static final int LOGIN_INFO_1 = 1;// 停用账户
    /**
     * 密码错误次数超限
     */
    public static final int LOGIN_INFO_2 = 2;// 密码错误次数超限
    /**
     * 密码验证成功
     */
    public static final int LOGIN_INFO_3 = 3;// 密码验证成功
    /**
     * 密码验证失败
     */
    public static final int LOGIN_INFO_4 = 4;// 密码验证失败
    

}
