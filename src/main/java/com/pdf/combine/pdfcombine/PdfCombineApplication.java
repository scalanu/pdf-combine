package com.pdf.combine.pdfcombine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.NoSuchFileException;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResource;


@SpringBootApplication
public class PdfCombineApplication {

	static Logger log = LoggerFactory.getLogger(PdfCombineApplication.class);
	
	/**
	 * This class uses Apache PDF Box to merge pdfs
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(PdfCombineApplication.class, args);
    	
	try {
        //Prepare input pdf file list as list of input stream.
		log.info("Started Process to combine pdfs...");
        
		String inputDir = applicationContext.getEnvironment().getProperty("inputDir");
		String outputDir = applicationContext.getEnvironment().getProperty("outputDir"); 
		String fileNameCheck = applicationContext.getEnvironment().getProperty("fileNameFormat"); 
		PDFMergerUtility docMerger = new PDFMergerUtility();
	    if(null != fileNameCheck && !fileNameCheck.isEmpty() && fileNameCheck.contains("LabelToCheck")){
			docMerger.addSource((new FileSystemResource(inputDir+"combineFilesInput1.pdf").getFile()));
			docMerger.addSource((new FileSystemResource(inputDir+"combineFilesInput2.pdf").getFile()));
			docMerger.setDestinationStream(new FileOutputStream(outputDir+"CombinedFile-Final.pdf"));
			docMerger.mergeDocuments(null);
		} else {
			log.error(fileNameCheck, "File Name is not in recognised format");
		}	
		
		 log.info("End of Process. Combined Pdf generated !");

    } catch(NoSuchFileException nfe){
		log.error("No such file  in source directory for file : "+nfe.getMessage());
	} catch(FileNotFoundException fnf){
		log.error("File not found in source directory for file : "+fnf.getMessage());
	} catch (Exception e) {
		log.error(e.getMessage());
	}
	}
   
}
