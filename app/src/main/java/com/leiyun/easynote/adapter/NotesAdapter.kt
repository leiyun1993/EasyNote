package com.leiyun.easynote.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.leiyun.easynote.App
import com.leiyun.easynote.R
import com.leiyun.easynote.model.Note
import com.leiyun.easynote.utils.DateUtil

/**
 * 类名：NotesAdapter
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-08-03 14:59
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var dataset: List<Note> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    fun setData(datas: List<Note>) {
        dataset = datas
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.itemView.tag = position
        holder.bindData(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_note, parent, false)
        itemView.setOnClickListener { v ->
            val note: Note = dataset[v.tag as Int]
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(note)
            }
        }
        itemView.setOnLongClickListener { v ->
            val note: Note = dataset[v!!.tag as Int]
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemLongClick(note)
            }
            true
        }
        return ViewHolder(itemView)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contentTv: TextView = itemView.findViewById(R.id.contentTv)
        var dateTv: TextView = itemView.findViewById(R.id.dateTv)
        var typeIcon: ImageView = itemView.findViewById(R.id.typeIcon)
        fun bindData(note: Note) {
            contentTv.text = note.content
            dateTv.text = DateUtil.getDateFormat("M月d日").format(note.date)
            val typeArr = itemView.context.resources.getStringArray(R.array.arr_note_type)
            if ("未标签" == note.type) {
                typeIcon.visibility = View.INVISIBLE
            } else {
                typeIcon.visibility = View.VISIBLE
                typeArr.indices
                        .filter { note.type == typeArr[it] }
                        .forEach { typeIcon.setColorFilter(itemView.context.resources.getColor(App.instance.colorArr[it])) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
        fun onItemLongClick(note: Note)
    }

}