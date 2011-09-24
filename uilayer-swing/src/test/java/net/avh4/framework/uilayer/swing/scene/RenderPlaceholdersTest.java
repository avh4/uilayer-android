package net.avh4.framework.uilayer.swing.scene;

import static net.avh4.util.imagecomparison.Matchers.isApproved;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RenderPlaceholdersTest extends RenderTestBase {

	@Test
	public void testRenderPlaceholders() throws Exception {
		scene.addPlaceholder("Background", 0, 0, 800, 600);
		scene.addPlaceholder("Body", 20, 20, 100, 560);
		assertThat(subject, isApproved());
	}

}