package com.example.kotlinprac.navigation2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.kotlinprac.R

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getString("item")
        Toast.makeText(requireActivity(), "item : $item", Toast.LENGTH_SHORT).show()

        val button = view.findViewById<Button>(R.id.second_button)
        button.setOnClickListener {
            findNavController().navigate(
                R.id.action_secondFragment_to_thirdFragment, bundleOf("intValue" to 123)
            )
        }
    }
}