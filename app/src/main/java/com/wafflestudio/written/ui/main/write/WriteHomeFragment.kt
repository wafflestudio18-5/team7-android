package com.wafflestudio.written.ui.main.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wafflestudio.written.databinding.FragmentWriteBinding
import com.wafflestudio.written.databinding.FragmentWriteHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_write_home.*
import timber.log.Timber

@AndroidEntryPoint
class WriteHomeFragment : Fragment() {

    companion object {
        fun newInstance(): WriteHomeFragment = WriteHomeFragment()
    }

    private lateinit var binding: FragmentWriteHomeBinding
    private val viewModel: WriteHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getTitleToday()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Timber.d(it)
                Toast.makeText(activity, "Cannot get today's title", Toast.LENGTH_SHORT).show()
            })

        viewModel.observeTitle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.titleText.text = it
            }

        binding.writeButton.setOnClickListener {
            val intent =
                WriteNewActivity.createIntent(requireActivity(), binding.titleText.text.toString(), null)
            activity?.startActivity(intent)
        }

    }

}
