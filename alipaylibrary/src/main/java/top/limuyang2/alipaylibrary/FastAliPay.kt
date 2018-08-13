package top.limuyang2.alipaylibrary

import android.support.v4.app.SupportActivity
import com.alipay.sdk.app.PayTask
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.limuyang2.basepaylibrary.PayResource


/**
 * @author limuyang
 * @date 2018/8/12
 * @class describe
 */
class FastAliPay(private val activity: SupportActivity) {

    fun pay(orderInfo: String, consumer: AliPayConsumer) {
        Observable.create<Map<String, String>> {
            // 构造PayTask 对象
            val aliPay = PayTask(activity)
            // 调用支付接口，获取支付结果
            it.onNext(aliPay.payV2(orderInfo, true))
        }.flatMap<PayResource> { map ->
            Observable.create<PayResource> {
                //支付结果处理
                val payResult = PayResult(map)

                val resultStatus = payResult.resultStatus
                // 判断resultStatus 为9000则代表支付成功
                when (resultStatus) {
                    "9000" -> {
                        it.onNext(PayResource.success())
                    }
                    "6001" -> {
                        it.onNext(PayResource.cancel())
                    }
                    "4000" -> {
                        it.onNext(PayResource.failed("订单支付失败"))
                    }
                    "5000" -> {
                        it.onNext(PayResource.failed("重复请求"))
                    }
                    "6002" -> {
                        it.onNext(PayResource.failed("网络连接出错"))
                    }
                    "6004" -> {
                        it.onNext(PayResource.failed("支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态"))
                    }
                    else -> {
                        it.onNext(PayResource.failed(payResult.memo))
                    }
                }
                it.onComplete()
            }
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)

    }


}