package io.github.koblizekxd.torch.tasks

import io.github.koblizekxd.torch.TorchPlugin
import io.github.koblizekxd.torch.minecraft.MinecraftProject
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

abstract class DownloadMappings : DefaultTask() {
    init {
        group = "torch"
    }

    @TaskAction
    fun downloadMappings() {
        if (TorchPlugin.mappings == "yarn") {
            // https://maven.fabricmc.net/net/fabricmc/yarn/1.20.1+build.9/yarn-1.20.1+build.9-v2.jar

        } else if (TorchPlugin.mappings == "official") {

        } else {
            throw RuntimeException("Invalid mapping type!")
        }
    }
    fun getHighestBuildVersion(version: String): Int {
        for (i in 1..Int.MAX_VALUE) {
            if (!doesExist("https://maven.fabricmc.net/net/fabricmc/yarn/$version+build.$i")) {
                return i - 1
            }
        }
        return 1
    }
    private fun doesExist(url: String): Boolean {
        val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        return connection.responseCode != 404
    }
}