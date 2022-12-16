package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.data.models.ShopModel
import ru.bmstu.ulife.data.models.StudentModel
import ru.bmstu.ulife.databinding.FragmentStudentListBinding
import ru.bmstu.ulife.list_adapter.FeedAdapter
import ru.bmstu.ulife.list_adapter.FeedItemBinder
import ru.bmstu.ulife.list_adapter.FeedItemClass
import ru.bmstu.ulife.list_adapter.binders.data.StudentListBinder
import ru.bmstu.ulife.utils.SharedPreferencesStorage

class StudentListFragment : Fragment() {
    private lateinit var binding: FragmentStudentListBinding
    private lateinit var adapter: FeedAdapter

    private var list: MutableList<ShopModel> = mutableListOf()
    private val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

    private var storage: SharedPreferencesStorage = get(named("storage"))

    @Suppress("UNCHECKED_CAST")
    private fun createBinders() {
        val data = StudentListBinder { data -> onStudentClick(data) }
        viewBinders[data.modelClass] = data as FeedItemBinder

        adapter = FeedAdapter(viewBinders)

        adapter.submitList(list as List<Any>?)
    }

    private fun onStudentClick(data: StudentModel) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createBinders()
        init()
        binding.studentListRv.adapter = adapter

        val database = Firebase.database.reference

        database.child("students").get().addOnCompleteListener {
            if (it.isSuccessful) {
                val students = it.result.children.mapNotNull { doc ->
                    doc.getValue(StudentModel::class.java)
                }
                println(students)
                showData(students as ArrayList<StudentModel>)
            } else {
                Log.d("StudentListFragment", it.exception?.message.toString())
            }
        }
    }

    private fun showData(resp: ArrayList<StudentModel>) {
        adapter.submitList(resp as List<Any>?)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() {
        binding.myEmail.text = storage.getUserEmail()
        binding.logoutBtn.setOnClickListener {
            storage.putIsLogout(true)
            findNavController().popBackStack()
        }
    }
}