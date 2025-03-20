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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ReadFileFromFSX {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dir = "\\\\gcawswidvap03.coraaide.ad\\Shared\\Nagarjuna_K\\All_Queries_Updated";		
		Long lUser = 7783l;
		String DBURL1 = "jdbc:oracle:thin:@gcaws-rds-lwfadev.coraaide.ad:1524:LWFADEV";
		String DBUSER1 = "LWUAT";
		String DBPASS1 = "lw_uaT0108";

		Connection con1 = getDBConnection(DBURL1, DBUSER1, DBPASS1);
		
		String rptQDataQry = "SELECT * FROM REPORT_QUEUE WHERE CLIENT_ID="+lUser+" AND MANAGER_ID IS NULL AND REPORT_PATH IS NOT NULL";
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		HashSet<String> reportPaths = new HashSet<String>();
		BufferedReader br = null;
		//BufferedWriter writeIntoFile = new BufferedWriter(new FileWriter("dbReportName.txt"));
		//BufferedWriter rptDeleteBw;
		try {
			pstmt = con1.prepareStatement(rptQDataQry);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				//writeIntoFile.write((String)resultSet.getString("REPORT_PATH"));
				//writeIntoFile.newLine();
				reportPaths.add((String)resultSet.getString("REPORT_PATH"));
			}
			br = new BufferedReader(new FileReader("E:\\DevWorkspace\\Nagarjuna_IPR\\LW_IPR\\LIVEWEALTH_IPR\\reportName1.txt"));
			//rptDeleteBw = new BufferedWriter(new FileWriter("reportNameDelete.txt"));
			String line ="";
			try(BufferedWriter rptDeleteBw = new BufferedWriter(new FileWriter("reportNameDelete.txt"))) {
	            while ((line = br.readLine()) != null) {
	                if(!reportPaths.contains(line)) {
	                		rptDeleteBw.write(line);
	                		rptDeleteBw.newLine();
	                	System.out.println("File Name...."+br.readLine());
	                }
	            }
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				//writeIntoFile.close();
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
		
		//try(BufferedWriter writeIntoFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("reportNames.txt"), "UTF-8"))){
		try(BufferedWriter reportFileName = new BufferedWriter(new FileWriter("reportName.txt"))){
		
			Set<String> fileSet = listFilesUsingFilesList(dir);
			//Set<String> anotherFileSet = listFilesUsingFilesList(anotherDir);
			for (String fileName : fileSet) {
				reportFileName.write(fileName);
				reportFileName.newLine();
				System.out.println("File Names ..."+fileName);
			}
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}	
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
