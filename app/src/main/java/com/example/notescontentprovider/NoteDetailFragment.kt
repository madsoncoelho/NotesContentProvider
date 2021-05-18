package com.example.notescontentprovider

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.notescontentprovider.database.NotesDatabaseHelper.Companion.COLUMN_DESCRIPTION
import com.example.notescontentprovider.database.NotesDatabaseHelper.Companion.COLUMN_TITLE
import com.example.notescontentprovider.database.NotesProvider.Companion.URI_NOTES

class NoteDetailFragment: DialogFragment(), DialogInterface.OnClickListener {

    private lateinit var etNoteTitle: EditText
    private lateinit var etNoteDescription: EditText
    private var id: Long = 0

    companion object {
        private const val EXTRA_ID = "id"
        fun newInstance(id: Long): NoteDetailFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)

            val noteFragment = NoteDetailFragment()
            noteFragment.arguments = bundle

            return noteFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.note_detail, null)

        etNoteTitle = view?.findViewById(R.id.etNoteTitle) as EditText
        etNoteDescription = view?.findViewById(R.id.etNoteDescription) as EditText

        var newNote = true
        if (arguments != null && arguments?.getLong(EXTRA_ID) != 0L) {
            id = arguments?.getLong(EXTRA_ID) as Long
            val uri = Uri.withAppendedPath(URI_NOTES, id.toString())
            val cursor = activity?.contentResolver?.query(
                uri, null, null, null, null)

            if (cursor?.moveToNext() as Boolean) {
                newNote = false
                etNoteTitle.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)))
                etNoteDescription.setText(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
            }
            cursor.close()
        }

        return AlertDialog.Builder(activity as Activity)
            .setTitle(if (newNote) "Nova mensagem" else "Editar mensagem")
            .setView(view)
            .setPositiveButton("Salvar", this)
            .setNegativeButton("Cancelar", this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, etNoteTitle.text.toString())
        values.put(COLUMN_DESCRIPTION, etNoteDescription.text.toString())

        if (id != 0L) {
            var uri = Uri.withAppendedPath(URI_NOTES, id.toString())
            context?.contentResolver?.update(uri, values, null, null)
        } else {
            context?.contentResolver?.insert(URI_NOTES, values)
        }

    }
}