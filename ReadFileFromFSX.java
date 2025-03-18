package com.coreExample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFileFromFSX {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dir = "C:\\Users\\703196449\\OneDrive - Genpact\\Desktop_26-JUL-2024\\Queries_103\\All_Queries_Updated";
		String anotherDir = "C:\\Users\\703196449\\OneDrive - Genpact\\Desktop\\IPR_Issues_1";
		
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

}
