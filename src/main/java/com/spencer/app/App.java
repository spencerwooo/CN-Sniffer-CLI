package com.spencer.app;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {

    private final static int maxPackets = 10;

    private static PcapNetworkInterface getNetworkDevice() {
        PcapNetworkInterface device = null;
        try {
            device = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return device;
    }

    public static void main(String[] args) throws PcapNativeException, NotOpenException {
        PcapNetworkInterface device = getNetworkDevice();

        int snapLen = 65536;
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeout = 1000;

        PcapHandle handle = device.openLive(snapLen, mode, timeout);

//        String filter = "tcp port 80 and tcp port 443";
        String filter = "";

        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);

        PacketListener packetListener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                System.out.println(packet);
            }
        };

        try {
            handle.loop(maxPackets, packetListener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handle.close();
    }
}
