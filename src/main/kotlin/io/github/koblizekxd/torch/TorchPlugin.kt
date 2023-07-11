package io.github.koblizekxd.torch

import io.github.koblizekxd.torch.minecraft.MinecraftProject
import io.github.koblizekxd.torch.tasks.DownloadMappings
import io.github.koblizekxd.torch.tasks.DownloadMinecraft
import io.github.koblizekxd.torch.tasks.DownloadMinecraftJson
import io.github.koblizekxd.torch.tasks.GradleDownloadMinecraft
import org.gradle.api.Plugin
import org.gradle.api.Project

class TorchPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        downloadMinecraftJson = project.tasks.create("downloadMinecraftJson", DownloadMinecraftJson::class.java)
        downloadMinecraft = project.tasks.create("downloadMinecraft", DownloadMinecraft::class.java)
        gradleDownloadMinecraft = project.tasks.create("gradleDownloadMinecraft", GradleDownloadMinecraft::class.java)
    }

    companion object {
        lateinit var downloadMinecraft: DownloadMinecraft
        lateinit var gradleDownloadMinecraft: GradleDownloadMinecraft
        lateinit var downloadMinecraftJson: DownloadMinecraftJson
        lateinit var mappings: String
        lateinit var version: String
    }
}
fun minecraft(proj: MinecraftProject.() -> Unit) {
    val mc = MinecraftProject()
    proj(mc)
    // Stuff is stored in the mc variable
    TorchPlugin.mappings = mc.mappings
    TorchPlugin.version = mc.version
}