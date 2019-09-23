

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class scrape_shows_Facebook {
	public static WebDriver driver;
	public static Wait <WebDriver> wait;
	public static int row;
	public static int col;
	public static String newLine = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/Chromedriver.exe");
	    driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		for (int x = 0; x< ReadWriteData.rowlast("Showlist", "Sheet1")-1; x++) {
		driver.get(ReadWriteData.readData("Showlist", "Sheet1", x+1, 10));
		System.out.println(newLine+ newLine+ newLine + newLine+ newLine);
		System.out.println("-----------------Event " + x +"------------------" + newLine);
		
		
		try{
			//bands
			String organizer_bands = driver.findElement(By.id("seo_h1_tag")).getAttribute("textContent");
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 0, organizer_bands,"WHITE");

			
			
//			if (driver.findElements(By.xpath("//*[@id='event_summary']/div/ul/li[2]/div/table/tbody/tr/td[2]/div/div/div[2]/div/div[1]/a[1]/div")).size()>0) {
//			//then it's a multi-day event
//			WebElement multiEventGrid = driver.findElement(By.xpath("//*[@id=\"event_summary\"]/div/ul/li[2]/div/table/tbody/tr/td[2]/div/div/div[2]/div/div[1]"));
//			String startDateEle =multiEventGrid.findElement(By.xpath( "//a/div/div/span").get("");
//			String startDate = span.split("to") [0].split("at") [0].trim();
//			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 3, startDate,"BLACK");
//			String startTime = span.split("to")[0].split("at") [1].replace("EST", "").replaceAll("EDT", "").trim();
//			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 4, timeFormat(startTime),"BLACK");
//			String endDate = span.split("to") [1].split("at") [0].trim();
//			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 5, endDate,"BLACK");
//			String endTime = span.split("to")[1].split("at") [1].replace("EST", "").replaceAll("EDT", "").trim();
//			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 6, timeFormat(endTime),"BLACK");			
//		}
		
			//Single Day Event
			if (driver.findElements(By.xpath("//*[@id='event_time_info']/div/table/tbody/tr/td[2]/div/div/div[2]/div/div[1]")).size()>0) {
			String singleEventContent = driver.findElement(By.xpath("//*[@id='event_time_info']/div/table/tbody/tr/td[2]/div/div/div[2]/div/div[1]")).getAttribute("textContent").replace("EST", "").replace("EDT","");
			String startTime = singleEventContent.split ("at") [1].split ("�") [0].trim();
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 4, timeFormat(startTime),"BLACK");			
			try {String endTime = singleEventContent.split ("at") [1].split ("�") [1].trim();
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 6, timeFormat(endTime),"BLACK");} catch (Exception E) {}				
			String date = singleEventContent.split (",") [1].trim() + ", " +singleEventContent.split (",") [2].split("at") [0].trim(); 
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 3, date,"BLACK");
		    }


			//place
			try {String place = driver.findElement(By.cssSelector("a[class='_5xhk']")).getAttribute("textContent");
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 1, place,"WHITE");} catch (Exception E) {}
			
			//facebook details
			try {
			try {driver.findElement(By.linkText("See More")).click();} catch (Exception E) {}
			String details = driver.findElement(By.cssSelector("div[data-testid='event-permalink-details']")).getAttribute("outerText");
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 14, details,"WHITE");} catch (Exception E) {}

			
			//attendance
			try {
			String attendance = driver.findElement(By.xpath(".//*[@id='event_guest_list']/div/div/div/div/a")).getAttribute("outerText");
			ReadWriteData.writeData("Showlist", "Sheet1", x+1, 15, attendance,"WHITE");} catch (Exception E) {}
			
////		} catch (Exception E) {System.out.println("URL Unreachable" + newLine+ newLine + newLine+ newLine);}
			
			// append End Date if empty
			if (ReadWriteData.readData("Showlist", "Sheet1", x+1, 5).isEmpty()) 
			{ReadWriteData.writeData("Showlist", "Sheet1", x+1, 5,
			 ReadWriteData.readData("Showlist", "Sheet1", x+1, 3), "BLACK");}
			
		}catch(Exception e) {continue;} 
		}
	}	
		
	public static String timeFormat(String raw) throws IOException, InterruptedException {
		String fixedTime=new String();
		raw=raw.replace("11:59", "11:30");
		if(raw.contains("AM") & raw.contains(":")) {fixedTime= Integer.toString((Integer.valueOf(raw.split (":") [0].trim()))).replace("12", "00") + ":" +raw.split (":") [1].replace(" AM", "");}
		else if (raw.contains("AM")) {fixedTime= Integer.toString((Integer.valueOf(raw.split (" ") [0].trim()))).replace("12", "00") + ":00" ;}
		
		if(raw.contains("PM") & raw.contains(":")) {fixedTime= Integer.toString((Integer.valueOf(raw.split (":") [0].trim())+12)).replace("24", "12") + ":" +raw.split (":") [1].replace(" PM", "");}
		else if (raw.contains("PM")) {fixedTime= Integer.toString((Integer.valueOf(raw.split (" ") [0].trim())+12)).replace("24", "12") + ":00" ;}
		return fixedTime;
		}
	
	public static String dateFormat(String raw) throws IOException, InterruptedException {
		String fixedDate= raw.split("-")[1] +"/"+ raw.split("-")[2] +"/"+ raw.split("-")[0];
		return fixedDate;
		}
}
