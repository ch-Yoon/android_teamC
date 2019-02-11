package kr.co.connect.boostcamp.livewhere.ui.detail

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailReviewRvAdapter
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import kr.co.connect.boostcamp.livewhere.util.BarChartUtil
import kr.co.connect.boostcamp.livewhere.util.EMPTY_REVIEW_TEXT
import kr.co.connect.boostcamp.livewhere.util.EMPTY_BARCHART_TEXT


@BindingAdapter("setBarChart")
fun setBarChart(barChart: BarChart, list: LiveData<ArrayList<HouseAvgPrice>>) {
    try {
        BarChartUtil.setChartData(barChart, list.value!!)
        BarChartUtil.showChart(barChart)
        barChart.notifyDataSetChanged()
    } catch (e: KotlinNullPointerException) {
        barChart.setNoDataText(EMPTY_BARCHART_TEXT)
    }
}

@BindingAdapter("setDetailImage") //로드뷰 이미지 세팅
fun setDetailImage(imageView: AppCompatImageView, location: LiveData<String>?) {
    try {
        Glide.with(imageView.context)
            .load("https://maps.googleapis.com/maps/api/streetview?size=360x200&location=${location!!.value}&key=${BuildConfig.GoogleApiKey}")
            .into(imageView)
    } catch (e: KotlinNullPointerException) {
        Toast.makeText(imageView.context, "이미지 없음", Toast.LENGTH_SHORT).show()
    }
}

@BindingAdapter("setRecentPrice")
fun setRecentPrice(textView: TextView, recentPrice: LiveData<RecentPrice>) {
    try {
        when (textView.id) {
            R.id.detail_fragment_tv_charter -> {
                textView.text =
                    textView.context.getString(R.string.recent_charter_price, recentPrice.value!!.charterPrice)
            }
            R.id.detail_fragment_tv_monthly_rent -> {
                textView.text =
                    textView.context.getString(R.string.recent_monthly_price, recentPrice.value!!.monthlyPrice)
            }
        }
    } catch (e: KotlinNullPointerException) {
        when (textView.id) {
            R.id.detail_fragment_tv_charter -> {
                textView.text = "전세 정보 없음"
            }
            R.id.detail_fragment_tv_monthly_rent -> {
                textView.text = "월세 정보 없음"
            }
        }
    }
}

@BindingAdapter("setRvItems") //과거 거래내역 rv
fun setRvItems(recyclerView: RecyclerView, itemList: List<PastTransaction>?) {
    if (itemList != null) {
        (recyclerView.adapter as DetailTransactionRvAdapter).setData(itemList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("setReviews")
fun setReviews(recyclerView: RecyclerView, reviewList: List<Review>?) {
    if (reviewList != null) {
        (recyclerView.adapter as DetailReviewRvAdapter).setData(reviewList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("setPreReview")
fun setPreReview(textView: TextView, review: List<Review>?) {
    try {
        when (textView.id) {
            R.id.detail_fragment_tv_review_id -> textView.text = review!![0].id
            R.id.detail_fragment_tv_review_nickname -> textView.text = review!![0].nickname
            R.id.detail_fragment_tv_review_contents -> textView.text = review!![0].contents
        }
    } catch (e: KotlinNullPointerException) {
        when (textView.id) {
            R.id.detail_fragment_tv_review_contents -> textView.text = EMPTY_REVIEW_TEXT
        }
    }
}


@BindingAdapter("setVmText")
fun setText( view:TextView, text:CharSequence? ) {
//    view.text = text
}

@InverseBindingAdapter(attribute = "setVmText", event = "android:textAttrChanged")
fun getTextString(textView: TextView): String {
    return textView.text.toString()
}

@BindingAdapter("android:textAttrChanged")
fun setTextWatcher(view: TextView, textAttrChanged: InverseBindingListener) {
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textAttrChanged.onChange()
        }

    })
}

@BindingAdapter("setBuildingTitle")
fun setBuildingTitle(view:TextView,addr:Address){

}