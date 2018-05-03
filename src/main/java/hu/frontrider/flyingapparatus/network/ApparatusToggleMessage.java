package hu.frontrider.flyingapparatus.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ApparatusToggleMessage implements IMessage {


    public ApparatusToggleMessage() {

    }

    public ApparatusToggleMessage(boolean mode)
    {
    }

    @Override
    public void fromBytes(ByteBuf byteBuf){
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {

    }
}
