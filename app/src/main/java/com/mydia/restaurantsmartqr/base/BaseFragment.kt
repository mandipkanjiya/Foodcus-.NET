package  com.mydia.restaurantsmartqr.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.util.NetworkConnectivity
import java.util.*
import javax.inject.Inject


abstract class BaseFragment<BindingType : ViewBinding, ViewModelType : BaseViewModel> : Fragment() {

    private val baseActivity by lazy { activity as BaseActivity<*, *>? }
    lateinit var binding: BindingType
    protected abstract val viewModel: ViewModelType
    abstract fun getViewBinding(): BindingType
    abstract fun onFragmentCreated()
    abstract fun observeViewModel()
    open fun observe() {}
    open fun onTickTick() {}

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    @Inject
    lateinit var prefs: PreferencesServices

/*
    @Inject
    lateinit var dbOperations: DbOperations
*/

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()




    }


    fun startTickTick(){
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                onTickTick()
            }
        }
        timer?.schedule(timerTask, 5000, 1000)
    }

    private fun stopTickTick(){
        timer?.cancel()
    }
    override fun onStop() {
        super.onStop()
        stopTickTick()
    }

    override fun onResume() {
        super.onResume()
        startTickTick()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onFragmentCreated()
        observeViewModel()
        observe()
       // observeActions()
        return binding.root
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
