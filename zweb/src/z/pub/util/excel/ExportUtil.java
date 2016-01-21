///*
// * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
// * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// * 
// */
//package z.pub.util.excel;
//
//import java.io.BufferedOutputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.CellRangeAddress;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//
//import com.jfinal.plugin.activerecord.Model;
//
///**
// * 描述 导出Excel的工具类
// * 
// * @author Fandy Liu
// * @created 2014年7月3日 下午5:43:06
// */
//@SuppressWarnings("deprecation")
//public class ExportUtil {
//
//    /**
//     * 描述 excel导出工具
//     * 
//     * @author Fandy Liu
//     * @created 2014年7月3日 下午5:41:04
//     * @param response
//     * @param request
//     * @param filename
//     * @param columnList
//     * @param records
//     */
//    @SuppressWarnings("unchecked")
//    public static <M extends Model<M>> void exportByRecord(HttpServletResponse response, HttpServletRequest request,
//            String filename, List<Map<?, ?>> columnList, List<M> records) {
//        exportByRecord(response, request, filename, new SheetData<M>(columnList, records));
//    }
//
//    /**
//     * 描述 导出记录
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 上午11:56:18
//     * @param response
//     * @param request
//     * @param filename
//     * @param sheetDatas
//     */
//    public static <M extends Model<M>> void exportByRecord(HttpServletResponse response, HttpServletRequest request,
//            String filename, SheetData<M>... sheetDatas) {
//
//        HSSFWorkbook wb = new HSSFWorkbook();
//
//        // 标题行的style
//        CellStyle titleCellStyle = wb.createCellStyle();
//        titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
//        titleCellStyle.setWrapText(true); // 自动换行
//        Font font = wb.createFont();
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 加粗
//        font.setFontName("微软雅黑");
//        titleCellStyle.setFont(font);
//
//        // 内容行的style
//        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
//        cellStyle.setWrapText(true);
//        Font font2 = wb.createFont();
//        font2.setFontName("微软雅黑");
//        cellStyle.setFont(font2);
//
//        // 多张sheet
//        for (SheetData<M> sheetData : sheetDatas) {
//            List<Map<?, ?>> titles = sheetData.getColumnList();
//            List<M> records = sheetData.records;
//            HSSFSheet sheet = wb.createSheet(filename);
//            HSSFRow row = sheet.createRow(0);
//            row.setHeight((short) 500);
//            // 设置第一行
//            HSSFCell cell = row.createCell(0);
//            cell.setCellValue(filename);
//            // 指定合并区域
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.size()));
//            cell.setCellStyle(titleCellStyle);
//
//            // 开始输出
//            int rowIndex = 1, cellIndex = 0;
//
//            // 创建标题行
//            row = sheet.createRow(rowIndex);
//            row.setHeight((short) 450);
//            rowIndex++;
//            for (Map<?, ?> map : titles) {
//                cell = row.createCell(cellIndex);
//                cell.setCellStyle(titleCellStyle); // 设置样式
//                cellIndex++;
//                cell.setCellValue(map.get("name").toString());
//            }
//            // 处理每一行
//            for (M record : records) {
//                row = sheet.createRow(rowIndex);
//                row.setHeight((short) 450);
//                rowIndex++;
//                cellIndex = 0;
//                for (Map<?, ?> map : titles) {
//                    cell = row.createCell(cellIndex);
//                    cell.setCellStyle(cellStyle); // 设置样式
//                    cellIndex++;
//                    String columnCode = map.get("code").toString();
//                    int index = columnCode.indexOf(".");
//                    if (index > -1) {
//                        columnCode = columnCode.substring(index + 1, columnCode.length());
//                    }
//                    Object value = record.get(columnCode);
//                    if (value != null) {
//                        /*// 字典数据处理
//                        if ("Dict".equals(map.get("type").toString())) {
//                            String dictType = map.get("dictType").toString();
//                            value = DictController.returnDictMap2().get(dictType + "_" + value.toString());
//                        }
//                        // 网格数据处理
//                        if ("Area".equals(map.get("type").toString())) {
//                            value = DictController.getAreaMap().get(value.toString());
//                        }*/
//                        cell.setCellValue(value.toString());
//                    }
//                }
//            }
//        }
//        // 序号
//        writeStream(filename, wb, response, request);
//    }
//
//    /**
//     * 描述 写出到输出流
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 上午11:56:54
//     * @param filename
//     * @param wb
//     * @param response
//     * @param request
//     */
//    private static void writeStream(String filename, HSSFWorkbook wb, HttpServletResponse response,
//            HttpServletRequest request) {
//
//        try {
//            String agent = request.getHeader("USER-AGENT");
//            filename += ".xls";
//            filename.replaceAll("/", "-");
//            // filename = new String(filename.getBytes("gbk"),"ISO8859_1");
//            if (agent.toLowerCase().indexOf("firefox") > 0) {
//                filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
//            } else {
//                filename = URLEncoder.encode(filename, "UTF-8");
//            }
//            response.reset();
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//            response.setContentType("application/octet-stream;charset=UTF-8");
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            wb.write(outputStream);
//            outputStream.flush();
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 描述 创建一个sheet需要的数据
//     * 
//     * @author Fandy Liu
//     * @created 2014年11月3日 上午11:57:15
//     */
//    public static class SheetData<M extends Model<M>> {
//        /**
//         * 描述 标题集
//         */
//        private List<Map<?, ?>> columnList;
//        /**
//         * 描述 records
//         */
//        private List<M> records;
//
//        /**
//         * 
//         * 描述 构造函数
//         * 
//         * @author Fandy Liu
//         * @created 2014年7月4日 上午12:17:34
//         * @param columnList
//         * @param records
//         */
//        public SheetData(List<Map<?, ?>> columnList, List<M> records) {
//            this.columnList = columnList;
//            this.records = records;
//        }
//
//        public List<Map<?, ?>> getColumnList() {
//            return columnList;
//        }
//
//        public void setColumnList(List<Map<?, ?>> columnList) {
//            this.columnList = columnList;
//        }
//
//        public List<M> getRecords() {
//            return records;
//        }
//
//        public void setRecords(List<M> records) {
//            this.records = records;
//        }
//
//    }
//}
