package io.github.koblizekxd.torch.tasks

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.koblizekxd.torch.TorchPlugin
import io.github.koblizekxd.torch.util.Download
import org.apache.commons.lang3.SystemUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class DownloadMinecraft : DefaultTask() {
    private lateinit var json: JsonObject

    init {
        group = "torch"
        description = "Downloads main minecraft jar and json file"
    }

    @TaskAction
    fun downloadJsonSource() {
        val url: String

        if (TorchPlugin.version == "1.19.2") {
            url = "https://piston-meta.mojang.com/v1/packages/ed548106acf3ac7e8205a6ee8fd2710facfa164f/1.19.2.json"
        } else throw RuntimeException("Unsupported version or null: ${TorchPlugin.version}")
        println("[Torch] Downloading Json source...")
        json = Gson().fromJson(Download(url, temporaryDir, "minecraft-json.json").file.readText(), JsonObject::class.java)
        println("[Torch] Finished downloading json source")
    }

    @TaskAction
    fun downloadSource() {
        println("[Torch] Downloading minecraft client jar...")
         val url = json.getAsJsonObject("downloads")
            .getAsJsonObject("client")
            .getAsJsonPrimitive("url")
            .asString
        Download(url, temporaryDir, "client.jar")
        println("[Torch] Finished downloading minecraft client jar")
    }
    @TaskAction
    fun downloadLibraries() {
        println("[Torch] Preparing to download libraries...")
        val libraries = json.getAsJsonObject("libraries")

        for (i in 0..libraries.size()) {
            val url = libraries.getAsJsonObject(i.toString()).getAsJsonObject("downloads")
                .getAsJsonObject("artifact")
                .getAsJsonPrimitive("url")
                .asString
            if (!canUse(libraries.getAsJsonObject(i.toString()))) continue
            println("[Torch] Downloading ${libraries.getAsJsonObject(i.toString()).getAsJsonPrimitive("name").asString}...")
            val file = Download(url, temporaryDir, libraries.getAsJsonObject(i.toString()).getAsJsonPrimitive("name").asString)
                .file

            project.dependencies.add("implementation", project.files(file))
            println("[Torch] Done")
        }
    }
    fun canUse(json: JsonObject): Boolean {
        val os = json.getAsJsonObject("rules") ?: return true
        val osname = os.getAsJsonObject("0").getAsJsonObject("os")
        .getAsJsonObject("name").asString
        return if (SystemUtils.IS_OS_WINDOWS && osname == "windows") {
            true
        } else if (SystemUtils.IS_OS_LINUX && osname == "linux") {
            true
        } else SystemUtils.IS_OS_MAC && osname == "osx"
    }
}