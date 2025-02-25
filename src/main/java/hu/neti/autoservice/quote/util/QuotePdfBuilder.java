package hu.neti.autoservice.quote.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import hu.neti.autoservice.quote.error.PdfGenerationException;
import hu.neti.autoservice.quote.model.Customer;
import hu.neti.autoservice.quote.model.Part;
import hu.neti.autoservice.quote.model.QuoteDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static hu.neti.autoservice.quote.constants.Constants.*;
import static hu.neti.autoservice.quote.util.Formatters.*;

@Slf4j
@Component
public class QuotePdfBuilder {
    public byte[] generateQuotePdf(Customer customer, QuoteDetails quoteDetails) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph(QUOTE).simulateBold().setFontSize(20));
            document.add(new Paragraph(String.join(EMPTY_STRING, CUSTOMER_NAME, customer.getName())));
            if(LOYAL_TYPE.equalsIgnoreCase(customer.getCustomerType().name())) {
                document.add(new Paragraph(String.join(EMPTY_STRING, CUSTOMER_TYPE, LOYAL)));
            } else if(VIP_TYPE.equalsIgnoreCase(customer.getCustomerType().name())) {
                document.add(new Paragraph(String.join(EMPTY_STRING, CUSTOMER_TYPE, VIP_TYPE)));
            }

            document.add(new Paragraph(WHITE_SPACE).setFontSize(20).setPadding(10));

            Table table = new Table(new float[]{5, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph(PART_NAME).simulateBold()).setMarginTop(20f).setPadding(5).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph(PRICE).simulateBold()).setMarginTop(20f).setPadding(5).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

            for (Part part : quoteDetails.getPartsCalculated()) {
                table.addCell(new Cell().add(new Paragraph(part.getName())).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph(getCurrencyFormatter().format(part.getPurchasePrice()))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
            }

            table.addCell(new Cell().add(new Paragraph(WHITE_SPACE)).setMarginTop(20f).setBorder(Border.NO_BORDER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph(WHITE_SPACE)).setMarginTop(20f).setBorder(Border.NO_BORDER).setPadding(5));

            table.addCell(new Cell().add(new Paragraph(String.format(LABOR_FEE, EMPTY_STRING)).simulateBold()).setBorder(Border.NO_BORDER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph(getCurrencyFormatter().format(quoteDetails.getLaborFee()))).setBorder(Border.NO_BORDER).setPadding(5).setTextAlignment(TextAlignment.RIGHT));
            if (quoteDetails.getDiscountOfParts() > 0) {
                table.addCell(new Cell().add(new Paragraph(String.format(DISCOUNT_ON_PARTS_PRICES, EMPTY_STRING)).simulateBold()).setBorder(Border.NO_BORDER).setPadding(5));
                table.addCell(new Cell().add(new Paragraph(String.join(EMPTY_STRING, MINUS, getCurrencyFormatter().format(quoteDetails.getDiscountOfParts())))).setBorder(Border.NO_BORDER).setPadding(5).setTextAlignment(TextAlignment.RIGHT));
            }
            table.addCell(new Cell().add(new Paragraph(String.format(TOTAL, EMPTY_STRING)).simulateBold()).setBorder(Border.NO_BORDER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph(getCurrencyFormatter().format(quoteDetails.getTotal()))).setBorder(Border.NO_BORDER).setPadding(5).setTextAlignment(TextAlignment.RIGHT));

            table.addCell(new Cell().add(new Paragraph(WHITE_SPACE)).setMarginTop(20f).setBorder(Border.NO_BORDER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph(WHITE_SPACE)).setMarginTop(20f).setBorder(Border.NO_BORDER).setPadding(5));

            document.add(table);

            document.add(new Paragraph(String.join(COMMA, AUTO_SERVICE, date())).setPadding(10));

            document.close();
            return out.toByteArray();

        } catch (IOException e) {
            log.error("An error occurred: {}", e.getMessage());
            throw new PdfGenerationException("An error occurred while generating the quote PDF file.", e);
        }
    }
}
