package com.sammomichael.retroroomco

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sammomichael.retroroomco.adapters.UserRecyclerViewAdapter
import com.sammomichael.retroroomco.data.userdata.User
import com.sammomichael.retroroomco.database.AppDatabase
import com.sammomichael.retroroomco.network.JsonService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var compositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRecyclerView = findViewById(R.id.user_recyclerview)
        val service = JsonService().jsonService // retrofit service
        val db: AppDatabase = AppDatabase.invoke(this) // room database

        // RxJava 2.0 style observe data response
        compositeDisposable = CompositeDisposable()
        val rxUsers = service.rxJavaUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onData)
        compositeDisposable.add(rxUsers)

        // async coroutines for coroutine style suspended execution
        async(Dispatchers.Default) {
            val users = service.getUsers()
            onData(users)
            db.userDao().insertUsers(*users.toTypedArray())
            val insertedUsers = db.userDao().getUsers()
            onData(insertedUsers)
        }
    }

    private fun onData(users: List<User>) {
        async(Dispatchers.Main) {
            users.forEach {
                Log.d(TAG, it.name ?: "")
            }
            setRecyclerView(users)
            Log.d(TAG, (Looper.myLooper() == Looper.getMainLooper()).toString())
        }
    }

    private fun setRecyclerView(users: List<User>) {
        val manager = LinearLayoutManager(this)
        userRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = UserRecyclerViewAdapter(users)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }
}