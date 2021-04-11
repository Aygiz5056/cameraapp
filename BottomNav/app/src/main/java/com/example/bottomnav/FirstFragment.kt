package com.example.bottomnav

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_one.*

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var list = listOf("#E91E63", "#9C27B0", "#673AB7", "#3F51B5",
            "#2196F3", "#00BCD4", "#009688", "#4CAF50", "#CDDC39")

        var count = 0
        button.setOnClickListener {
            textViewHome.text = count.toString()
            count++
            home_layout.setBackgroundColor(Color.parseColor(list.random()))
        }
    }

}