package com.example.matomo_tracker.traffic.sdk.dispatcher;


public interface PacketSender {
    /**
     * @return true if successful
     */
    boolean send(Packet packet);

    /**
     * @param timeout in milliseconds
     */
    void setTimeout(long timeout);

    void setGzipData(boolean gzip);
}
