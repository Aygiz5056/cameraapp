package com.example.rxkotlin3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxkotlin3.data.MovieAdapter
import com.example.rxkotlin3.data.MovieItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieList = listOf<MovieItem>(MovieItem
            (R.drawable.movie1, "Зависнуть в Палм-Спрингс", "комедия", 2020),
            MovieItem(R.drawable.movie2, "Суд над чикагской семеркой", "триллер", 2020),
        MovieItem(R.drawable.movie3, "Довод", "фантастика", 2020),
            MovieItem(R.drawable.movie4, "Маленькие женщины", "драма", 2019),
            MovieItem(R.drawable.movie5, "Джентльмены", "криминал", 2020)
        )
        recyclerView.adapter = MovieAdapter(movieList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }
}
//        Observable.interval (1000, TimeUnit.MILLISECONDS)
//
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {  it + 4 }
//            .map {  it - 10 }
//
//            .subscribe(object : Observer<Long> {
//                    override fun onComplete() {
//
//                    }
//
//                    override fun onSubscribe(d: Disposable?) {
//
//                    }
//
//                    override fun onNext(t: Long?) {
//                        var number = t
//                        if (number != null) {
//                            ourButton.text = "${number - 10}"
//                        }
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.i("ObservError", Log.getStackTraceString(e))
//                    }
//                })
//        Thread.sleep(1000)
//        ourButton.setOnClickListener{
//            Log.i("Observ", "Main")
//        }
//    }
//}