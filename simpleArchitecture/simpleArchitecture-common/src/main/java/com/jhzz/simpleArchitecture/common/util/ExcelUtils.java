package com.jhzz.simpleArchitecture.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wuhoujian Excel 解析工具
 */
public class ExcelUtils {

    /**
     * 内置类，用来配置Excel与Bean属性的映射关系
     */
    public static class CellMapping {

        private String header;

        private String property;

        private String type;

        public CellMapping(){

        }

        public CellMapping(String header, String property){
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    public static <T> List<T> excel2bean(Sheet sheet, Class<T> clazz, String config) throws Exception {
        return excel2bean(sheet, clazz, config, 0);
    }

    public static <T> List<T> excel2bean(Sheet sheet, Class<T> clazz, String config, int headerRowNum) throws Exception {
        List<CellMapping> mappingList = JSONObject.parseArray(config, CellMapping.class);
        return excel2bean(sheet, clazz, mappingList, headerRowNum);
    }

    public static <T> List<T> excel2bean(Sheet sheet, Class<T> clazz, List<CellMapping> mappingList) throws Exception {
        return excel2bean(sheet, clazz, mappingList, 0);
    }

    public static <T> List<T> excel2beanStrong(Sheet sheet, Class<T> clazz, List<CellMapping> mappingList)
                                                                                                          throws Exception {
        return excel2beanStrong(sheet, clazz, mappingList, 0);
    }

    public static <T> List<T> importExcel2bean(Sheet sheet, Class<T> clazz, List<CellMapping> mappingList,
                                               int headerRowNum) throws Exception {
        Map<String, Integer> configMap = new HashMap<String, Integer>();
        Row row = sheet.getRow(headerRowNum);
        for (int c = 0; c < row.getLastCellNum(); c++) {
            String key = getCellString(row.getCell(c));
            if (!configMap.containsKey(key)) {
                configMap.put(key, c);
            } else {
                throw new RuntimeException("表头第" + (configMap.get(key) + 1) + "列和第" + (c + 1) + "列重复");
            }
        }

        List<T> resultList = new ArrayList<T>();

        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            T t = clazz.newInstance();
            for (CellMapping cf : mappingList) {

                Integer index = configMap.get(cf.getHeader());
                if (index == null) {
                    continue;
                }
                if ("string".equalsIgnoreCase(cf.getType())) {
                    PropertyUtils.setSimpleProperty(t, cf.getProperty(), getCellString(row.getCell(index)));
                } else if ("date".equalsIgnoreCase(cf.getType())) {
                    PropertyUtils.setSimpleProperty(t, cf.getProperty(), getCellDate(row.getCell(index)));
                } else if ("double".equalsIgnoreCase(cf.getType())) {
                    PropertyUtils.setSimpleProperty(t, cf.getProperty(), getCellDouble(row.getCell(index)));
                } else if ("float".equalsIgnoreCase(cf.getType())) {
                    PropertyUtils.setSimpleProperty(t, cf.getProperty(), (float) getCellDouble(row.getCell(index)));
                } else if ("int".equalsIgnoreCase(cf.getType()) || "integer".equalsIgnoreCase(cf.getType())) {
                    PropertyUtils.setSimpleProperty(t, cf.getProperty(), (int) getCellDouble(row.getCell(index)));
                } else if ("long".equalsIgnoreCase(cf.getType())) {
                    PropertyUtils.setSimpleProperty(t, cf.getProperty(), (long) getCellDouble(row.getCell(index)));
                } else {
                    throw new Exception("Unrecognize Config Type");
                }
            }
            resultList.add(t);
        }

        return resultList;
    }

