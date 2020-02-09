package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.calc.Calculator;
import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Attributes;
import be.leukspul.data.ecs.component.Avatar;
import be.leukspul.data.ecs.component.Vitals;

public class PlayerInfo extends GameWritePacket {
    public PlayerInfo(Shard s, Entity e) {
        super(s);
        entity = e;
    }

    @Override
    protected void write() {
        Avatar avatar = entity.get(Avatar.class);
        Vitals vitals = entity.get(Vitals.class);
        Attributes attr = entity.get(Attributes.class);

        writeC(0x04);
        writeD((int)entity.position().x());
        writeD((int)entity.position().y());
        writeD((int)entity.position().z());
        writeD((int)entity.direction().heading());
        writeD(entity.id());
        writeS(avatar.Name);
        writeD(avatar.Race);
        writeD(avatar.Gender);
        writeD(avatar.Class);
        writeD(1); // Level
        writeQ(0L); // Exp
        writeD(attr.Strength); // STR
        writeD(attr.Dexterity); // DEX
        writeD(attr.Constitution); // CON
        writeD(attr.Intelligence); // INT
        writeD(attr.Wisdom); // WIT
        writeD(attr.Mentality); // MEN
        writeD(100);
        writeD(vitals.Health); // Current Health
        writeD(0); // Max Mp
        writeD(0); // Current Mp
        writeD(0); // SP
        writeD(vitals.Load); // Load
        writeD(0); // max load
        writeD(0); // Active Weapon ID

        for (int i = 0; i < 2; i++) {
            writeD(0); // Hair all?
            writeD(0); // Right ear
            writeD(0); // Left ear
            writeD(0); // Neck
            writeD(0); // Right Finger
            writeD(0); // Left Finger
            writeD(0); // Head
            writeD(0); // Right Hand
            writeD(0); // Left Hand
            writeD(0); // Gloves
            writeD(0); // Chest
            writeD(0); // Legs
            writeD(0); // Feet
            writeD(0); // Back
            writeD(0); // Right Hand
            writeD(0); // Hair
            writeD(0); // Face
        }

        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeD(0); // Right Hand
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeD(0); // Left Hand
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);
        writeH(0x00);

        writeD(0); // Patk
        writeD(0); // Patk speed
        writeD(0); // PDef
        writeD(0); // Evasion
        writeD(0); // Accuracy
        writeD(0); // Critical Hit
        writeD(0); // Matk
        writeD(0); // Matk speed
        writeD(0); // Patk speed
        writeD(0); // Mdef
        writeD(0); // Pvp flag
        writeD(900); // Karma

        writeD((int) Calculator.speed(entity)); // Run speed
        writeD((int) Calculator.speed(entity)); // Walk Speed
        writeD(133); // Swim Speed
        writeD(100); // Walk Swim Speed
        writeD(0);
        writeD(0);
        writeD(133); // Fly speed
        writeD(100); // Fly walk speed

        writeF(Calculator.movementMultiplier(entity)); // Movement speed multiplier
        writeF(1.0); // Attack speed multiplier

        if (avatar.Gender == 0) {
            writeF(avatar.RadiusMale); // Collision Radius
            writeF(avatar.HeightMale); // Collision Height
        }
        else {
            writeF(avatar.RadiusFemale); // Collision Radius
            writeF(avatar.HeightFemale); // Collision Height
        }

        writeD(avatar.Hair);
        writeD(avatar.Color);
        writeD(avatar.Face);

        writeD(1); // Is GM

        writeS(avatar.Title);
        writeD(0); // Clan ID
        writeD(0); // Clan Crest ID
        writeD(0); // Ally ID
        writeD(0); // Ally Crest ID

        writeD(0); // Relation?
        writeC(0); // Mount Type
        writeC(0); // Store Type
        writeC(0); // has dwarf crafts
        writeD(0); // pk kills
        writeD(0); // pvp kills

        writeH(0); // cubics

        writeC(0); // Party matching
        writeD(0); // Abnormal Effects

        writeC(0); // ?

        writeD(0); // Clan privs

        writeH(0); // Recom left
        writeH(0); // Recom have
        writeD(0); // Mount NPC id
        writeH(80); // Inventory Limit

        writeD(avatar.Class);
        writeD(0); // Special effects?
        writeD(100); // Max CP
        writeD(vitals.Hunger); // Current CP
        writeC(0); // Enchant effect

        writeC(0); // Team circle 1 = blue, 2 = red
        writeD(0); // Clan crest large id?
        writeC(0); // Is noble
        writeC(0); // Is hero

        writeC(0); // is fishing

        writeD((int)entity.position().x());
        writeD((int)entity.position().y());
        writeD((int)entity.position().z());

        writeD(0xff0000); // Name Color

        writeC(avatar.Walking ? 0 : 1); // Is running
        writeD(0); // Pledge class
        writeD(0); // Pledge Type

        writeD(0x00ff00); // Title Color

        writeD(0); // Cursed weapon

    }

    private Entity entity;
}
