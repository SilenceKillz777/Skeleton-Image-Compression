import java.io.PrintWriter;
import java.util.Scanner;

public class imageProcessing {

	int numRows, numCols, minVal, maxVal, newMinVal, newMaxVal;
	int[][] zeroFramedAry;
	int[][] skeletonAry;
	int[] neighborAry;
	Scanner inFile;
	PrintWriter outFile1, outFile2, outFile3, outFile4;
	
	//Constructor
	imageProcessing(int numRows, int numCols, int minVal, int maxVal, Scanner inFile){
		this.numRows = numRows;
		this.numCols = numCols;
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.inFile = inFile;
		neighborAry = new int [5];
		zeroFramedAry = new int [numRows+2][numCols+2];
		skeletonAry = new int [numRows+2][numCols+2];
	}
	
	//Methods
	void loadImage() {
		for(int rows=0;rows<numRows;rows++) {
			for(int cols=0;cols<numCols;cols++) {
				zeroFramedAry[rows+1][cols+1] = Integer.parseInt(inFile.next());
			}
		}
	}
	
	void loadNeighbors(int rows, int cols) {
		neighborAry[0] = zeroFramedAry[rows][cols];
		neighborAry[1] = zeroFramedAry[rows-1][cols]+1;
		neighborAry[2] = zeroFramedAry[rows][cols-1]+1;
		neighborAry[3] = zeroFramedAry[rows][cols+1]+1;
		neighborAry[4] = zeroFramedAry[rows+1][cols]+1;
	}
	
	void loadMaximaNeighbors(int rows, int cols){
		neighborAry[0] = zeroFramedAry[rows][cols];
		neighborAry[1] = zeroFramedAry[rows-1][cols];
		neighborAry[2] = zeroFramedAry[rows][cols-1];
		neighborAry[3] = zeroFramedAry[rows][cols+1];
		neighborAry[4] = zeroFramedAry[rows+1][cols];
	}
	
	int min(int pass){
		int min = 0;
		if(pass==1){
			min = neighborAry[1];
			if(neighborAry[2]<min)
				min = neighborAry[2];
		}
		else if(pass==2) {
			min = neighborAry[0];
			for(int i=3;i<5;i++) {
				if(neighborAry[i]<min)
					min = neighborAry[i];
			}
		}
		return min;
	}
	
	void firstPass_4Distance(){
		for(int rows=0;rows<numRows;rows++) {
			for(int cols=0;cols<numCols;cols++) {
				if(zeroFramedAry[rows+1][cols+1]>0) {
					loadNeighbors(rows+1, cols+1);
					zeroFramedAry[rows+1][cols+1] = min(1);
				}
			}
		}
	}
	
	void secondPass_4Distance(){
		for(int rows=numRows;rows>0;rows--) {
			for(int cols=numCols;cols>0;cols--) {
				if(zeroFramedAry[rows+1][cols+1]>0) {
					loadNeighbors(rows+1, cols+1);
					zeroFramedAry[rows+1][cols+1] = min(2);
					if(zeroFramedAry[rows+1][cols+1]<newMinVal)
						newMinVal = zeroFramedAry[rows+1][cols+1];
					if(zeroFramedAry[rows+1][cols+1]>newMaxVal)
						newMaxVal = zeroFramedAry[rows+1][cols+1];
				}
			}
		}
	}
	
	int is_Maxima(int rows, int cols){
		loadMaximaNeighbors(rows, cols);
		if(zeroFramedAry[rows][cols]>=neighborAry[1])
			if(zeroFramedAry[rows][cols]>=neighborAry[2])
				if(zeroFramedAry[rows][cols]>=neighborAry[3])
					if(zeroFramedAry[rows][cols]>=neighborAry[4])
						return 1;
					else return 0;
				else return 0;
			else return 0;
		else return 0;
	}
	
	void compute_localMaxima(PrintWriter outFile3){
		outFile3.println(numRows+" "+numCols+" "+newMinVal+" "+newMaxVal);
		for(int rows=0;rows<numRows+2;rows++) {
			for(int cols=0;cols<numCols+2;cols++) {
				if(zeroFramedAry[rows][cols]>0){
					if(is_Maxima(rows,cols)==1) {
						skeletonAry[rows][cols] = zeroFramedAry[rows][cols];
						outFile3.println(rows+" "+cols+" "+ skeletonAry[rows][cols]);
					}
					else 
						skeletonAry[rows][cols] = 0;
				}
			}
		}
	}
	
	void printImage(PrintWriter file, int fileNum) {
		if(fileNum==1) {
			file.println("NewMinVal = " + newMinVal);
			file.println("NewMaxVal = " + newMaxVal);
			for(int rows=0;rows<numRows;rows++){
				for(int cols=0;cols<numCols;cols++){
					file.print(zeroFramedAry[rows+1][cols+1]+" ");
				}
				file.println();
			}
			file.println();
		}
		else if(fileNum==2){
			file.println("NewMinVal = " + newMinVal);
			file.println("NewMaxVal = " + newMaxVal);
			for(int rows=0;rows<numRows;rows++){
				for(int cols=0;cols<numCols;cols++){
					file.print(skeletonAry[rows+1][cols+1]+" ");
				}
				file.println();
			}
			file.println();
		}
	}
	
	
	void prettyPrintDistance(PrintWriter outFile4, int pass){
		outFile4.println("Pass-" + pass + " Result:");
		for(int rows=0;rows<numRows+2;rows++){
			for(int cols=0;cols<numCols+2;cols++){
				if(zeroFramedAry[rows][cols]>0)
					outFile4.print(zeroFramedAry[rows][cols]+" ");
				else outFile4.print("  ");
			}
			outFile4.println();
		}
		outFile4.println();
	}
	
	void prettyPrintSkeleton(PrintWriter outFile4) {
		outFile4.println("Skeleton Array:");
		for(int rows=0;rows<numRows+2;rows++){
			for(int cols=0;cols<numCols+2;cols++){
					
				if(skeletonAry[rows][cols]==0)
					outFile4.print(".");
				else outFile4.print("9");
				
			}
			outFile4.println();
		}
		outFile4.println();			
	}
}
