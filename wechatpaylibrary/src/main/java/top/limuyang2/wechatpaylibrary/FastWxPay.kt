package top.limuyang2.wechatpaylibrary

import android.support.v4.app.SupportActivity
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import top.limuyang2.basepaylibrary.PayResource


/**
 * @author limuyang
 * @date 2018/8/12
 * @class describe
 */
class FastWxPay(appId: String, private val activity: SupportActivity) {

    init {
        mWXApi = WXAPIFactory.createWXAPI(activity.applicationContext, null)
        // 将该app注册到微信
        mWXApi?.registerApp(appId)
    }

    fun pay(payReq: PayReq, consumer: WxPayConsumer) {
        Observable.create<PayResource> {
            if (mWXApi == null) {
                it.onNext(PayResource.failed("WXAPI NULL"))
                return@create
            } else {
                if (!mWXApi!!.isWXAppInstalled) {
                    it.onNext(PayResource.failed("微信App 未安装"))
                    return@create
                }
                if (mWXApi!!.wxAppSupportAPI < Build.PAY_SUPPORTED_SDK_INT) {
                    it.onNext(PayResource.failed("当前微信版本不支持付款"))
                    return@create
                }
            }
            emitter = it
            mWXApi?.sendReq(payReq)
        }.subscribe(consumer)

    }

    companion object {
        var emitter: ObservableEmitter<PayResource>? = null

        var mWXApi: IWXAPI? = null
    }

}