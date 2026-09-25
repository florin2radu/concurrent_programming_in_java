package ro.florinradu.concurrentjava.extra.producerconsumer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PdfUtils {
    private PdfUtils() {
        /* This utility class should not be instantiated */
    }

    public static void createPdf(File txtFile, Path pdfPath) {
        try (PDDocument document = new PDDocument()) {
            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            float fontSize = 12;
            float margin = 50;
            float lineHeight = 15;

            PDPage page = new PDPage();
            document.addPage(page);

            float maxWidth = page.getMediaBox().getWidth() - 2 * margin;
            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();
            content.setFont(font, fontSize);
            content.newLineAtOffset(margin, page.getMediaBox().getHeight() - margin);

            float y = page.getMediaBox().getHeight() - margin;

            for (String line : Files.readAllLines(txtFile.toPath())) {
                for (String wrappedLine : wrapLine(line, font, fontSize, maxWidth)) {
                    if (y < margin + lineHeight) {
                        content.endText();
                        content.close();

                        page = new PDPage();
                        document.addPage(page);

                        content = new PDPageContentStream(document, page);
                        content.beginText();
                        content.setFont(font, fontSize);
                        content.newLineAtOffset(margin, page.getMediaBox().getHeight() - margin);

                        y = page.getMediaBox().getHeight() - margin;
                    }

                    content.showText(wrappedLine);
                    content.newLineAtOffset(0, -lineHeight);
                    y -= lineHeight;
                }
            }

            content.endText();
            content.close();
            document.save(pdfPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> wrapLine(String text, PDType1Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();

        for (String word : text.split(" ")) {
            String test = line.isEmpty() ? word : line + " " + word;
            float width = font.getStringWidth(test) / 1000 * fontSize;

            if (width > maxWidth && !line.isEmpty()) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(test);
            }
        }

        lines.add(line.toString());
        return lines;
    }
}
