package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.databinding.CustomDialogBackpressedBinding
import org.w3c.dom.Text
import java.lang.Exception
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding?=null
    private var countDownTimer:CountDownTimer?=null
    private var timePassed=0
    private var exerciseCountDownTimer:CountDownTimer?=null
    private var exerciseTimePassed=0
    private var exerciseList:ArrayList<ExerciseList>?=null
    private var currentExercisePosition=-1
    private var tts:TextToSpeech?=null
    private var player:MediaPlayer?=null
    private var adapter:ExerciseAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar!=null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarExercise?.setNavigationOnClickListener{
            customdialogbackpressed()
        }
        tts= TextToSpeech(this,this)
        exerciseList=Constants.getExerciseList()
        setUpRestView()
        setUpRecyclerView()

    }

    override fun onBackPressed() {
        customdialogbackpressed()
    }
    private fun setUpRecyclerView(){
        binding?.rv?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter= ExerciseAdapter(exerciseList!!)
        binding?.rv?.adapter=adapter
    }

    private fun setUpRestView(){

        try{
            var soundUri=Uri.parse("android.resource://com.example.a7minuteworkout/")
            player=MediaPlayer.create(applicationContext,soundUri)
            player?.isLooping=false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }


        binding?.frame?.visibility=View.VISIBLE
        binding?.tvStart?.visibility=View.VISIBLE
        binding?.upcomingExercise?.visibility=View.VISIBLE
        binding?.upcomingExerciseName?.visibility=View.VISIBLE
        binding?.exercisefl?.visibility=View.INVISIBLE
        binding?.exercisename?.visibility=View.INVISIBLE
        binding?.ivExercise?.visibility=View.INVISIBLE
        binding?.upcomingExerciseName?.text=exerciseList!![(currentExercisePosition+1)].name

        if(countDownTimer!=null){
            countDownTimer?.cancel()
            countDownTimer=null
            timePassed=0
        }
        startTimer()
    }
    private fun customdialogbackpressed(){
        val customDialog=Dialog(this)
        var dataBinding=CustomDialogBackpressedBinding.inflate(layoutInflater)
        customDialog.setContentView(dataBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dataBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dataBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }
    private fun setUpExerciseView(){
        binding?.frame?.visibility=View.INVISIBLE
        binding?.tvStart?.visibility=View.INVISIBLE
        binding?.exercisename?.visibility=View.VISIBLE
        binding?.ivExercise?.visibility=View.VISIBLE
        binding?.upcomingExercise?.visibility=View.INVISIBLE
        binding?.upcomingExerciseName?.visibility=View.INVISIBLE

        binding?.exercisefl?.visibility=View.VISIBLE
        if(exerciseCountDownTimer!=null){
            exerciseCountDownTimer?.cancel()
            //countDownTimer=null
            exerciseTimePassed=0
        }
        binding?.exercisename?.text=exerciseList!![currentExercisePosition].name
        binding?.ivExercise?.setImageResource(exerciseList!![currentExercisePosition].image)
        speakOut(exerciseList!![currentExercisePosition].name)

        startExerciseTimer()
    }


    private fun startTimer(){
        countDownTimer=object :CountDownTimer(5000-timePassed.toLong(),1000){
            override fun onTick(p0: Long) {
                timePassed++
                binding?.progressBar?.progress=10-timePassed
                binding?.tvTimer?.text=(10-timePassed).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].isSelected=true
                adapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }

        }.start()
    }
    private fun startExerciseTimer(){
        exerciseCountDownTimer=object :CountDownTimer(5000-exerciseTimePassed.toLong(),1000){
            override fun onTick(p0: Long) {
                exerciseTimePassed++
                binding?.exerciseprogressBar?.progress=30-exerciseTimePassed
                binding?.exerciseTvTimer?.text=(30-exerciseTimePassed).toString()
            }

            override fun onFinish() {

                if(currentExercisePosition < exerciseList!!.size-1) {
                    exerciseList!![currentExercisePosition].isSelected=false
                    exerciseList!![currentExercisePosition].isCompleted=true
                    adapter!!.notifyDataSetChanged()
                    setUpRestView()
                }
                else{
                    val intent= Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(countDownTimer!=null){
            countDownTimer?.cancel()
            //countDownTimer=null
            timePassed=0
        }
        if(exerciseCountDownTimer!=null){
           exerciseCountDownTimer?.cancel()
            //exerciseCountDownTimer=null
            exerciseTimePassed=0
        }
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        if(player!=null){
            player?.stop()
        }
        binding=null
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.UK)
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA)
                Log.e("TTS","not supported")
            else{
                Log.e("TTS","problem")

            }
        }
    }
}