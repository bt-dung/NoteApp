package com.ezdev.noteapp

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.newFixedThreadPoolContext
import androidx.core.view.WindowInsetsAnimationCompat.Callback as Callback

//  adapter
class NoteAdapter(
    private var list: List<Note>
) : RecyclerView.Adapter<NoteAdapter.NoteHolder>()  {
    //  view holder
    private lateinit var onClick: MyCallBack
    private lateinit var onLongClick: MyCallBack

    interface MyCallBack {
        fun onClick(position: Int, item: Note)
    }

    fun setLongOnClickListener(listener: MyCallBack) {
        onLongClick = listener
    }

    fun setOnClickListener(listener: MyCallBack){
        onClick = listener
    }


    class NoteHolder(view: View) : ViewHolder(view), View.OnClickListener {
        val tv_title: TextView
        var checkbox: CheckBox
        lateinit var myItemClickListener: ItemClickListener
        init {
            tv_title = view.findViewById(R.id.tv_title)
            checkbox = view.findViewById(R.id.checkbox)
            checkbox.setOnClickListener(this)

        }
        interface ItemClickListener{
            fun onItemClick(v: View)
        }
        fun setItemClickListener(itemClick : ItemClickListener){
            myItemClickListener = itemClick
        }

        override fun onClick(v: View) {
            myItemClickListener.onItemClick(v)
        }

    }

    fun refreshData(newData: List<Note>) {
        list = newData
        shouldShowCheckbox = false
        notifyDataSetChanged()
    }

     var shouldShowCheckbox = false

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NoteHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_view, viewGroup, false)
        return NoteHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: NoteHolder, position: Int) {
        val ghiChu = list.get(position)
        viewHolder.tv_title.text = ghiChu.title.toString()
        viewHolder.checkbox.isChecked = ghiChu.check

        viewHolder.checkbox.visibility = if(shouldShowCheckbox) View.VISIBLE else View.GONE

        viewHolder.itemView.setOnLongClickListener {
            onLongClick.onClick(position,ghiChu)
            shouldShowCheckbox = true
            notifyDataSetChanged()
            true
        }
        viewHolder.itemView.setOnClickListener{
            onClick.onClick(position,ghiChu)
            true
        }
        viewHolder.setItemClickListener(object : NoteHolder.ItemClickListener{
            override fun onItemClick(v: View) {
                val checkBox = (v as CheckBox).isChecked
                if(checkBox){
                    ghiChu.check = true
                }else if(!checkBox){
                    ghiChu.check = false
                }
                notifyDataSetChanged()
                Log.e("idCheck", ghiChu.id.toString())
                Log.e("selectedList",ghiChu.check.toString())
            }
        })


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size


}



