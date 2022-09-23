package com.example.a7minuteworkout

object Constants {

    fun getExerciseList():ArrayList<ExerciseList>{
        val exerciseList=ArrayList<ExerciseList>()

        val exe1=ExerciseList(1,"PUSHUPS", R.drawable.pushups,false,false)
        exerciseList.add(exe1)
        val exe2=ExerciseList(2,"PULLUPS",R.drawable.pullups,false,false)
        exerciseList.add(exe2)
        val exe3=ExerciseList(3,"SITUPS",R.drawable.situps,false,false)
        exerciseList.add(exe3)
        val exe4=ExerciseList(4,"SKIPPING",R.drawable.skipping,false,false)
        exerciseList.add(exe4)
        val exe5=ExerciseList(5,"STRETCHING",R.drawable.stretching,false,false)
        exerciseList.add(exe5)
        val exe6=ExerciseList(6,"SUPERMAN",R.drawable.superman,false,false)
        exerciseList.add(exe6)


        return exerciseList
    }
}