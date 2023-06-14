package com.codelabs.pdam.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val myPref = "My_Pref"
    val sharedPreference: SharedPreferences

    init {
        sharedPreference = context.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    companion object {
        const val USER_TOKEN = "token"
        const val LOGIN = "login"
        const val REMEMBER = "remember"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val NAME = "name"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val PHOTO = "photo"
        const val ID = "id"
        const val PRICE = "price"
        const val CODE = "code"
        const val NAMECUST = "namecust"
        const val PROVINCE = "province"
        const val KABUPATEN = "kabupaten"
        const val KECAMATAN = "kecamatan"
        const val IDPROV = "idprov"
        const val IDKAB = "idkab"
        const val IDKEC = "idkec"
        const val IDGOL = "idgol"
        const val IDUK = "iduk"
        const val CUSTID = "custid"
    }

    //function save token
    fun saveAuthToken(token: String){
        sharedPreference.edit().putString(USER_TOKEN, token).apply()
    }

    //function fetch token
    fun fetchAuthToken(): String? {
        return  sharedPreference.getString(USER_TOKEN, "")
    }

    //boolean put
    fun put(login: Boolean){
        sharedPreference.edit()
            .putBoolean(LOGIN, login)
            .apply()
    }

    //boolean get
    fun getBoolean() : Boolean{
        return sharedPreference.getBoolean(LOGIN, false)
    }

    //boolean put
    fun saveRemember(remember: Boolean){
        sharedPreference.edit()
            .putBoolean(REMEMBER, remember)
            .apply()
    }

    //boolean get
    fun fetchRemember() : Boolean{
        return sharedPreference.getBoolean(REMEMBER, false)
    }

    //function save password
    fun saveUsername(username: String){
        sharedPreference.edit().putString(USERNAME, username).apply()
    }

    //function fetch password
    fun fetchUsername(): String? {
        return  sharedPreference.getString(USERNAME, "")
    }

    //function save password
    fun savePassword(password: String){
        sharedPreference.edit().putString(PASSWORD, password).apply()
    }

    //function fetch password
    fun fetchPassword(): String? {
        return  sharedPreference.getString(PASSWORD, "")
    }

    //function save name
    fun saveName(name: String){
        sharedPreference.edit().putString(NAME, name).apply()
    }

    //function fetch name
    fun fetchName(): String? {
        return  sharedPreference.getString(NAME, "")
    }

    //function save email
    fun saveEmail(email: String){
        sharedPreference.edit().putString(EMAIL, email).apply()
    }

    //function fetch email
    fun fetchEmail(): String? {
        return  sharedPreference.getString(EMAIL, "")
    }

    //function save phone
    fun savePhone(phone: String){
        sharedPreference.edit().putString(PHONE, phone).apply()
    }

    //function fetch phone
    fun fetchPhone(): String? {
        return  sharedPreference.getString(PHONE, "")
    }

    //function save photo
    fun savePhoto(photo: String){
        sharedPreference.edit().putString(PHOTO, photo).apply()
    }

    //function fetch photo
    fun fetchPhoto(): String? {
        return  sharedPreference.getString(PHOTO, "")
    }

    //function save id
    fun saveId(id: String){
        sharedPreference.edit().putString(ID, id).apply()
    }

    //function fetch id
    fun fetchId(): String? {
        return  sharedPreference.getString(ID, "")
    }

    //function save price
    fun savePrice(price: String){
        sharedPreference.edit().putString(PRICE, price).apply()
    }

    //function fetch price
    fun fetchPrice(): String? {
        return  sharedPreference.getString(PRICE, "")
    }

    //function save code
    fun saveCode(code: String){
        sharedPreference.edit().putString(CODE, code).apply()
    }

    //function fetch code
    fun fetchCode(): String? {
        return  sharedPreference.getString(CODE, "")
    }

    //function save name cust
    fun saveNameCust(namecust: String){
        sharedPreference.edit().putString(NAMECUST, namecust).apply()
    }

    //function fetch name cust
    fun fetchNameCust(): String? {
        return  sharedPreference.getString(NAMECUST, "")
    }

    //function save name province
    fun saveProvince(province: String){
        sharedPreference.edit().putString(PROVINCE, province).apply()
    }

    //function fetch name province
    fun fetchProvince(): String? {
        return  sharedPreference.getString(PROVINCE, "")
    }

    //function save name kabupaten
    fun saveKabupaten(kabupaten: String){
        sharedPreference.edit().putString(KABUPATEN, kabupaten).apply()
    }

    //function fetch name kabupaten
    fun fetchKabupaten(): String? {
        return  sharedPreference.getString(KABUPATEN, "")
    }

    //function save name kecamatan
    fun saveKecamtan(kecamatan: String){
        sharedPreference.edit().putString(KECAMATAN, kecamatan).apply()
    }

    //function fetch name kecamatan
    fun fetchKecamtan(): String? {
        return  sharedPreference.getString(KECAMATAN, "")
    }

    //function save id
    fun saveIdProv(id: String){
        sharedPreference.edit().putString(IDPROV, id).apply()
    }

    //function fetch id
    fun fetchIdProv(): String? {
        return  sharedPreference.getString(IDPROV, "")
    }

    //function save id
    fun saveIdKab(id: String){
        sharedPreference.edit().putString(IDKAB, id).apply()
    }

    //function fetch id
    fun fetchIdKab(): String? {
        return  sharedPreference.getString(IDKAB, "")
    }

    //function save id
    fun saveIdKec(id: String){
        sharedPreference.edit().putString(IDKEC, id).apply()
    }

    //function fetch id
    fun fetchIdKec(): String? {
        return  sharedPreference.getString(IDKEC, "")
    }

    //function save id
    fun saveIdGolongan(id: String){
        sharedPreference.edit().putString(IDGOL, id).apply()
    }

    //function fetch id
    fun fetchIdGolongan(): String? {
        return  sharedPreference.getString(IDGOL, "")
    }

    //function save id
    fun saveIdUkuran(id: String){
        sharedPreference.edit().putString(IDUK, id).apply()
    }

    //function fetch id
    fun fetchIdUkuran(): String? {
        return  sharedPreference.getString(IDUK, "")
    }

    //function save customer id
    fun saveCustId(custid: String){
        sharedPreference.edit().putString(CUSTID, custid).apply()
    }

    //function fetch customer id
    fun fetchCustId(): String? {
        return  sharedPreference.getString(CUSTID, "")
    }
}