package com.example.swiftbargain.ui.profile.view_model

import android.net.Uri

interface ProfileInteractions {
    fun getProfileInfo()
    fun controlEditProfileVisibility()
    fun onCancelEdit()
    fun onSelectImage(image: Uri)
    fun onChangeName(name: String)
    fun controlGenderDropDownVisibility()
    fun onSelectGender(gender: String)
    fun onSelectDate(date: String)
    fun onChangePhone(phone: String)
    fun controlSaveChangesDialogVisibility()
    fun updateProfile()
}
