package top.limuyang2.unionpaylibrary

import android.content.Intent
import android.support.v4.app.SupportActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import top.limuyang2.basepaylibrary.PayResource


/**
 * @author limuyang
 * @date 2018/8/12
 * @class describe
 */
class FastUnionPay(private val activity: SupportActivity) {

    fun pay(payModel: UnionPayType, tn: String, consumer: UnionPayConsumer) {
        Observable.create<PayResource> {
            emitter = it
            val intent = Intent(activity, UnionPayAssistActivity::class.java)
            intent.putExtra(UnionPayAssistActivity.PAY_TYPE, payModel)
            intent.putExtra(UnionPayAssistActivity.TN, tn)
            activity.startActivity(intent)
        }.subscribe(consumer)
    }

    companion object {
//        var unionPayLiveData: MutableLiveData<PayResource>? = null
        var emitter: ObservableEmitter<PayResource>? = null
    }


}

enum class UnionPayType(val model: String) {
    RELEASE("00"),
    TEST("01");
}

