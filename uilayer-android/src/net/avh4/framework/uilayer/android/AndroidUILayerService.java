package net.avh4.framework.uilayer.android;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import net.avh4.framework.uilayer.ClickReceiver;
import net.avh4.framework.uilayer.Font;
import net.avh4.framework.uilayer.KeyReceiver;
import net.avh4.framework.uilayer.SceneCreator;
import net.avh4.framework.uilayer.UILayerService;

public class AndroidUILayerService implements UILayerService {

    Context context;

    public void run(SceneCreator game, ClickReceiver receiver, KeyReceiver keyReceiver) {
        throw new RuntimeException(
                "Android applications do not implement a main entry point.  " +
                        "Subclass AndroidSceneRendererActivity instead.");
    }

    public void init(Context context) {
        this.context = context;
    }

    public int getImageWidth(String image) {
        return 0;
    }

    public int getImageHeight(String image) {
        return 0;
    }

    public int getFontHeight(Font font) {
        final Paint paint = new Paint();
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), font.getResourceName()));
        paint.setTextSize(font.getSize());
        final Paint.FontMetrics metrics = paint.getFontMetrics();
        return (int) Math.ceil(-metrics.ascent + metrics.descent + metrics.leading);
    }

    public int measureText(Font font, String text) {
        final Paint paint = new Paint();
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), font.getResourceName()));
        paint.setTextSize(font.getSize());
        return (int) Math.ceil(paint.measureText(text));
    }
}
