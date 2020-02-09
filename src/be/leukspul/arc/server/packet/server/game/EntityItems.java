package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Item;

public class EntityItems extends GameWritePacket {

    public EntityItems(Shard s, Entity entity, boolean showWindow) {
        super(s);
        ShowWindow = showWindow;
        this.entity = entity;
    }

    @Override
    protected void write() {
        //EntityInventory inventory = getClient().entity().get(RefInventory.class).Inventory;
        writeC(0x1b);
        writeH(ShowWindow ? 1 : 0);
        writeH(0);
        //writeH(inventory.list().size()); // Item Size

        /*
        for(Entity item : inventory.list()) {
            Item comp = item.get(Item.class);
            ItemTemplate template = comp.Template;
            writeType1(comp);
            writeD(item.id()); // object id
            writeD(template.Id); // item id
            writeD(comp.Amount); // total
            writeType2(comp);
            writeH(0); // custom type 1
            writeH(0); // is equipped
            writeD(0); // body part
            writeH(0); // enchanted
            writeH(0); // custom type two
            writeD(0); // Aug bonus
            writeD(0); // Mana
        }
        */
    }

    private void writeType1 (Item comp) {
        switch (comp.Template.Type) {
            case "EtcItem":
                writeH(Type1.Item_Quest_Adena);
                break;
            case "Armor":
                writeH(Type1.Shield_Armor);
                break;
            case "Accessory":
            case "Weapon":
                writeH(Type1.Weapon_Ring_Earring_Necklace);
                break;
        }
    }

    public void writeType2 (Item comp) {
        switch (comp.Template.Type) {
            case "EtcItem":
                writeH(Type2.Other);
                break;
            case "Weapon":
                writeH(Type2.Weapon);
                break;
            case "Armor":
                writeH(Type2.Shield_Armor);
                break;
            case "Accessory":
                writeH(Type2.Accessory);
                break;
        }
    }

    private Entity entity;
    private boolean ShowWindow;

    public static class Type1 {
        public static int Weapon_Ring_Earring_Necklace = 0;
        public static int Shield_Armor = 1;
        public static int Item_Quest_Adena = 4;
    }

    public static class Type2 {
        public static int Weapon = 0;
        public static int Shield_Armor = 1;
        public static int Accessory = 2;
        public static int Quest = 3;
        public static int Money = 4;
        public static int Other = 5;
    }

    public static class Slot {
        public static int None = 0x0000;
        public static int Underwear = 0x0001;
        public static int RightEar1 = 0x0002;
        public static int LeftEar1 = 0x0004;
        public static int Neck = 0x0008;
        public static int RightFinger = 0x0010;
        public static int LeftFinger = 0x0020;
        public static int Head = 0x0040;
        public static int RightHand = 0x0080;
        public static int LeftHand = 0x0100;
        public static int Gloves = 0x0200;
        public static int Chest = 0x0400;
        public static int Legs = 0x0800;
        public static int Feet = 0x1000;
        public static int Back = 0x2000;
        public static int TwoHand = 0x4000;
        public static int FullArmor = 0x8000;
        public static int Face = 0x010000;
        public static int FullCloths = 0x020000;
        public static int Hair = 0x040000;
        public static int HairAll = 0x080000;

    }
}
