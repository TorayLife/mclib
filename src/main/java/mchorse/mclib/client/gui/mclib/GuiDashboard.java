package mchorse.mclib.client.gui.mclib;

import mchorse.mclib.McLib;
import mchorse.mclib.client.gui.framework.GuiBase;
import mchorse.mclib.client.gui.framework.elements.utils.GuiDraw;
import mchorse.mclib.client.gui.utils.Icons;
import mchorse.mclib.client.gui.utils.keys.IKey;
import mchorse.mclib.config.gui.GuiConfig;
import mchorse.mclib.events.RegisterDashboardPanels;
import mchorse.mclib.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiDashboard extends GuiBase
{
	public static GuiDashboard dashboard;

	public GuiDashboardPanels panels;
	public GuiConfig config;

	private boolean wasClosed = true;

	public static GuiDashboard get()
	{
		if (dashboard == null)
		{
			dashboard = new GuiDashboard(Minecraft.getMinecraft());
		}

		return dashboard;
	}

	public GuiDashboard(Minecraft mc)
	{
		this.panels = new GuiDashboardPanels(mc);

		this.panels.flex().relative(this.viewport).wh(1F, 1F);
		this.panels.registerPanel(this.config = new GuiConfig(mc, this), IKey.lang("mclib.gui.config.tooltip"), Icons.GEAR);
		McLib.EVENT_BUS.post(new RegisterDashboardPanels(this));

		this.panels.setPanel(this.config);

		this.root.add(this.panels);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	protected void closeScreen()
	{
		this.panels.close();
		this.wasClosed = true;

		super.closeScreen();
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height)
	{
		if (this.wasClosed)
		{
			this.wasClosed = false;
			this.panels.open();
			this.panels.setPanel(this.panels.view.delegate);
		}

		super.setWorldAndResolution(mc, width, height);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		if (this.panels.view.delegate != null && this.panels.view.delegate.needsBackground())
		{
			ResourceLocation background = McLib.backgroundImage.get();
			int color = McLib.backgroundColor.get();

			if (background == null)
			{
				Gui.drawRect(0, 0, this.width, this.height, color);
			}
			else
			{
				this.mc.renderEngine.bindTexture(background);
				ColorUtils.bindColor(color);
				GlStateManager.enableAlpha();
				GuiDraw.drawBillboard(0, 0, 0, 0, this.width, this.height, this.width, this.height);
			}
		}
		else
		{
			this.drawGradientRect(0, 0, this.width, this.height / 8, 0x44000000, 0x00000000);
			this.drawGradientRect(0, this.height - this.height / 8, this.width, this.height, 0x00000000, 0x44000000);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}