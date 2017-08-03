package com.leiyun.easynote

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.leiyun.easynote.adapter.NotesAdapter
import com.leiyun.easynote.base.BaseActivity
import com.leiyun.easynote.base.showTips
import com.leiyun.easynote.model.Note
import com.leiyun.easynote.model.Note_
import com.leiyun.easynote.ui.NoteActivity
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.query.QueryBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivityForResult


class MainActivity : BaseActivity() {
    private val reqCode: Int = 0x01
    private lateinit var noteBox: Box<Note>
    private lateinit var noteQuery: Query<Note>
    private lateinit var mAdapter: NotesAdapter


    override fun getContentLayout(): Int = R.layout.activity_main

    override fun setStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, Color.WHITE, 50)
    }

    override fun initData() {

        noteBox = (application as App).boxStore.boxFor(Note::class.java)
        noteQuery = noteBox.query().build()


        val typeArr = resources.getStringArray(R.array.arr_note_type)
        for (i in typeArr.indices) {
            val itemView = View.inflate(this, R.layout.item_note_type, null)
            itemView.tag = i
            val typeIcon: ImageView = itemView.findViewById(R.id.typeIcon)
            val typeName: TextView = itemView.findViewById(R.id.typeName)
            typeIcon.setColorFilter(resources.getColor((application as App).colorArr[i]))
            typeName.text = typeArr[i]
            leftDrawer.addView(itemView)
            itemView.setOnClickListener { v ->
                queryNotes(typeArr[v.tag as Int])
                drawerLayout.closeDrawer(Gravity.START, false)
            }
        }

        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home)
        toolbar.title = "备忘录"
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(Gravity.START, true)
        }
        floatBtn.setOnClickListener {
            startActivityForResult<NoteActivity>(reqCode)
        }

        recycleView.layoutManager = LinearLayoutManager(this)
        mAdapter = NotesAdapter()
        recycleView.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                startActivityForResult<NoteActivity>(reqCode, "noteID" to note.id)
            }

            override fun onItemLongClick(note: Note) {
                deleteNote(note)
            }

        })
        queryNotes()
    }

    fun queryNotes() {
        val notes = noteQuery.find()
        mAdapter.setData(notes)
    }

    fun queryNotes(type: String) {
        val builder: QueryBuilder<Note> = noteBox.query()
        val notes = builder.equal(Note_.type, type).build().find()
        mAdapter.setData(notes)
    }

    fun deleteNote(note: Note) {
        AlertDialog.Builder(this)
                .setMessage("确认删除？")
                .setTitle("温馨提示")
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("确定") { dialog, _ ->
                    dialog.dismiss()
                    noteBox.remove(note)
                    showTips(coordinatorLayout, "删除成功")
                    queryNotes()
                }.show()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == reqCode) {
            queryNotes()
        }
    }


}
