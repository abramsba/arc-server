package be.leukspul.arc.server.config;

import org.json.JSONObject;

public class ConfigMmo {

    public ConfigMmo(JSONObject json) {
        MaxReadPerPass = json.getInt("maxReadPerPass");
        MaxSendPerPass = json.getInt("maxSendPerPass");
        SleepTime = json.getInt("sleepTime");
        BufferCount = json.getInt("bufferCount");
        ThreadsPerPool = json.getInt("threadsPerPool");
        QueueSize = json.getInt("queueSize");
    }

    public final int MaxReadPerPass;
    public final int MaxSendPerPass;
    public final int SleepTime;
    public final int BufferCount;
    public final int ThreadsPerPool;
    public final int QueueSize;


}
