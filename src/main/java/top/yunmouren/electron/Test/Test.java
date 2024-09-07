package top.yunmouren.electron.Test;

import com.mojang.blaze3d.platform.InputConstants;
import icyllis.modernui.annotation.MainThread;
import icyllis.modernui.annotation.RenderThread;
import icyllis.modernui.annotation.UiThread;
import icyllis.modernui.graphics.Canvas;
import icyllis.modernui.graphics.Paint;
import icyllis.modernui.graphics.drawable.Drawable;
import icyllis.modernui.mc.forge.UICallback;
import icyllis.modernui.view.KeyEvent;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class Test extends UICallback implements Drawable.Callback {

    @UiThread
    public boolean isBackKey(int keyCode, @Nonnull KeyEvent event) {
        if (keyCode == 256) {
            return true;
        } else {
            InputConstants.Key key = InputConstants.getKey(keyCode, event.getScanCode());
            return Minecraft.getInstance().options.keyDown.isActiveAndMatches(key);
        }
    }
    @MainThread
    public boolean shouldClose() {
        return true;
    }

    @MainThread
    public boolean isPauseScreen() {
        return false;
    }

    @RenderThread
    public boolean hasDefaultBackground() {
        return true;
    }

    @RenderThread
    public boolean shouldBlurBackground() {
        return true;
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {

    }

    @Override
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {

    }

    @Override
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {

    }
}
