package com.mydia.restaurantsmartqr.activity


import com.mydia.restaurantsmartqr.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityWebviewBinding
import com.mydia.restaurantsmartqr.viewModel.VMWebview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebviewActivity : BaseActivity<ActivityWebviewBinding, VMWebview>(){
    override fun getViewBinding() =  ActivityWebviewBinding.inflate(layoutInflater)

    override fun observeViewModel() {

    }

    override fun onActivityCreated() {
        binding.vm = viewModel

        binding.lifecycleOwner=this
        binding.imgBack.setOnClickListener {
            finish()
        }
        val intent1: Intent = intent
        /*if (intent1.hasExtra("from")) {
            from = intent.getStringExtra("from").toString()
            //getVM().jobslistData.set(familyId)
        }*/
        if (intent1.hasExtra("weburl")) {
            viewModel.weburl.set(intent.getStringExtra("weburl"))
            //getVM().jobslistData.set(familyId)
        }
        if (intent1.hasExtra("cTitle")) {
            binding.tvTitle.setText(intent.getStringExtra("cTitle"))
            //getVM().jobslistData.set(familyId)
        }

        binding.webView.getSettings().setJavaScriptEnabled(true)
        binding.webView.setWebViewClient(WebViewClient())
        binding.webView.setSoundEffectsEnabled(true)
        binding.webView.getSettings().setAllowFileAccess(true)
        binding.webView.getSettings().setSupportZoom(true)
        binding.webView.loadUrl(viewModel.weburl.get().toString())
    }

    override val viewModel: VMWebview by viewModels()

}