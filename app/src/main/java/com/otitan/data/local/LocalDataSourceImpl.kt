package com.otitan.data.local

class LocalDataSourceImpl() {

    private object Holder {
        val single = LocalDataSourceImpl()
    }

    companion object {
        val instances: LocalDataSourceImpl by lazy { Holder.single }
    }
}