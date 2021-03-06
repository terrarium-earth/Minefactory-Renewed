package dev.terrarium.minefactoryrenewed.client.gui.machine.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.blockentity.container.machine.mobs.SlaughterhouseContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.SlaughterhouseBlockEntity;
import dev.terrarium.minefactoryrenewed.client.gui.machine.MachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SlaughterhouseScreen extends MachineScreen<SlaughterhouseContainer> {

    private static final ResourceLocation SLAUGHTERHOUSE_GUI = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/slaughterhouse_gui.png");

    public SlaughterhouseScreen(SlaughterhouseContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, SLAUGHTERHOUSE_GUI, false);
    }

    @Override
    public void renderGui(PoseStack poseStack, int x, int y) {
        SlaughterhouseBlockEntity machine = (SlaughterhouseBlockEntity) menu.getBlockEntity();
        renderBarTooltip(poseStack, machine.getWorkTime(), machine.getMaxWorkTime(), workBarX, workBarY, x, y, "Wk");
        renderBarTooltip(poseStack, machine.getIdleTime(), machine.getMaxIdleTime(), idleBarX, idleBarY, x, y, "Ticks");

        renderBarTooltip(
                poseStack,
                machine.getPinkSlimeTank().getFluidAmount(),
                machine.getPinkSlimeTank().getCapacity(),
                114, fluidBarY,
                fluidBarWidth, fluidBarHeight,
                x, y,
                "MB Slime"
        );
    }

    @Override
    public void renderGuiLast(PoseStack poseStack, int x, int y) {
        SlaughterhouseBlockEntity machine = (SlaughterhouseBlockEntity) menu.getBlockEntity();
        renderWork(poseStack, machine.getWorkTime(), machine.getMaxWorkTime());
        renderIdle(poseStack, machine.getIdleTime(), machine.getMaxIdleTime());

        if (machine.getPinkSlimeTank().getFluidAmount() > 0)
            renderFluid(poseStack, machine.getPinkSlimeTank(), 114, fluidBarY, fluidBarWidth, fluidBarHeight);
    }
}
