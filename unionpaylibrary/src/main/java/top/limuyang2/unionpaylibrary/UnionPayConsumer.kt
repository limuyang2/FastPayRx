package top.limuyang2.unionpaylibrary

import top.limuyang2.basepaylibrary.BasePayConsumer
import top.limuyang2.basepaylibrary.PayResource

/**
 * @author limuyang
 * @date 2018/8/12
 * @class describe
 */
interface UnionPayConsumer : BasePayConsumer {

    override fun accept(t: PayResource?) {
        super.accept(t)
        FastUnionPay.emitter = null
    }
}

abstract class JavaUnionPayConsumer : UnionPayConsumer