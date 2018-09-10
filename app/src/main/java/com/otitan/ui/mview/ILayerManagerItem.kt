package com.otitan.ui.mview

import java.io.File

interface ILayerManagerItem {
    fun addLayer(file: File, checked: Boolean)

    fun setExtent(file: File)
}