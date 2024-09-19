package com.example.swiftbargain.ui.profile.view_model

interface ProfileInteractions {
    fun getProfileInfo()
    fun controlEditProfileVisibility()
    fun onChangeName(name: String)
    fun controlGenderDropDownVisibility()
    fun onSelectGender(gender: String)
    fun onSelectDate(date: String)
    fun onChangePhone(phone: String)
}
