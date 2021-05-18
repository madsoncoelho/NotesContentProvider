package com.example.notescontentprovider

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notescontentprovider.database.NotesDatabaseHelper.Companion.COLUMN_DESCRIPTION
import com.example.notescontentprovider.database.NotesDatabaseHelper.Companion.COLUMN_TITLE

class NotesAdapter(private val listener: NoteClickedListener): RecyclerView.Adapter<NotesViewHolder>() {

    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.note_item, parent, false
        ))

    override fun getItemCount(): Int = if (mCursor != null) mCursor?.count as Int else 0

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        mCursor?.moveToPosition(position)

        holder.tvNoteTitle.text = mCursor?.getString(mCursor?.getColumnIndex(COLUMN_TITLE) as Int)
        holder.tvNoteDescription.text = mCursor?.getString(mCursor?.getColumnIndex(COLUMN_DESCRIPTION) as Int)
        holder.btnRemoveNote.setOnClickListener {
            mCursor?.moveToPosition(position)
            listener.noteRemoveItem(mCursor)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            listener.noteClickedItem(mCursor as Cursor)
        }
    }

    fun setCursor(newCursor: Cursor?) {
        mCursor = newCursor
        notifyDataSetChanged()
    }
}

class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle) as TextView
    val tvNoteDescription = itemView.findViewById(R.id.tvNoteDescription) as TextView
    val btnRemoveNote = itemView.findViewById(R.id.btnRemoveNote) as Button

}