    public static <T> List<T> excel2bean(Sheet sheet, Class<T> clazz, List<CellMapping> mappingList, int headerRowNum)
                                                                                                                      throws Exception {
        Map<String, Integer> configMap = new HashMap<String, Integer>();
        Row row = sheet.getRow(headerRowNum);
        for (int c = 0; c < row.getLastCellNum(); c++) {
            String key = getCellString(row.getCell(c));
            if (!configMap.containsKey(key)) {
                configMap.put(key, c);
            } else {
                throw new RuntimeException("表头第" + (configMap.get(key) + 1) + "列和第" + (c + 1) + "列重复");
            }
        }

        List<T> resultList = new ArrayList<T>();

        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            if (row == null) break;// 遇空行，表示结束
            T t = clazz.newInstance();
            Map<String, Object> properties = new HashMap<String, Object>();
            boolean flag = true;// 判断整行属性全为空
            for (CellMapping cm : mappingList) {

                Integer index = configMap.get(cm.getHeader());
                if (index == null) {
                    continue;
                }
                Object cellValue = getCellValue(row.getCell(index));
                if (cellValue != null) {
                    properties.put(cm.getProperty(), cellValue);
                    if (flag) {
                        flag = false;// 有一列值不为空，则为false
                    }
                }
            }
            if (flag) break;// 遇一行中所有值都为空，结束
            BeanUtils.populate(t, properties);
            resultList.add(t);
        }

        return resultList;
    }

    public static <T> List<T> excel2beanStrong(Sheet sheet, Class<T> clazz, List<CellMapping> mappingList,
                                               int headerRowNum) throws Exception {
        Map<String, Integer> configMap = new HashMap<String, Integer>();
        Row row = sheet.getRow(headerRowNum);
        for (int c = 0; c < row.getLastCellNum(); c++) {
            String key = getCellString(row.getCell(c));
            if (!configMap.containsKey(key)) {
                configMap.put(key, c);
            } else {
                throw new RuntimeException("表头第" + (configMap.get(key) + 1) + "列和第" + (c + 1) + "列重复");
            }
        }

        List<T> resultList = new ArrayList<T>();

        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            if (row == null) break;// 遇空行，表示结束
            T t = clazz.newInstance();
            Map<String, Object> properties = new HashMap<String, Object>();
            boolean flag = true;// 判断整行属性全为空
            for (CellMapping cm : mappingList) {

                Integer index = configMap.get(cm.getHeader());
                if (index == null) {
                    continue;
                }
                Object cellValue = getCellValue(row.getCell(index));
                if (StringUtils.isNotBlank(cellValue.toString())) {
                    properties.put(cm.getProperty(), cellValue);
                    if (flag) {
                        flag = false;// 有一列值不为空，则为false
                    }
                }
            }
            if (flag) break;// 遇一行中所有值都为空，结束
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.populate(t, properties);
            resultList.add(t);
        }

