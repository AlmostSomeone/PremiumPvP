package dev.almostsomeone.premiumpvp.common.nms.classes;

public class ClassBuilder_v1_8_R3 extends GlobalClassBuilder {

    public ClassBuilder_v1_8_R3() throws ClassNotFoundException {
        this.ChatSerializer = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer");
        this.ChatComponent = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent");
    }
}
