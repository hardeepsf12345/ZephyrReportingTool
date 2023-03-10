package com.report;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.report.report_CycleName;
public class SendMailWithHTMLContent {
	public static String PieChart() throws IOException
	{
		File obj1= new File((System.getProperty("user.dir") +"/Report.xlsx"));
		FileInputStream creds = new FileInputStream(obj1);
		XSSFWorkbook workbook = new XSSFWorkbook(creds);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowCount= sheet.getPhysicalNumberOfRows();
		int ActualCount=0;
		int a=report_CycleName.last_rowCount;
		System.out.println("Folder Count: "+a);
		for (int i=1; i<=a;i++)
		{
			try {
			String total1=sheet.getRow(i).getCell(11).getStringCellValue().trim();
			if(total1.equals("") || total1.equals(" "))
			continue;
			else
				ActualCount++;
			}
			finally{
				continue;
			}
		}
		String defects="",wip="",blocked="",unexecuted="", fail="", pass="", total="", blockedPerString="";
		int total1=0, total2=0, pass1=0, pass2=0, fail2=0, fail1=0, unexecuted1=0, unexecuted2 = 0, wip1=0, wip2=0, blocked1=0, blocked2=0, defects1=0, defects2= 0;
		float val1=0, val2=0, val3=0, val4=0;
		float passPercent=0, failPercent=0, unexePercent=0, wipPercent=0, blockedPercent=0;
		for (int i=1; i<=ActualCount;i++)
		{
		total=sheet.getRow(i).getCell(11).getStringCellValue().trim();
		if(total.equals(""))
		{
			total1 = 0;
		}
		else
		{
			total1=Integer.parseInt(sheet.getRow(i).getCell(11).getStringCellValue().trim());
		}
		total2  = total2 + total1;
		pass=sheet.getRow(i).getCell(6).getStringCellValue().trim();
		if(pass.equals(""))
		{
			pass1 = 0;
		}
		else
		{
			pass1=Integer.parseInt(sheet.getRow(i).getCell(6).getStringCellValue().trim());
		}
		pass2=pass2 + pass1;
		passPercent=(float)(((int)(((pass2*100)/total2) *100.0))/100.0);
		fail=sheet.getRow(i).getCell(7).getStringCellValue().trim();
		if(fail.equals(""))
		{
			fail1 = 0;
		}
		else
		{
			fail1=Integer.parseInt(sheet.getRow(i).getCell(7).getStringCellValue().trim());
		}	
		fail2=fail2 + fail1;
		failPercent=(float)(((int)(((fail2*100)/total2) *100.0))/100.0);
		unexecuted=sheet.getRow(i).getCell(8).getStringCellValue().trim();
		if(unexecuted.equals(""))
		{
			unexecuted1 = 0;
		}
		else
		{
			unexecuted1=Integer.parseInt(sheet.getRow(i).getCell(8).getStringCellValue().trim());
		}
		unexecuted2=unexecuted2 + unexecuted1;
		unexePercent=(float)(((int)(((unexecuted2*100)/total2) *100.0))/100.0);
		wip=sheet.getRow(i).getCell(9).getStringCellValue().trim();
		if(wip.equals(""))
		{
			wip1 = 0;
		}
		else
		{
			wip1=Integer.parseInt(sheet.getRow(i).getCell(9).getStringCellValue().trim());
		}
		wip2=wip2+wip1;
		wipPercent=(float)(((int)(((wip2*100)/total2) *100.0))/100.0);
		blocked = sheet.getRow(i).getCell(10).getStringCellValue().trim();
		if(blocked.equals(""))
		{
			blocked1 = 0;
		}
		else
		{
			blocked1=Integer.parseInt(sheet.getRow(i).getCell(10).getStringCellValue().trim());
		}
		blocked2=blocked2 + blocked1;
		blockedPercent=(float)(((int)(((blocked2*100)/total2) *100.0))/100.0);
		}
		//pie chart creation
		String chartURL="https://quickchart.io/chart?w=300&h=200&v=4&c="
				+ "{ type: 'pie', "
				+ "data: { labels: ['Pass-"+passPercent+"%' , 'Fail-"+failPercent+"%', 'Unexecuted-"+unexePercent+"%', 'WIP-"+wipPercent+"%', 'Blocked-"+blockedPercent+"%'], "
				+ "datasets: [{ backgroundColor: ['green', 'red', 'grey', 'orange', 'black'], "
				+ "data: ["+passPercent+","+failPercent+","+unexePercent+","+wipPercent+","+blockedPercent+"] } ], },"
				+ " options: { plugins: "
				+ "{ datalabels:"
				+ "{ display: "+false+"}},"
				+ "legend: "
				+ " { display: "+true+","+" position: 'right', align: 'right'}}}";
		String message = "<b style=color:Black;>Please see the chart for test summary below:<br><br><img src=\"" + chartURL + "\">";
		return message;
	}
	public static String MessageOne() throws IOException
	{
		StringBuilder html = new StringBuilder();
		FileReader fr = new FileReader("./result.html");
			BufferedReader br = new BufferedReader(fr);
			String val;
			String pie= PieChart();
		//	String black= "\\u001B[30m";
			String Sig1= "Thanks,";
			String Sig2= "SourceFuse Automation Bots";
			String Signature=  Sig1 +"<br>"+ Sig2+"<br>";
			// Reading the String till we get the null string and appending to the string
			while ((val = br.readLine()) != null) {
				html.append(val);
			}
			html.append(pie + "<br><br>");
			html.append(Signature + "<br><br>");
			// AtLast converting into the string
			String result = html.toString();
			// System.out.println(result);
			// Closing the file after all the completion of Extracting
			br.close();
			return result;
	}
	public static void main(String[] args) throws IOException  {
		Properties Prop= new Properties();
		File obj2= new File((System.getProperty("user.dir") +"/Properties"));
		FileInputStream fis= new FileInputStream(obj2);
		Prop.load(fis);
		String message= MessageOne();
		String subject ="Test Execution Status: " + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
		//String to=Prop.getProperty("Recepient");
		String to= "prakriti.sharma@sourcefuse.com";
		String from="prakriti.sharm@sourcefuse.com";
		sendEmail(message,subject,to,from);
	}
	public static void sendEmail(String message, String subject, String to, String from) {
		
		//Gmail Host
		String host="smtp.gmail.com";
//		Properties properties= new Properties();
		Properties properties= System.getProperties();
//		System.out.println("p"+properties);
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", 465);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		properties.put("mail.smtp.host", "smtp.gmail.com");
	
		properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		properties.put("mail.from", from);
		Session session =Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
// Use App password as a password from security option in GMAIL for access the gmail ID
				return new PasswordAuthentication("sfautomation@sourcefuse.com","Source@123");
			}	
		});
		//session.setDebug(true);
		
		MimeMessage msg = new MimeMessage(session);
		try {
		msg.setFrom();
		msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
		msg.setSubject(subject);
		msg.setContent(message  ,"text/html" );
		Transport.send(msg);
		System.out.println("Sent message successfully....");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	}