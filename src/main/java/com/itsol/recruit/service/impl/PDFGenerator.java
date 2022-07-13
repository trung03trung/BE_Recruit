package com.itsol.recruit.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class PDFGenerator {
    private final JobRepository jobRepository;

    public PDFGenerator(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public ByteArrayInputStream JobPDFReport(long id) {
        Job job=jobRepository.findJobById(id);
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document,out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
                    BaseColor.BLACK);
            Paragraph para = new Paragraph("Bảng job", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);
            // Add PDF Table Header ->
            Stream.of("ID", "Tên công việc", "Vị trí công việc","Năm kinh nghiệm","Địa chỉ").forEach(headerTitle ->
            {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.
                        getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });
                PdfPCell idCell = new PdfPCell(new Phrase(job.getId().
                        toString()));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);
                PdfPCell nameCell = new PdfPCell(new Phrase
                    (job.getName()));
                PdfPCell jobPositionCell = new PdfPCell(new Phrase
                        (job.getJobPosition().getCode()));
                PdfPCell numberExperienceCell=new PdfPCell(new Phrase
                        (job.getNumberExperience()));
                PdfPCell addressCell=new PdfPCell(new Phrase
                    (job.getAddressWork()));
                nameCell.setPaddingLeft(4);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameCell);
                jobPositionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                jobPositionCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                jobPositionCell.setPaddingRight(4);
                table.addCell(jobPositionCell);
                numberExperienceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                numberExperienceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                numberExperienceCell.setPaddingRight(4);
                 table.addCell(numberExperienceCell);
                addressCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                addressCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                addressCell.setPaddingRight(4);
                 table.addCell(addressCell);


            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
