package com.otitan.model

import com.contrarywind.interfaces.IPickerViewData
import java.io.Serializable

class XZQHModel : Serializable, IPickerViewData {
    var Name: String = ""
    var Code: String = ""
    var ChildAdministrativeDivisions = ArrayList<CityModel>()
    override fun getPickerViewText(): String {
        return Name
    }

    class CityModel : Serializable, IPickerViewData {
        var Name: String = " "
        var Code: String = " "
        var ChildAdministrativeDivisions = ArrayList<CountyModel>()
        override fun getPickerViewText(): String {
            return Name
        }
    }

    class CountyModel : Serializable, IPickerViewData {
        var Name: String = " "
        var Code: String = " "
        override fun getPickerViewText(): String {
            return Name
        }
    }
}