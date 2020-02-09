package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.calc.Calculator;
import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;
import be.leukspul.data.ecs.component.Vitals;

public class EntityInfo extends GameWritePacket {

    private Entity entity;

    public EntityInfo(Shard s, Entity e) {
        super(s);
        entity = e;
    }

    @Override
    protected void write() {
        Avatar avatar = entity.get(Avatar.class);
        if (avatar.IsNpc) {
            npc(avatar);
        }
        else {
            pc(avatar);
        }
    }

    private void npc(Avatar avatar) {
        Vitals vitals = entity.get(Vitals.class);
        writeC(0x16);
        writeD(entity.id());
        writeD(avatar.NpcId + 1000000);
        writeD(0);
        writeD((int)entity.position().x());
        writeD((int)entity.position().y());
        writeD((int)entity.position().z());
        writeD((int)entity.direction().heading());
        writeD(0);
        writeD(0); // matk speed
        writeD(avatar.AttackSpeed);
        writeD(avatar.RunSpeed);
        writeD(avatar.WalkSpeed);
        writeD(avatar.RunSpeed);
        writeD(avatar.WalkSpeed);
        writeD(avatar.RunSpeed);
        writeD(avatar.WalkSpeed);
        writeD(avatar.RunSpeed);
        writeD(avatar.WalkSpeed);
        writeF(1.0); // move multiplier
        writeF(1.0); // attack multiplier
        writeF(avatar.Radius);
        writeF(avatar.Height);
        writeD(0); // right hand
        writeD(0); // chest
        writeD(0); // left hand
        writeC(0);
        writeC(1); // Running
        writeC(1); // In combat
        writeC(0); // Dead
        writeC(0); // Summoned
        writeS(avatar.Name);
        writeS("");
        writeD(0);
        writeD(0);
        writeD(0);
        writeD(0); // abnormal effects
        writeD(0); // clan id
        writeD(0); // clan crest
        writeD(0); // ally id
        writeD(0); // ally crest
        writeC(0); // Flying (2 : 0)
        writeC(0);
        writeF(avatar.Radius);
        writeF(avatar.Height);
        writeD(0); // Enchant effect
        writeD(0); // Is flying (1 : 0)
    }

    private void pc(Avatar avatar) {
        Vitals vitals = entity.get(Vitals.class);
        writeC(0x03);
        writeD((int)entity.position().x());
        writeD((int)entity.position().y());
        writeD((int)entity.position().z());
        writeD((int)entity.direction().heading());
        writeD(entity.id());
        writeS(avatar.Name);
        writeD(avatar.Race);
        writeD(avatar.Gender);
        writeD(avatar.Class);
        writeD(0); // Hair all?
        writeD(0); // Head
        writeD(0); // Rhand
        writeD(0); // Lhand
        writeD(0); // Gloves
        writeD(0); // Chest
        writeD(0); // Legs
        writeD(0); // Feet
        writeD(0); // Back
        writeD(0); // Rhand
        writeD(0); // Hair
        writeD(0); // Face
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
        writeD(0); // pvp flag
        writeD(0); // Karma
        writeD(0); // Matk speed
        writeD(0); // Matk speed
        writeD(0); // PVP flag
        writeD(0); // Karma
        int speed = (int)Calculator.speed(entity);
        writeD(speed); // Run Speed
        writeD(speed); // Walk Speed
        writeD(speed);
        writeD(speed);
        writeD(speed);
        writeD(speed);
        writeD(speed);
        writeD(speed);
        writeF(Calculator.movementMultiplier(entity)); // move speed
        writeF(1); // attack speed
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
        writeS(avatar.Title);
        writeD(0); // clen id
        writeD(0); // clan crest id
        writeD(0); // ally id
        writeD(0); // ally crest id
        writeD(0); // ?
        writeC(1); // Is sitting
        writeC(avatar.Walking ? 0 : 1); // Is running
        writeC(0); // In combat
        writeC(0); // Is dead
        writeC(0); // Invisible
        writeC(0); // Mount type
        writeC(0); // Store type
        writeH(0); // cubics
        writeC(0); // party matching
        writeD(0); // abnormal effects
        writeC(0); // recom left
        writeH(0); // recom have
        writeD(avatar.Class);
        writeD(100); // Max CP
        writeD(vitals.Hunger);
        writeC(0); // ench effect
        writeC(0); // team circle
        writeD(0); // crest large id?
        writeC(0); // is noble
        writeC(0); // Is hero
        writeC(0); // is fishing
        writeD(0); // Fishing loc
        writeD(0);
        writeD(0);
        writeD(0x00ff00);
        writeD(0x00); // ?
        writeD(0); // pldge class
        writeD(0); // pledge type
        writeD(0x00ff00);
        writeD(0); // cursed weapon
    }
}
