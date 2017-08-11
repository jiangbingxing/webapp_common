package com.boco.msgl.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	// ------------------------写Excel-----------------------------------
	/**
	 * 创建workBook对象 xlsx(2007以上版本)
	 * 
	 * @return
	 */
	public static Workbook createWorkbook() {
		return createWorkbook(true);
	}

	/**
	 * 创建WorkBook对象
	 * 
	 * @param flag
	 *            true:xlsx(1997-2007) false:xls(2007以上)
	 * @return
	 */
	public static Workbook createWorkbook(boolean flag) {
		Workbook wb;
		if (flag) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		return wb;
	}

	/**
	 * 添加图片
	 * 
	 * @param wb
	 *            workBook对象
	 * @param sheet
	 *            sheet对象
	 * @param picFileName
	 *            图片文件名称（全路径�?
	 * @param picType
	 *            图片类型
	 * @param row
	 *            图片�?在的�?
	 * @param col
	 *            图片�?在的�?
	 */
	public static void addPicture(Workbook wb, Sheet sheet, String picFileName, int picType, int row, int col) {
		InputStream is = null;
		try {
			// 读取图片
			is = new FileInputStream(picFileName);
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, picType);
			is.close();
			// 写图�?
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			// 设置图片的位�?
			anchor.setCol1(col);
			anchor.setRow1(row);
			Picture pict = drawing.createPicture(anchor, pictureIdx);

			pict.resize();
		} catch (Exception e) {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 创建Cell 默认为水平和垂直方式都是居中
	 * 
	 * @param style
	 *            CellStyle对象
	 * @param row
	 *            Row对象
	 * @param column
	 *            单元格所在的�?
	 * @return
	 */
	public static Cell createCell(CellStyle style, Row row, int column) {
		return createCell(style, row, column, XSSFCellStyle.ALIGN_CENTER, XSSFCellStyle.ALIGN_CENTER);
	}

	/**
	 * 创建Cell并设置水平和垂直方式
	 * 
	 * @param style
	 *            CellStyle对象
	 * @param row
	 *            Row对象
	 * @param i
	 *            单元格所在的�?
	 * @param halign
	 *            水平对齐方式：XSSFCellStyle.VERTICAL_CENTER.
	 * @param valign
	 *            垂直对齐方式：XSSFCellStyle.ALIGN_LEFT
	 */
	public static Cell createCell(CellStyle style, Row row, int i, short halign, short valign) {
		Cell cell = row.createCell(i);
		setAlign(style, halign, valign);
		cell.setCellStyle(style);
		return cell;
	}

	/**
	 * 合并单元�?
	 * 
	 * @param sheet
	 * @param firstRow
	 *            �?始行
	 * @param lastRow
	 *            �?后行
	 * @param firstCol
	 *            �?始列
	 * @param lastCol
	 *            �?后列
	 */
	public static void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	// ---------------------------------设置样式-----------------------

	/**
	 * 设置单元格对齐方�?
	 * 
	 * @param style
	 * @param halign
	 * @param valign
	 * @return
	 */
	public static CellStyle setAlign(CellStyle style, short halign, short valign) {
		style.setAlignment(halign);
		style.setVerticalAlignment(valign);
		return style;
	}

	/**
	 * 自定义颜色（xssf)
	 * 
	 * @param style
	 *            xssfStyle
	 * @param red
	 *            RGB red (0-255)
	 * @param green
	 *            RGB green (0-255)
	 * @param blue
	 *            RGB blue (0-255)
	 */
	public static CellStyle setBackColorByCustom(XSSFCellStyle style, int red, int green, int blue) {
		// 设置前端颜色
		style.setFillForegroundColor(new XSSFColor(new java.awt.Color(red, green, blue)));
		// 设置填充模式
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		return style;
	}

	public boolean createExcel(Workbook wb, OutputStream os) {
		boolean flag = true;
		try {
			wb.write(os);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 将Workbook写入文件
	 * 
	 * @param wb
	 *            workbook对象
	 * @param fileName
	 *            文件的全路径
	 * @return
	 */
	public static boolean createExcel(Workbook wb, String fileName) {
		boolean flag = true;
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			flag = false;
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return flag;
	}
	
    public static String getCellValue(Cell cell) {  
        String cellValue = "";  
        DecimalFormat df = new DecimalFormat("#");  
        if(cell == null)
        	return "";
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_STRING:  
            cellValue = cell.getRichStringCellValue().getString().trim();  
            break;  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            cellValue = df.format(cell.getNumericCellValue()).toString();  
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA:  
            cellValue = cell.getCellFormula();  
            break;  
        default:  
            cellValue = "";  
        }  
        return cellValue;  
    }  


}