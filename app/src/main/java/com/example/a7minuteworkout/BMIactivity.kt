package com.example.a7minuteworkout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkout.databinding.BmiActivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIactivity : AppCompatActivity() {
    private var binding:BmiActivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= BmiActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.bmitoolbar)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding?.bmitoolbar?.title="BMI Calculator"
        }
        binding?.bmitoolbar?.setNavigationOnClickListener{

            onBackPressed()
        }
        binding?.btncalculate?.setOnClickListener{
            if(validatedata()){
                if(binding?.metricRb?.isChecked==true){
                    val bmiheight=binding?.edheight?.text.toString().toFloat()/100
                    val bmiweight=binding?.edweight?.text.toString().toFloat()
                    val bmi:Float=bmiweight/(bmiheight*bmiheight)

                    calculatebmimetric(bmi)
                }
                else{
                    val bmiusheight=(binding?.edUSHeightFeet?.text.toString().toFloat()*12)+
                            binding?.edUSheightInch?.text.toString().toFloat()
                    val bmiusweight=binding?.edusweight?.text.toString().toFloat()
                    val bmiforus=(bmiusweight/(bmiusheight*bmiusheight))*703

                    calculatebmius(bmiforus)
                }
            }
            else{
                Toast.makeText(this,"please enter your height and weight ",Toast.LENGTH_SHORT).show()
            }
        }
        binding?.metricRb?.setOnClickListener{
            checkVisiblity()
        }
        binding?.usRb?.setOnClickListener{
            checkVisiblity()
        }
    }

    private fun checkVisiblity(){
        if(binding?.metricRb?.isChecked==true){
            binding?.tipHeight?.visibility=View.VISIBLE
            binding?.tipMetricWeight?.visibility=View.VISIBLE
            binding?.tipUSWeight?.visibility=View.GONE
            binding?.llForUsunits?.visibility=View.GONE
            binding?.llbmi?.visibility=View.INVISIBLE
            binding?.edusweight?.text?.clear()
            binding?.edUSHeightFeet?.text?.clear()
            binding?.edUSheightInch?.text?.clear()
        }else{
            binding?.tipHeight?.visibility=View.GONE
            binding?.tipMetricWeight?.visibility=View.GONE
            binding?.tipUSWeight?.visibility=View.VISIBLE
            binding?.llForUsunits?.visibility=View.VISIBLE
            binding?.llbmi?.visibility=View.INVISIBLE
            binding?.edweight?.text?.clear()
            binding?.edheight?.text?.clear()

        }
    }
    private fun calculatebmimetric(bmi:Float){

        binding?.llbmi?.visibility= View.VISIBLE
        var bmitype:String="normal"
        var bmides:String="normal"

        if(bmi.compareTo(18.5f)<0){
            bmitype="Underweight"
            bmides="please eat a lot "
        }
        else if(bmi.compareTo(18.5f)>=0 && bmi.compareTo(25f)<0){
            bmitype="Normal"
            bmides="You are perfect.Enjoy your life"
        }else if(bmi.compareTo(25f)>=0 && bmi.compareTo(30)<0){
            bmitype="overwieght class |"
            bmides="kam kha mote!!!"
        }else if(bmi.compareTo(30)>=0){
            bmitype="overweight class ||"
            bmides="hippopotomus"
        }
        binding?.tvBmiType?.text=bmitype
        binding?.tvDescription?.text=bmides
        binding?.tvBmivalue?.text=bmi.toString()
    }
    private fun validatedata():Boolean{
        var answer=true
        if(binding?.metricRb?.isChecked==true){
            if(binding?.edheight?.text.toString().isEmpty()){
                answer =false
            }else if(binding?.edweight?.text.toString().isEmpty()){
                answer=false
            }
        }else{
            if(binding?.edUSHeightFeet?.text.toString().isEmpty())
                answer=false
            else if(binding?.edUSheightInch?.text.toString().isEmpty())
                answer=false
            else if(binding?.edusweight?.text.toString().isEmpty())
                answer=false
        }
        return answer
    }
    private fun calculatebmius(bmi:Float){
        binding?.llbmi?.visibility=View.VISIBLE
        binding?.tvBmivalue?.text=bmi.toString()
        var bmitype:String?=null
        var bmides:String?=null


        if(bmi.compareTo(18.5f)<0){
            bmitype="Underweight"
            bmides="please eat a lot "
        }
        else if(bmi.compareTo(18.5f)>=0 && bmi.compareTo(25f)<0){
            bmitype="Normal"
            bmides="You are perfect.Enjoy your life"
        }else if(bmi.compareTo(25f)>=0 && bmi.compareTo(30)<0){
            bmitype="overwieght class |"
            bmides="kam kha mote!!!"
        }else if(bmi.compareTo(30)>=0){
            bmitype="overweight class ||"
            bmides="hippopotomus"
        }
        binding?.tvBmiType?.text=bmitype
        binding?.tvDescription?.text=bmides
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}