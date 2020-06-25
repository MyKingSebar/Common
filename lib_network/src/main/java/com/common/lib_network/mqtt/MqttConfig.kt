package com.common.lib_network.mqtt


class MqttConfig {

    private var baseUrl = "tcp://139.219.8.60:1883"
    private var userName = "admin"
    private var password = "admin"
    private var clientId = "MqttAndroidClient"
    private var mUpTopic = ""
    private var mTopic: Array<String> = arrayOf()
    private var mQos = intArrayOf(MqttManager.QOS_ONLYONE, MqttManager.QOS_ONLYONE, MqttManager.QOS_ONLYONE)
    private var timeout = 30
    private var heartbeat = 30
    private var will: ByteArray? = null

    // 清除缓存 保持长久连接
    private var cleanSession = true

    //自动重连
    private var automaticReconnect = false


    fun create(): MqttConfig {
        return this
    }

    fun setBaseUrl(baseUrl: String): MqttConfig {
        this.baseUrl = baseUrl
        return this
    }

    fun setUserName(userName: String): MqttConfig {
        this.userName = userName
        return this
    }

    fun setPassword(password: String): MqttConfig {
        this.password = password
        return this
    }

    fun setClientId(clientId: String): MqttConfig {
        this.clientId = clientId
        return this
    }

    fun setUpTopic(mUpTopic: String): MqttConfig {
        this.mUpTopic = mUpTopic
        return this
    }

    fun setTopic(mTopic: Array<String>): MqttConfig {
        this.mTopic = mTopic
        return this
    }

    fun getBaseUrl(): String {
        return this.baseUrl
    }

    fun getUserName(): String {
        return this.userName
    }

    fun getPassword(): String {
        return password
    }

    fun getClientId(): String {
        return clientId
    }

    fun getUpTopic(): String {
        return mUpTopic
    }

    fun getTopic(): Array<String> {
        return mTopic
    }

    fun setQos(mQos: IntArray): MqttConfig {
        this.mQos = mQos
        return this
    }

    fun getQos(): IntArray {
        return mQos
    }

    fun setTimeout(timeout: Int): MqttConfig {
        this.timeout = timeout
        return this
    }

    fun getTimeout(): Int {
        return timeout
    }

    fun setHeartbeat(heartbeat: Int): MqttConfig {
        this.heartbeat = heartbeat
        return this
    }

    fun getHeartbeat(): Int {
        return heartbeat
    }

    fun setWill(will: ByteArray): MqttConfig {
        this.will = will
        return this
    }

    fun getWill(): ByteArray? {
        return will
    }

    fun setCleanSession(cleanSession: Boolean): MqttConfig {
        this.cleanSession = cleanSession
        return this
    }

    fun getCleanSession(): Boolean {
        return cleanSession
    }

    fun setAutomaticReconnect(automaticReconnect: Boolean): MqttConfig {
        this.automaticReconnect = automaticReconnect
        return this
    }

    fun getAutomaticReconnect(): Boolean {
        return automaticReconnect
    }

    fun setDebug(isDebug: Boolean) {
//        MqttLoger.setDebug(isDebug)
    }
}