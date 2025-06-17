package com.example.satchelbooksharing.viewModel.satchel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.satchelbooksharing.data.LocalLibraryRepository

class LibraryViewModelFactory(
    private val repo: LocalLibraryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            return LibraryViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}