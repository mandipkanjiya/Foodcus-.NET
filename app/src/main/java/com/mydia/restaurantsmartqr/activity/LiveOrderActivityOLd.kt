package com.mydia.restaurantsmartqr.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.AcceptedOrderAdapter
import com.mydia.restaurantsmartqr.adapter.IncomingOrderAdapter
import com.mydia.restaurantsmartqr.adapter.ReadyOrderAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityLiveOrderBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseDataManager
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.util.PaginationScrollListener
import com.mydia.restaurantsmartqr.viewModel.VMLiveOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LiveOrderActivityOLd : BaseActivity<ActivityLiveOrderBinding, VMLiveOrder>(),FirebaseEventListener
{
    lateinit var incomingOrderAdapter: IncomingOrderAdapter
    lateinit var acceptedOrderAdapter: AcceptedOrderAdapter
    lateinit var readyOrderAdapter: ReadyOrderAdapter

    private val TAG = LiveOrderActivityOLd::class.java.simpleName

    override fun getViewBinding() = ActivityLiveOrderBinding.inflate(layoutInflater)


    override fun onActivityCreated() {
        binding.vm = viewModel
        FirebaseDataManager.firebaseDataReference.firebaseEventListener = this

        viewModel.getUserData()
        binding.dLayout.llLiveOrder.isSelected = true
        drawerClickListner()
        viewModel.allApiCall()

      /*  binding.imgBack.setOnClickListener{
            finish()
        }*/


        setEventsIncomingOrders()
        setAcceptedAdapter()
    }

    private fun drawerClickListner(){
        binding.imgMenu.setOnClickListener {
            openDrawer()
        }
        binding.dLayout.imgClose.setOnClickListener{
            closeDrawer()
        }
        binding.dLayout.llDashboard.setOnClickListener {
            closeDrawer()
        }
        binding.dLayout.llLiveOrder.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,LiveOrderActivityOLd::class.java))
        }
        binding.dLayout.llOrderCompleted.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,CompletedOrderActivity::class.java))
        }
        binding.dLayout.llAnalytics.setOnClickListener {
            closeDrawer()
        }
        binding.dLayout.llLogout.setOnClickListener {
            closeDrawer()
            showLogoutDialog()
        }

    }
    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }
    override fun observeViewModel() {
        viewModel.incomingOrder.observe(this){
            if(it.status == 1){

                updateIncomingOrderList(it.result)
            }
        }
        viewModel.acceptedOrder.observe(this){
            if(it.status == 1){
                updateAcceptedOrderList(it.result)
                //setAcceptedAdapter(it.result)
            } else{

            }
        }

        viewModel.readyOrder.observe(this){
            if(it.status == 1){
                Log.e(TAG," ready order")
                binding.llReady.visibility = View.VISIBLE
                setReadyAdapter(it.result)
            }else{
                binding.llReady.visibility = View.GONE
            }
        }

    }

    override val viewModel:  VMLiveOrder by viewModels()

    private fun updateIncomingOrderList(list:ArrayList<OrderList>){
        Log.e(TAG," accept ${list.size}")
       // incomingOrderAdapter.addData(list)
    }

    private fun updateAcceptedOrderList(list:ArrayList<OrderList>){
        Log.e(TAG," accept ${list.size}")
       // acceptedOrderAdapter.addData(list)
    }
    private fun setEventsIncomingOrders(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvIncomingOrder.layoutManager=linearLayoutManager
       // incomingOrderAdapter = IncomingOrderAdapter(this)
        binding.rvIncomingOrder.adapter=incomingOrderAdapter
        binding.rvIncomingOrder.addOnScrollListener(object :PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
              //  viewModel.nextPageIncomingOrder()
            }

            override val isLastPage: Boolean=false
            override val isLoading: Boolean=false
        })
        incomingOrderAdapter.setOnItemCLickListener(object : IncomingOrderAdapter.OnItemClickListener{
            override fun onItemAcceptClick(datum: OrderList?, pos: Int) {
                val title = "Are you sure you want to accept this order?"
                showAcceptRejectDialog(title, datum!!.nid.toString(),"2")

            }
            override fun onItemRejectClick(datum: OrderList?, pos: Int) {
                val title = "Are you sure you want to reject this order?"
                showAcceptRejectDialog(title, datum!!.nid.toString(),"5")
            }
        })

    }

    fun setReadyAdapter(list:ArrayList<OrderList>){
        Log.e(TAG," ready order adapter")
        val pickupOrderList = list.filter { it-> it.orderType == "2" }
        if(pickupOrderList.isNotEmpty()){
            Log.e(TAG,"order${pickupOrderList.size}")
            viewModel.totalReadyOrder.set(pickupOrderList.size.toString())
            binding.llReady.visibility = View.VISIBLE
            readyOrderAdapter = ReadyOrderAdapter(this, pickupOrderList)
            binding.rvReadyOrder.adapter = readyOrderAdapter
        }else{
            Log.e(TAG,"order${pickupOrderList.size}")
            binding.llReady.visibility = View.GONE
        }


    }
    fun setAcceptedAdapter(){

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvAcceptedOrder.layoutManager=linearLayoutManager
       // acceptedOrderAdapter = AcceptedOrderAdapter(this)
        binding.rvAcceptedOrder.adapter = acceptedOrderAdapter
        binding.rvAcceptedOrder.addOnScrollListener(object :PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
             //   viewModel.nextPageAcceptedOrder()
            }

            override val isLastPage: Boolean=false
            override val isLoading: Boolean=false
        })
        acceptedOrderAdapter.setOnItemCLickListener(object : AcceptedOrderAdapter.OnItemClickListener{

            override fun onItemAcceptClick(datum: OrderList?, pos: Int, orderType: String) {
                var title = "Are you sure this order is prepared?"
                if(orderType.equals("2")){
                    showAcceptRejectDialog(title, datum!!.nid.toString(),"4")
                    //dinin
                }else if(orderType.equals("1")){
                    showAcceptRejectDialog(title, datum!!.nid.toString(),"3")
                }

            }
        })
    }

    private fun showAcceptRejectDialog(title:String,orderId:String,orderStatus:String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(title)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->

                    val orderStatusApi  = OrderStatusRequest(order_id = orderId, norder_status = orderStatus)
                    viewModel.orderStatusApi(orderStatusApi)
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
            .show()
    }
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.clearData()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK // To clean up all activities

                    startActivity(intent)
                    finish()
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
            .show()
    }
    private fun showNewOrderDialog()=lifecycleScope.launch(Dispatchers.Main) {
        AlertDialog.Builder(this@LiveOrderActivityOLd)
            .setTitle(getString(R.string.app_name))
            .setMessage("New Order")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->

                viewModel.allApiCall()

                })

            .show()
    }

    override fun onRebootCommand() {

    }

    override fun onRestartCommand() {

    }

    override fun onContentUpdateCommand() {
        Log.e(TAG,"ck")
        var resID = resources.getIdentifier("notification", "raw", packageName)
        val mediaPlayer = MediaPlayer.create(this, resID)
        mediaPlayer.start()

        showNewOrderDialog()
    }

    override fun onQueueUpdateCommand() {

    }

    override fun onClearCatchCommand() {

    }

    override fun onPOSOrderCountCommand() {

    }


}