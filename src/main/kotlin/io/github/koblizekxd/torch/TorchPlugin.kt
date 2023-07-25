package io.github.koblizekxd.torch

import io.github.koblizekxd.torch.minecraft.MinecraftProject
import io.github.koblizekxd.torch.tasks.DownloadMappings
import io.github.koblizekxd.torch.tasks.DownloadMinecraft
import io.github.koblizekxd.torch.tasks.DownloadMinecraftJson
import io.github.koblizekxd.torch.tasks.GradleDownloadMinecraft
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.plugins.JavaPlugin

class TorchPlugin : Plugin<Project> {

    class DependencyResolver(project: Project) : DependencyResolutionListener {
        private var project: Project

        init {
            this.project = project
        }

        override fun beforeResolve(dependencies: ResolvableDependencies) {
            val deps = project.configurations.getByName("implementation").dependencies
            val runtimeDeps = project.configurations.getByName("runtimeOnly").dependencies
            TorchPlugin.dependencies.forEach {
                if (it.value) {
                    runtimeDeps.add(project.dependencies.create(it))
                } else {
                    deps.add(project.dependencies.create(it))
                }
            }
            TorchPlugin.dependencies.clear()
            project.gradle.removeListener(this)
        }

        override fun afterResolve(dependencies: ResolvableDependencies) {
            println("[Torch] Dependencies resolved successfully!")
        }
    }

    override fun apply(project: Project) {
        downloadMinecraftJson = project.tasks.create("downloadMinecraftJson", DownloadMinecraftJson::class.java)
        downloadMinecraft = project.tasks.create("downloadMinecraft", DownloadMinecraft::class.java)
        gradleDownloadMinecraft = project.tasks.create("gradleDownloadMinecraft", GradleDownloadMinecraft::class.java)
        project.repositories.add(project.repositories.maven {
            it.url = project.uri("https://libraries.minecraft.net")
        })
        val mcImpl = project.configurations.create("mcImpl")
        val mcRunt = project.configurations.create("mcRunt")
        project.afterEvaluate {
            if (dependencies.isNotEmpty()) {
                dependencies.forEach {
                    println("Applying dependency: ${it.key}")
                    if (it.value) {
                        //project.dependencies.add("runtimeOnly", it.key)
                        mcRunt.dependencies.add(project.dependencies.create(it.key))
                    } else {
                        //project.dependencies.add("implementation", it.key)
                        mcImpl.dependencies.add(project.dependencies.create(it.key))
                    }
                }
                dependencies.clear()
                project.configurations.getByName("implementation").extendsFrom(mcImpl)
                project.configurations.getByName("runtimeOnly").extendsFrom(mcRunt)
            }
        }
    }

    companion object {
        var dependencies: HashMap<String, Boolean> = HashMap()
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