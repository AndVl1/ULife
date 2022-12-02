package ru.bmstu.ulife.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable
import org.koin.core.component.KoinComponent
import ru.bmstu.ulife.data.models.ShopModel
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.data.models.UserWithTokenModel
import java.util.Collections.emptySet

class SharedPreferencesStorage constructor(
    context: Context,
) : KoinComponent {

    private val storage: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun putShopList(shopList: MutableList<ShopModel>) {
        storage.edit()
            .putStringSet("SHOP_LIST_TITLE", shopList.map { it.title }.toMutableSet())
            .putStringSet("SHOP_LIST_METRO", shopList.map { it.metro }.toMutableSet())
            .putStringSet("SHOP_LIST_ADDRESS", shopList.map { it.address }.toMutableSet())
            .putStringSet("SHOP_LIST_PHONE", shopList.map { it.phone }.toMutableSet())
            .putStringSet("SHOP_LIST_LATITUDE", shopList.map { it.latitude.toString() }.toMutableSet())
            .putStringSet("SHOP_LIST_LONGITUDE", shopList.map { it.longitude.toString() }.toMutableSet())
            .putStringSet("SHOP_LIST_URL", shopList.map { it.url }.toMutableSet())
            .apply()
    }

    fun getShopList(): MutableList<ShopModel> {
        val titleList = storage.getStringSet("SHOP_LIST_TITLE", mutableSetOf())?.toList()
        val metroList = storage.getStringSet("SHOP_LIST_METRO", mutableSetOf())?.toList()
        val addressList = storage.getStringSet("SHOP_LIST_ADDRESS", mutableSetOf())?.toList()
        val phoneList = storage.getStringSet("SHOP_LIST_PHONE", mutableSetOf())?.toList()
        val latitudeList = storage.getStringSet("SHOP_LIST_LATITUDE", mutableSetOf())?.toList()
        val longitudeList = storage.getStringSet("SHOP_LIST_LONGITUDE", mutableSetOf())?.toList()
        val urlList = storage.getStringSet("SHOP_LIST_URL", mutableSetOf())?.toList()

        val resultList = mutableListOf<ShopModel>()
        return if (titleList != null && metroList != null && addressList != null && phoneList != null && latitudeList != null && longitudeList != null && urlList != null) {
            for (i in titleList.indices) {
                resultList.add(ShopModel(titleList[i], metroList[i], addressList[i], phoneList[i], latitudeList[i].toDouble(), longitudeList[i].toDouble(), urlList[i]))
            }
            resultList
        } else {
            mutableListOf()
        }
    }

    fun putIsUserRegistered(isUserRegistered: Boolean) {
        storage.edit()
            .putBoolean(PrefsKeys.KEY_IS_USER_REGISTERED, isUserRegistered)
            .apply()
    }

    fun getIsUserRegistered(): Boolean = storage.getBoolean(PrefsKeys.KEY_IS_USER_REGISTERED, false)

    fun putLatitude(lat: Double) {
        storage.edit()
            .putFloat("lat", lat.toFloat())
            .apply()
    }

    fun putLongitude(lng: Double) {
        storage.edit()
            .putFloat("lng", lng.toFloat())
            .apply()
    }

    fun putAuthToken(token: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_TOKEN, token)
            .apply()
    }

    fun putProfileId(profileId: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_PROFILE_ID, profileId)
            .apply()
    }

    fun putFlagFirstEnter(flag: Boolean) {
        storage.edit()
            .putBoolean(PrefsKeys.KEY_FIRST_ENTER, flag)
            .apply()
    }

    fun putFlagIsUserLoggedIn(flag: Boolean) {
        storage.edit()
            .putBoolean(PrefsKeys.KEY_IS_USER_LOGGED_IN, flag)
            .apply()
    }

    fun putCheckedCategories(categoryIds: List<String>) {
        storage.edit()
            .putStringSet(PrefsKeys.KEY_CATEGORIES, categoryIds.toSet())
            .apply()
    }

    fun getCheckedCategories(): List<String> = storage.getStringSet(
        PrefsKeys.KEY_CATEGORIES,
        emptySet()
    )!!.toList()

    fun getCoordinates(): String {
        val lat = getLatitude()
        val lng = getLongitude()
        return "$lat,$lng"
    }


    fun putPushStockID(stockID: String?) {
        storage.edit()
            .putString("pushStockID", stockID)
            .apply()
    }

    fun putPushCommentID(commentID: String?) {
        storage.edit()
            .putString("pushCommentID", commentID)
            .apply()
    }

    fun putIsClickOnUpdateWindow(isClick: Boolean) {
        storage.edit()
            .putBoolean(PrefsKeys.KEY_IS_CLICK_UPDATE_WINDOW, isClick)
            .apply()
    }

    fun putUserName(name: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USERNAME, name)
            .apply()
    }

    fun putLastSearchQuery(query: String?) {
        storage.edit()
            .putString(PrefsKeys.KEY_QUERY, query)
            .apply()
    }

    fun putIsOnMarkerPromoClick(flag: Boolean) {
        storage.edit()
            .putBoolean(PrefsKeys.KEY_IS_ON_MARKER_PROMO_CLICK, flag)
            .apply()
    }

    fun getIsOnMarkerPromoClick(): Boolean =
        storage.getBoolean(PrefsKeys.KEY_IS_ON_MARKER_PROMO_CLICK, false)

    fun getLastQuery(): String? = storage.getString(PrefsKeys.KEY_QUERY, "")

    fun getUserName(): String? = storage.getString(PrefsKeys.KEY_USERNAME, "")

    fun getPushStockID(): String? = storage.getString("pushStockID", null)

    fun getPushCommentID(): String? = storage.getString("pushCommentID", null)

    fun getLatitude(): Float = storage.getFloat("lat", 55.75222f)

    fun getLongitude(): Float = storage.getFloat("lng", 37.61556f)

    fun getAuthToken(): String? = storage.getString(PrefsKeys.KEY_TOKEN, null)

    @Nullable
    fun getProfileId(): String? = storage.getString(PrefsKeys.KEY_PROFILE_ID, null)

    fun getFlagFirstEnter(): Boolean = storage.getBoolean(PrefsKeys.KEY_FIRST_ENTER, true)

    fun getFlagIsUserLoggedIn(): Boolean =
        storage.getBoolean(PrefsKeys.KEY_IS_USER_LOGGED_IN, false)

    fun clear() {
        storage.edit().clear().apply()
    }

    fun putSelectCategoriesOnMap(list: MutableList<String>?) {
        storage.edit()
            .putStringSet(PrefsKeys.KEY_SELECT_CATEGORIES_LIST, list?.toMutableSet())
            .apply()
    }

    fun getSelectCategoriesOnMap(): Set<String> =
        storage.getStringSet(PrefsKeys.KEY_SELECT_CATEGORIES_LIST, emptySet()) as Set<String>

    fun putLastPromosSize(size: Int) {
        storage.edit()
            .putInt(PrefsKeys.KEY_LAST_PROMOS_SIZE, size)
            .apply()
    }

    fun putRecentlyRequests(list: Set<String>) {
        storage.edit()
            .putStringSet(PrefsKeys.KEY_RECENTLY_REQUESTS, list)
            .apply()
    }

    fun getRecentlyRequests(): Set<String>? =
        storage.getStringSet(PrefsKeys.KEY_RECENTLY_REQUESTS, emptySet())

    fun putLastTransitionFromMap(layoutName: String?) {
        storage.edit()
            .putString(PrefsKeys.KEY_LAST_TRANSITION_FROM_MAP, layoutName)
            .apply()
    }

    fun getLastTransitionFromMap(): String? =
        storage.getString(PrefsKeys.KEY_LAST_TRANSITION_FROM_MAP, null)

    fun putLastClickedMarkerId(tag: String?) {
        storage.edit()
            .putString(PrefsKeys.KEY_LAST_CLICKED_MARKER_ID, tag)
            .apply()
    }

    fun putUserBalance(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_BALANCE, data)
            .apply()
    }

    fun getUserBalance(data: String): String? = storage.getString(PrefsKeys.KEY_USER_BALANCE, null)


    fun putUserId(data: Int) {
        println("LOG put userId=" + data)
        storage.edit()
            .putInt(PrefsKeys.KEY_USER_ID, data)
            .apply()
    }

    fun getUserId(): Int = storage.getInt(PrefsKeys.KEY_USER_ID, 0)

    fun putUserFirstName(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_FIRST_NAME, data)
            .apply()
    }

    fun getUserFirstName(): String? =
        storage.getString(PrefsKeys.KEY_USER_FIRST_NAME, null)

    fun putUserSecondName(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_SECOND_NAME, data)
            .apply()
    }

    fun getUserSecondName(): String? =
        storage.getString(PrefsKeys.KEY_USER_SECOND_NAME, null)

    fun putUserEmail(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_EMAIL, data)
            .apply()
    }

    fun getUserEmail(): String? = storage.getString(PrefsKeys.KEY_USER_EMAIL, null)

    fun putUserPassword(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_PASSWORD, data)
            .apply()
    }

    fun getUserPassword(): String? =
        storage.getString(PrefsKeys.KEY_USER_PASSWORD, null)

    fun putUserCountry(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_COUNTRY, data)
            .apply()
    }

    fun getUserCountry(): String? = storage.getString(PrefsKeys.KEY_USER_COUNTRY, null)

    fun putUserCity(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_CITY, data)
            .apply()
    }

    fun getUserCity(): String? = storage.getString(PrefsKeys.KEY_USER_CITY, null)

    fun putUserGender(data: String) {
        storage.edit()
            .putString(PrefsKeys.KEY_USER_GENDER, data)
            .apply()
    }

    fun getUserGender(): String? = storage.getString(PrefsKeys.KEY_USER_GENDER, null)

    fun putUserAge(data: Int) {
        storage.edit()
            .putInt(PrefsKeys.KEY_USER_AGE, data)
            .apply()
    }

    fun getUserAge(): Int = storage.getInt(PrefsKeys.KEY_USER_AGE, 0)

    fun putUserModel(userModel: UserModel) {
        println("LOG: put user model = " + userModel)
        putUserId(userModel.userId)
        putUserFirstName(userModel.firstName)
        putUserSecondName(userModel.lastName)
        putUserEmail(userModel.email)
        putUserPassword(userModel.password)
        putUserAge(userModel.age)
        putUserCountry(userModel.country)
        putUserCity(userModel.city)
        putUserGender(userModel.gender)
    }

    fun putUserWithTokenModel(userModel: UserWithTokenModel) {
        println("LOG put user with token: "+ userModel)
        putUserId(userModel.userId)
        putUserFirstName(userModel.firstName)
        putUserSecondName(userModel.lastName)
        putUserEmail(userModel.email)
        putUserPassword(userModel.password)
        putUserAge(userModel.age)
        putUserCountry(userModel.country)
        putUserCity(userModel.city)
        putUserGender(userModel.gender)
        putAuthToken(userModel.token)
    }

    fun removeAuthToken() {
        storage.edit()
            .putString(PrefsKeys.KEY_TOKEN, null)
            .apply()
    }

    fun removeUser() {
        putUserId(0)
        putUserFirstName("")
        putUserSecondName("")
        putUserEmail("")
        putUserPassword("")
        putUserAge(0)
        putUserCountry("")
        putUserCity("")
        putUserGender("")
    }
}