package com.alimojarrad.encrypter.CreateKey
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.alimojarrad.encrypter.R

class CustomGrid(private val mContext: Context, private val keys: Array<String>) : BaseAdapter() {

    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return keys.size
    }

    override fun getItem(position: Int): Any? {
        // TODO Auto-generated method stub
        return null
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO Auto-generated method stub
        var grid: View

        if (convertView == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            grid = inflater.inflate(R.layout.gride_item_view, null)


        } else {
            grid = convertView
        }
        val gridItemText = grid.findViewById<TextView>(R.id.grid_item) as TextView
        val gridColorText = grid.findViewById<TextView>(R.id.grid_color) as TextView

         gridItemText.text = keys[position]

        return grid
    }
}
