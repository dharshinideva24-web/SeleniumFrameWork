package rahulshettyacademy.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Collects one screenshot per Cucumber step (held in memory, per thread)
 * and, at the end of a scenario, writes them all into a single PDF -
 * one screenshot per page with a "Step N" caption.
 *
 * Output: reports/evidence/<ScenarioName>.pdf
 */
public class EvidenceManager {

    // One list of step-screenshots per thread == per scenario.
    private static final ThreadLocal<List<Shot>> SHOTS =
            ThreadLocal.withInitial(ArrayList::new);

    private static class Shot {
        final byte[] png;
        final String label;
        Shot(byte[] png, String label) {
            this.png = png;
            this.label = label;
        }
    }

    /** Called from the @AfterStep hook after every Gherkin step. */
    public static void addShot(byte[] png, String label) {
        SHOTS.get().add(new Shot(png, label));
    }

    /** Called from the @After hook - builds the PDF for this scenario. */
    public static String buildPdf(String scenarioName) throws IOException {

        List<Shot> shots = SHOTS.get();
        if (shots.isEmpty()) {
            return null; // nothing captured, nothing to build
        }

        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
        String dir = System.getProperty("user.dir") + File.separator + "reports"
                   + File.separator + "evidence";
        new File(dir).mkdirs();
        String pdfPath = dir + File.separator + safeName + ".pdf";

        try (PDDocument doc = new PDDocument()) {
            int stepNo = 1;
            for (Shot shot : shots) {
                PDImageXObject img =
                        PDImageXObject.createFromByteArray(doc, shot.png, shot.label);

                // Scale the screenshot to fit an A4-width page, keep aspect ratio.
                float maxW = PDRectangle.A4.getWidth() - 40;      // side margins
                float scale = Math.min(1f, maxW / img.getWidth());
                float w = img.getWidth() * scale;
                float h = img.getHeight() * scale;

                PDPage page = new PDPage(new PDRectangle(w + 40, h + 70));
                doc.addPage(page);

                try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                    // Caption at the top of the page.
                    cs.beginText();
                    cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    cs.newLineAtOffset(20, h + 45);
                    cs.showText("Step " + stepNo + ": " + sanitize(shot.label));
                    cs.endText();

                    // The screenshot itself.
                    cs.drawImage(img, 20, 20, w, h);
                }
                stepNo++;
            }
            doc.save(pdfPath);
        }
        return pdfPath;
    }

    public static void reset() {
        SHOTS.remove();
    }

    // PDFBox standard fonts can't render some Unicode chars - strip them for the caption.
    private static String sanitize(String s) {
        return s == null ? "" : s.replaceAll("[^\\x20-\\x7E]", " ");
    }
}
