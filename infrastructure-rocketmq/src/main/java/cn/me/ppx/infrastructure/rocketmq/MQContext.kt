package cn.me.ppx.infrastructure.rocketmq

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer
import org.apache.rocketmq.client.consumer.MessageQueueListener
import org.apache.rocketmq.client.producer.DefaultMQProducer
import org.apache.rocketmq.common.message.MessageQueue
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel
import org.apache.rocketmq.remoting.RPCHook
import org.apache.rocketmq.remoting.protocol.RemotingCommand

/**
 * @author  ym
 * @date  2022/11/18 10:57
 *
 */
class MQContext {
    /**
     * 主动拉取消费模式
     * 优点：获得消息状态方便、负载均衡性能可控
     * 缺点：及时性差
     * LitePullConsumer  -> DefaultLitePullConsumer: 替代 DefaultMQPullConsumer
     * MQPullConsumer -> DefaultMQPullConsumer:4.6.0已经标记为过期，api太底层
     */
    fun newLitePullConsumer(topic: String) {
        val consumer = DefaultLitePullConsumer("name_space", "group", object : RPCHook {
            override fun doBeforeRequest(remoteAddr: String?, request: RemotingCommand?) {
                TODO("Not yet implemented")
            }

            override fun doAfterResponse(remoteAddr: String?, request: RemotingCommand?, response: RemotingCommand?) {
                TODO("Not yet implemented")
            }

        })

    }

    fun newPullConsumer(topic: String) {
        val consumer = DefaultMQPullConsumer("name_space", "group", object : RPCHook {
            override fun doBeforeRequest(remoteAddr: String?, request: RemotingCommand?) {
                TODO("Not yet implemented")
            }

            override fun doAfterResponse(remoteAddr: String?, request: RemotingCommand?, response: RemotingCommand?) {
                TODO("Not yet implemented")
            }

        }).also {
            it.registerTopics
            it.messageQueueListener = MessageQueueListener { topic, mqAll, mqDivided ->
                run {
                    println(topic)
                }
            }
            // 广播模式
            it.messageModel = MessageModel.BROADCASTING
            it.namesrvAddr = ""
        }
    }

    /**
     * 自动推送消费模式
     * 优点：及时性、服务端统一处理实现方便
     * 缺点：容易造成堆积、负载性能不可控
     */
    fun newPushConsumer(topic: String) {

    }

    fun newProducer(topic: String) {
        val producer = DefaultMQProducer("name_space", "group", object : RPCHook {
            override fun doBeforeRequest(remoteAddr: String?, request: RemotingCommand?) {
                TODO("Not yet implemented")
            }

            override fun doAfterResponse(remoteAddr: String?, request: RemotingCommand?, response: RemotingCommand?) {
                TODO("Not yet implemented")
            }

        })
        // producer 和 consumer 通过name_server联系
        producer.namesrvAddr = ""
        producer.start()
//        producer.send()
    }

}