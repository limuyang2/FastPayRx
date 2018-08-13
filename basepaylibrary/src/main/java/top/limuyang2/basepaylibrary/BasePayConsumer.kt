package top.limuyang2.basepaylibrary

import io.reactivex.functions.Consumer

/**
 * @author limuyang
 * @date 2018/8/12
 * @class describe
 */

interface BasePayConsumer : Consumer<PayResource> {

    fun onSuccess() {}

    fun onFailed(message: String) {}

    fun onCancel()

    fun onComplete()

    override fun accept(t: PayResource?) {
        t?.let {
            when (t.status) {
                PayStatus.SUCCESS -> {
                    onSuccess()
                    onComplete()
                }
                PayStatus.FAILED -> {
                    onFailed(t.message ?: "未知错误")
                    onComplete()
                }
                PayStatus.CANCEL -> onCancel()
            }
        }
    }
}
