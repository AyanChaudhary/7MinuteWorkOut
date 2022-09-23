package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding:ActivityHistoryBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.historyToolbar)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding?.historyToolbar?.title="HISTORY"
        }
        binding?.historyToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
        val dao=(application as HistoryApp).db.historyDao()
        setUpRecyclerView(dao)

    }

    private fun setUpRecyclerView(historyDao: HistoryDao){
        lifecycleScope.launch{
            historyDao.fetchAllDates().collect {allCompletedDatesList->
                if(allCompletedDatesList.isNotEmpty()){
                    var dates=ArrayList<String>()
                    for(date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val adapter=HistoryAdapter(dates)
                    binding?.historyRv?.adapter=adapter
                }

            }
        }
    }
}