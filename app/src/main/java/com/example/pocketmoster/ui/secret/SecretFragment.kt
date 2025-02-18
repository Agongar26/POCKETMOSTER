package com.example.pocketmoster.ui.secret

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pocketmoster.databinding.FragmentSecretBinding

class SecretFragment : Fragment() {

private var _binding: FragmentSecretBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
    val secretViewModel =
    ViewModelProvider(this).get(SecretViewModel::class.java)

    _binding = FragmentSecretBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textSecret
    secretViewModel.text.observe(viewLifecycleOwner) {
        textView.text = it
    }
    return root
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}