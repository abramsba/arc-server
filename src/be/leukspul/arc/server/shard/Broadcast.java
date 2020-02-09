package be.leukspul.arc.server.shard;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.data.ecs.Entity;

public class Broadcast {

    public static void ToAllClients(Shard shard, GameWritePacket packet) {
        for (GameClient gc : shard.clients()) {
            gc.send(packet);
        }
    }

    public static void ToNearbyClients(Shard shard, Entity entity, double radius, GameWritePacket packet) {
        for (GameClient gc : shard.clients(entity.position(), radius)) {
            gc.send(packet);
        }
    }

    public static void ToNearbyOtherClients(Shard shard, Entity entity, double radius, GameWritePacket packet) {
        for (GameClient gc : shard.clients(entity.position(), radius)) {
            if (gc.entity() != entity) {
                gc.send(packet);
            }
        }
    }

}
