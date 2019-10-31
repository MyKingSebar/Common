package com.hanshow.test;

import java.util.List;

public class LocalTestBean {

    /**
     * takeState : true
     * priority : 1
     * eventList : [{"eventId":1,"describe":"1","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":2,"describe":"2","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":3,"describe":"3","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":4,"describe":"4","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":5,"describe":"5","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":6,"describe":"6","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":7,"describe":"7","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAbUNhAJxTKRbZG7Y972.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":8,"describe":"8","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":9,"describe":"9","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":10,"describe":"10","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":11,"describe":"11","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGmAHu5PAIpxPHrUj9g983.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":12,"describe":"12","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4","eventPriority":"1","duration":10000,"type":2},{"eventId":13,"describe":"13","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4","eventPriority":"1","duration":5000,"type":2},{"eventId":14,"describe":"14","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4","eventPriority":"1","duration":5000,"type":2},{"eventId":15,"describe":"15","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4","eventPriority":"1","duration":5000,"type":2},{"eventId":16,"describe":"16","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gHG6AHXNXAHnGF94sT-g127.mp4","eventPriority":"1","duration":5000,"type":2},{"eventId":17,"describe":"男","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4GAf0vRAANhnNX8gPA187.jpg","eventPriority":"2","duration":5000,"type":1},{"eventId":18,"describe":"女","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4GALmoTAAaBoSAR1ig257.png","eventPriority":"3","duration":5000,"type":1},{"eventId":19,"describe":"0~10","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAUDNJAAWP_DK_c6o274.png","eventPriority":"4","duration":5000,"type":1},{"eventId":20,"describe":"11~20","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAUDNJAAWP_DK_c6o274.png","eventPriority":"5","duration":5000,"type":1},{"eventId":21,"describe":"21~30","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAUDNJAAWP_DK_c6o274.png","eventPriority":"5","duration":5000,"type":1},{"eventId":22,"describe":"31~40","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAdPukAASF8X2v1Fw875.jpg","eventPriority":"6","duration":5000,"type":1},{"eventId":23,"describe":"41~50","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAdPukAASF8X2v1Fw875.jpg","eventPriority":"6","duration":5000,"type":1},{"eventId":24,"describe":">50","url":"http://192.168.2.53/group1/M00/00/61/wKgCNV2gG4KAHLyvAAWJ3ew3t9I699.png","eventPriority":"7","duration":5000,"type":1}]
     */

    private boolean takeState;
    private int priority;
    private List<EventListBean> eventList;

    public boolean isTakeState() {
        return takeState;
    }

    public void setTakeState(boolean takeState) {
        this.takeState = takeState;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<EventListBean> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventListBean> eventList) {
        this.eventList = eventList;
    }

    public static class EventListBean {
        /**
         * eventId : 1
         * describe : 1
         * url : http://192.168.2.53/group1/M00/00/61/wKgCNV2gHGSAPCF4AIx75GFhCRw971.mp4
         * eventPriority : 1
         * duration : 10000
         * type : 2
         */

        private long eventId;
        private String describe;
        private String url;
        private String eventPriority;
        private int duration;
        private int type;

        public long getEventId() {
            return eventId;
        }

        public void setEventId(long eventId) {
            this.eventId = eventId;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEventPriority() {
            return eventPriority;
        }

        public void setEventPriority(String eventPriority) {
            this.eventPriority = eventPriority;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }


}
