///*
// * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
// * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// * 
// */
//package z.pub.util.excel;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
///**
// * 描述 解析xml工具类
// * 
// * @author Fandy Liu
// * @created 2014年11月3日 下午12:03:01
// */
//@SuppressWarnings("rawtypes")
//public class ParseXMLUtil {
//
//    /**
//     * 描述 entity map对象，key:name ,value:entity的属性map集
//     */
//    private Map entityMap;
//
//    /**
//     * 描述 column map 对象，key:entityName_colName , value:column的属性map集
//     */
//    private Map columnMap;
//
//    /**
//     * 描述 rule map 对象，key:entityName_colName_ruleName, value: rule
//     * 的map集：找到一行rule
//     * 
//     **/
//    private Map ruleMap;
//
//    /**
//     * 描述 rules map 对象, key:entityName_colName, value: rules
//     * 的map集:找到该column下所有的rule
//     */
//    private Map columnRulesMap;
//
//    /**
//     * 描述 entity--column map: key:entityName, value: column list:根据实体类名得到所有的列
//     */
//    private Map columnListMap;
//
//    /**
//     * 描述 column list
//     */
//    private List columnList;
//
//    /**
//     * 描述 构造函数，解析xml文件
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:03:50
//     * @param xmlFilePath
//     */
//    public ParseXMLUtil(File xmlFilePath) {
//        FileInputStream in = null;
//        try {
//            if (xmlFilePath == null) {
//                throw new FileNotFoundException();
//            }
//            SAXReader reader = new SAXReader();
//            in = new FileInputStream(xmlFilePath);
//            Document doc = reader.read(in);
//            Element root = doc.getRootElement();
//            Iterator itEntity = root.elements("entity").iterator();
//            while (itEntity.hasNext()) {
//                Element entity = (Element) itEntity.next();
//                parseEntity(entity);
//            }
//
//            /** 测试entityMap 是否正确 **/
//            /*
//             * Map enMap = (Map) this.getEntityMap().get("用户表"); Set<?> set =
//             * enMap.keySet(); Iterator it = set.iterator();
//             * while(it.hasNext()){ String uu = (String) it.next();
//             * System.out.println("entity properties:"+uu+" = "+enMap.get(uu));
//             * }
//             */
//
//            /**//** 测试column list是否正确 **/
//            /*
//             * List colList = (List) this.getColumnListMap().get("用户表");
//             * System.out.println("column size:"+colList.size());
//             *//** 测试columnMap是否正确 **/
//            /*
//             * Map colMap = (Map) this.getColumnMap().get("用户表_员工号"); Set<?>
//             * coListSet = colMap.keySet(); Iterator coListIt =
//             * coListSet.iterator(); while(coListIt.hasNext()){ String coListKey
//             * = (String) coListIt.next();
//             * System.out.println("column  properties: "
//             * +coListKey+" = "+colMap.get(coListKey)); }
//             *//** 测试ruleMap是否正确 **/
//            /*
//             * if(this.getColumnRulesMap() != null){ List rulesValidList =
//             * (List) this.getColumnRulesMap().get("用户表_员工号"); for(int
//             * i=0;i<rulesValidList.size(); i++){ Map colRuleMap = (Map)
//             * rulesValidList.get(i); String ruleName = (String)
//             * colRuleMap.get("name"); Map ruleMa = (Map)
//             * this.getRuleMap().get("用户表_员工号_"+ruleName); //eg:
//             * 用户表_用户名_nullable String mess = (String) ruleMa.get("message");
//             * System.out.println("Validate Rules"+i+" : "+mess); } }
//             */
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 描述 开始解析entity
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:04:08
//     * @param entity
//     */
//    @SuppressWarnings("unchecked")
//    public void parseEntity(Element entity) {
//        if (entity != null) {
//
//            /** 对数据进行初始化设置 **/
//            columnListMap = new HashMap();
//            columnMap = new HashMap();
//            entityMap = new HashMap();
//            ruleMap = new HashMap();
//            columnRulesMap = new HashMap();
//            columnList = new ArrayList();
//
//            setEntityMap(entity);
//            String entityName = entity.attributeValue("name");
//            Iterator itColumn = entity.elements("column").iterator();
//            while (itColumn.hasNext()) {
//                Element column = (Element) itColumn.next();
//                setColumnMap(entityName, column);
//            }
//            columnListMap.put(entityName, columnList);
//        }
//    }
//
//    /**
//     * 描述 将entity放入entityMap中
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:04:24
//     * @param entity
//     */
//    @SuppressWarnings("unchecked")
//    public void setEntityMap(Element entity) {
//        Map ent = new HashMap();
//        String name = entity.attributeValue("name");
//        String code = entity.attributeValue("code");
//        ent.put("name", name);
//        ent.put("code", code);
//        entityMap.put(name, ent);
//    }
//
//    /**
//     * 描述 将column放入columnMap中
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:04:41
//     * @param entityName
//     * @param column
//     */
//    @SuppressWarnings("unchecked")
//    public void setColumnMap(String entityName, Element column) {
//        if (column != null) {
//            Map col = new HashMap();
//            String name = column.attributeValue("name");
//            String code = column.attributeValue("code");
//            String type = column.attributeValue("type");
//            col.put("name", name);
//            col.put("code", code);
//            col.put("type", type);
//            if ("Dict".equals(type)) {
//                String dictType = column.attributeValue("dictType");
//                col.put("dictType", dictType);
//            }
//
//            String columnMapKey = entityName + "_" + name; // eg: 用户表_用户名
//            columnMap.put(columnMapKey, col);
//            columnList.add(col);
//            Iterator ruleIt = column.elements("rules").iterator(); // 获得rules
//            while (ruleIt.hasNext()) {
//                Element rules = (Element) ruleIt.next();
//                Iterator rule = rules.elements("rule").iterator(); // 获得 rule
//                while (rule.hasNext()) {
//                    Element ruleValid = (Element) rule.next(); // 获得每一行rule
//                    setRuleMap(entityName, name, ruleValid);
//                }
//            }
//        }
//    }
//
//    /**
//     * 描述 将 rule 验证规则放入ruleMap中
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:04:58
//     * @param entityName
//     * @param columnName
//     * @param ruleValid
//     */
//    @SuppressWarnings("unchecked")
//    public void setRuleMap(String entityName, String columnName, Element ruleValid) {
//        if (ruleValid != null) {
//            String ruleName = ruleValid.attributeValue("name");
//            String ruleMsg = ruleValid.attributeValue("message");
//
//            Map ruleValidMap = new HashMap();
//            ruleValidMap.put("name", ruleName);
//            ruleValidMap.put("message", ruleMsg);
//            if ("date".equals(ruleName)) {
//                String formater = ruleValid.attributeValue("formater");
//                ruleValidMap.put("formater", formater);
//            }
//            if ("length".equals(ruleName)) {
//                String length = ruleValid.attributeValue("length");
//                ruleValidMap.put("length", length);
//            }
//            if ("unique".equals(ruleName)) {
//                String tableName = ruleValid.attributeValue("tableName");
//                ruleValidMap.put("tableName", tableName);
//                String colName = ruleValid.attributeValue("colName");
//                ruleValidMap.put("colName", colName);
//            }
//
//            String ruleStrKey = entityName + "_" + columnName + "_" + ruleName;
//            String colStrKey = entityName + "_" + columnName;
//            if (this.getColumnRulesMap().containsKey(colStrKey)) {
//                List valids = (List) this.getColumnRulesMap().get(colStrKey);
//                valids.add(ruleValidMap);
//            } else {
//                List valids = new ArrayList();
//                valids.add(ruleValidMap);
//                this.columnRulesMap.put(colStrKey, valids); // 将每个column下的所有rules存入该map中
//            }
//            ruleMap.put(ruleStrKey, ruleValidMap); // 将每个column下的一条rule存入该map中
//        }
//    }
//
//    /**
//     * 描述 返回所有列的字符，以","拼接，方便生成sql使用
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:05:15
//     * @return
//     */
//    public String getColumnCodeStr() {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < this.columnList.size(); i++) {
//            sb.append(",");
//            Map columnMap = (Map) this.columnList.get(i);
//            if ("t2.UNITNAME".equals(columnMap.get("code").toString())) {
//                String code = "wm_concat(" + columnMap.get("code").toString() + ") as UNITNAME ";
//                sb.append(code);
//            } else if ("t3.BDNAME".equals(columnMap.get("code").toString())) {
//                String code = columnMap.get("code").toString() + " as BDNAME ";
//                sb.append(code);
//            } else if ("t4.UNITNAME".equals(columnMap.get("code").toString())) {
//                String code = columnMap.get("code").toString() + " as UNITNAME ";
//                sb.append(code);
//            } else {
//                String code = columnMap.get("code").toString();
//                sb.append(code);
//            }
//        }
//        if (sb.length() > 0) {
//            sb.deleteCharAt(0);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 描述 主方法 测试
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 下午12:05:35
//     * @param args
//     */
//    public static void main(String[] args) {
//        File file = new File("src/常住人口信息登记表.xml");
//        ParseXMLUtil util = new ParseXMLUtil(file);
//        util.getColumnListMap();
//        util.getColumnMap();
//        util.getEntityMap();
//    }
//
//    /** 所有的get set 方法 **/
//    public Map getEntityMap() {
//        return entityMap;
//    }
//
//    public void setEntityMap(Map entityMap) {
//        this.entityMap = entityMap;
//    }
//
//    public Map getColumnMap() {
//        return columnMap;
//    }
//
//    public void setColumnMap(Map columnMap) {
//        this.columnMap = columnMap;
//    }
//
//    public Map getRuleMap() {
//        return ruleMap;
//    }
//
//    public void setRuleMap(Map ruleMap) {
//        this.ruleMap = ruleMap;
//    }
//
//    public Map getColumnRulesMap() {
//        return columnRulesMap;
//    }
//
//    public void setColumnRulesMap(Map columnRulesMap) {
//        this.columnRulesMap = columnRulesMap;
//    }
//
//    public Map getColumnListMap() {
//        return columnListMap;
//    }
//
//    public void setColumnListMap(Map columnListMap) {
//        this.columnListMap = columnListMap;
//    }
//
//    /**
//     * @author Fandy Liu
//     * @created 2014年7月3日 下午11:56:48
//     * @return type
//     */
//    public List getColumnList() {
//        return columnList;
//    }
//
//}
