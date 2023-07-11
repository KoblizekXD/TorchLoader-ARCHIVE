package io.github.koblizekxd.torch.tasks

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.koblizekxd.torch.TorchPlugin
import io.github.koblizekxd.torch.util.Download
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class DownloadMinecraftJson : DefaultTask() {
    init {
        group = "torch"
    }
    @TaskAction
    fun downloadJson() {
        val url: String

        if (TorchPlugin.version == "1.19.2") {
            url = "https://piston-meta.mojang.com/v1/packages/ed548106acf3ac7e8205a6ee8fd2710facfa164f/1.19.2.json"
        } else throw RuntimeException("Unsupported version or null: ${TorchPlugin.version}")
        println("[Torch] Downloading Json source...")
        Download(url, temporaryDir, "minecraft-json.json")
        println("[Torch] Finished downloading json source")
    }
}