package mchorse.mclib.config.values;

import com.google.gson.JsonElement;
import mchorse.mclib.client.gui.framework.elements.GuiElement;
import mchorse.mclib.config.gui.GuiConfigPanel;
import mchorse.mclib.network.IByteBufSerializable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IConfigValue extends IByteBufSerializable
{
    public String getId();

    public List<IConfigValue> getSubValues();

    public Object getValue();

    public void setValue(Object value);

    public void reset();

    public void resetServer();

    public boolean isVisible();

    public boolean isClientSide();

    public boolean isSyncable();

    @SideOnly(Side.CLIENT)
    public List<GuiElement> getFields(Minecraft mc, GuiConfigPanel gui);

    public boolean parseFromCommand(String value);

    public void copy(IConfigValue value);

    public void copyServer(IConfigValue value);

    public void fromJSON(JsonElement element);

    public JsonElement toJSON();
}