package io.github.koblizekxd.torch.util

import io.github.koblizekxd.torch.TorchPlugin
import java.io.File
import java.net.URL

class Download(url: String, temp: File, name: String) {
    internal val file: File

    init {
        val uri = URL(url)
        file = File(temp, name)
        uri.openStream().use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}