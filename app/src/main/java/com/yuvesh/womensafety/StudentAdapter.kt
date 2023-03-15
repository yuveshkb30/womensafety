package com.yuvesh.womensafety

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter:RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList:ArrayList<StudentModel> = ArrayList()
    private var onClickItem:((StudentModel) ->Unit)? = null
    private var onClickDeleteItem:((StudentModel) ->Unit)? = null
    fun additems(items:ArrayList<StudentModel>)
    {
               this.stdList=items
               notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (StudentModel)->Unit)
    {
        this.onClickItem=callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel)->Unit)
    {
        this.onClickDeleteItem=callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

           val std=stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(std)
        }

        holder.delete.setOnClickListener {
            onClickDeleteItem?.invoke(std)
        }

    }

    override fun getItemCount(): Int {
         return stdList.size
    }

    class StudentViewHolder(var view: View):RecyclerView.ViewHolder(view)
    {
        private var id=view.findViewById<TextView>(R.id.txtid)
        private var phone=view.findViewById<TextView>(R.id.txtphone)
         var delete=view.findViewById<Button>(R.id.btndelete)

        fun bindView(std:StudentModel)
        {
            id.text=std.id.toString()
            phone.text=std.id.toString()
        }
    }


}