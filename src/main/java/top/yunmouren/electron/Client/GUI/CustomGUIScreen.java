package top.yunmouren.electron.Client.GUI;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.yunmouren.electron.Server.tools.Http.BuildUrl;

@OnlyIn(Dist.CLIENT)
public class CustomGUIScreen extends Screen {
    private final String url;
    public CustomGUIScreen(String argument,String url) {
        super(Component.nullToEmpty(argument));
        this.url = url;
    }

    @Override
    protected void init() {
        // 初始化GUI组件
        super.init();
        BuildUrl.joinGui();
        BuildUrl.loadUrl(url);
    }
    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        super.render(poseStack, mouseX, mouseY, delta);
    }
    @Override
    public void onClose() {
        super.onClose();
        BuildUrl.exitGui();
    }
}
