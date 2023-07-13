package com.example.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

// only thing is changing from fragment to fragment is only data binding class and inflate method
// so make it generic
open class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment() {

    // first, nullable reference to Fragment binding(binding classes are auto generated when you set Data Binding in gradle).
    // Why nullable? -> As you can't inflate any view until onCreateView() is called and there is time in between fragment instance is created
    // (when onCreate() is called) and when its usable & fragment can be destroyed and created multiple times in its lifecycle
    private var _binding: T? = null

    // get() is get-only property, once it is assigned to it, you can assign it to something else
    // !! as it won't be null when will access it
    private val binding: T get() = _binding!!

    // our extension function to initialize something in onCreateView()
    // make it open so that it can be inherited
    // with type parameter of T, which allows to function to perform with different types
    open fun T.initialize() {}

    open fun T.initializeOnViewCreated() {}

    // DataBindingUtil.inflate
    // this will inflate the layout and returns the newly created binding for that layout using layout res Id
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        // when using LiveData as data variable, we have to give it lifecycle owner to this lifecycle (as LiveData is lifecycle aware observable)
        binding.lifecycleOwner = this

        // calling the extension function on binding of that particular returned binding instance from DataBindingUtil.inflate
        binding.initialize()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initializeOnViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}