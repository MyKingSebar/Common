package com.hanshow.test;

import android.os.Environment;

public class TestConfig {
    public static final boolean OPENTEST = true;
    public static final String CONFIGPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/takeConfig.txt";
    public static final String TESTACTION = "com.hanshow.TRIGGER_LOCAL_TAKE";
    public static final String TESTDATA = "com.hanshow.LOCAL_TAKE.id";
    public static final String DEFAULTTEST = "{\n" +
            "    \"takeState\":true,\n" +
            "    \"priority\":1,\n" +
            "    \"eventList\":[\n" +
            "{\"eventId\":1,\"describe\":\"1\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":2,\"describe\":\"2\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":3,\"describe\":\"3\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":4,\"describe\":\"4\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":5,\"describe\":\"5\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":6,\"describe\":\"6\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":7,\"describe\":\"7\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":8,\"describe\":\"8\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":9,\"describe\":\"9\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":10,\"describe\":\"10\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":11,\"describe\":\"11\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":12,\"describe\":\"12\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4\",\"eventPriority\":\"1\" , \"duration\":10000,\"type\":2 },\n" +
            "{\"eventId\":13,\"describe\":\"13\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4\",\"eventPriority\":\"1\" , \"duration\":5000,\"type\":2 },\n" +
            "{\"eventId\":14,\"describe\":\"14\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4\",\"eventPriority\":\"1\" , \"duration\":5000,\"type\":2 },\n" +
            "{\"eventId\":15,\"describe\":\"15\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4\",\"eventPriority\":\"1\" , \"duration\":5000,\"type\":2 },\n" +
            "{\"eventId\":16,\"describe\":\"16\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4\",\"eventPriority\":\"1\" , \"duration\":5000,\"type\":2 },\n" +
            "{\"eventId\":17,\"describe\":\"男\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4GAf0vRAANhnNX8gPA187.jpg\",\"eventPriority\":\"2\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":18,\"describe\":\"女\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4GALmoTAAaBoSAR1ig257.png\",\"eventPriority\":\"3\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":19,\"describe\":\"0~10\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAUDNJAAWP_DK_c6o274.png\",\"eventPriority\":\"4\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":20,\"describe\":\"11~20\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAUDNJAAWP_DK_c6o274.png\",\"eventPriority\":\"5\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":21,\"describe\":\"21~30\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAUDNJAAWP_DK_c6o274.png\",\"eventPriority\":\"5\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":22,\"describe\":\"31~40\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAdPukAASF8X2v1Fw875.jpg\",\"eventPriority\":\"6\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":23,\"describe\":\"41~50\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAdPukAASF8X2v1Fw875.jpg\",\"eventPriority\":\"6\" , \"duration\":5000,\"type\":1 },\n" +
            "{\"eventId\":24,\"describe\":\">50\",\"url\":\"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAHLyvAAWJ3ew3t9I699.png\",\"eventPriority\":\"7\" , \"duration\":5000,\"type\":1 }\n" +
            "    ]\n" +
            "    }";
}
