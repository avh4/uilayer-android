package net.avh4.framework.uilayer.scene;

import net.avh4.framework.uilayer.Image;
import net.avh4.math.Rect;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.stub;

@SuppressWarnings({"LawOfDemeter", "NestedMethodCall", "ChainedMethodCall"})
public class SceneImageTest extends RenderTestBase {

    private static final int IMAGE_WIDTH = 101;
    private static final int IMAGE_HEIGHT = 101;
    @Mock
    private Image image;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        stub(image.getWidth()).toReturn(IMAGE_WIDTH);
        stub(image.getHeight()).toReturn(IMAGE_HEIGHT);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                assertThat(invocation.getArguments()[0], sameInstance((Object) g));
                StringBuilder sb = new StringBuilder("%%% DRAW IMAGE ");
                for (int i = 1; i < 7; i++) {
                    sb.append(invocation.getArguments()[i]).append(" ");
                }
                sb.append("%%%");
                g.drawMockOperation(sb.toString());
                return null;
            }
        }).when(image).drawImage(any(GraphicsOperations.class),
                anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void shouldSetInitialClipToImageSize() throws Exception {
        SceneImage tile1 = new SceneImage(0, 0, 0, 0, image);
        assertThat(tile1.clipX, is(0));
        assertThat(tile1.clipY, is(0));
        assertThat(tile1.clipWidth, is(IMAGE_WIDTH));
        assertThat(tile1.clipHeight, is(IMAGE_HEIGHT));
    }

    @Test
    public void shouldDrawImage() throws Exception {
        draw(new Rect(0, 0, 50, 50), new SceneImage(0, 0, 50, 50, image));
        assertRendering(containsString("%%% DRAW IMAGE 0 0 101 101 50.0 50.0 %%%\n"));
    }

    @Test
    public void shouldDrawImageInCorrectPosition() throws Exception {
        draw(new Rect(100, 120, 50, 50), new SceneImage(100, 120, 50, 50, image));
        assertRenderingIs("" +
                "=== TRANSLATE to 100, 120 ===\n" +
                "%%% DRAW IMAGE 0 0 101 101 50.0 50.0 %%%\n" +
                "=== TRANSLATE to 0, 0 ===\n");
    }

    @Test
    public void testRenderRepositionedImage() throws Exception {
        final SceneImage image = new SceneImage(100, 100, 50, 50, this.image);
        image.setPosition(2, 3);
        draw(new Rect(2, 3, 50, 50), image);
        assertRenderingIs("" +
                "=== TRANSLATE to 2, 3 ===\n" +
                "%%% DRAW IMAGE 0 0 101 101 50.0 50.0 %%%\n" +
                "=== TRANSLATE to 0, 0 ===\n");
    }

    @Test
    public void testRenderClippedImage() throws Exception {
        draw(new Rect(0, 0, 50, 55), new SceneImage(0, 0, 50, 55, image, 0, 1, 20, 22));
        assertRendering(containsString("%%% DRAW IMAGE 0 1 20 22 50.0 55.0 %%%\n"));
    }

    @Test
    public void testRenderReclippedClippedImage() throws Exception {
        final SceneImage image = new SceneImage(0, 0, 50, 55, this.image, 0, 0, 20, 22);
        image.setClipPosition(10, 11);
        draw(new Rect(0, 0, 50, 55), image);
        assertRendering(containsString("%%% DRAW IMAGE 10 11 20 22 50.0 55.0 %%%\n"));
    }

    @Test
    public void withNoImage_shouldRenderAPlaceholder() throws Exception {
        draw(new Rect(0, 0, 100, 200), new SceneImage(0, 0, 100, 200));
        assertRenderingIs("" +
                "Rectangle: 0.0, 0.0, 100.0, 200.0, 0xffb7abfb\n" +
                "Text: \"Missing Image\" 5.0, 195.0 Font{'Pfennig.ttf' (12)} 0xff000000\n");
    }

    @Test
    public void setImage_shouldSetTheImageAndResetClipSize() throws Exception {
        SceneImage image = new SceneImage(10, 11, 100, 200);
        image.setImage(this.image);
        draw(new Rect(10, 11, 100, 200), image);
        assertRendering(containsString("%%% DRAW IMAGE 0 0 101 101 100.0 200.0 %%%\n"));
    }

    @Test
    public void setImage_shouldResetClipPosition() throws Exception {
        SceneImage image = new SceneImage(10, 11, 100, 200);
        image.setClipPosition(70, 77);
        image.setImage(this.image);
        draw(new Rect(10, 11, 100, 200), image);
        assertRendering(containsString("%%% DRAW IMAGE 0 0 101 101 100.0 200.0 %%%\n"));
    }
}
