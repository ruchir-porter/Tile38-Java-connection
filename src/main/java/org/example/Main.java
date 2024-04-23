package org.example;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.output.NestedMultiOutput;
import io.lettuce.core.output.StatusOutput;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.LettuceCharsets;
import io.lettuce.core.protocol.ProtocolKeyword;

import java.util.List;



enum Nearby implements ProtocolKeyword {
    NEARBY("NEARBY");

    private final byte[] name;

    Nearby(String commandName) {
        name = commandName.getBytes(LettuceCharsets.ASCII);
    }

    public byte[] getBytes() {
        return name;
    }
}

// enum Follow implements ProtocolKeyword {
//     FOLLOW("FOLLOW");

//     private final byte[] name;

//     Follow(String commandName) {
//         name = commandName.getBytes(LettuceCharsets.ASCII);
//     }

//     public byte[] getBytes() {
//         return name;
//     }
// }
/**
 * Example for using Tile38 in Java using lettuce version 5.0.1.Beta1
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Connecting to redis");
        // RedisClient client = RedisClient.create("redis://localhost:9851");
        RedisClient client = RedisClient.create("redis-sentinel://localhost:5000,localhost:5001,localhost:5002#mymaster");
        // RedisURI redisURI = RedisURI.Builder.sentinel("localhost", 5000, "mymaster")
        // .withSentinel("localhost", 5001).withSentinel("localhost",5002).build();
        // RedisClient client = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisCommands<String, String> sync = connection.sync();

        StringCodec codec = StringCodec.UTF8;

        //scan example
        CommandArgs<String, String> argScan = new CommandArgs<>(codec).add("fleet");
        List<Object> responseScan = sync.dispatch(CommandType.SCAN, new NestedMultiOutput<>(codec), argScan);
        System.out.println("scan: " + responseScan);

        //set example
        CommandArgs<String, String> argSet = new CommandArgs<>(codec).add("fleet").add("truck1").add("POINT").add("33.462").add("-112.268");
        String responseSet = sync.dispatch(CommandType.SET, new StatusOutput<>(codec), argSet);
        System.out.println("set: " + responseSet);

        //get example
        CommandArgs<String, String> argGet = new CommandArgs<>(codec).add("fleet").add("truck1");
        String responseGet = sync.dispatch(CommandType.GET, new StatusOutput<>(codec), argGet );
        System.out.println("get:" + responseGet);

        // //nearby example
        // CommandArgs<String, String> argNearby = new CommandArgs<>(codec).add("fleet").add("POINT").add("33.462").add("-112.268").add("6000");
        // List<Object> responseNearby = sync.dispatch(Nearby.NEARBY, new NestedMultiOutput<>(codec), argNearby);
        // System.out.println("nearby: "+responseNearby);

    }
}