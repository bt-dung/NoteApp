package com.ezdev.noteapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


import android.view.ActionMode
import android.view.Gravity

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.createBitmap
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezdev.noteapp.R.id.ImgBtn
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener

class MainActivity : AppCompatActivity(){

    private lateinit var ImgButton: ImageButton
    private lateinit var dl: DrawerLayout
     private lateinit var nav: NavigationView
    private lateinit var rev: RecyclerView
    private lateinit var dao: DAO
    private var mActionMode: ActionMode? = null
    private lateinit var adapter: NoteAdapter
    private  var searchView: SearchView? = null
    private lateinit var notes: List<Note>


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rev = findViewById<RecyclerView>(R.id.rev)
        searchView = findViewById(R.id.searchView)
        nav = findViewById(R.id.nav_view)
        ImgButton = findViewById(R.id.ImgBtn)
        dl = findViewById(R.id.drawerLayout)


        ImgButton.setOnClickListener{
            dl.openDrawer(GravityCompat.START)
        }
        nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_add -> {Toast.makeText(this, "Tạo một ghi chú mới", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@MainActivity,AddActivity::class.java)
                startActivity(intent)
                true}
                R.id.nav_home -> {false}
                else -> true
            }

        }
        //TODO  #recyclerview
        //  _dataset
        dao = DAO(this)
        notes = dao.getData()

        //  _adapter
        adapter = NoteAdapter(notes)
        adapter.setLongOnClickListener(object :NoteAdapter.MyCallBack{
            override fun onClick(position:Int,item: Note) {
                showContextMenu()
                item.check = true
            }
        })
        adapter.setOnClickListener(object:NoteAdapter.MyCallBack{
            override fun onClick(position: Int, item: Note) {
                viewNote(item.id)
            }
        })

        //  _view
        rev.adapter = adapter
        rev.layoutManager = GridLayoutManager(
            this,
            2,
        )

        //TODO  #add note
        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        fab.setOnClickListener{
            Toast.makeText(this, "Tạo một ghi chú mới", Toast.LENGTH_SHORT).show()
            var intent = Intent(this@MainActivity,AddActivity::class.java)
              startActivity(intent)
        }

        //TODO  #filter by title
        searchView!!.setOnQueryTextListener(object: SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return filterAction(query.toString())
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return filterAction(newText)
            }

        })

    }

    //TODO  #read note
    private fun viewNote(id: Int){
        var intent = Intent(this@MainActivity,viewNote::class.java)
        intent.putExtra("note_id",id)
//        Log.e("note_id",id.toString())
        startActivity(intent)
    }
    //TODO  #filter by title
    private fun filterAction(text: String?): Boolean{
        if(text == null || text.isEmpty()){
            adapter.refreshData(dao.getData())
            return false
        }

        val filterList = mutableListOf<Note>()
        for(item in notes){
            if(item.title.contains(text,false)){
                filterList.add(item)
            }
        }

        if (filterList.size == 0){
            Toast.makeText(this@MainActivity,"Bản ghi không tồn tại!!", Toast.LENGTH_SHORT).show()
        }
        adapter.refreshData(filterList)
        return true

    }

    //TODO  #contextual menu
    private val actionModeCallback= object : ActionMode.Callback{
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater= mode.menuInflater
            inflater.inflate(R.menu.menu_bar, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem):Boolean {
            when (item.itemId) {
                R.id.delete -> {
                    deleteItem(getSelectedList())
//                    notes = dao.getData()
//                    adapter.refreshData(notes)
                    mode.finish()
                    return true
                }

                R.id.chooseAll -> {
                    selectAll(getSelectedList().isEmpty())
                    return true
                }

                else -> return false
            }

        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mActionMode = null
            notes = dao.getData()
            adapter.refreshData(notes)
        }
    }

    fun selectAll(isChecked: Boolean){
        for(item in notes){
            item.check = isChecked
        }

        adapter.notifyDataSetChanged()
    }

    private fun showContextMenu(){
        if(mActionMode==null){
            mActionMode = this.startActionMode(actionModeCallback)
        }
    }

    //TODO  #delete note
    private fun deleteItem(id:List<Int>){
        dao.deleteData(id)
    }

    fun getSelectedList(): List<Int> {
        val selectedList= mutableListOf<Int>()
        for(item in notes){
            if(item.check){
                selectedList.add(item.id)
            }
            Log.e("check",item.check.toString())
            Log.e("id", item.id.toString())
        }
        Log.e("Note", notes.toString())
//        Log.e("selectedList",selectedList.toString())
        return selectedList
    }

}

