package com.noyan.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import com.noyan.util.LogUtil;
import com.noyan.util.NullUtil;

public class PdfMerger {
	private String directory;
	private String destinationFileName;

	private int current = 1;

	public PdfMerger(String directory) {
		this(directory, "merged.pdf");
	}

	public PdfMerger(String directory, String destinationFileName) {
		this.directory = directory;

		if (NullUtil.isNull(destinationFileName)) {
			this.destinationFileName = "merged.pdf";
		} else {
			this.destinationFileName = destinationFileName;
			if (!destinationFileName.contains(".")) {
				this.destinationFileName = destinationFileName + ".pdf";
			}
		}

		LogUtil.logMessage(getClass(), "PdfMerger created" + "\nDirectory: " + this.directory
				+ "\nDestination FileName: " + this.destinationFileName);
	}

	public boolean merge() throws COSVisitorException, IOException {
		File directoryFile = new File(directory);
		if (!directoryFile.isDirectory()) {
			LogUtil.logMessage(getClass(), Level.WARNING, directory + " must be directory");
			return false;
		}

		PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();

		List<File> listFiles = Arrays.asList(directoryFile.listFiles());
		if (NullUtil.isNull(listFiles) || listFiles.isEmpty()) {
			LogUtil.logMessage(getClass(), Level.WARNING, directory + " is empty");
			return false;
		}

		boolean added = false;
		while (true) {
			try {
				File file = listFiles.stream().filter(a -> a.getName().equals(current + ".pdf")).findFirst().get();
				pdfMergerUtility.addSource(file);
				added = true;
			} catch (NoSuchElementException ignored) {
				break;
			}

			current++;
		}

		if (!added) {
			LogUtil.logMessage(getClass(), Level.WARNING, "pdf files not found");
			return false;
		}
		String destinationPath = directoryFile + "\\" + destinationFileName;

		long beginDate = new Date().getTime();
		LogUtil.logMessage(getClass(), "Merge started");

		pdfMergerUtility.setDestinationFileName(destinationPath);
		pdfMergerUtility.mergeDocuments();

		LogUtil.logMessage(getClass(), "Merge finished duration: " + (new Date().getTime() - beginDate) / 1000 + " s");
		LogUtil.logMessage(getClass(), "Merged file is ready in " + destinationPath);

		return true;
	}

	public static void merge(String directory, String destinationFileName) throws COSVisitorException, IOException {
		PdfMerger pdfMerger = new PdfMerger(directory, destinationFileName);
		pdfMerger.merge();
	}

	public static void main(String[] args) throws COSVisitorException, IOException {
		String helpString = "\nConstructor01-> [DIRECTORY]\n";
		helpString += "Constructor02-> [DIRECTORY] [DESTINATIONFILENAME]\n\n";
		helpString += "Correct file name in directory \"1.pdf\",\"2.pdf,\"3.pdf,\"4.pdf,\"5.pdf\" (1-5)\n";
		helpString += "Correct file name in directory \"4.pdf\",\"1.pdf,\"3.pdf,\"5.pdf,\"2.pdf\" (1-5)\n";
		helpString += "Wrong directory files \"1.pdf\",\"4.pdf,\"5.pdf,\"2.pdf\" (1-2,4-5)\n";

		if (args == null) {
			System.out.println(helpString);
			return;
		}

		String directory = null;
		String destinationFileName = null;

		if (args.length < 1) {
			System.out.println(helpString);
			return;
		}

		directory = args[0];
		if (args.length > 1) {
			destinationFileName = args[1];
		}

		PdfMerger pdfMerger = new PdfMerger(directory, destinationFileName);

		if (pdfMerger.merge()) {
			System.out.println("thank you");
		}
	}
}
