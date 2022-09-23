package com.example.a7minuteworkout

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding:ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val dao=(application as HistoryApp).db.historyDao()

        binding?.finishbtn?.setOnClickListener{
            finish()
        }
        addDataToHistoryDataBase(dao)

    }

    private fun addDataToHistoryDataBase(historyDao: HistoryDao){
        val c=Calendar.getInstance()
        val DateTime=c.time
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(DateTime)
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }
}