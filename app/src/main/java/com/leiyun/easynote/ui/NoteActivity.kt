package com.leiyun.easynote.ui

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.leiyun.easynote.App
import com.leiyun.easynote.R
import com.leiyun.easynote.base.BaseActivity
import com.leiyun.easynote.model.Note
import com.leiyun.easynote.model.Note_
import com.leiyun.easynote.utils.DateUtil
import com.leiyun.easynote.utils.SoftInputUtil
import io.objectbox.Box
import io.objectbox.query.Query
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.imageResource
import java.util.*

class NoteActivity : BaseActivity() {
    private var date: Date? = null
    private lateinit var noteBox: Box<Note>
    private lateinit var notesQuery: Query<Note>
    private var note: Note? = null
    private var noteID: Long = -1
    private var type: String = "未标签"

    override fun getContentLayout(): Int = R.layout.activity_note

    override fun initData() {
        noteID = intent.getLongExtra("noteID", -1)
        noteBox = (application as App).boxStore.boxFor(Note::class.java)
        notesQuery = noteBox.query().order(Note_.content).build()
        if (noteID != -1L) {
            note = noteBox.get(noteID)
        }

        toolbar.setNavigationIcon(R.mipmap.ic_back)
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        sureBtn.setOnClickListener {
            addNote()
            setResult(Activity.RESULT_OK)
            finish()
        }
        tagBtn.setOnClickListener {
            sureBtn.visibility = View.VISIBLE
            edtNoteTitle.visibility = View.VISIBLE
            toolbar.title = ""
            showTypeDialog()
        }

        if (note == null) {
            noteTime.text = DateUtil.getNowTimeFriendly()
            date = DateUtil.getDate()
        } else {
            sureBtn.visibility = View.GONE
            edtNoteTitle.visibility = View.GONE
            toolbar.title = "备忘录"
            date = (note as Note).date
            noteTime.text = DateUtil.getFriendlyTimeOfDate((note as Note).date!!)
            contentTv.setText((note as Note).content)
            contentTv.isFocusable = false
            contentTv.isFocusableInTouchMode = false
            contentTv.setOnClickListener {
                contentTv.isFocusable = true
                contentTv.isFocusableInTouchMode = true
                contentTv.requestFocus()
                contentTv.setSelection((note as Note).content!!.length)
                SoftInputUtil.showSoftInput(this@NoteActivity)
                sureBtn.visibility = View.VISIBLE
                edtNoteTitle.visibility = View.VISIBLE
                toolbar.title = ""
                contentTv.setOnClickListener(null)
            }
        }

    }

    fun showTypeDialog() {
        val view: LinearLayout = LinearLayout(this)
        val dialog = AlertDialog.Builder(this)
                .setView(view).show()
        view.orientation = LinearLayout.VERTICAL
        val typeArr = resources.getStringArray(R.array.arr_note_type)
        for (i in typeArr.indices) {
            val itemView = View.inflate(this, R.layout.item_note_type, null)
            itemView.tag = i
            val typeIcon: ImageView = itemView.findViewById(R.id.typeIcon)
            val typeName: TextView = itemView.findViewById(R.id.typeName)
            typeIcon.setColorFilter(resources.getColor((application as App).colorArr[i]))
            typeName.text = typeArr[i]
            view.addView(itemView)
            itemView.setOnClickListener { v ->
                val position = v.tag as Int
                type = typeArr[position]
                tagBtn.imageResource = R.mipmap.ic_note_type
                tagBtn.setColorFilter(resources.getColor((application as App).colorArr[position]))
                dialog.dismiss()
            }
        }
    }

    private fun addNote() {
        val content = contentTv.text.toString()
        val note = Note(type = type, content = content, date = date)
        if (noteID != -1L) {
            note.id = noteID
        }
        noteBox.put(note)
    }


}
