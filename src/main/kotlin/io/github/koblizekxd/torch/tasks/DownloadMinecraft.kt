package io.github.koblizekxd.torch.tasks

import io.github.koblizekxd.torch.TorchPlugin
import io.github.koblizekxd.torch.util.Download
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.com.google.gson.Gson
import org.gradle.internal.impldep.com.google.gson.JsonObject

abstract class DownloadMinecraft : DefaultTask() {
    private lateinit var json: JsonObject

    @TaskAction
    fun downloadJsonSource() {
        val url: String

        if (TorchPlugin.version == "1.19.2") {
            url = "https://piston-meta.mojang.com/v1/packages/ed548106acf3ac7e8205a6ee8fd2710facfa164f/1.19.2.json"
        } else throw RuntimeException("Unsupported version or null: ${TorchPlugin.version}")
        json = Gson().fromJson(Download(url, temporaryDir, "minecraft-json.json").file.readText(), JsonObject::class.java)
    }

    @TaskAction
    fun downloadSource() {
         val url = json.getAsJsonObject("downloads")
            .getAsJsonObject("client")
            .getAsJsonPrimitive("url")
            .asString
        Download(url, temporaryDir, "client.jar")
    }
}