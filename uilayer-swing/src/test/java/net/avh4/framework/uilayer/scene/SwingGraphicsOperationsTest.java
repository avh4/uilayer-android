package net.avh4.framework.uilayer.scene;

import junit.framework.TestCase;
import net.avh4.framework.uilayer.Color;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import static net.avh4.util.imagecomparison.Matchers.isApproved;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"LawOfDemeter", "NestedMethodCall", "ChainedMethodCall"})
public class SwingGraphicsOperationsTest extends TestCase {

    private SwingSceneRenderer subject;
    protected SwingGraphicsOperations g;
    protected Runnable graphicsOperations;

    @Override
    public void setUp() {
        g = new SwingGraphicsOperations();
        SceneRenderer mockRenderer = Mockito.mock(SceneRenderer.class);
        Mockito.stub(mockRenderer.getWidth()).toReturn(800);
        Mockito.stub(mockRenderer.getHeight()).toReturn(600);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                graphicsOperations.run();
                return null;
            }
        }).when(mockRenderer).render(Mockito.same(g), Mockito.any(FontMetricsService.class));
        subject = new SwingSceneRenderer(g, mockRenderer);
    }

    protected void assertRenderingIsApproved() throws IOException {
        assertThat(subject, isApproved());
    }

    public void testRenderLine() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawLine(100, 100, 200, 200, Color.WHITE);
                g.drawLine(0, 300, 800, 0, Color.YELLOW);
            }
        };
        assertRenderingIsApproved();
    }

    public void testRenderRect() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawRect(50, 50, 200, 500, Color.RED);
                g.drawRect(450, 50, 200, 300, Color.YELLOW);
            }
        };
        assertRenderingIsApproved();
    }

    public void testRenderOval() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawOval(50, 50, 200, 500, Color.RED);
                g.drawOval(450, 50, 200, 300, Color.YELLOW);
            }
        };
        assertRenderingIsApproved();
    }

    public void testRenderResourceImage() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawImage("tile1.png", 100, 100, 150, 150, 0, 0, 101, 101);
            }
        };
        assertRenderingIsApproved();
    }

    public void testRenderMultipleImages() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawImage("tile1.png", 100, 100, 250, 250, 0, 0, 101, 101);
                g.drawImage("tile2.png", 150, 150, 225, 225, 0, 0, 94, 94);
                g.drawImage("tile1.png", 100, 300, 116, 316, 0, 0, 101, 101);
            }
        };
        assertRenderingIsApproved();
    }

    public void testRenderClippedImage() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawImage("tile1.png", 100, 100, 150, 150, 0, 0, 25, 25);
            }
        };
        assertRenderingIsApproved();
    }

    public void testRenderReclippedClippedImage() throws Exception {
        graphicsOperations = new Runnable() {
            @Override
            public void run() {
                g.drawImage("tile1.png", 100, 100, 150, 150, 25, 25, 50, 50);
            }
        };
        assertRenderingIsApproved();
    }
}