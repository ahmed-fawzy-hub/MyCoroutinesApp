package com.example.mycoroutinesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.mycoroutinesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
//    val customScope= CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//       val jobParent= lifecycleScope.launch {
//           val deferred1 = async {  repeatLogs1() }
//           val deferred2 = async {  repeatLogs2() }
//
//
//           Log.d(TAG, deferred1.await())
//           Log.d(TAG, deferred2.await())
//           Log.d(TAG, "onCreate: ")
//       }
       // Log.d(TAG, "after corountines")
//        runBlocking {
//            fakeApiRequest()
//        }
    playWithCoroutines()
    binding.btnCancel.setOnClickListener {
        job4.cancel()
    }
    playWithFlow()
    }

    lateinit var job1: Job
    lateinit var job2: Job
    lateinit var job3: Job
    lateinit var job4: Job
    lateinit var job5: Job

    private fun playWithCoroutines(){
        val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(TAG, throwable.message.toString())
        }
        job1 = lifecycleScope.launch(exceptionHandler) {
            delay(2000)
            job2 = launch {
                delay(2000)
                Log.d(TAG, "I am job2")
                job4 = launch {
                    delay(2000)
                    Log.d(TAG, "I am job4")
                }
                job5 = launch {
                    delay(2000)
                    Log.d(TAG, "I am job5")
                }
            }
            job3 = launch {
                delay(2000)
                val result = 5 / 0
                Log.d(TAG, "I am job3")
            }
            Log.d(TAG, "I am job1")
        }
    }
    suspend fun repeatLogs1():String {
        delay(5000)
            return "response 1 "


//        delay(2500)
//        withContext(Dispatchers.Main){
//            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
//            finish()
  //  }

        }
    suspend fun repeatLogs2():String {
        delay(3000)

        return "response 2"

    }

//    suspend fun fakeApiRequest(){
//        delay(2500)
//        Log.d(TAG, "fakeApiRequest: ")
//        Log.d(TAG, Thread.currentThread().name)
//        withContext(Dispatchers.Main){
//            binding.txtView.text="fake request"
//            Log.d(TAG, Thread.currentThread().name)
//        }
//    }

    private fun playWithFlow(){

        val mFlow= flow<Int> {
            for (i in 1 .. 8){
                emit(i)
                delay(2000)
            }
        }


        lifecycleScope.launch {
            mFlow.buffer().collect{
                delay(2000)
                Log.d(TAG, "collect($it)")
            }
        }
    }

    companion object{
        const val TAG="Tamer"
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        customScope.cancel()
//    }
}