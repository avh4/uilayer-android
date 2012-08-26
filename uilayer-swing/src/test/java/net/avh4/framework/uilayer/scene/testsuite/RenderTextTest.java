package net.avh4.framework.uilayer.scene.testsuite;

import net.avh4.framework.uilayer.Color;
import net.avh4.framework.uilayer.Font;
import net.avh4.framework.uilayer.scene.RenderTestBase;
import net.avh4.framework.uilayer.scene.ScenePlaceholder;
import net.avh4.framework.uilayer.scene.SceneRect;
import net.avh4.framework.uilayer.scene.SceneText;
import net.avh4.framework.uilayer.test.categories.FontRendering;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FontRendering.class)
public class RenderTextTest extends RenderTestBase {

    @Test
    public void testRenderText() throws Exception {
        final String longString = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        scene.add(new ScenePlaceholder("Text box: size 36", 50, 50, 200, 1000));
        scene.add(new SceneText(longString, 50, 50, 200, "Tuffy.ttf", 36, Color.WHITE));
        scene.add(new ScenePlaceholder("Text box: size 12", 450, 50, 200, 1000));
        scene.add(new SceneText(longString, 450, 50, 200, "Tuffy.ttf", 12, Color.WHITE));
        assertRenderingIsApproved();
    }

    @Test
    public void testUpdateText() throws Exception {
        final SceneText text = scene.add(new SceneText("Wrong", 10, 10, 200, "Tuffy.ttf", 36, Color.WHITE));
        text.setText("Correct");
        assertRenderingIsApproved();
    }

    @Test
    public void testColoredText() throws Exception {
        scene.add(new SceneText("Blue", 10, 10, 100, "Tuffy.ttf", 36, Color.BLUE));
        assertRenderingIsApproved();
    }

    @Test
    public void testIncludedFontPfennig() throws Exception {
        final String text = "Pfennig";
        final Font font = Font.PFENNIG.size(32);
        final int width = font.measureText(text);
        final int height = font.getLineHeight();
        scene.add(new SceneRect(100, 100, width, height, Color.GREY));
        scene.add(new SceneText(text, 100, 100, 300, font, Color.YELLOW));
        assertRenderingIsApproved();
    }

    @Test
    public void testNewlinesInText() throws Exception {
        scene.add(new SceneText("Line /\nLine", 0, 0, 500, Font.PFENNIG, Color.YELLOW));
        assertRenderingIsApproved();
    }
}
