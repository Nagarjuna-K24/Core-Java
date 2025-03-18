package com.rage.clientportal.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContext;

import com.rage.clientportal.config.LwDbConfig;

public class ReadFileFromFSX {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dir = "\\\\gcawswidvap03.coraaide.ad\\Shared\\Nagarjuna_K\\All_Queries_Updated";
		String anotherDir = "C:\\Users\\703196449\\OneDrive - Genpact\\Desktop\\IPR_Issues_1";
		
		// Properties reading
		
		Properties prop = new Properties();
		Long lUser = 4352l;
		/*
		 * System.out.println("Processing Started...."); prop.load(new
		 * FileReader("db.properties")); String DBURL = prop.getProperty("DBURL");
		 * String DBUSER = prop.getProperty("DBUSER"); String DBPASS =
		 * prop.getProperty("DBPASS"); System.out.println("DBURL...." + DBURL);
		 * System.out.println("DBUSER...." + DBUSER); System.out.println("DBPASS...." +
		 * DBPASS);
		 */

		String DBURL1 = "jdbc:oracle:thin:@gcaws-rds-lwfadev.coraaide.ad:1524:LWFADEV";
		String DBUSER1 = "LWUAT";
		String DBPASS1 = "lw_uaT0108";

		long l = System.currentTimeMillis();
		//Connection con = getDBConnection(DBURL, DBUSER, DBPASS);
		Connection con1 = getDBConnection(DBURL1, DBUSER1, DBPASS1);
		
		String rptQDataQry = "SELECT * FROM REPORT_QUEUE WHERE CLIENT_ID='2787' 7783' AND MANAGER_ID IS NULL AND REPORT_PATH IS NOT NULL;";
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		HashSet<String> reportPaths = new HashSet<String>();
		try {
			String reportPath = "";
			pstmt = con1.prepareStatement(rptQDataQry);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				reportPaths.add((String)resultSet.getString("REPORT_PATH"));
			}
			BufferedReader br = new BufferedReader(new FileReader("E:\\DevWorkspace\\Nagarjuna_IPR\\LW_IPR\\LIVEWEALTH_IPR\reportPath1"));
			String line;
            while ((line = br.readLine()) != null) {
                if(!reportPaths.contains(line)) {
                	try (BufferedWriter writeIntoFile = new BufferedWriter(new FileWriter("reportNameDelete.txt"))) {
						writeIntoFile.write(line);
						writeIntoFile.newLine();
					}catch(Exception ex) {
						ex.printStackTrace();
					}
                	System.out.println("File Names ..."+line);
                }
                	
            }
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				con1.close();
				resultSet.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//
		
//		try (BufferedReader bReader = new BufferedReader(new FileReader("C:\\Users\\703196449\\OneDrive - Genpact\\Desktop\\IPR_Issues\\UTILITIES_INDEX_STREAM.txt"))){
//			String line;
//			while((line = bReader.readLine()) != null) {
//				
//				System.out.println("BReader line value.."+line);
//				
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
//		try (BufferedReader reader = Files.newBufferedReader(Path.of("C:\\Users\\703196449\\OneDrive - Genpact\\Desktop\\IPR_Issues\\UTILITIES_INDEX_STREAM.txt"))) {
//            reader.lines().forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		
		//try(BufferedWriter writeIntoFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("reportNames.txt"), "UTF-8"))){
		try(BufferedWriter writeIntoFile = new BufferedWriter(new FileWriter("reportName.txt"))){
		
			Set<String> fileSet = listFilesUsingFilesList(dir);
			//Set<String> anotherFileSet = listFilesUsingFilesList(anotherDir);
			for (String fileName : fileSet) {
				writeIntoFile.write(fileName);
				writeIntoFile.newLine();
				System.out.println("File Names ..."+fileName);
			}
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		
		//System.out.println(fileSet);
		//System.out.println(anotherFileSet);		
		

	}
	
	public static boolean stringCheck2(String test1, String test2) {
	    return Optional.ofNullable(test1).filter(t -> t.equals(test2)).isPresent();
	}
	
	public static Set<String> listFilesUsingFilesList(String dir) throws IOException {
	    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
	        return stream
	          .filter(file -> !Files.isDirectory(file))
	          .map(Path::getFileName)
	          .map(Path::toString)
	          .collect(Collectors.toSet());
	    }
	}
	
	public static Connection getDBConnection(String URL,String UN, String PS)
	{
    	Connection conn = null;
    	try{
	    	// Load Oracle JDBC Driver
	        // Connect to Oracle Database\
    		Class.forName("oracle.jdbc.driver.OracleDriver");
	    	conn = DriverManager.getConnection(URL, UN, PS);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    	return conn;
	}

}
