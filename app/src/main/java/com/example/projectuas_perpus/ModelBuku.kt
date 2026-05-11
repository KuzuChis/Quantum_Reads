package com.example.projectuas_perpus

class ModelBuku() {
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