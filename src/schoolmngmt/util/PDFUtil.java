package schoolmngmt.util;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import schoolmngmt.model.SchoolClass;

public class PDFUtil {
	public static void ExportPdf(File file, SchoolClass schoolClass) throws IOException, COSVisitorException {
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
			
		PDFont font = PDType1Font.HELVETICA_BOLD;
			
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
			
		contentStream.beginText();
			
		contentStream.setFont(font, 48);
		contentStream.moveTextPositionByAmount(100, 700);
		contentStream.drawString(schoolClass.getName());
			
		contentStream.setFont(font, 24);
		contentStream.moveTextPositionByAmount(0, -50);
		contentStream.drawString(schoolClass.getCourse());
						
		contentStream.setFont(font, 16);
		contentStream.moveTextPositionByAmount(0, -50);
		contentStream.drawString("Teachers");
					
		contentStream.setFont(font, 12);
		contentStream.moveTextPositionByAmount(0, -20);
		for (String teacher : schoolClass.getTeachers()) {
			contentStream.drawString(teacher + "\n");
			contentStream.moveTextPositionByAmount(0, -15);
		}
			
		contentStream.setFont(font, 16);
		contentStream.moveTextPositionByAmount(0, -50);
		contentStream.drawString("Students");
			
		contentStream.setFont(font, 12);
		contentStream.moveTextPositionByAmount(0, -20);
		for (String student : schoolClass.getStudents()) {
			contentStream.drawString(student + "\n");
			contentStream.moveTextPositionByAmount(0, -15);
		}
			
		contentStream.endText();
		contentStream.close();
			
		document.save(file);
		document.close();
	}
}
