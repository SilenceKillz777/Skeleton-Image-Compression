import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] argv) {
		
	int numRows = 0, numCols = 0, minVal = 0, maxVal = 0;
			
		try {	
			Scanner inFile = new Scanner(new File(argv[0]));	//read in data
			PrintWriter outFile1 = new PrintWriter(new FileWriter(argv[1]));	//Pass2 Result
			PrintWriter outFile2 = new PrintWriter(new FileWriter(argv[2]));	//Skeleton
			PrintWriter outFile3 = new PrintWriter(new FileWriter(argv[3]));	//Compression
			PrintWriter outFile4 = new PrintWriter(new FileWriter(argv[4]));	//Pretty Print
			
			numRows = Integer.parseInt(inFile.next());
			numCols = Integer.parseInt(inFile.next());
			minVal = Integer.parseInt(inFile.next());
			maxVal = Integer.parseInt(inFile.next());
			
			imageProcessing process = new imageProcessing(numRows, numCols, minVal, maxVal, inFile);
			process.loadImage();
			process.firstPass_4Distance();
			process.prettyPrintDistance(outFile4,1);
			process.secondPass_4Distance();
			process.printImage(outFile1,1);
			process.prettyPrintDistance(outFile4,2);
			process.compute_localMaxima(outFile3);
			process.printImage(outFile2,2);
			process.prettyPrintSkeleton(outFile4);	
			inFile.close();
			outFile1.close();
			outFile2.close();
			outFile3.close();
			outFile4.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
