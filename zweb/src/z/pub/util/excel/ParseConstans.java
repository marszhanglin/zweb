/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package z.pub.util.excel;

/**
 * 描述 excel验证常量类
 * 
 * @author Fandy Liu
 * @created 2014年11月3日 上午11:58:02
 */
public class ParseConstans {

    /**
     * 描述 非空验证
     */
    public static String RULE_NAME_NULLABLE = "nullable";
    /**
     * 描述 唯一性验证
     */
    public static String RULE_NAME_UNIQUE = "unique";
    /**
     * 描述 输入字符长度验证
     */
    public static String RULE_NAME_LENGTH = "length";
    /**
     * 描述 身份证号码验证
     */
    public static String RULE_NAME_IDCARD = "IDCard";
    /**
     * 描述 时间格式验证
     */
    public static String RULE_NAME_DATE = "date";

    /**
     * 描述 整数验证
     */
    public static String RULE_NAME_INT = "IntValue";
    /**
     * 描述 email验证
     */
    public static String RULE_NAME_EMAIL = "email";
    /**
     * 描述 电话号码验证
     */
    public static String RULE_NAME_TEL = "tel";
    /**
     * 描述 手机号码验证
     */
    public static String RULE_NAME_MOBILE = "mobile";
    /**
     * 描述 联系电话验证
     */
    public static String RULE_NAME_MOBILEANDTEL = "mobileAndTel";
    /**
     * 描述 中文验证
     */
    public static String RULE_NAME_CHINESE = "Chinese";
    /**
     * 描述 ip验证
     */
    public static String RULE_NAME_IP = "IP";
    /**
     * 描述 QQ号码验证
     */
    public static String RULE_NAME_QQ = "QQ";
    /**
     * 描述 邮编验证
     */
    public static String RULE_NAME_POSTCODE = "postCode";

    /**
     * 描述 excel 中的数据为空--错误提示
     */
    public static String ERROR_EXCEL_NULL = "excel中数据为空!<br>";
    /**
     * 描述 excel 上传的模板有误--错误提示
     */
    public static String ERROR_EXCEL_COLUMN_NOT_EQUAL = "提交的Excel模板错误，" + "请从系统下载最新的数据模板!<br>";
    /**
     * 描述 excel 数据类型转换错误
     */
    public static String ERROR_EXCEL_DATA_TYPE = "数据类型错误!";

    /**
     * 描述 excel 数据类型转换错误
     */
    public static String ERROR_EXCEL_DATA_NUM = "超过1000行数据!";

}
