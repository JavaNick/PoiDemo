package cn.nick;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Nick
 * @date 2016年11月1日
 */
public class PoiTest {
	public static void main(String[] args) throws Exception {
		// 存调查表的目录  例如：E://QMDownload//2015古树名木核查 - 副本
		String path = "~~换成你自己的目录，用英文状态下的双斜杠~~";
		traverseFolder(path);
	}

	/**
	 * @Description: 获取两个特定的坐标并存入数组
	 * @param path
	 * @return String[]        
	 * @author: Nick       
	 * @date: 2016-11-2
	 */
	private static String[] readXls(String path) {
		String[] cellContent = new String[2];
		try {
			Workbook book = Workbook.getWorkbook(new File(path));
			Sheet sheet = book.getSheet(0);
			// 获取指定单元格的内容
			Cell D9 = sheet.getCell(3, 8); // D9
			Cell F9 = sheet.getCell(5, 8); // F9
			cellContent[0] = D9.getContents();
			cellContent[1] = F9.getContents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellContent;
	}

	/**
	 * @Description: 修改坐标
	 * @param filePath
	 * @param x  列数
	 * @param y  横数
	 * @author: Nick       
	 * @date: 2016-11-2
	 */
	public static void writeValue(String filePath, int x, int y, double value) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			HSSFSheet sheet = wb.getSheetAt(0); // 获取第一个sheet
			HSSFRow row = sheet.getRow(x);
			HSSFCell cell = row.getCell((short) y);
			cell.setCellValue(value); // 设置值
			FileOutputStream os;
			os = new FileOutputStream(filePath);
			wb.write(os); // 输出流写入
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 递归遍历每个表格并处理坐标
	 * @param path void        
	 * @author: Nick       
	 * @date: 2016-11-2
	 */
	public static void traverseFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) { // 任然是文件夹
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                    	String filePath = file2.getAbsolutePath(); // 获取文件名
                    	System.out.println();
                    	System.out.println("开始操作"+file2.getAbsolutePath()+"表格!");
                    	if(filePath.endsWith(".xls")){ // 文件名以.xls结束
                    		String[] coods = readXls(filePath);// 获取两个特定的坐标
                    		DecimalFormat df = new DecimalFormat("#.000000"); // 坐标格式：六位小数
                    		// 替换第一个坐标
							if (null != coods[0] && !StringUtil.isEmpty(coods[0])) {
                    			int dSize = coods[0].indexOf("°"); // 度的位置
                    			int fSize = coods[0].indexOf("′"); // 分的位置
                    			int mSize = coods[0].indexOf("″"); // 秒的位置
                    			String degree = coods[0].substring(0, dSize); // 度的值
                    			String fen = coods[0].substring(dSize+1, fSize); // 分的值
                    			String miao = coods[0].substring(fSize+1, mSize); // 秒的值
                    			double d = Double.parseDouble(degree);
                    			double f = Double.parseDouble(fen);
                    			double m = Double.parseDouble(miao);
                    			double coodinate1 = d + f / 60 + m / 3600; // 计算坐标值
                    			coodinate1 = Double.parseDouble(df.format(coodinate1)); // 六位小数处理
                    			writeValue(filePath, 8, 3, coodinate1); // 复写值 D9
                    		} else {
                    			System.out.println("经度坐标为空！");
							}
                    		
                    		// 替换第二个坐标
                    		if( !StringUtil.isEmpty(coods[1])&& null != coods[1]){
                    			int ddSize = coods[1].indexOf("°"); 
                    			int ffSize = coods[1].indexOf("′"); 
                    			int mmSize = coods[1].indexOf("″"); 
                    			String d1 = coods[1].substring(0, ddSize);
                    			String f1 = coods[1].substring(ddSize+1, ffSize);
                    			String m1 = coods[1].substring(ffSize+1, mmSize);
                    			double d2 = Double.parseDouble(d1);
                    			double f2 = Double.parseDouble(f1);
                    			double m2 = Double.parseDouble(m1);
                    			double coodinate2 = d2 + f2 / 60 + m2 / 3600;
                    			coodinate2 = Double.parseDouble(df.format(coodinate2));
                    			writeValue(filePath, 8, 5, coodinate2); // F9
                    		} else {
                    			System.out.println(file2.getName()+"有空坐标！");
							}
                    	}
                        System.out.println("纬度坐标为空!");
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}