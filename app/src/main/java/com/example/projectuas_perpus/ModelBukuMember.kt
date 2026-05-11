package com.example.projectuas_perpus

class ModelBukuMember() {
    var username: String = ""
        get() = field
        set(value) { field = value }

    var id: Int = 0
        get() = field
        set(value) { field = value }

    var imageBuku: ByteArray? = null
        get() = field
        set(value) { field = value }

    var judul: String = ""
        get() = field
        set(value) { field = value }

    var penulis: String = ""
        get() = field
        set(value) { field = value }

    var jenis: String = ""
        get() = field
        set(value) { field = value }

    var genre: String = ""
        get() = field
        set(value) { field = value }
}