        return resultList;
    }

    public static List<Map<String, Object>> excel2map(Sheet sheet, List<CellMapping> mappingList) throws Exception {
        return excel2map(sheet, mappingList, 0);
    }

    public static List<Map<String, Object>> excel2map(Sheet sheet, List<CellMapping> mappingList, int headerRowNum)
                                                                                                                   throws Exception {
        Map<String, Integer> configMap = new HashMap<String, Integer>();
        Row row = sheet.getRow(headerRowNum);
        for (int c = 0; c < row.getLastCellNum(); c++) {
            String key = getCellString(row.getCell(c));
            if (!configMap.containsKey(key)) {
                configMap.put(key, c);
            } else {
                throw new RuntimeException("表头第" + (configMap.get(key) + 1) + "列和第" + (c + 1) + "列重复");
            }
        }

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            if (row == null) break;// 遇空行，表示结束
            Map<String, Object> properties = new HashMap<String, Object>();
            boolean flag = true;// 判断整行属性全为空
            for (CellMapping cm : mappingList) {

                Integer index = configMap.get(cm.getHeader());
                if (index == null) {
                    continue;
                }
                Object cellValue = getCellValue(row.getCell(index));
                if (cellValue != null) {
                    properties.put(cm.getProperty(), cellValue);
                    if (flag) {
                        flag = false;// 有一列值不为空，则为false
                    }
                }
            }
            if (flag) break;// 遇一行中所有值都为空，结束
            resultList.add(properties);
        }

        return resultList;
    }

    public static List<Map<String, Object>> excel2mapnohead(Sheet sheet, List<CellMapping> mappingList,
                                                            HashMap<String, Integer> configMap) throws Exception {

        int headerRowNum = 0;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (int r = headerRowNum; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            Map<String, Object> properties = new HashMap<String, Object>();
            for (CellMapping cm : mappingList) {

                Integer index = configMap.get(cm.getHeader());
                if (index == null) {
                    continue;
                }
                Object cellValue = getCellValue(row.getCell(index));
                if (cellValue != null) {
                    properties.put(cm.getProperty(), cellValue);
                }
            }

            if (properties.isEmpty() == false) {
                resultList.add(properties);
            }
        }

        return resultList;
    }

    public static <T> void bean2excel(List<T> list, Sheet sheet, List<CellMapping> mappingList) throws Exception {
        bean2excel(list, sheet, mappingList, 0);
    }

    /**
     * Excel sheet 由参数传入，建议Excel样式由模板提供 程序只负责导数据，后续可以考虑增加 CellStyle 参数
     */
    public static <T> void bean2excel(List<T> list, Sheet sheet, List<CellMapping> mappingList, int headerRowNum)
                                                                                                                 throws Exception {

        int colPointer = 0;
        // 渲染第一行
        Row row = accessRow(sheet, headerRowNum++);
        CellStyle dateStyle = null;
        for (CellMapping cm : mappingList) {
            XSSFCellStyle cellStyle = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
            if (cm.getHeader().contains("*")) {
                // 红色强调
                cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(240, 180, 180)));
            } else {
                // 黄色标题
                cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(220, 220, 220)));
            }
            cellStyle.setWrapText(true);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            ;
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            Cell cell = accessCell(row, colPointer++);
            cell.setCellValue(cm.getHeader());
            cell.setCellStyle(cellStyle);
        }

        for (T d : list) {
            row = accessRow(sheet, headerRowNum++);
            colPointer = 0;
            for (CellMapping cm : mappingList) {
                Object o = PropertyUtils.getSimpleProperty(d, cm.getProperty());
                Cell cell = accessCell(row, colPointer++);
                if (o == null) {

                } else if (String.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((String) o);
                } else if (Date.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((Date) o);// 日期存储为Number，显示需求依赖CellStyle
                    if (dateStyle == null) {
                        dateStyle = sheet.getWorkbook().createCellStyle();
                        dateStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy/m/d"));
                    }
                    cell.setCellStyle(dateStyle);
                } else if (Number.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Number) o).doubleValue());
                } else if (Boolean.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Boolean) o).booleanValue());
                } else {
                    cell.setCellValue(o.toString());
                }
            }
        }
    }

    /**
     * Excel导出是考虑
     */
    public static <T> void bean2excelWithLock(List<T> list, Sheet sheet, List<CellMapping> mappingList, int headerRowNum)
                                                                                                                         throws Exception {

        int colPointer = 0;
        List<Integer> lockColumns = new ArrayList<Integer>();
        // 渲染第一行
        Row row = accessRow(sheet, headerRowNum++);
        CellStyle dateStyle = null;
        for (CellMapping cm : mappingList) {
            XSSFCellStyle cellStyle = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
            if (cm.getHeader().contains("*")) {
                // 红色强调,不锁定可必填
                cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 255, 0)));
            } else {
                // 黄色标题,锁定不可更改
                cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(220, 220, 220)));
                lockColumns.add(colPointer);
            }
            cellStyle.setWrapText(true);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            ;
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            Cell cell = accessCell(row, colPointer++);
            cell.setCellValue(cm.getHeader());
            cell.setCellStyle(cellStyle);
        }

        for (T d : list) {
            row = accessRow(sheet, headerRowNum++);
            colPointer = 0;
            for (CellMapping cm : mappingList) {
                XSSFCellStyle cellStyle = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
                Object o = PropertyUtils.getSimpleProperty(d, cm.getProperty());
                if (lockColumns.contains(colPointer)) {
                    cellStyle.setLocked(true);
                } else {
                    cellStyle.setLocked(false);
                }
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                // cellStyle.setWrapText(true);
                Cell cell = accessCell(row, colPointer++);
                cell.setCellStyle(cellStyle);

                if (o == null) {

                } else if (String.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((String) o);
                } else if (Date.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((Date) o);// 日期存储为Number，显示需求依赖CellStyle
                    if (dateStyle == null) {
                        dateStyle = sheet.getWorkbook().createCellStyle();
                        dateStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy/m/d"));
                    }
                    cellStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy/m/d"));
                    cell.setCellStyle(cellStyle);
                } else if (Number.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Number) o).doubleValue());
                } else if (Boolean.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Boolean) o).booleanValue());
                } else {
                    cell.setCellValue(o.toString());
                }
            }
        }
    }

    public static <T> Workbook exportToExcel(List<T> list, List<CellMapping> mappingList) throws Exception {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("export");
        int colPointer = 0;
        int rowPointer = 0;
        Row row = sheet.createRow(rowPointer++);

        CellStyle dateStyle = null;

        for (CellMapping cm : mappingList) {
            row.createCell(colPointer++).setCellValue(cm.getHeader());
        }
        for (T d : list) {
            row = sheet.createRow(rowPointer++);
            colPointer = 0;
            for (CellMapping cm : mappingList) {
                Object o = PropertyUtils.getSimpleProperty(d, cm.getProperty());
                Cell cell = accessCell(row, colPointer++);
                if (o == null) {

                } else if (String.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((String) o);
                } else if (Date.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((Date) o);// 日期存储为Number，显示需求依赖CellStyle
                    if (dateStyle == null) {
                        dateStyle = wb.createCellStyle();
                        dateStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("yyyy/m/d"));
                    }
                    cell.setCellStyle(dateStyle);
                } else if (Number.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Number) o).doubleValue());
                } else if (Boolean.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Boolean) o).booleanValue());
                } else {
                    cell.setCellValue(o.toString());
                }
            }
        }
        return wb;
    }

    public static void map2excel(List<Map<String, Object>> list, Sheet sheet, List<CellMapping> mappingList)
                                                                                                            throws Exception {
        int colPointer = 0;
        int headerRowNum = 0;
        Row row = accessRow(sheet, headerRowNum++);
        CellStyle dateStyle = null;
        for (CellMapping cm : mappingList) {
            accessCell(row, colPointer++).setCellValue(cm.getHeader());
        }
        for (Map<String, Object> d : list) {
            row = accessRow(sheet, headerRowNum++);
            colPointer = 0;
            for (CellMapping cm : mappingList) {
                Object o = d.get(cm.getProperty());
                Cell cell = accessCell(row, colPointer++);
                if (o == null) {

                } else if (String.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((String) o);
                } else if (Date.class.isAssignableFrom(o.getClass())) {

                    cell.setCellValue((Date) o);// 日期存储为Number，显示需求依赖CellStyle
                    if (dateStyle == null) {
                        dateStyle = sheet.getWorkbook().createCellStyle();
                        if (o.toString().trim().length() == 10) {
                            dateStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy/m/d"));
                        } else {
                            dateStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                        }
                    }
                    cell.setCellStyle(dateStyle);
                } else if (Number.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Number) o).doubleValue());
                } else if (Boolean.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Boolean) o).booleanValue());
                } else {
                    cell.setCellValue(o.toString());
                }
            }
        }
    }

    public static void map2excelnohead(List<Map<String, Object>> list, Sheet sheet, List<CellMapping> mappingList)
                                                                                                                  throws Exception {
        int colPointer = 0;
        int headerRowNum = 0;

        CellStyle dateStyle = null;
        /*
         * Row row = accessRow(sheet,headerRowNum++); for(CellMapping cm:mappingList){
         * accessCell(row,colPointer++).setCellValue(cm.getHeader()); }
         */

        for (Map<String, Object> d : list) {
            Row row = accessRow(sheet, headerRowNum++);
            colPointer = 0;

            XSSFCellStyle cellStyle = null;

            if (d.get("style") != null) {
                // cellStyle = sheet.getWorkbook().createCellStyle();

                cellStyle = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();

                if (d.get("style").equals("yellow")) {
                    cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 204, 0)));
                } else if (d.get("style").equals("blue")) {
                    cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(155, 194, 230)));
                }
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }

            for (CellMapping cm : mappingList) {
                Object o = d.get(cm.getProperty());
                Cell cell = accessCell(row, colPointer++);
                if (o == null) {

                } else if (String.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((String) o);
                    if (cellStyle != null) {
                        cell.setCellStyle(cellStyle);
                    }
                } else if (Date.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue((Date) o);// 日期存储为Number，显示需求依赖CellStyle
                    if (dateStyle == null) {
                        dateStyle = sheet.getWorkbook().createCellStyle();
                        dateStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy/m/d"));
                    }
                    cell.setCellStyle(dateStyle);
                } else if (Number.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Number) o).doubleValue());
                } else if (Boolean.class.isAssignableFrom(o.getClass())) {
                    cell.setCellValue(((Boolean) o).booleanValue());
                } else {
                    cell.setCellValue(o.toString());
                    if (cellStyle != null) {
                        cell.setCellStyle(cellStyle);
                    }
                }
            }
        }
    }

    public static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copy style from old cell and apply to new cell
            CellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            ;
            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (oldCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(
                                                                            newRow.getRowNum(),
                                                                            (newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                                                                            cellRangeAddress.getFirstColumn(),
                                                                            cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    private static Row accessRow(Sheet sheet, int rownum) {
        Row row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }
        return row;
    }

    private static Cell accessCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null) {
            cell = row.createCell(column);
        }
        return cell;
    }

    public static String getCellString(Cell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
            || (cell.getCellType() == Cell.CELL_TYPE_FORMULA && cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC)) {
            if (cell.getNumericCellValue() == (long) cell.getNumericCellValue()) {
                return String.valueOf((long) cell.getNumericCellValue());
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        } else {
            return cell.toString();
        }
    }

    public static Date getCellDate(Cell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        }

        if (DateUtil.isCellDateFormatted(cell)) {
            return DateUtil.getJavaDate(cell.getNumericCellValue());
        } else {
            String result = getCellString(cell);
            Date resultDate = null;
            try {
                resultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result);
            } catch (ParseException e) {
                try {
                    resultDate = new SimpleDateFormat("yyyy-MM-dd").parse(result);
                } catch (ParseException e1) {

                }
            }
            return resultDate;
        }

    }

    public static double getCellDouble(Cell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return 0D;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            if (cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC) {
                return cell.getNumericCellValue();
            }
        }
        double result = 0;
        try {
            result = Double.parseDouble(getCellString(cell));
        } catch (Exception e) {

        }
        return result;
    }

    public static Object getCellValue(Cell cell) {
        Object result = null;

        if (cell != null) {
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_FORMULA) {
                cellType = cell.getCachedFormulaResultType();
            }
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        result = cell.getDateCellValue();
                    } else {
                        if (cell.getNumericCellValue() == (long) cell.getNumericCellValue()) {
                            return (long) cell.getNumericCellValue();
                        } else {
                            return cell.getNumericCellValue();
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                default:
            }
        }
        return result;
    }

}
