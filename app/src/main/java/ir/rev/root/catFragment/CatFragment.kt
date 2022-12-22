package ir.rev.root.catFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ir.rev.root.databinding.FragmentFirstBinding
import ir.rev.root.utils.PaginationScrollListener

class CatFragment : Fragment() {

    private lateinit var viewModel: CatViewModel
    private lateinit var binding: FragmentFirstBinding

    private var adapter: CatListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CatViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CatListAdapter()
        binding.catList.apply {
            adapter = adapter
            PaginationScrollListener(
                context = context,
                loadNextPage = {
                    viewModel.refreshByScroll()
                },
            ).apply {
                setOnTouchListener(this)
            }
        }

        viewModel.catListLiveData.observe(viewLifecycleOwner) {
            adapter?.reload(it)
        }
        viewModel.action.handler = { event ->
            when (event) {
                is ShowToast -> Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                else -> {
                    /* должно быть пусто*/
                }
            }
        }
    }

    override fun onDestroyView() {
        adapter = null // это называется явное зануление, что б сборщик мусора удалил данные быстрее и гарантировано
        super.onDestroyView()
    }

}