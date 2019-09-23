
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteData {
	public static String docPath = "src/main/resources/";
	
	
	
	
    //String a = Doc Name
    //String b = Sheet Name
    //int c = row number
    //int d = column number
	public static String readData( String a, String b, int c, int d) throws IOException {
		File src;
		FileInputStream fis;
		XSSFWorkbook wb;
		XSSFSheet sheet;

	    src=new File(docPath  +  a + ".xlsx");      
	    fis=new FileInputStream(src);
	    scrape_shows_Facebook.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    wb=new XSSFWorkbook(fis);
	    sheet=wb.getSheet(b);
	    String out;
	    try {out =sheet.getRow(c).getCell(d).getStringCellValue();} catch (NullPointerException e) {out = "";}
	    wb.close();
	    fis.close();
	    return out;
	}
	
	
	
	//String a = Doc Name
    //String b = Sheet Name
    //int c = row number
    //int d =  column number
	//String value = value to be written
	public static String writeData( String a, String b, int c, int d, String value, String cellColor ) throws IOException {
		File src;
		FileInputStream fis;
		FileOutputStream fos;
		XSSFWorkbook wb;
		XSSFSheet sheet;
		
        src=new File(docPath  + a + ".xlsx");         
        fis=new FileInputStream(src);
        scrape_shows_Facebook.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wb=new XSSFWorkbook(fis);
        fos=new FileOutputStream(src);
        scrape_shows_Facebook.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        sheet=wb.getSheet(b);
        
        CellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        if (cellColor.compareTo("GREEN") ==0) {font.setColor(HSSFColor.GREEN.index);}
        if (cellColor.compareTo("RED") ==0) {font.setColor(HSSFColor.RED.index);}
        style.setFont(font);
        
        sheet.getRow(c).createCell(d).setCellValue(value);
        sheet.getRow(c).getCell(d).setCellStyle(style);
        System.out.println(d + "|" +value);
        wb.write(fos);
        wb.close();
        fis.close();
        fos.close();
        return sheet.getRow(c).getCell(d).getStringCellValue();
	}
	
	
	
	//String a = Doc Name
    //String b = Sheet Name
	public static Integer rowlast(String a, String b) throws IOException {
		File src;
		FileInputStream fis;
		XSSFWorkbook wb;
		XSSFSheet sheet;
		
	    src=new File(docPath +  a + ".xlsx");      
	    fis=new FileInputStream(src);
	    wb=new XSSFWorkbook(fis);
	    sheet=wb.getSheet(b);
	    int rowlength = sheet.getLastRowNum();
	    wb.close();
	    fis.close();
	    return rowlength+1;
	}
	
	
	
	//String a = Doc Name
    //String b = Sheet Name
	public static Integer columnlast(String a, String b) throws IOException {
		File src;
		FileInputStream fis;
		XSSFWorkbook wb;
		XSSFSheet sheet;
		
	    src=new File(docPath  + a + ".xlsx");      
	    fis=new FileInputStream(src);
	    wb=new XSSFWorkbook(fis);
	    sheet=wb.getSheet(b);
	    int columnlast = sheet.getRow(0).getLastCellNum();
	    wb.close();
	    fis.close();
	    return columnlast;
	}
	
}	
	

