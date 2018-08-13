package top.limuyang2.wechatpaylibrary

import top.limuyang2.basepaylibrary.BasePayConsumer
import top.limuyang2.basepaylibrary.PayResource
import top.limuyang2.wechatpaylibrary.FastWxPay.Companion.mWXApi

/**
 * @author limuyang
 * @date 2018/8/12
 * @class describe
 */
interface WxPayConsumer : BasePayConsumer {

    override fun accept(t: PayResource?) {
        super.accept(t)
        mWXApi = null
        FastWxPay.emitter = null
    }
}

abstract class JavaWxPayConsumer : WxPayConsumer