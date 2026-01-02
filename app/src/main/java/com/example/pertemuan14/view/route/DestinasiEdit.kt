package com.example.pertemuan14.view.route

import com.example.pertemuan14.R
import com.example.pertemuan14.view.route.DestinasiNavigasi

object DestinasiEdit: DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}